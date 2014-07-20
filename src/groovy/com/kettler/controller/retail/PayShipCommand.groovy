package com.kettler.controller.retail

import org.apache.commons.validator.CreditCardValidator;
import java.io.Serializable;

import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.Consumer;
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.Coupon
import com.kettler.domain.orderentry.share.GiftCard

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH


class PayShipCommand implements Serializable {
	String giftCard
	String creditCardType
	String creditCard
	String coupon
    String paypalToken
    String spcode
	int ccid
	int month
	int year
	boolean loggedIn = false
	boolean registerWarranty = true
	String upsServiceCode = setInitialServicecode()
	static constraints = {
		giftCard nullable:true, blank:true
		creditCard validator: { creditCard, checkout -> 
            if (checkout.creditCardType != 'paypal') {
                def ccValidator = new CreditCardValidator(CreditCardValidator.VISA + CreditCardValidator.AMEX + CreditCardValidator.MASTERCARD + CreditCardValidator.DISCOVER)
                if (!checkout.giftCard && !ccValidator.isValid(creditCard)) {
                    return "payShipCommand.creditCard.creditCard.invalid"
                }
            }
			return true
		}
		creditCardType validator: {creditCardType, checkout ->  // nullable:false, blank:false
			if (checkout.creditCardType != 'paypal' && !checkout.giftCard && !creditCardType?.size()) {
				return "payShipCommand.creditCardType.blank"
			}
			return true
		}
		ccid validator: {ccid, checkout ->  // min:1
			if (checkout.creditCardType != 'paypal' && !checkout.giftCard && !ccid) {
				return "payShipCommand.ccid.min.notmet"
			}
			return true
		}
		month range:(1..12)
		year range:(2011..2050)
		if (CH.config.rateService.equals('UPS')){
			upsServiceCode nullable:false, inList:Cart.SHIP_METHODS_UPS.keySet() as List
		} else if (CH.config.rateService.equals('FEDEX')){
			upsServiceCode nullable:false, inList:Cart.SHIP_METHODS_FEDEX.keySet() as List
		}
		coupon validator: { couponNo, payShipCmd ->
			Coupon coupon
			if (couponNo) {
				coupon = Coupon.findByNo(couponNo)
				if (!coupon) {
					return false
				}
			}
		}
        giftCard validator: { cardNum, payShipCmd ->
            boolean res = false
            GiftCard giftCard
            if (cardNum) {
                try {
                    giftCard = GiftCard.findByCardNumber(cardNum)
                } catch (Exception e) {
                    print "Error getting giftcard: ${e.toString()}"
                }
                if (giftCard) {
                    res = true
                }
                return res
			}
		}

	}
    String zeroPadCCID() {
    	if (creditCardType?.toLowerCase() == 'american express') {
    		return new java.text.DecimalFormat('0000').format(ccid)
    	}
		return new java.text.DecimalFormat('000').format(ccid)
    }
	static public PayShipCommand populatePayShipCommand(Consumer consumer ) {
		PayShipCommand payShip = new PayShipCommand()
		ConsumerBillTo billTo = consumer.billTos?consumer.billTos.toArray()[0]:new ConsumerBillTo()
		payShip.creditCardType = billTo.cardType
		payShip.creditCard = billTo.cardNo
		payShip.ccid = billTo.ccid
		payShip.year = billTo.expYear
		payShip.month = billTo.expMonth
		payShip
	}

    def getAttributeMap() {
        [giftCard:giftCard?:'',
        creditCardType:creditCardType?:'',
        creditCard:creditCard?:'',
        coupon:coupon?:'',
        ccid:ccid?:'',
        month:month?:'',
        year:year?:'',
        loggedIn:loggedIn?:'',
        registerWarranty:registerWarranty?:'',
        upsServiceCode:upsServiceCode]
    }

		String setInitialServicecode(){
		if (CH.config.rateService.equals('UPS')){
			return '03'
		} else if (CH.config.rateService.equals('FEDEX')){
			return '21'
		}
	}

}
