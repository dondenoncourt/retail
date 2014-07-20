package com.kettler.service.retail

import groovy.xml.StreamingMarkupBuilder

import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.httpclient.methods.StringRequestEntity
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.HttpClient

import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.commons.ApplicationHolder

import com.kettler.upsTracking.TrackResponse
import com.kettler.upsTracking.Activity

import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.varsity.share.ShippingManifest
import com.kettler.domain.varsity.share.Packer

import com.kettler.controller.retail.AddressesCommand
import com.kettler.controller.retail.PayShipCommand

import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import groovy.util.XmlSlurper
import groovy.util.slurpersupport.GPathResult
import grails.util.Environment

/**
 * Service for interacting with UPS Tracking.
 */
class UpsService {
    static transactional = true
    HttpClient httpclient = new HttpClient()
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
    SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy")

    /**
     * Create a XML formatted string for the tracking request
     * RequestOption = 0 means only last activity returned, 1 means all activity
     * @param trackingNumber Tracking Number
     * @param orderNo Order Number
     * @return the request
     */
    public String getRequestXml(trackingNumber, orderNo) {
        def licNumber = CH.config.upsAccessCode
        def user = CH.config.upsUserId
        def password = CH.config.upsPassword
        def mb = new StreamingMarkupBuilder()
        mb.encoding = "UTF-8"
        def doc = mb.bind  {
            mkp.xmlDeclaration()
            AccessRequest('xml:lang':'en-US') {
                AccessLicenseNumber(licNumber)
                UserId(user)
                Password(password)
            }
            TrackRequest {
                Request {
                    TransactionReference {
                        CustomerContext(orderNo)
                    }
                    RequestAction('Track')
                }
                // 0 = Last Activity; 1 = All Activity
                RequestOption('0')
                TrackingNumber(trackingNumber)
            }
        }
        doc.toString()
    }

    /**
     * Send tracking request xml to UPS and return the XML response
     * @param data XML formatted request
     * @return XML response
     * @throws Exception Network error or non-successful response from UPS HTTP server
     */
    public String getTrackingInfo(data) throws Exception {
        return upsApi(CH.config.upsTrackUrl,data)
    }

    /**
     * Parses the UPS response XML and returns a populated TrackResponse
     * @param xml UPS response as XML
     * @return Populated TrackResponse
     */
    public TrackResponse getTrackResponse(xml) {
    	def response = new XmlSlurper().parseText(xml)
        def tr = new TrackResponse()
        try {
            tr.customerContext = response.Response.TransactionReference.CustomerContext.toString().trim()
            def statusCode = response.Response.ResponseStatusCode.toString().trim()
            tr.success = statusCode.equals("1")
            if (!tr.success) {
                tr.errorDescription = response.Response.Error.ErrorDescription.toString().trim()
            } else {
                def addr1 = response.Shipment.ShipTo.Address.AddressLine1.toString().trim()
                tr.addressLine = addr1
                def addr2 = response.Shipment.ShipTo.Address.AddressLine2.toString().trim()
                if (addr2.length()>0) {
                    tr.addressLine2 = addr2
                }
                def city = response.Shipment.ShipTo.Address.City.toString().trim()
                tr.city = city
                def state = response.Shipment.ShipTo.Address.StateProvinceCode.toString().trim()
                tr.state = state
                def zip = response.Shipment.ShipTo.Address.PostalCode.toString().trim()
                tr.zip = zip
                tr.countryCode = response.Shipment.ShipTo.Address.CountryCode.toString().trim()
                def service = response.Shipment.Service.Description.toString().trim()
                tr.service = service
                def tempDate = response.Shipment.PickupDate.toString().trim()
                if (tempDate.length()>0) {
                    Date d = sdf.parse(tempDate)
                    tr.pickUpDate = sdf2.format(d)
                }
                def elem = response.Shipment.Package
                tr.freight = (elem.size() == 0)

                if (!tr.freight) {
                    tr.trackingNumber = response.Shipment.Package.TrackingNumber.toString().trim()
                    addActivities(tr, response)
                }
            }
        } catch (Exception e) {
            println "** Error in getTrackResponse: ${e.message}"
            tr.success = false
            tr.errorDescription = "Internal error: ${e.message}"
        }
        tr
    }

    private void addActivities (tr, response) {
        def activities = response.Shipment.Package.Activity

        activities.each { act ->
            Activity activity = new Activity()
            activity.addressLine = act.ActivityLocation.Address.AddressLine1.toString().trim()
            activity.addressLine2 = act.ActivityLocation.Address.AddressLine2.toString().trim()
            activity.city = act.ActivityLocation.Address.City.toString().trim()
            activity.state = act.ActivityLocation.Address.StateProvinceCode.toString().trim()
            activity.zip = act.ActivityLocation.Address.PostalCode.toString().trim()
            activity.countryCode = act.ActivityLocation.Address.CountryCode.toString().trim()
            activity.code = act.ActivityLocation.Code.toString().trim()
            activity.description = act.ActivityLocation.Description.toString().trim()
            activity.signedByName = act.ActivityLocation.SignedForByName.toString().trim()
            activity.statusTypeCode = act.Status.StatusType.Code.toString().trim()
            activity.statusTypeDescription = act.Status.StatusType.Description.toString().trim()
            activity.statusCode = act.Status.StatusCode.Code.toString().trim()
            def tempDate = act.Date.toString().trim()
            Date d = sdf.parse(tempDate)
            activity.activityDate = sdf2.format(d)
            activity.activityTime = act.Time.toString().trim()
            tr.activities << activity
        }
    }
    /**
     * Send ShipConfirm request xml to UPS and return the XML response
     */
    public String getShipConfirm(ShippingManifest mani, List<Packer> packList, Long raId) throws Exception {
    	String data = getAccessRequestXml()
    	data += getShipConfirmRequestXml(mani, packList, raId)
    	//println  "getShipConfirm request xml:"+data   	
		if (Environment.current != Environment.PRODUCTION) {
			new File("target/ShipConfirmRequest${raId}.xml").write(data)
    		def xml = upsApi(CH.config.upsShipConfirmUrl, data)
			new File("target/ShipConfirmResponse${raId}.xml").write(xml)
			return xml
		} else {
    		return upsApi(CH.config.upsShipConfirmUrl, data)
		}
    }
    /**
     * Send ShipConfirm request xml to UPS and return the XML response
     */
    public String getShipConfirm(Cart cart, AddressesCommand addr, PayShipCommand payShip, boolean checkAddr) throws Exception {
    	String data = getAccessRequestXml()
    	data += getShipConfirmRequestXml(cart, addr, payShip, checkAddr)
		println "xml = "+data
		def url = 'https://onlinetools.ups.com/ups.app/xml/ShipConfirm'
		println "url = "+url
    	//return upsApi(CH.config.upsShipConfirmUrl, data)
		return upsApi(url, data)
    }
    /**
     * Send ShipAccept request xml to UPS and return the XML response
     */
    public String getShipAccept(String digest) throws Exception {
    	String data = getAccessRequestXml()
    	data += getShipAcceptRequestXml(digest)  
    	//println  "getShipAccept request xml:"+data   	
		if (Environment.current != Environment.PRODUCTION) {
			new File("target/ShipAcceptRequest"+(new SimpleDateFormat("ddMMyy-hhmmss.SSS").format( new Date() ) )+".xml").write(data)
			def xml = upsApi(CH.config.upsShipAcceptUrl, data)
			new File("target/ShipAcceptResponse"+(new SimpleDateFormat("ddMMyy-hhmmss.SSS").format( new Date() ) )+".xml").write(xml)
			return xml
		} else {
    		return upsApi(CH.config.upsShipAcceptUrl, data)
		}
    }
    public GPathResult getShipmentConfirmResponse(ShippingManifest mani, List packList, Long raId) 
	throws Exception {
    	def xml = getShipConfirm(mani, packList, raId)
    	//println  "getShipmentConfirmResponse xml:"+xml   	
		if (Environment.current != Environment.PRODUCTION) {new File("target/ShipConfirmResponse${raId}.xml").write(xml)}
    	def shipmentConfirmResponse = new XmlSlurper().parseText(xml)
    	if ('Hard' == shipmentConfirmResponse.Response.Error.ErrorSeverity.text().trim()) {
    		throw new Exception(shipmentConfirmResponse.Response.Error.ErrorDescription.text().trim())
    	}
    	return shipmentConfirmResponse
    }
    public GPathResult getShipmentConfirmResponse(Cart cart, AddressesCommand addr, PayShipCommand payShip, boolean checkAddr) 
	throws Exception {
    	def xml = getShipConfirm(cart, addr, payShip, checkAddr)
		//println  "getShipmentConfirmResponse checkAddr $checkAddr xml:"+xml   	
    	def shipmentConfirmResponse = new XmlSlurper().parseText(xml)
    	if ('Hard' == shipmentConfirmResponse.Response.Error.ErrorSeverity.text().trim()) {
    		throw new Exception(shipmentConfirmResponse.Response.Error.ErrorDescription.text().trim())
    	}
    	return shipmentConfirmResponse
    }
    public String[] buildLabelHtmlImages(GPathResult shipmentConfirmResponse, String htmlGifWritePath) 
    		throws Exception {
    	def xml = getShipAccept(shipmentConfirmResponse.ShipmentDigest.text().trim())
    	//println  "getShipmentAcceptResponse xml:"+xml   	
    	GPathResult shipmentAcceptResponse = new XmlSlurper().parseText(xml)
    	if ('Hard' == shipmentAcceptResponse.Response.Error.ErrorSeverity.text().trim()) {
    		throw new Exception (shipmentAcceptResponse.Response.Error.ErrorDescription.text().trim())
    	}
    	def trackingNos = []
       	shipmentAcceptResponse.ShipmentResults.PackageResults.eachWithIndex {packageResult, i ->
   	    	trackingNos[i] = packageResult.TrackingNumber.text().trim()
   	    	log.debug "creating ${htmlGifWritePath}/label${trackingNos[i]}.gif"
   	    	FileOutputStream fos = new FileOutputStream(new File("${htmlGifWritePath}/label${trackingNos[i]}.gif" ))
   	    	fos.write(packageResult.LabelImage.GraphicImage.toString().decodeBase64())
   	    	fos.flush()
   	    	fos.close()
       	}
       	trackingNos
    }
    public BigDecimal getShippingCost(GPathResult shipmentConfirmResponse) {
    	def moneyStr = shipmentConfirmResponse.ShipmentCharges.TransportationCharges.MonetaryValue
    	try {
    		return moneyStr.toBigDecimal()
    	} catch (e) {
    		log.error "error converting $moneyStr to BigDecimal returning 0g"
    		return 0g
    	}
    }
    
    private upsApi(def api, def data) {
	    String result = null
	    PostMethod httppost = new PostMethod (api)
		println("has to do with api")
		println "api = "+api
	    byte[] responseBody
	    try {
	        httppost.setRequestEntity(new StringRequestEntity(data,"text/plain", "UTF-8"))
	        int statusCode = httpclient.executeMethod(httppost)
	        responseBody = httppost.getResponseBody()
	        if (statusCode != HttpStatus.SC_OK) {
	            throw new Exception("UPS Service returned error code (${statusCode})")
	        }
	        def s = new String(responseBody)
	        def stringWriter = new StringWriter()
	        def node = new XmlParser().parseText(s);
	        new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
	
	        result = stringWriter.toString()
	    } catch (Exception e) {
	        log.error "error in getShipConfirm: ${e.message}"
	        throw e
	    } finally {
	        httppost.releaseConnection()
	    }
	    result
	}
    
    /**
     * Create a XML formatted string for the ship confirm request
     */
    public String getShipConfirmRequestXml(ShippingManifest mani, List<Packer> packList, Long raId) {
        def mb = new StreamingMarkupBuilder()
        mb.encoding = "UTF-8"
        def doc = mb.bind  {
        	mkp.xmlDeclaration()
        	ShipmentConfirmRequest("xml:lang":"en-US") {
        		Request {
	        		TransactionReference {
		        		CustomerContext ("Customer Comment")
		        		XpciVersion ()
	        		}
	        		RequestAction ("ShipConfirm")
	        		RequestOption ("validate")
        		}
	       		LabelSpecification {
	        		LabelPrintMethod {
		        		Code ("GIF")
		        		Description ("gif file")
	        		}
	        		HTTPUserAgent ("Mozilla/4.5")
	        		LabelImageFormat {
		        		Code ("GIF")
		       			Description ("gif")
	        		}
	       		}
	       		Shipment {
	        		RateInformation {
	        			NegotiatedRatesIndicator { }
	        		} 
	        		Description ()
	        		Shipper {
		        		Name ('Kettler International, Inc.')
		        		PhoneNumber ("97225377171")
		        		ShipperNumber ("230334")
		        		TaxIdentificationNumber ('')
		        		Address {
			        		AddressLine1 ("1355 London Bridge Road")
			        		City ("Virginia Beach")
			        		StateProvinceCode ("VA")
			        		PostalCode ("23453")
			        		CountryCode ("US")
		        		}
	        		}
	        		ShipTo {
		        		CompanyName ("Kettler International, Inc")
		        		AttentionName ("Web Return")
		        		PhoneNumber ("97225377171")
		        		Address {
			        		AddressLine1 ("1355 London Bridge Road")
			        		City ("Virginia Beach")
			        		StateProvinceCode ("VA")
			        		PostalCode ("23453")
			        		CountryCode ("US")
		        		}
	        		}
	        		ShipFrom {
		        		CompanyName (mani.custName)
		        		AttentionName (mani.custName)
		        		PhoneNumber (mani.recipientTelephoneNo)
		        		TaxIdentificationNumber ('')
		        		Address {
			        		AddressLine1 (mani.custAddr1)
			        		City (mani.shipToCity)
			        		StateProvinceCode (mani.shipToStateCode)
			        		PostalCode (mani.shipToPostalCd)
			        		CountryCode (mani.shipToCountry)
		        		}
	        		}
	        		PaymentInformation {
		        		Prepaid {
			        		BillShipper {
			        			AccountNumber ("230334")
			        		}
		        		}
	        		}
	        		Service {
		        		Code ("03") //Code ("02")
		        		Description ("Ground") //Description ("2nd Day Air")
	        		}
	        		// get total weight per box
	        		def boxes = [:]
		            packList.findAll{it.returnIt}.each {
						if (!boxes[it.containerId]) {
							boxes.put(it.containerId, it.actualWeight)
						} else {
							boxes[it.containerId] += it.actualWeight
						}
					}
					boxes.eachWithIndex{ containerId, boxWeight, boxIndex ->
		        		Package {
			        		PackagingType {
				        		Code ("02")  
				        		Description ("KETTLER")  
			        		}
			        		Description ('Package Description')
			        		def itemInBoxNo = 0
			        		ReferenceNumber {
				        		Code ('00')
				        		def vlu = 'Items: '
				        		packList.findAll{it.returnIt && it.containerId == containerId}.each {item ->
				        			vlu += item.itemNo + ' '
				        		}
				        		Value (vlu)
			        		}
			        		ReferenceNumber {
				        		Code ('01')
				        		Value ("RA No: ${raId}:${boxIndex+1}")
			        		}
			        		PackageWeight {
				        		UnitOfMeasurement ()
				        		Weight (boxWeight)
			        		}
			        		AdditionalHandling ("0")
							if (Environment.current != Environment.PRODUCTION) { // only required for certification
								PackageServiceOptions {
									InsuredValue {
										//CurrencyCode('USD')
										MonetaryValue('2000.00')
									}
								}
							} 
		    			}
	        		}
	       		}
        	}
        }
        doc.toString()
    }
    /**
     * Create a XML formatted string for the ship confirm request
     */
    public String getShipConfirmRequestXml(Cart cart, AddressesCommand addr, PayShipCommand payShip, boolean checkAddr) {
        def mb = new StreamingMarkupBuilder()
        mb.encoding = "UTF-8"
        def doc = mb.bind  {
        	mkp.xmlDeclaration()
        	ShipmentConfirmRequest("xml:lang":"en-US") {
        		Request {
	        		TransactionReference {
		        		CustomerContext ("Customer Comment")
		        		XpciVersion ()
	        		}
	        		RequestAction ("ShipConfirm")
	        		RequestOption (checkAddr?'validate':'nonvalidate')
        		}
	       		LabelSpecification {
	        		LabelPrintMethod {
		        		Code ("GIF")
		        		Description ("gif file")
	        		}
	        		HTTPUserAgent ("Mozilla/4.5")
	        		LabelImageFormat {
		        		Code ("GIF")
		       			Description ("gif")
	        		}
	       		}
	       		Shipment {
	        		RateInformation {
	        			NegotiatedRatesIndicator { }
	        		} 
	        		Description ()
	        		Shipper {
		        		Name ('Kettler International, Inc.')
		        		PhoneNumber ("97225377171")
		        		ShipperNumber ("230334")
		        		TaxIdentificationNumber ('')
		        		Address {
			        		AddressLine1 ("1355 London Bridge Road")
			        		City ("Virginia Beach")
			        		StateProvinceCode ("VA")
			        		PostalCode ("23453")
			        		CountryCode ("US")
		        		}
	        		}
	        		ShipTo {
		        		CompanyName (addr.shipToSameAsBillTo?addr.billingName:addr.shippingName)
		        		AttentionName (addr.shipToSameAsBillTo?addr.billingName:addr.shippingName)
		        		PhoneNumber (addr.phone)
		        		TaxIdentificationNumber ('')
		        		Address {
			        		AddressLine1 (addr.shipToSameAsBillTo?addr.billingName:addr.shippingName)
			        		City (addr.shipToSameAsBillTo?addr.billingCity:addr.shippingCity)
			        		StateProvinceCode (addr.shipToSameAsBillTo?addr.billingState:addr.shippingState)
			        		PostalCode (addr.shipToSameAsBillTo?addr.billingZip:addr.shippingZip)
			        		CountryCode ('US')
		        		}
	        		}
	        		ShipFrom {
		        		CompanyName ("Kettler International, Inc")
		        		AttentionName ("Bill Moii")
		        		PhoneNumber ("97225377171")
		        		Address {
			        		AddressLine1 ("1355 London Bridge Road")
			        		City ("Virginia Beach")
			        		StateProvinceCode ("VA")
			        		PostalCode ("23453")
			        		CountryCode ("US")
		        		}
	        		}
	        		PaymentInformation {
		        		Prepaid {
			        		BillShipper {
			        			AccountNumber ("230334")
			        		}
		        		}
	        		}
	        		Service {
		        		Code (payShip.upsServiceCode)  
		        		Description (Cart.SHIP_METHODS_UPS[payShip.upsServiceCode]) 
	        		}

		            cart.items.each{ item ->
		        		Package {
			        		PackagingType {
				        		Code ("02") // could use 02
				        		Description ("KETTLER") // and put division desc here
			        		}
			        		Description ('Package Description')
			        		PackageWeight {
				        		UnitOfMeasurement ()
				        		Weight (weightAlgorithm(item.item, item.qty))
			        		}
			        		AdditionalHandling ("0")
		    			}
	        		}
	       		}
        	}
        }
        doc.toString()
    }
    
    public String getAccessRequestXml() {
        def licNumber = CH.config.upsAccessCode
        def user = CH.config.upsUserId
        def password = CH.config.upsPassword
        def mb = new StreamingMarkupBuilder()
        mb.encoding = "UTF-8"
        def doc = mb.bind  {
        	mkp.xmlDeclaration()
	        AccessRequest("xml:lang":"en-US") {
	            AccessLicenseNumber(licNumber)
	            UserId(user)
	            Password(password)
	        }
        }
        doc.toString()
    }
    /**
     * Create a XML formatted string for the ship accept request
     */
    public String getShipAcceptRequestXml(String digest) {
        def mb = new StreamingMarkupBuilder()
        mb.encoding = "UTF-8"
        def doc = mb.bind  {
        	mkp.xmlDeclaration()
		    ShipmentAcceptRequest {
			    Request {
			        TransactionReference {
			            CustomerContext('Customer Comment') 
			        } 
			        RequestAction('ShipAccept')
			        RequestOption('1')
			    }
			    ShipmentDigest(digest)
        	}
        }
        doc.toString()
    }
    /**
     * Create a XML formatted string for the void shipment request
     */
    public String getVoidShipmentRequestXml(String shipIdNo) {
        def mb = new StreamingMarkupBuilder()
        mb.encoding = "UTF-8"
        def doc = mb.bind  {
        	mkp.xmlDeclaration()
		    VoidShipmentRequest {
			    Request {
			        TransactionReference {
			            CustomerContext('Customer Comment') 
						XpciVersion('1.0')
			        } 
			        RequestOption('1')
			        RequestAction('1')
			    }
			    ShipmentIdentificationNumber(shipIdNo)
        	}
        }
        doc.toString()
    }
	/**
	* Send VoidShipment request xml to UPS and return the XML response
	*/
   public Boolean voidShipment(String shipIdNo) throws Exception {
	   String data = getAccessRequestXml()
	   data += getVoidShipmentRequestXml(shipIdNo)
	   if (Environment.current != Environment.PRODUCTION) {new File("target/VoidShipmentRequest${shipIdNo}.xml").write(data)}
	   def xml = upsApi(CH.config.upsVoidShipmentUrl, data)
	   if (Environment.current != Environment.PRODUCTION) {new File("target/VoidShipmentResponse${shipIdNo}.xml").write(xml)}
	   def voidShipmentResponse = new XmlSlurper().parseText(xml)
       if ('Hard' == voidShipmentResponse.Response.Error.ErrorSeverity.text().trim()) {
           throw new Exception(voidShipmentResponse.Response.Error.ErrorDescription.text().trim())
	   }
   	   return true
   }
   
	public BigDecimal getShippingCost(Cart cart, AddressesCommand addr, PayShipCommand payShip, boolean checkAddr) {
		cart.shippingCost = 0g
		def shipToState = addr.shipToSameAsBillTo?addr.billingState:addr.shippingState
    	if (cart.upsServiceCode == Cart.LTL                               || 
    		(cart.totalWithoutGiftCard(shipToState) >= 40g && !['HI','AK'].find{it == shipToState} && 
			 payShip.upsServiceCode == Cart.GROUND)
			) { 
    		return 0g
    	}
			
 		GPathResult shipmentConfirmResponse
		try { 
			shipmentConfirmResponse = getShipmentConfirmResponse(cart, addr, payShip, checkAddr)
		} catch (Exception e) {
			def error = e.message 
			if (error == 'Address Validation Error on ShipTo address') {
				error = 'UPS cannot validate your address'
			}
			throw new Exception(error)
		}
		cart.shippingCost = getShippingCost(shipmentConfirmResponse)
		log.debug "shipping cost before adding ${cart.upsServiceCode} surcharges: $cart.shippingCost"
		if (cart.upsServiceCode == Cart.GROUND) {
			cart.shippingCost = ((cart.shippingCost + 2.45g) * 1.095g)
		} else {
			cart.shippingCost = ((cart.shippingCost + 2.75g) * 1.145g)
		}
		cart.shippingCost = cart.shippingCost.setScale(2, BigDecimal.ROUND_HALF_UP)
		log.debug "shipping cost after adding ${cart.upsServiceCode} surcharges: $cart.shippingCost"
		cart.shippingCost
   	}

	public PayShipCommand populatePayShipCommand(Consumer consumer ) {
		PayShipCommand payShip = new PayShipCommand()
		ConsumerBillTo billTo = consumer.billTos?consumer.billTos.toArray()[0]:new ConsumerBillTo()
		payShip.creditCardType = billTo.cardType
		payShip.creditCard = billTo.cardNo
		payShip.ccid = billTo.ccid
		payShip.year = billTo.expYear
		payShip.month = billTo.expMonth
		payShip
	}
	public AddressesCommand populateAddressesCommand(Consumer consumer ) {
		AddressesCommand addr = new AddressesCommand()
		addr.type = AddressesCommand.RETURN_CUSTOMER
		addr.phone = consumer.phone
		addr.email = consumer.email
		addr.billingName = consumer.name

		ConsumerBillTo billTo = consumer.billTos?consumer.billTos.toArray()[0]:new ConsumerBillTo()
		addr.billingName  = billTo.name
		addr.billingAddress1 = billTo.addr1
		addr.billingAddress2 = billTo.addr2
		addr.billingCity = billTo.city
		addr.billingState = billTo.state
		addr.billingZip = billTo.zipCode

		ConsumerShipTo shipTo = consumer.shipTos?consumer.shipTos.toArray()[0]:null 
		if (shipTo) {
			addr.shipToSameAsBillTo = false
			addr.shippingName = shipTo.name
			addr.shippingAddress1 = shipTo.addr1
			addr.shippingAddress2 = shipTo.addr2
			addr.shippingCity = shipTo.city
			addr.shippingState = shipTo.state
			addr.shippingZip = shipTo.zipCode
		}  
		addr
	}
   private int weightAlgorithm(def item, int qty) {
	   println ("item  "+item.dump())
		int cubicInch =  item.dimLength * item.dimWidth * item.dimHeight
		int dimWeight = (cubicInch / 166.0g) + 0.5
		int oversized = item.dimLength + (item.dimWidth*2) + (item.dimHeight*2)
		
		if (oversized  > 165) {
		   log.warn "$oversized oversize should ship freight\n"
		   item.unitWeight * qty
		} else if (dimWeight > 150 || item.unitWeight > 150) {
		   log.warn "dimWeight $dimWeight > 150 or unitWeight $item.unitWeight > 150 so should ship freight but use unitWeight"
		   item.unitWeight * qty
		} else if (item.unitWeight > dimWeight) {
		   log.debug "unitWeight $item.unitWeight > dimWeight $dimWeight use unitWeight"
		   item.unitWeight * qty
		} else if (cubicInch  > 5184) {
		   log.debug "cubicInch $cubicInch > 5184 so use dimWeight of $dimWeight instead of unitWeight of $item.unitWeight"
		   dimWeight * qty
		} else {
		    log.debug "weight algorithm default use unitWeight"
		    item.unitWeight * qty
		}
   }

}
