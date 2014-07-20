package com.kettler.controller.retail

import org.apache.commons.validator.CreditCardValidator;

import com.kettler.domain.orderentry.share.Cart;
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.work.Constants 

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH


class CheckoutCommand {
	String email = ''
	int billToId = 0
	String billingName
	String billingAddress1
	String billingAddress2
	String billingCity
	String billingState
	String billingZip
	String division
	boolean shipInfoDiff
	int shipToId = 0
	String shippingName
	String shippingAddress1
	String shippingAddress2
	String shippingCity
	String shippingState
	String shippingZip
	String giftCard
	String creditCardType
	String creditCard
	int ccid
	int month
	int year
	boolean loggedIn = false
	boolean saveAccount = true
	boolean registerWarranty
	String billToUpdateOrAdd = UPDATE
	String shipToUpdateOrAdd = UPDATE
	String password
	String confirmPassword
	String upsServiceCode = setInitialServicecode()
    String phone = ''
	
	static def UPDATE = 'update'
	static def ADD = 'add'
		
	static constraints = {
		email email:true, blank:false, nullable:false, validator: { email, checkout ->
			email = email.toString()
	        if (checkout.saveAccount && 
	        	!checkout.loggedIn  && 
	        	(email.size() && 
	             Consumer.findByEmail(email)
	            )
	        ) {
	        	return "consumer.email.unique"
	        }
		}
		billingName nullable:false, blank:false
		billingAddress1 nullable:false, blank:false
		billingAddress2 nullable:true, blank:true
		billingCity nullable:false, blank:false
		billingState nullable:false, blank:false, inList:['AL','AK','AZ','AR','CA','CO','CT','DE','DC','FL','GA','HI','ID','IL','IN','IA','KS','KY','LA','ME','MD','MA','MI','MN','MS','MO','MT','NE','NV','NH','NJ','NM','NY','NC','ND','MP','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VT','VA','WA','WV','WI','WY']
		billingZip nullable:false, blank:false, 
	        validator: { zipCode, checkout ->
	        if (!(zipCode ==~ /^\d{5,9}$/) ) { 
	        	return "checkoutCommand.billingZip.usa.invalid"
	        }
	        def entry = Constants.zipStateMap.find{it.key.contains(zipCode[0..2].toInteger())}
	        if (!entry || entry.value != checkout.billingState) {
	        	return "checkoutCommand.billingZip.state.no.match"
	        }
	        return true
		}
		division nullable:false, blank:false
		shippingName validator: { shippingName, checkout ->
	        if (checkout.shipInfoDiff && !shippingName ) { 
	        	return "checkoutCommand.shippingName.blank"
	        }
		}
		shippingAddress1 validator: { shippingAddress1, checkout ->
	        if (checkout.shipInfoDiff && !shippingAddress1 ) { 
	        	return "checkoutCommand.shippingAddress1.blank"
	        }
		} 
		shippingCity validator: { shippingCity, checkout ->
	        if (checkout.shipInfoDiff && !shippingCity ) { 
	        	return "checkoutCommand.shippingCity.blank"
	        }
		} 
		shippingState validator: { shippingState, checkout ->
	        if (checkout.shipInfoDiff && !shippingState ) { 
	        	return "checkoutCommand.shippingState.blank"
	        }
		} 
		shippingZip validator: { shippingZip, checkout ->
	        if (checkout.shipInfoDiff && !shippingZip ) { 
	        	return "checkoutCommand.shippingZip.blank"
	        }
	        if (checkout.shipInfoDiff && !(shippingZip ==~ /^\d{5,9}$/) ) { 
	        	return "checkoutCommand.shippingZip.usa.invalid"
	        }
	        if (checkout.shipInfoDiff) {
		        def entry = Constants.zipStateMap.find{it.key.contains(shippingZip[0..2].toInteger())}
		        if (!entry || entry.value != checkout.shippingState) {
		        	return "checkoutCommand.shippingZip.state.no.match"
		        }
			}
		}   
		giftCard nullable:true, blank:true
//		creditCard creditCard:true, minSize:15, maxSize:16, blank:false, nullable:false
		creditCard validator: { creditCard, checkout -> 
			def ccValidator = new CreditCardValidator(CreditCardValidator.VISA + CreditCardValidator.AMEX + CreditCardValidator.MASTERCARD + CreditCardValidator.DISCOVER)
			if (!ccValidator.isValid(creditCard)) {
				return "checkoutCommand.creditCard.creditCard.invalid"
			}
			return true
		}
		creditCardType nullable:false, blank:false
		ccid min:1
		month range:(1..12)
		year range:(2010..2050)
		billToUpdateOrAdd nullable:true, blank:true, validator: { billToUpdateOrAdd, checkout ->
				if (billToUpdateOrAdd && checkout.saveAccount &&
					![CheckoutCommand.UPDATE, CheckoutCommand.ADD].find {it == billToUpdateOrAdd}) {
					return false
			}
		}	 
		shipToUpdateOrAdd nullable:true, blank:true, validator: { shipToUpdateOrAdd, checkout ->
				if (shipToUpdateOrAdd && checkout.saveAccount &&
					![CheckoutCommand.UPDATE, CheckoutCommand.ADD].find {it == shipToUpdateOrAdd}) {
					return false
			}
		}	 
		password validator: { password, checkout ->
			if (checkout.saveAccount && !checkout.loggedIn && password?.size() < 7) {
					return "checkoutCommand.password.minSize.notmet"
			}
		}	 
		confirmPassword validator: { confirmPassword, checkout ->
			if (checkout.saveAccount && confirmPassword != checkout.password) {
				return "registerCommand.confirmPassword.validator.error"
			}
		}	
		if (CH.config.rateService.equals('UPS')){
			upsServiceCode nullable:false, inList:Cart.SHIP_METHODS_UPS.keySet() as List
		} else if (CH.config.rateService.equals('FEDEX')){
			upsServiceCode nullable:false, inList:Cart.SHIP_METHODS_FEDEX.keySet() as List
		}
    	phone nullable:false, validator: { phone, checkout ->
			checkout.phone = phone.replaceAll(/\D/, '')
    	    if (checkout.phone ==~ /^\d{10}$/) {
    	    	return true
    	    }
    	    return "checkoutCommand.phone.matches.invalid"
		}   
	}
	void setShipTo(ConsumerShipTo shipTo) {
		if (!shipTo || !shipToId) {
			return
		}
        shipToId = shipTo.id			                                               
		shippingName = shipTo.name
		shippingAddress1 = shipTo.addr1
		shippingAddress2 = shipTo.addr2
		shippingCity = shipTo.city
		shippingState = shipTo.state
		shippingZip = shipTo.zipCode
	}
	void setBillTo(ConsumerBillTo billTo) {
        billToId = billTo.id?:0	                                               
		billingName = billTo.name
		billingAddress1 = billTo.addr1 
		billingAddress2 = billTo.addr2
		billingCity = billTo.city
		billingState = billTo.state
		billingZip = billTo.zipCode
		creditCardType = billTo.cardType
		creditCard = billTo.cardNo
		ccid = billTo.ccid
		month = billTo.expMonth
		year = billTo.expYear
	}
	void setFromConsumer(Consumer user) {
		loggedIn = true
		email = user?.email
	    phone = user?.phone
		if (user.billTos) {
			setBillTo(user.billTos?.toArray()[0])
		} else {
			println "no billTos found to populate CheckoutCommand"
		}
		if (user.shipTos) {
			setShipTo(user.shipTos?.toArray()[0])                                   
		} else {
			println "no shipTos found to populate CheckoutCommand"
		}
		saveAccount = user.password.size() as boolean 
	}
	String toString() {
		def str = """ 
		division: $division upsServiceCode: $upsServiceCode Credit Card: $creditCardType $creditCard $ccid $month/$year
		billToId: $billToId
		$billingName
		$billingAddress1
		$billingAddress2
		$billingCity $billingState $billingZip
        upsServiceCode: $upsServiceCode
		"""
		if (shipInfoDiff) {
			str += """shipInfoDiff: $shipInfoDiff
         		shipToId: $shipToId
				$shippingName
				$shippingAddress1
				$shippingAddress2
				$shippingCity $shippingState $shippingZip 
				"""
		}
		str += "saveAccount: $saveAccount billToUpdateOrAdd: $billToUpdateOrAdd loggedIn: $loggedIn giftCard: $giftCard"			
	}
	String setInitialServicecode(){
		if (CH.config.rateService.equals('UPS')){
			println 'HERE in UPS'
			return '03'
		} else if (CH.config.rateService.equals('FEDEX')){
			println 'HERE in FEDEX'
			return '21'
		}
	}

}
