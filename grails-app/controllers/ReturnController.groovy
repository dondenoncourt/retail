
import com.kettler.domain.orderentry.share.Carrier
import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.CartItem
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.orderentry.share.InvoicedOrderHeader
import com.kettler.domain.orderentry.share.InvoicedOrderDetailItem as InvoicedItem
import com.kettler.domain.orderentry.share.ReasonCode

import com.kettler.domain.item.share.ItemMasterExt

import com.kettler.domain.actrcv.share.Customer
import com.kettler.domain.actrcv.share.Return
import com.kettler.domain.actrcv.share.ReturnCondition
import com.kettler.domain.actrcv.share.ReturnDisposition
import com.kettler.domain.actrcv.share.ReturnItem
import com.kettler.domain.actrcv.share.ReturnItemDetail
import com.kettler.domain.actrcv.share.ReturnNote
import com.kettler.domain.actrcv.share.ReturnReason
import com.kettler.domain.actrcv.share.ReturnStatus
import com.kettler.domain.actrcv.share.ReturnFreightDesc
import com.kettler.domain.actrcv.share.ReturnFreightDenial


import com.kettler.domain.varsity.share.ShippingManifest
import com.kettler.domain.varsity.share.Packer

import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.util.Environment
import groovy.util.slurpersupport.GPathResult

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ReturnController {
   	def upsService 
   	static def ZERO_PAD2 = new java.text.DecimalFormat('00')
   	
    def beforeInterceptor = {
    	log.debug "action: $actionName params: $params flash: $flash"
    }
   	
    def orderReturn = {   			
        def cart = Cart.get(params.cartId as int)
        assert cart
        session.cart = cart
       	cart.discard()
       	// check expiration
        Calendar now = new GregorianCalendar(TimeZone.getTimeZone("EST"),  Locale.US)
        now.setTime(new Date())
        int nowYearMonth = now.get(Calendar.YEAR) * 100
        nowYearMonth += now.get(Calendar.MONTH)+1
        int expYearMonth = cart.consumerBillTo.expYear * 100
        expYearMonth += cart.consumerBillTo.expMonth
        if (now.get(Calendar.DATE) > 10) {
        	expYearMonth--
        }
       	if (expYearMonth <= nowYearMonth) {
       		flash.message = message(code:'localized.return.cc.expired') //'Your credit card will have been expired when credit would be applied, please reset your Credit Card information'
       		redirect controller:'consumer', action:'show'
       		return
       	}
    	session.orderHeader = InvoicedOrderHeader.findByCompCodeAndOrderNo('01', cart.orderNo)
        session.manifest = ShippingManifest.findByOrderShipNo(session.orderHeader.orderNo+'-'+ZERO_PAD2.format(session.orderHeader.shipNo))
		if (!session.manifest) {
			log.error "Shipping Manifest not found for order ship no $session.orderHeader.orderNo - ${ZERO_PAD2.format(session.orderHeader.shipNo)}"
			flash.message = message(code:'localized.shipping.manifest.not.found') //"Sorry, we could not find your shipping manifest, please call KETTLER at 800.205.9831 for a manual return (Monday thru Friday from 9:00 a.m. 4:30 p.m. EST)"
       		redirect controller:'consumer', action:'show'
       		return
		}
     
       	if (cart.upsServiceCode == Cart.LTL) {
            render view:'listOrder', model:[orderHeader:session.orderHeader, 
                                            manifest:session.manifest, 
                                            cart:session.cart, params:params]
  		    return
       	} 

        
		session.packList = Packer.findAllByOrderShipNo(session.orderHeader.orderNo+'-'+ZERO_PAD2.format(session.orderHeader.shipNo))
		assert session.packList
		if (!session.packList) {
			log.error "Shipping Pack List not found for order ship no $session.orderHeader.orderNo - ${ZERO_PAD2.format(session.orderHeader.shipNo)}"
			flash.message = message(code:'localized.shipping.manifest.not.found') //"Sorry, we could not find your shipping manifest, please call KETTLER at 800.205.9831 for a manual return (Monday thru Friday from 9:00 a.m. 4:30 p.m. EST)"
       		redirect controller:'consumer', action:'show'
       		return
		}
		session.packList.sort {it.containerId}

		render view:'listOrder', 
		  model:[orderHeader:session.orderHeader, 
		         orderItems:InvoicedItem.findAllWhere(compCode:'01', orderNo:cart.orderNo, lineType:'I'), 
		         manifest:session.manifest, packList:session.packList, 
		         cart:session.cart, 
		         shippingCost:getShippingCost(session.manifest, session.packList, 0),
		         edit:true,
		         params:params]
    }
   	private BigDecimal getShippingCost(ShippingManifest manifest, List packList, int raId ) {
   		GPathResult shipmentConfirmResponse = 
   					upsService.getShipmentConfirmResponse(manifest, session.packList.findAll{it.returnIt}, raId)
	    upsService.getShippingCost(shipmentConfirmResponse)	 										 
   	}
   	def continueReturn = {
    	render view:'continueReturn', 
		  model:[orderHeader:session.orderHeader, 
		         manifest:session.manifest, packList:session.packList, 
		         cart:session.cart, 
		         shippingCost:getShippingCost(session.manifest, session.packList, 0),
		         edit:false,
		         params:params]
   	}
   	def reduceQty = {
   		if (!params.quantity?.toInteger()) {
   			redirect action:'removeItem', params:params
   			return
   		}
   		def packItem = session.packList.find{packItem -> 
   			packItem.containerId == params.containerId &&
			packItem.itemNo == params.itemNo
		}
   		assert packItem
   		if (!params.quantity?.trim()) {
 			 redirect action:'removeItem', params:[containerId:packItem.containerId, itemNo:packItem.itemNo] 
 			 return
 		}
   		if (packItem.packQuantity < params.quantity?.toInteger()) {
   			flash.message = message(code:'localized.do.not.increase.qty') //"Please do not increase the quanity of a returned item"
   			redirect action:'orderReturn', params:[cartId:session.cart.id]
   			return
   		}
		try {              
			packItem.packQuantity = params.quantity?.toInteger()?:0
		} catch (NumberFormatException nfe) {
			packItem.packQuantity = 0
		}
    	render view:'listOrder', 
		  model:[orderHeader:session.orderHeader, 
		         manifest:session.manifest, packList:session.packList, 
		         cart:session.cart, 
		         shippingCost:getShippingCost(session.manifest, session.packList, 0),
		         edit:true,
		         params:params]
   	}
   	def removeItem = {
   		def toRemove = session.packList.find{packItem -> 
   			packItem. containerId == params.containerId &&
			packItem.itemNo == params.itemNo
		}
		session.packList.remove(toRemove)
		if (!session.packList.findAll{it.returnIt}.size()) {
			flash.message = message(code:'localized.return.all.items.abort') //'all items were removed from your order, return aborted.'
			redirect controller:'consumer', action:'show'
			return
		}
    	render view:'listOrder', 
    		  model:[orderHeader:session.orderHeader, 
    		         manifest:session.manifest, packList:session.packList, 
    		         cart:session.cart, 
    		         shippingCost:getShippingCost(session.manifest, session.packList, 0),
    		         edit:true,
    		         params:params]
   	}
   	def upsPickup = {
   		session.upsPickup = params.upsPickup
    	render view:'continueReturn', 
    		  model:[orderHeader:session.orderHeader, 
    		         manifest:session.manifest, packList:session.packList, 
    		         cart:session.cart, 
    		         shippingCost:getShippingCost(session.manifest, session.packList, 0),
    		         edit:false,
    		         params:params]
   	}
   	def createLtlRA = {
   		Consumer cust = session.consumer
   		def cart = session.cart
   		ConsumerBillTo billTo = cart.consumerBillTo
   		Return ra = new Return(customer:Customer.findByCustNo('KE1126'), createDate:new Date(), 
	             createUser:'Retail Web', noRA:false,
	             name:billTo.name, addr1:billTo.addr1, addr2:billTo.addr2?:'', 
	             city:billTo.city, state:billTo.state, zipCode: billTo.zipCode,
	             status:ReturnStatus.get('NEWRA'),
	             freightInCustDb: session.manifest.actualFrtChg,
	             // shipVia, Carrier, shippingTerms?
				)
   		cart.items.each {item ->
			InvoicedItem invItem = InvoicedItem.findByOrderNoAndItemNo(cart.orderNo, item.item.itemNo)
			ReturnItem raItem = new ReturnItem(orderNo:cart.orderNo, shipNo:1, 
									itemNo:item.item.itemNo, desc:item.item.desc, unitPrice:invItem.unitPrice,
									qty: item.qty, authQty: item.qty,
									reason:ReturnReason.findByCrsReason(ReasonCode.get('CR'))
								)
			ra.addToItems(raItem)
			if (!raItem.validate()) {
				raItem.errors.each {log.errors it}
				assert false
			}
		}
		if (!ra.save()) {
			ra.errors.each {log.errors it}
			assert false
		}
		session.raId = ra.id
		model:[raId:ra.id, 
		       cartId:session.cart.id,
		       carrier:Carrier.get(session.manifest.shipperNo)]
   	}
   	def createUpsRA = {
   		BigDecimal shippingCost = getShippingCost(session.manifest, session.packList, 0)
   		shippingCost += session.upsPickup?6:0
   		Return ra = createRA (session.consumer, session.cart, session.manifest, session.packList, shippingCost)
   		def orderShipNo = "${session.cart.orderNo}-01"
   		GPathResult shipmentConfirmResponse = 
   			upsService.getShipmentConfirmResponse(session.manifest, session.packList.findAll{it.returnIt}, ra.id)
        def trackingNos	= upsService.buildLabelHtmlImages(shipmentConfirmResponse, CH.config.ra.label.directory) // getRootPath())			 										 
   		log.debug "tracking numbers for order: ${orderShipNo} RA: $ra.id: ${trackingNos}"
		ra.attach()
		ra.freightTrackingNo = trackingNos[0]
		ra.save(flush:true)    
	    shippingCost = upsService.getShippingCost(shipmentConfirmResponse)	 										 
    	render view:'listUpsLabels', model:[raId:ra.id, trackingNos:trackingNos, shippingCost:shippingCost]
   	}
   	def createSelfShipRA = {
   		Return ra = createRA (session.consumer, session.cart, session.manifest, session.packList, 0)
   		[ra:ra]
   	}
   	private Return createRA (Consumer cust, Cart cart, ShippingManifest mani, List packList, BigDecimal shippingCost) {
   		ConsumerBillTo billTo = cart.consumerBillTo
		Return ra = new Return(customer:Customer.findByCustNo('KE1126'), createDate:new Date(), 
		             createUser:'Retail Web', noRA:false,
		             name:billTo.name, addr1:billTo.addr1, addr2:billTo.addr2?:'', 
		             city:billTo.city, state:billTo.state, zipCode: billTo.zipCode,
		             status:ReturnStatus.get('NEWRA'),
		             freightInCustDb:shippingCost
					)
	    packList.findAll{it.returnIt}.each {packItem ->
			InvoicedItem invItem = InvoicedItem.findByOrderNoAndItemNo(cart.orderNo, packItem.itemNo)
	   		assert invItem
	    	ItemMasterExt item = ItemMasterExt.findByCompCodeAndItemNo(invItem.compCode, packItem.itemNo)
	    	assert item
			ReturnItem raItem = new ReturnItem(orderNo:cart.orderNo, shipNo:1, 
									itemNo:item.itemNo, desc:item.desc, unitPrice:invItem.unitPrice,
									qty: packItem.packQuantity, authQty: packItem.packQuantity,
									reason:ReturnReason.findByCrsReason(ReasonCode.get('CR'))
								)
			ra.addToItems(raItem)
			if (!raItem.validate()) {
				raItem.errors.each {log.errors it}
				assert false
			}
		}
		if (!ra.save(flush:true)) {
			ra.errors.each {log.errors it}
			assert false
		}
		ra
   	}
   	def label = {
   			// comment
   		[trackingNo:params.trackingNo]
   	}
   	def createSelfShipLabel = {}
   	def ltlBillOfLading = {
        def cart = Cart.get(params.cartId as int)
        assert cart
    	def orderHeader = InvoicedOrderHeader.findByCompCodeAndOrderNo('01', cart.orderNo)
    	assert orderHeader
        def manifest = ShippingManifest.findByOrderShipNo(orderHeader.orderNo+'-'+ZERO_PAD2.format(orderHeader.shipNo))
   		def raId = params.cartId as int
   		model:[raId:params.raId,  cart:cart,
   		       shipFrom:cart.consumerShipTo?:cart.consumerBillTo, 
   		       soldTo:cart.consumerBillTo,
   		       manifest:manifest,
   		       carrier:Carrier.get(manifest.shipperNo)
   		      ]
   	}
	private String getRootPath() {
   		String rootPath = ApplicationHolder.application.parentContext.getResource(".").file.getAbsolutePath()
   		rootPath = rootPath.replaceAll(/\/web-app/, '')
   		rootPath = rootPath.replaceAll(/\\temp\\\d*-retail/, '')
		if (Environment.current == Environment.DEVELOPMENT) { 
			rootPath = './web-app'
		} 
   		rootPath
	}
    def renderImage = {
    	def image = new File(CH.config.ra.label.directory+'/label'+params.trackingNo+'.gif') 
    	println image.dump()
        response.setContentLength(image.size().toInteger())
        response.outputStream.write(image.bytes)
    }
   	
}
