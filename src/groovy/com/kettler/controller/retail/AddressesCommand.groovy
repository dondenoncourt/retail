package com.kettler.controller.retail

import com.kettler.domain.work.Constants 
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo


class AddressesCommand implements Serializable {
	String email = ''
	String billingName
	String billingAddress1
	String billingAddress2
	String billingCity
	String billingState
	String billingZip
	String division
	boolean shipToSameAsBillTo = true
	String shippingName
	String shippingAddress1
	String shippingAddress2
	String shippingCity
	String shippingState
	String shippingZip
    String phone = ''
	String password
	String confirmPassword
	int type 
	
	static int GUEST = 1
	static int NEW_CUSTOMER = 2
	static int RETURN_CUSTOMER = 3
	

	static constraints = {
		type inList:[GUEST, NEW_CUSTOMER, RETURN_CUSTOMER]
		email email:true, blank:false, nullable:false, validator: { email, addrCmd ->
			if (addrCmd.type == NEW_CUSTOMER && Consumer.findByEmail(email)) {
				return "addressesCommand.email.not.unique"
			}
		}
		billingName nullable:false, blank:false
		billingAddress1 nullable:false, blank:false, validator: {addr, addrCmd ->
			if (addr ==~ /(?i)P[\. ]?O[\. ]?\s*BOX.*/ && addrCmd.shipToSameAsBillTo) {
				return "addressesCommand.billingAddress1.po.box.invalid"
			}
		}
		billingAddress2 nullable:true, blank:true, validator: {addr, addrCmd ->
			if (addr ==~ /(?i)P[\. ]?O[\. ]?\s*BOX.*/ && addrCmd.shipToSameAsBillTo) {
				return "addressesCommand.billingAddress2.po.box.invalid"
			}
		}
		billingCity nullable:false, blank:false
		billingState nullable:false, blank:false, inList:['AL','AK','AZ','AR','CA','CO','CT','DE','DC','FL','GA','HI','ID','IL','IN','IA','KS','KY','LA','ME','MD','MA','MI','MN','MS','MO','MT','NE','NV','NH','NJ','NM','NY','NC','ND','MP','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VT','VA','WA','WV','WI','WY']
		billingZip nullable:false, blank:false, 
	        validator: { zipCode, addrCmd ->
	        if (!(zipCode ==~ /^\d{5,9}$/) ) { 
	        	return "addressesCommand.billingZip.usa.invalid"
	        }
	        def entry = Constants.zipStateMap.find{it.key.contains(zipCode[0..2].toInteger())}
	        if (!entry || entry.value != addrCmd.billingState) {
	        	return "addressesCommand.billingZip.state.no.match"
	        }
	        return true
		}
		shippingName validator: { shippingName, addrCmd ->
	        if (!addrCmd.shipToSameAsBillTo && !shippingName ) { 
	        	return "addressesCommand.shippingName.blank"
	        }
		}
		shippingAddress1 validator: { shippingAddress1, addrCmd ->
	        if (!addrCmd.shipToSameAsBillTo && !shippingAddress1?.trim() ) { 
	        	return "addressesCommand.shippingAddress1.blank"
	        }
			if (shippingAddress1 ==~ /(?i)P[\. ]?O[\. ]?\s*BOX.*/) {
				return "addressesCommand.shippingAddress1.po.box.invalid"
			}
		} 
		shippingCity validator: { shippingCity, addrCmd ->
	        if (!addrCmd.shipToSameAsBillTo && !shippingCity?.trim() ) { 
	        	return "addressesCommand.shippingCity.blank"
	        }
		} 
		shippingState validator: { shippingState, addrCmd ->
	        if (!addrCmd.shipToSameAsBillTo && !shippingState?.trim() ) { 
	        	return "addressesCommand.shippingState.blank"
	        }
		} 
		shippingZip validator: { shippingZip, addrCmd ->
	        if (!addrCmd.shipToSameAsBillTo && !shippingZip?.trim() ) { 
	        	return "addressesCommand.shippingZip.blank"
	        }
	        if (!addrCmd.shipToSameAsBillTo && !(shippingZip ==~ /^\d{5,9}$/) ) { 
	        	return "addressesCommand.shippingZip.usa.invalid"
	        }
	        if (!addrCmd.shipToSameAsBillTo) {
		        def entry = Constants.zipStateMap.find{it.key.contains(shippingZip[0..2].toInteger())}
		        if (!entry || entry.value != addrCmd.shippingState) {
		        	return "addressesCommand.shippingZip.state.no.match"
		        }
			}
		}  
    	phone nullable:false, validator: { phone, addrCmd ->
		addrCmd.phone = phone.replaceAll(/\D/, '')
		    if (addrCmd.phone ==~ /^\d{10}$/) {
		    	return true
		    }
		    return "addressesCommand.phone.matches.invalid"
		}   
		password validator: { password, addrCmd ->
			if (addrCmd.type == NEW_CUSTOMER && password?.size() < 7) {
				return "addressesCommand.password.minSize.notmet"
			} 
		}	 
		confirmPassword validator: { confirmPassword, addrCmd ->
			if (addrCmd.type == NEW_CUSTOMER && confirmPassword != addrCmd.password) {
				return "addressesCommand.confirmPassword.validator.error"
			}
		}	
	}
	String toString() {
		def str = """ 
		$billingName
		$billingAddress1
		${billingAddress2?:''}
		$billingCity $billingState $billingZip
        $email $phone
		"""
		if (!shipToSameAsBillTo) {
			str += """ 
				$shippingName
				$shippingAddress1
				$shippingAddress2
				$shippingCity $shippingState $shippingZip 
				"""
		}
		str += "type: $type [1:GUEST, 2:NEW_CUSTOMER, 3:RETURN_CUSTOMER]"
		str
	}
	static public AddressesCommand populateAddressesCommand(Consumer consumer ) {
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

}
