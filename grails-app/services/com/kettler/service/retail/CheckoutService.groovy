package com.kettler.service.retail
 
import org.apache.commons.validator.CreditCardValidator

import com.kettler.controller.retail.AddressesCommand
import com.kettler.controller.retail.PayShipCommand

import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.CartItem;
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.orderentry.share.ConsumerBillTo

import com.kettler.domain.orderentry.share.OrderDetailComment
import com.kettler.domain.orderentry.share.OrderDetailItem
import com.kettler.domain.orderentry.share.OrderDiscountAllowance
import com.kettler.domain.orderentry.share.SalesTax;
import com.kettler.domain.orderentry.share.OrderDetailMisc
import com.kettler.domain.orderentry.share.OrderHeader
import com.kettler.domain.orderentry.share.ShipTo
import com.kettler.domain.orderentry.share.GiftCard

import com.kettler.domain.actrcv.share.Customer

import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.item.share.ItemWarehouse

import com.kettler.domain.warranty.share.WarrantyCustomer
import com.kettler.domain.warranty.share.WarrantyItem
import com.kettler.domain.warranty.share.WarrantyPeriod

import com.kettler.domain.work.DateUtils

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import groovy.sql.Sql

class CheckoutService {
    def rpgService  
    def mailService
    def preAuthorizationService
    def sessionFactory
        
    static transactional = false

       String checkout(Cart cart, AddressesCommand addr, PayShipCommand payShip) {
        def giftCardReduced = 0.00g
        if (cart.giftCard) {
            giftCardReduced = cart.giftCardReduced(addr.shippingState?:addr.billingState)
        } 
        CreditCardReturn ccCheck = new CreditCardReturn(valid:true, codeOrRouting:"0")
        if (payShip.creditCardType != 'paypal' && (!cart.giftCard || cart.total(addr.shippingState?:addr.billingState) > giftCardReduced)) {
            ccCheck = preAuthorizationService.preAuth(cart, addr, payShip)
            if (!ccCheck.valid && CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') {
                return ccCheck.codeOrRouting
            }
        }
        OrderHeader orderHeader = createOrder(cart, addr, payShip, ccCheck.codeOrRouting)
        try {
            mailService.sendMail {
                to addr.email
                subject "Your KETTLER Order"
                body  view:"/checkout/checkout/confirm", plugin:"email-confirmation", model:[cart:cart, addrCmd:addr, payShipCmd:payShip, orderHeader:orderHeader, emailTask:true]
                from "orders@kettlerusa.com"
            }
        } catch (e) {
            log.warn("sendMail failed:"+e)
        }
        if (cart.giftCard) {
            GiftCard gc = cart.giftCard
            if (giftCardReduced < gc.currentValue) {
                gc.currentValue -= giftCardReduced
            } else {
                gc.currentValue = 0.0F
            }
            gc.save()
        }
        cart.email = addr.email
        cart.items.each {item ->
            if (item.item.itemNo ==~ /GIFTCARD.*/) {
                return    // no need to do inventory stuff on gift card
            }
            item.item.refresh() // incase someone else updated and the version changed
            item.item.qtyAlloc += item.qty
            if (!item.item.save()){
                item.item.errors.each {log.error it}
                assertErrorEmail("error in item.item.save(), first error:"+item.item.errors[0]) 
				assert false
            }
            if (item.item.specialOrder) {
                item.item.qtyOnBackOrder += item.qty
                if (!item.item.save(flush:true) ) {
					item.item.errors.each {log.error it}
					assertErrorEmail("error in special order item.item.save(), first error:"+item.item.errors[0]) 
					assert false
                }
            }
            def itemWhseUpdate = ItemWarehouse.findWhere([compCode:item.item.compCode, itemNo:item.item.itemNo, warehouse:'1'])
            if (itemWhseUpdate) {
                if (item.item.specialOrder) {
                    itemWhseUpdate.qtyOnBackOrder += item.qty
                }
                itemWhseUpdate.qtyAlloc += item.qty
                if (!itemWhseUpdate.save()){
                    itemWhseUpdate.errors.each {log.error it}
					assertErrorEmail("error in itemWhsUpdate, first error:"+itemWhseUpdate.errors[0]) 
                    assert false
                }
            }
        }
        if (cart.registerWarranty) {
            def warrantyCustomer = WarrantyCustomer.findByEmail(cart.email)
            if (!WarrantyCustomer.findByEmail(cart.email)) {
                warrantyCustomer = new WarrantyCustomer(
                        email:            cart.email,
                        lastOrCorpName:    addr.billingName,
                        addr1:            addr.billingAddress1,
                        addr2:            addr.billingAddress2?:'',
                        city:            addr.billingCity,
                        state:            addr.billingState,
                        zipCode:        addr.billingZip?.replaceAll(/\D/, ''),
                        phone:            addr.phone
                        )
                warrantyCustomer.id = orderHeader.orderNo
                if (!warrantyCustomer.save()) {
                    warrantyCustomer.errors.each {log.error it}
					assertErrorEmail("error in warrantyCustomer.save, first error:"+warrantyCustomer.errors[0]) 
                    assert false
                }
            }
            int seq = 0
            def maxSeq = WarrantyItem.executeQuery( "select max(sequenceNo) from WarrantyItem where custNo = :wcusid",  [wcusid:warrantyCustomer.id])
            if (maxSeq[0]) {
                seq = maxSeq[0]
            }  
            cart.items.each {item ->
                if (item.item.residentialWarrantyCode) {
                    def warrantyPeriod = WarrantyPeriod.get(item.item.residentialWarrantyCode)
                    def warrantyItem = new WarrantyItem(
                                compCode:        item.item.compCode,
                                itemNo:            item.item.itemNo,
                                itemDesc:        item.item.desc,
                                purchaseDate:     new Date(),
                                purchaseQty:    item.qty,
                                warrantyCode:     item.item.residentialWarrantyCode,
                                expirationDate: new Date() + 
                                            ((warrantyPeriod.years * 365) + warrantyPeriod.days)
                                ) 
                    warrantyItem.custNo = warrantyCustomer.id
                    warrantyItem.sequenceNo = ++seq 
                    if (!warrantyItem.save()) {
                        warrantyItem.errors.each {log.error it}
						assertErrorEmail("error in warrantyItem.save, first error:"+warrantyItem.errors[0]) 
                        assert false
                    }
                }
            }
        }
        if (!cart.save(flush:true)) {
			cart.errors.each {log.error it}
			assertErrorEmail("error in cart.save, first error:"+cart.errors[0]) 
			assert false
        }
		creditManagerWarningEmail(orderHeader, cart, addr)
		
        null
       }
       
    OrderHeader createOrder(Cart cart, AddressesCommand addr, PayShipCommand payShip, String ccRoutingNo) {
        int max = 0
        int orderNo = rpgService.getNextOrderNo('01')
        OrderHeader ord = new OrderHeader(
                 compCode:            '01',  
                 custNo:            'KE1126',  
                 orderNo:            orderNo, 
                 statusCode:        'O',
                 poNo:                orderNo,
                 salesperson1:         Customer.findByCompCodeAndCustNo('01', 'KE1126').salespersonCode, 
                 packingListCode:    '01',
                 acknEmail:            addr.email,
                 shippingPhoneNo:    addr.phone,
                 shipToNo:            9999, 
                 shipNo:            1,
                 taxCode1:          cart.calcTax(addr.shippingState?:addr.billingState)?(addr.shippingState?:addr.billingState):'',
                 carrierCode:         'UPSN',
                 approvedDate:         new Date(),
                 approvedTime:      new java.sql.Time(new Date().time),
                 fobCode:             'PP', // prepaid by kettler
                 termsCode:            '9', // pay by credit card
                 routingNo:            ccRoutingNo.toInteger(),
                 cancelAfterDate:    DateUtils.getBeginningOfTime(),
                 enteredBy:            'WEB',
                 approvedBy:        'WEB'
                 )
println "payShip.spcode:"+payShip.spcode
        if (payShip.spcode?.trim()?.size() > 0) {// salesperson assigned in checkout page 2
            ord.salesperson1 = payShip.spcode;
        }
        ord.orderTotal = cart.total(addr.shippingState?:addr.billingState)

        if (cart.items.find{it.item.arDistrictCode.equals('GFC')}) {
                ord.statusCode = 'S'
        } else {
            switch (cart.upsServiceCode) { 
            case '03':
                ord.shipInstructions = 'R-UPS GND RESID'
                def state = addr.shipToSameAsBillTo?addr.billingState:addr.shippingState
                // Hawaii or Alaska or less than $40
                if (['HI','AK'].find{it == state} || cart.totalItemCost() < 40.0g ) {
                    ord.fobCode = 'PC' // charge to customer
                } else {
                    ord.fobCode = 'PP' // prepaid by kettler
                    cart.shippingCost = 0.0g
                }
                break
            case '02':
                ord.shipInstructions = 'R-UPS 2DY AIR R'
                ord.fobCode = 'PC' // charge to customer
                break
            case '13':        
                ord.shipInstructions = 'R-UPS ND AR RES'
                ord.fobCode = 'PC' // charge to customer
                break
            case '21':
                ord.shipInstructions = 'R-FEDEX GROUND' //fedex ground
                def state = addr.shipToSameAsBillTo?addr.billingState:addr.shippingState
                // Hawaii or Alaska or less than $40
                if (['HI','AK'].find{it == state} || cart.totalItemCost() < 40.0g ) {
                    ord.fobCode = 'PC' // charge to customer
                } else {
                    ord.fobCode = 'PP' // prepaid by kettler
                    cart.shippingCost = 0.0g
                }
				if (cart.totalWeight() < 70.0g){
					ord.shipInstructions = 'R-FEDEX-HOME DL' //fedex ground residential
				}
				ord.specialChrgCd1 = 'RES'
				ord.carrierCode = 'FDEG'
                break
            case '20':
                ord.shipInstructions = 'R-FX-ECON 2 DAY'  //fedex 2 day
                ord.fobCode = 'PC' // charge to customer
				ord.specialChrgCd1 = 'RES'
				ord.carrierCode = 'FDEG'
                break
            case '22':        
                ord.shipInstructions = 'R-FEDEX-1D'  // fedex next day
                ord.fobCode = 'PC' // charge to customer
				ord.specialChrgCd1 = 'RES'
				ord.carrierCode = 'FDEG'
                break
            case '99': 
                ord.shipInstructions = 'R-TRAILER'
                ord.fobCode = 'PP' // prepaid by kettler
                ord.carrierCode = ''
                ord.shipVia = 'ROUTING'
                if (ord.orderTotal >= 999.00g) {
					if (CH.config.rateService.equals('UPS')){
						ord.specialChrgCd1 = 'RESD'
					} else {
						ord.specialChrgCd1 = 'RES'
					}
                    ord.specialChrgCd2 = 'IND'
                }
                break
            default:
				assertErrorEmail("invalid cart.upsServiceCode:"+cart.upsServiceCode) 
                assert false
            }
            if (ord.orderTotal >= 199.00g && ['02', '03', '13', '20', '21', '22'].find{it == cart.upsServiceCode}) {
				if (CH.config.rateService.equals('UPS')){
					ord.specialChrgCd1 = 'SDCR'
				} else {
					ord.specialChrgCd1 = 'SIGND'
				}
            }
            def salesTax = SalesTax.get(addr.shippingState?:addr.billingState)
            if (salesTax) {
                ord.taxPct1 = salesTax.taxPct/100.0g?:0g
            }
        }
        int lastLineNo = 0
        BigDecimal taxableAmount = 0.00g
        cart.items.eachWithIndex{cartItem, index ->
            def ordItem = new OrderDetailItem(
                     compCode:        '01',  
                     orderNo:        ord.orderNo, 
                     shipNo:        ord.shipNo,
                     lineNo:         index ? (index+1) * 10: 10,
                     itemNo:        (cartItem.itemWithColor?:(cartItem.itemWithFrameSize?:cartItem.item)).itemNo,
                     desc:            (cartItem.itemWithColor?:(cartItem.itemWithFrameSize?:cartItem.item)).desc,
                     distrCode:        cartItem.item.arDistrictCode,
                     nmfcNo:        cartItem.item.nmfcNo,
                     qtyUnitMeas:    cartItem.item.stdUnitMeas,
                     orderQty:        cartItem.qty,
                     shipQty:       cartItem.qty,
                     priceUnitMeas: cartItem.item.priceUnitMeas,
                     unitPrice:        cartItem.item.closeoutCode?cartItem.item.specialPrice:cartItem.item.retailPrice,
                     amount:        (cartItem.qty * (cartItem.item.closeoutCode?cartItem.item.specialPrice:cartItem.item.retailPrice)),
                     subjToTax1:    ord.taxPct1?true:false,
                     lineType:'I' // Stock item
                    )
            if (cartItem.item.taxableCode) {
                ordItem.subjToTax1 = true
                ordItem.subjToTax2 = false
                ordItem.subjToTax3 = false
                taxableAmount += ordItem.amount
            }
            if (cartItem.item.specialOrder) {
                ordItem.shipQty = 0
                ordItem.backOrderQty = cartItem.qty
                ordItem.backOrderCode = 'Y'
                ord.statusCode = 'B'
            }
            if (!ordItem.save()) {
                ordItem.errors.each {log.error it.inspect()}
				assertErrorEmail("error in ordItem.save, first error:"+ordItem.errors[0]) 
                assert false
            }
            lastLineNo = ordItem.lineNo
        }
        lastLineNo += 10
        if (!payShip.giftCard || (cart.total(addr.shippingState?:addr.billingState) > cart.giftCard?.currentValue?:0.0g)) {
            def ordDtlCmt = new OrderDetailComment(
                compCode:'01', orderNo:ord.orderNo, shipNo:ord.shipNo,
                lineNo:lastLineNo, 
                text: payShip.creditCardType,
                printCode:'I')
            if (!ordDtlCmt.save(insert:true)) {
                ordDtlCmt.errors.each { log.error it.inspect()}
				assertErrorEmail("error in ordDtlCmt.save, first error:"+ordDtlCmt.errors[0]) 
                assert false
            }
            lastLineNo += 10
            def cardNo = payShip.creditCard
            ordDtlCmt = new OrderDetailComment(
                compCode:'01', orderNo:ord.orderNo, shipNo:ord.shipNo,
                lineNo:lastLineNo,  
                text: payShip.creditCardType=='paypal'?"PayerId: ${cart.paypalPayerId}":"**** **** **** ${cardNo[12..(cardNo.size()-1)]}",
                printCode:'I')
            if (!ordDtlCmt.save(insert:true)) {
                ordDtlCmt.errors.each { log.error it.inspect()}
				assertErrorEmail("error in ordDtlCmt.save, first error:"+ordDtlCmt.errors[0]) 
                assert false
            }
            lastLineNo += 10
        }
        if (cart.coupon) {
            def ordDtlCmt = new OrderDetailComment(
                compCode:'01', orderNo:ord.orderNo, shipNo:ord.shipNo,
                lineNo:lastLineNo,
                text: "Coupon:$cart.coupon.no-${(cart.coupon.percent?cart.coupon.percent:cart.coupon.amount)}${cart.coupon.percent?'%':''}",
                printCode:'I')
            if (!ordDtlCmt.save(insert:true)) {
                ordDtlCmt.errors.each { log.error it.inspect()}
				assertErrorEmail("error in ordDtlCmt.save, first error:"+ordDtlCmt.errors[0]) 
                assert false
            }
            if (cart.coupon.singleUse) {
                cart.coupon.expire = new Date() 
            }
            def discountAmount = cart.coupon.percent? cart.totalItemCost() * (cart.coupon.percent/100) : cart.coupon.amount
            def discAllow = new OrderDiscountAllowance(compCode:'01', orderNo:ord.orderNo,
                    shipNo:ord.shipNo,profitCenter:cart.items.toArray()[0].item.profitCenterClass ,code:'INVD',
                    percent:cart.coupon.percent?:discountAmount / cart.totalItemCost())
            if (!discAllow.save(flush:true, insert:true)) {
                log.error "save of OrderDiscAllowance failed"+discAllow.dump()
                try {
                    mailService.sendMail {
                        if (CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') { to( ['mamato@kettlerusa.com']) } else { to (['dondenoncourt@gmail.com','dondenoncourt@comcast.net']) }    
                        subject "Retail Order ${ord.orderNo} write error" 
                        body "Retail order ${ord.orderNo} had issues writing a OrderDiscountAllowance record/object check log " 
                        from "orders@kettlerusa.com"
                    }
                } catch (e) {
                    log.warn("sendMail failed:"+e)
                }
            }
            writeNonInvDetail(ord, cart, discountAmount, 9991) 
        } 
        if (cart.giftCard) {
            def ordDtlCmt = new OrderDetailComment(
                compCode:'01', orderNo:ord.orderNo, shipNo:ord.shipNo,
                lineNo:lastLineNo,
                text: "Gift Card:${cart.giftCard.cardNumber}",
                printCode:'I')
            if (!ordDtlCmt.save(insert:true)) {
                ordDtlCmt.errors.each { log.error it.inspect()}
				assertErrorEmail("error in ordDtlCmt.save, first error:"+ordDtlCmt.errors[0]) 
                assert false
            }
            def totalWithoutGiftCard = cart.totalWithoutGiftCard(addr.shippingState?:addr.billingState) + cart.shippingCost
            writeNonInvDetail(ord, cart, cart.giftCardReduced(addr.shippingState?:addr.billingState), Cart.GIFT_CARD_NON_INV_LINE_NO) 
        } 

        if (cart.shippingCost) {
            lastLineNo += 10
            def ordDtlMsc = new OrderDetailMisc(
                compCode:    '01', 
                orderNo:    ord.orderNo, 
                shipNo:        ord.shipNo,
                lineNo:        lastLineNo,
                desc:        'UPS charge',
                distrCode:  'UPS',
                amount:        cart.shippingCost,
                flatChargeCode: true
                )
				if (CH.config.rateService.equals('FEDEX')){
					ordDtlMsc.desc = 'FEDEX charge'
				}
            if (!ordDtlMsc.save(insert:true)) {
                ordDtlMsc.errors.each { log.error it.inspect()}
				assertErrorEmail("error in ordDtlMsc.save, first error:"+ordDtlMsc.errors[0]) 
                assert false
            }
        }
        
        ShipTo shipTo = new ShipTo(
                compCode:    ord.compCode,
                orderNo:    ord.orderNo,
                shipNo:        1, 
                shipToNo:    9999, 
                custNo:        ord.custNo,
                name:        addr.shippingName?:addr.billingName,  
                addr1:        addr.shippingAddress1?:addr.billingAddress1,
                addr2:        addr.shippingAddress2?:addr.billingAddress2?:'',
                city:        addr.shippingCity?:addr.billingCity,
                state:        addr.shippingState?:addr.billingState,
                zipCode:    addr.shippingZip?addr.shippingZip?.replaceAll(/\D/, ''):addr.billingZip?.replaceAll(/\D/, ''),
                residentialCommercial:'R'
                )
        if (!shipTo.save()) {
            shipTo.errors.each {log.error it.inspect()}
			assertErrorEmail("error in shipTo.save, first error:"+shipTo.errors[0]) 
            assert false
        }
        if (!ord.save(flush:true)) { // flush so Id can be displayed
            ord.errors.each{log.error it.inspect()}
			assertErrorEmail("error in ord.save, first error:"+shipTo.errors[0]) 
            assert false
        }
        cart.orderPlaced = true
        cart.orderNo = ord.orderNo
        if (ord.statusCode == "B") { // back order 
            try {
                mailService.sendMail {
                    if (CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') {
                        to( ['mamato@kettlerusa.com','fitness&bikes@kettlerusa.com'])
                    } else {
                        to (['dondenoncourt@gmail.com','dondenoncourt@comcast.net'])
                    }    
                    subject "Retail Special Order ${ord.orderNo}"
                    body "A retail special order of ${ord.orderNo} was just added"
                    from "orders@kettlerusa.com"
                }
            } catch (e) {
                log.warn("sendMail failed:"+e)
            }
        }
        
        return ord
    }
        
    
    
    Consumer saveConsumer(Cart cart, AddressesCommand addr, PayShipCommand payShip, Consumer consumer) {
        consumer.phone = addr.phone.toLong()
        consumer.saveAccount = true
        consumer.email = addr.email
        consumer.name = addr.billingName
        
        if (addr.type == AddressesCommand.NEW_CUSTOMER) {
            consumer.password = addr.password
        }
        
        ConsumerBillTo billTo = consumer.billTos?consumer.billTos.toArray()[0]:new ConsumerBillTo()

        billTo.name = addr.billingName
        billTo.addr1 = addr.billingAddress1
        billTo.addr2 = addr.billingAddress2
        billTo.city = addr.billingCity
        billTo.state = addr.billingState
        billTo.zipCode = addr.billingZip?.replaceAll(/D/,'')
        def ccValidator = new CreditCardValidator(CreditCardValidator.VISA + CreditCardValidator.AMEX + CreditCardValidator.MASTERCARD + CreditCardValidator.DISCOVER)
        if (ccValidator.isValid(payShip.creditCard)) {
            billTo.cardType = payShip.creditCardType
            billTo.cardNo = payShip.creditCard
            billTo.ccid = payShip.ccid
            billTo.expYear = payShip.year
            billTo.expMonth = payShip.month
        }
        billTo.consumer = consumer
        billTo = billTo.unique(consumer.billTos)
        consumer.addToBillTos billTo
        ConsumerShipTo shipTo
        if (!addr.shipToSameAsBillTo) {
            shipTo = consumer.shipTos?.size()?consumer.shipTos.toArray()[0]:new ConsumerShipTo()
            shipTo.name = addr.shippingName
            shipTo.addr1 = addr.shippingAddress1
            shipTo.addr2 = addr.shippingAddress2
            shipTo.city = addr.shippingCity
            shipTo.state = addr.shippingState
            shipTo.zipCode     = addr.shippingZip?.replaceAll(/D/,'')
            shipTo = shipTo.unique(consumer.shipTos)
            consumer.addToShipTos shipTo
        }
        
        if (!consumer.save(flush:true)) { // so ids are generated
            // consumer is not available to page (in session) so stuff errors in the cmd
			println "##############################error save consumer #########################################"
            consumer.errors.each {
                println it
                it.getAllErrors().each {
                    log.error it.field
                }
            }
			assertErrorEmail("error in consumer.save, first error:"+consumer.errors[0]) 
            assert false
        } 
        if (shipTo) {
            cart.consumerShipTo = shipTo
        }
        billTo.save(flush:true)
        
        if (CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') {
            def sql = new Sql(sessionFactory.getCurrentSession().connection())
            sql.execute("update " + CH.config.orderentry.schema + ".csmrbillto set ccencrypt = '' where ccencrypt = ''")
        }

        cart.consumerBillTo = billTo
        cart.consumer = consumer
        return consumer
    }

    private writeNonInvDetail(def ord, def cart, def amount, lineNo = 9991) {
            def ordItem = new OrderDetailItem(
                     compCode:        '01',  
                     orderNo:        ord.orderNo, 
                     shipNo:        ord.shipNo,
                     lineNo:         lineNo,
                     itemNo:        'INVD',    
                     desc:            'INVOICE DISCOUNT', 
                     discAllowCode: 'INVD',
                     qtyUnitMeas:    'EA',
                     orderQty:        1,
                     shipQty:       1,
                     priceUnitMeas: 'EA',
                     distrCode:        'DFR',
                     unitPrice:     (amount * -1),
                     amount:        (amount * -1),
                     discAllowProfitCenter: cart.items.toArray()[0].item.profitCenterClass, 
                     lineType:'N' // non inventory 
                    )
            if (!ordItem.save()) {
                ordItem.errors.each {log.error it.inspect()}
                try {
                    mailService.sendMail {
                        if (CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') { to( ['mamato@kettlerusa.com']) } else { to (['dondenoncourt@gmail.com','dondenoncourt@comcast.net']) }    
                        subject "Retail Order ${ord.orderNo} write error" 
                        body "Retail order ${ord.orderNo} had issues writing a coupon item detail record, check the log" 
                        from "orders@kettlerusa.com"
                    }
                } catch (e) {
                    log.warn("sendMail failed:"+e)
                }
            }
    }
	private void assertErrorEmail(errorDetail) {
		try {
			sendMail {
				to CH.config.app.error.email.to.addresses.toArray()
				subject "assertion failure in CheckoutService. Review log"
				body errorDetail
			}
		} catch (Exception e) {
			log.error "Problem emailing $e.message", e
		}
	}
	private void creditManagerWarningEmail(OrderHeader ord, Cart cart, AddressesCommand addr) {
		if (ord.orderTotal > 1000.00g && !addr.shipToSameAsBillTo) {
	        mailService.sendMail {
	            to CH.config.credit.warning.emails.toArray()
	            subject "Retail web sale alert: order over 1K with ship to different from bill to"
	            body  "order no: ${ord.orderNo} addresses:"+addr.toString()
	            from "orders@kettlerusa.com"
	        }
		}
	}
}
