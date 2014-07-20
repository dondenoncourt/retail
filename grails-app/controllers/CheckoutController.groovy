import com.kettler.domain.orderentry.share.ConsumerBillTo;

import org.apache.commons.validator.CreditCardValidator;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger

import com.kettler.controller.retail.AddressesCommand
import com.kettler.controller.retail.PayShipCommand

import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.Coupon
import com.kettler.domain.orderentry.share.OrderHeader
import com.kettler.domain.orderentry.share.Role
import com.kettler.domain.orderentry.share.WebUser

import com.kettler.domain.varsity.share.ShippingManifest

import groovy.util.slurpersupport.GPathResult
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import com.kettler.domain.orderentry.share.GiftCard

import grails.util.Environment

class CheckoutController {
	def upsService
	def upsAddressService
	def checkoutService
	def sessionFactory
	def fedexService
	

    def beforeInterceptor = {
        log.debug "action: $actionName params: $params flash: $flash"
    }
    def afterInterceptor = {model ->
        log.debug "after action: $actionName model: $model params: $params flash: $flash"
    }

    static def stepTitles = [
                      1:'Click to change address, email, and phone number',
                      2:'Click to change payment and shipping method',
                      3:'Click to view the confirmation page',
                      4:'Click to confirm your order' 
                 ]
	
	def index = {
		if (session.consumer) {
			redirect action:'checkout', params:[type:AddressesCommand.RETURN_CUSTOMER]
		}
        def theCart = Cart.get(session.cartId)
		if (!theCart) {
			flash.message = message(code:'localized.cart.empty')
			redirect uri:"/${params.division.replaceAll(/ /, '-')}/", params:params
		}
        boolean containsGiftCard = false
        theCart.items?.each {
            if (it.item.arDistrictCode.equals('GFC')) {
                containsGiftCard = true
                log.debug "cart contains gift card"
            }
        }
        [containsGiftCard: containsGiftCard]
	}

    def paypal = {
        log.debug "PP_Sandbox_UserGuide Chap 5 Step 4: CheckoutController::paypal "
        // Note: can't directly update cart.paypalPayerId because cart is in the flow scope
        // so we are using the session to hold the Paypal Payer Id.
        session.paypalPayerId=params['PayerID'] 
    	redirect action:'checkout', params:params
    }
	
	def cancelCart = {
    	clearSession()
		flash.message = message(code:'localized.cart.cleared')
    	redirect controller:'homePage', action:'home'
	}
	
	def emailPassword = {
		def consumer = Consumer.findByEmail(params.email)
		assert consumer
		try {
			sendMail {
	            to params.email
	            subject "Your KETTLER USA profile"
	            body "${consumer.name}:\nYour KETTLER USA password is: ${consumer.password}"
	            from "webmaster@kettlerusa.com"
			}
		} catch (e) {
			println("sendMail failed:"+e)
			log.warn("sendMail failed:"+e)
		}

		flash.message = message(code:'localized.password.emailed', args:[params.email])//"Your password has been emailed to ${params.email}" 
		redirect action:'index', params:params
	}
	
	def returning = { 
		def consumer = Consumer.findByEmail(params.email?:'')
		if (!consumer) {
			flash.message = message(code:'localized.invalid.email.or.password')//"Invalid email or password"  
			redirect action:'index', params:[type:AddressesCommand.RETURN_CUSTOMER, params:params]
			return	              
		} else if (consumer?.password == params.password) {
			flash.message = message(code:'localized.welcome.back', args:[consumer.name])//"Welcome back ${consumer.name}"
			session.consumer = consumer
			params.remove('email') 
			params.remove('password') 
			redirect action:'checkout', params:[type:AddressesCommand.RETURN_CUSTOMER]
	    	return
		} 
		// else good email, bad password
		flash.message = message(code:'localized.invalid.password')+' '+
                        message(code:'localized.click')+' '+
                        "<a href='/checkout/emailPassword?email=${params.email}'>"+' '+
                        message(code:'localized.here')+
                        '</a>'+' '+
                        message(code:'localized.invalid.password.email')
		redirect action:'index', params:[email:params.email]
	}
	def checkoutFlow = {
        begin {
            action {
        	    log.debug("checkoutFlow begin action params: $params")
        	    flow.cart = Cart.get(session.cartId)
				params.type = params.type?:AddressesCommand.GUEST
				params.type = params.type.toInteger()
				if (flow.cart) {
	        	    flow.stepTitles = stepTitles
	        	    if (params.type == AddressesCommand.RETURN_CUSTOMER) {
	        	    	flow.addrCmd = session.addrCmd?:upsService.populateAddressesCommand(session.consumer)
	        	    	flow.payShipCmd = session.payShipCmd?:upsService.populatePayShipCommand(session.consumer)
						flow.payShipCmd.upsServiceCode = flow.cart.upsServiceCode
						flow.cart.consumer = session.consumer
		        	    flow.cart.saveAccount = true
	        	    } else {
	            	    flow.addrCmd = session.addrCmd?:new AddressesCommand(type:params.type)
	            	    flow.payShipCmd = session.payShipCmd?:new PayShipCommand()
		        	    flow.cart.saveAccount = (params.type == AddressesCommand.NEW_CUSTOMER)
	        	    }
                    flow.cart.paypalToken = null
                    flow.cart.paypalPayerId = null
                    session.paypalPayerId = null
	        	    stepOne()
				} else {
					flash.message = message(code:'localized.cart.empty')//"Your shopping cart is empty"
					shop()
				}
            }
            on('stepOne').to('stepOne')
            on('shop').to('shop')
        }
        stepOne {
            on("cancel").to('cancel') 
            on("shop").to('shop')
            on("stepTwo") {AddressesCommand addrCmd ->
            	flow.addrCmd = addrCmd
            	session.addrCmd = flow.addrCmd
	    		if (!flow.addrCmd.validate()) return error()
        	}.to('stepTwo')
        }
        stepTwo {
            on("cancel") {session.cartId = null}.to('cancel') 
            on("shop").to('shop')
            on("stepOne").to('stepOne')
            on("calcShipping") {PayShipCommand payShipCmd ->
            	flow.payShipCmd = payShipCmd
				flow.payShipCmd.validate()
            	session.payShipCmd = flow.payShipCmd
	    		flow.cart.upsServiceCode  = payShipCmd.upsServiceCode
				try {
					println "**********8******8 yoyo"
					flow.cart.shippingCost = getShippingCost(flow.cart, flow.addrCmd, flow.payShipCmd)
					if (!flow.cart.save(flush:true)) {
						flow.cart.errors.each{println it}
						assert false
					}
					flash.message = message(code:'localized.shipping.cost')+' '+(flow.cart.shippingCost?flow.cart.shippingCost:'FREE') //"Your shipping cost will be: 
				} catch (Exception e) {
					setPayShipCmdWithSuggestedAddresses(session.addrCmd, flow.payShipCmd) 
					flash.message = e.message
				}
           		error()
            }.to('stepTwo')
            on("stepThree") {PayShipCommand payShipCmd ->
                payShipCmd.giftCard = payShipCmd.giftCard?.trim()
				def giftCardReduced = 0.00g
				if (payShipCmd.giftCard) {
					giftCardReduced = flow.cart.giftCardReduced(flow.cart.consumerShipTo?.state?:flow.cart.consumerBillTo?.state?:flow.addrCmd.shippingState?:flow.addrCmd.billingState)
				} 
            	flow.payShipCmd = payShipCmd
	    		if (!flow.payShipCmd.validate()) return error()
                if (payShipCmd.giftCard) {
                    GiftCard gc = null
                    try {
                        gc = GiftCard.findByCardNumber(payShipCmd.giftCard)
                    } catch (Exception e) {
                        log.error "Error looking up giftcard: ${e.toString()}"
                    }
                    if (!gc) {
                        flash.message = message(code:'localized.invalid.giftcard.no')//'Invalid gift card number'
                        return error()
                    }
                    if (gc.currentValue == 0.0F) {
                        flash.message = message(code:'localized.giftcard.empty') // 'Gift card has no value'
                        return error()
                    }
                    flow.cart.giftCard = gc
                }
				try {
					println "alksjflsajflsajflsajflf"
					flow.cart.shippingCost = getShippingCost(flow.cart, flow.addrCmd, flow.payShipCmd)
					flash.message = message(code:'localized.shipping.cost')+' '+(flow.cart.shippingCost?flow.cart.shippingCost:'FREE') //"Your shipping cost will be: 
				} catch (Exception e) {
					setPayShipCmdWithSuggestedAddresses(session.addrCmd, flow.payShipCmd) 
					flash.message = e.message
					return error()
				}
                if (payShipCmd.creditCardType != 'paypal') {
                    flow.cart.paypalToken = null
                    flow.cart.paypalPayerId = null
                    session.paypalPayerId = null
                } else if (!flow.cart.paypalToken) {
                    log.debug "PP_Sandbox_UserGuide Chap 5 Step 1: CheckoutController::checkout on(stepThree) "
                    def matcher = request.queryString =~ /execution=e(\d*)s(\d*)&?/
                    def step3Execution = 'e'+matcher[0][1]+'s'+(matcher[0][2].toInteger()+1)
                    def currentExecution = 'e'+matcher[0][1]+'s'+(matcher[0][2].toInteger())
                    // https://cms.paypal.com/cms_content/US/en_US/files/developer/PP_ExpressCheckout_AdvancedFeaturesGuide.pdf
                    // explains suppressing PayPal address prompting
                    def returnURL =  CH.config.grails.serverURL+"/checkout/paypal?execution=${step3Execution}&"+
                                    payShipCmd.attributeMap.collect { k,v -> "${k}=${v.encodeAsURL()}" }.join('&')
                    def cancelURL =  CH.config.grails.serverURL+"/checkout/index"
                    def paypalMap = [
                        USER:CH.config.paypal.user, PWD:CH.config.paypal.pwd, SIGNATURE:CH.config.paypal.signature,
                        VERSION:'95',
                        METHOD:'SetExpressCheckout',
                        PAYMENTREQUEST_0_PAYMENTACTION:'Sale',
                        PAYMENTREQUEST_0_AMT:flow.cart.total(flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingState:flow.addrCmd.shippingState),
                        PAYMENTREQUEST_0_CURRENCYCODE:'USD',
                        RETURNURL:returnURL,
                        CANCELURL:cancelURL,
                        ADDROVERRIDE:'1',
                        PAYMENTREQUEST_0_SHIPTONAME:flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingName:flow.addrCmd.shippingName,
                        PAYMENTREQUEST_0_SHIPTOSTREET:flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingAddress1:flow.addrCmd.shippingAddress1,
                        PAYMENTREQUEST_0_SHIPTOCITY:flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingCity:flow.addrCmd.shippingCity,
                        PAYMENTREQUEST_0_SHIPTOSTATE:flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingState:flow.addrCmd.shippingState,
                        PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE:'US',
                        PAYMENTREQUEST_0_SHIPTOZIP:flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingZip:flow.addrCmd.shippingZip,
                        PAYMENTREQUEST_0_SHIPTOPHONENUM:flow.addrCmd.phone
                    ]
                    def paypalParams = paypalMap.collect { k,v -> "${k}=${v.encodeAsURL()}" }.join('&')
                    def paypalResponse = parsePaypalResponse("https://${CH.config.paypal.url.nvp}?${paypalParams}".toURL().text)
                    log.debug "PP_Sandbox_UserGuide Chap 5 Step 2: CheckoutController::checkout on(stepThree) "
                    if (paypalResponse.ACK != 'Success') {
                        flash.message = 'Paypal error: '+paypalResponse.L_LONGMESSAGE0
                        return error()
                    }
                    flash.message = "Redirecting to Paypal"
                    flow.cart.paypalToken = paypalResponse.TOKEN
                }
				def shipToState = flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingState:flow.addrCmd.shippingState
				if (!payShipCmd.giftCard || (payShipCmd.giftCard && flow.cart.totalWithoutGiftCard(shipToState ) > flow.cart.giftCard.currentValue)) {
                    if (payShipCmd.creditCardType != 'paypal') {
                        def ccValidator = new CreditCardValidator(CreditCardValidator.VISA + CreditCardValidator.AMEX + CreditCardValidator.MASTERCARD + CreditCardValidator.DISCOVER)
                        if (!ccValidator.isValid(payShipCmd.creditCard)) {
                            flash.message = "Please enter a valid credit card"
                            return error()
                        }
                    }
					if (!payShipCmd.creditCardType?.size()) {
						flash.message = g.message(code:"payShipCommand.creditCardType.blank")
		           		return error()
					}
					if (payShipCmd.creditCardType != 'paypal' && !payShipCmd.ccid) {
						flash.message = g.message(code:"payShipCommand.ccid.min.notmet")
		           		return error()
					}
				}
	    		if (payShipCmd.coupon) {
					def coupon = Coupon.withCriteria(uniqueResult:true) {
						eq 'no', payShipCmd.coupon?:''
				        isNotNull 'approvedBy'
				        le 'start', new Date() 
				        ge 'expire', new Date()
					}
					if (!coupon) {
	               		flash.message = message(code:'localized.coupon.invalid') //"Invalid coupon"
	               		return error()
					}
	    			if (coupon.item && !flow.cart.items.find{it.item.id == coupon.item.id}) {
	               		flash.message = message(code:'localized.coupon.not.for.item') //"Your coupon is for an item that is not in your cart"
	               		return error()
					} else if (coupon.division && !flow.cart.items.find {it.item.division == coupon.division} ) {
	               		flash.message = message(code:'localized.coupon.not.for.division', args:[coupon.division.name]) // "Your coupon is for ${coupon.division.name} and no items in your cart are from that division"
	               		return error()
			   		} else if (coupon.category && !flow.cart.items.find {it.item.category == coupon.category} ) {
	               		flash.message = message(code:'localized.coupon.not.for.category', args:[coupon.category.name]) // "Your coupon is for one of ${coupon.category.name} and no items in your cart are from that category"
	               		return error()
			   		} 
					
	    			flow.cart.coupon = coupon
	    		}
            }.to('stepThree')
        }
        stepThree {
            on("cancel") {session.cartId = null}.to('cancel') 
            on("shop").to('shop')
            on("stepOne").to('stepOne')
            on("stepTwo").to('stepTwo')
            on("stepFour").to('stepFour') 
        }
        stepFour { // note: this is an "Action" 
        	action {
                if (flow.cart.paypalToken) {
                    flow.cart.paypalPayerId = session.paypalPayerId
                    log.debug "PP_Sandbox_UserGuide Chap 5 Step 5: CheckoutController::checkout on(confirm) "
                    def paypalMap = [
                        USER:CH.config.paypal.user, PWD:CH.config.paypal.pwd, SIGNATURE:CH.config.paypal.signature,
                        VERSION:'95',
                        TOKEN:flow.cart.paypalToken,
                        METHOD:'GetExpressCheckoutDetails'
                    ]
                    def paypalParams = paypalMap.collect { k,v -> "${k}=${v.encodeAsURL()}" }.join('&')
                    def paypalResponse = parsePaypalResponse("https://${CH.config.paypal.url.nvp}?${paypalParams}".toURL().text)
                    assert paypalResponse.ACK == 'Success'

                    log.debug "PP_Sandbox_UserGuide Chap 5 Step 6: CheckoutController::checkout on(confirm) "
                    paypalMap = [
                        USER:CH.config.paypal.user, PWD:CH.config.paypal.pwd, SIGNATURE:CH.config.paypal.signature,
                        VERSION:'95',
                        TOKEN:flow.cart.paypalToken,
                        PAYMENTACTION:'Sale',
                        PAYERID:flow.cart.paypalPayerId,
                        AMT:flow.cart.total(flow.addrCmd.shipToSameAsBillTo?flow.addrCmd.billingState:flow.addrCmd.shippingState),
                        METHOD:'DoExpressCheckoutPayment'
                    ]
                    paypalParams = paypalMap.collect { k,v -> "${k}=${v.encodeAsURL()}" }.join('&')
                    paypalResponse = parsePaypalResponse("https://${CH.config.paypal.url.nvp}?${paypalParams}".toURL().text)

                    log.debug "PP_Sandbox_UserGuide Chap 5 Step 7: CheckoutController::checkout on(confirm) "
                    if (paypalResponse.ACK != 'Success') {
                        log.error "paypal response error:"+paypalResponse
	               		return error()
                    }
                    flow.cart.paypalTransactionId = paypalResponse.TRANSACTIONID
                    flow.cart.paypalCorrelationId = paypalResponse.CORRELATIONID
                }

            	String ccError = checkoutService.checkout(flow.cart, flow.addrCmd, flow.payShipCmd)
            	if (ccError) {
            		flash.message = ccError
            		creditCardError()
            	} else {
	            	flow.orderHeader = OrderHeader.findByCompCodeAndOrderNo('01', flow.cart.orderNo)  
					log.debug "flow.addrCmd: "+flow.addrCmd
	            	if (flow.cart.saveAccount) {
						def consumer = 
	            			checkoutService.saveConsumer(flow.cart, flow.addrCmd, flow.payShipCmd,  
	            				     (flow.addrCmd.type == AddressesCommand.NEW_CUSTOMER)?new Consumer():Consumer.get(session.consumer?.id)
	            				    )
						flow.cart.consumer = consumer
						flow.cart.setShipToBillTo(consumer)
						flow.cart.save(flush:true)
	            	}
                    // Save gift card data in the cart
                    flow.cart.items.each { cartItem ->
                        if (cartItem.item.arDistrictCode.equals('GFC')) {
                            log.debug "found gift card: ${cartItem.item.desc}"
                            GiftCard gc = new GiftCard()
                            gc.itemNo = cartItem.item.itemNo
                            gc.originalValue = cartItem.item.retailPrice
                            gc.currentValue = gc.originalValue
                            gc.cardNumber = getGiftCardNumber()
                            gc.orderNumber = flow.orderHeader.orderNo
                            gc.consumerId = flow.cart?.consumer?.id
                            if (!gc.validate()) {
                                gc.errors.allErrors.each {
                                    log.error("error saving gift card: ${it}")
                                }
                            } else {
                                gc = gc.save(flush:true)
                                log.debug("saved gift card, id: ${gc.id}")
                                cartItem.giftCardId = gc.id
                                cartItem.save(flush:true)
                            }
                        }
                    }

	            	sessionFactory.currentSession.clear()
	            	clearSession()
	            	orderConfirmed()
            	}
        	}
            on("creditCardError").to("stepTwo")
            on("orderConfirmed").to("confirm")
        }
        cancel {
        	clearSession()
        	redirect action:'cancelCart'
        }
        shop {
        	redirect controller:'homePage', action:'home'
        }  
        confirm()
	}

    private int getGiftCardNumber() {
        Random ran = new Random(System.currentTimeMillis())
        def cards = []
        int num = Math.abs(ran.nextInt() + 1000000)
        log.debug("random number: ${num}")
        cards = GiftCard.findAllByCardNumber(num)
        log.debug("findAllCards: ${cards}")
        while (cards?.size() > 0) {
            log.debug("recalculating num: ${num}")
            num = Math.abs(ran.nextLong())
            cards = GiftCard.findByCardNumber(num)
        }
        log.debug "returning: ${num}"
        num
    }

	private void setPayShipCmdWithSuggestedAddresses(AddressesCommand addrCmd, PayShipCommand payShipCmd) {
		List upsRtnAddrs = upsAddressService.upsAddress(
				addrCmd.shipToSameAsBillTo?addrCmd.billingName:addrCmd.shippingName,
				addrCmd.shipToSameAsBillTo?addrCmd.billingAddress1:addrCmd.shippingAddress1,
				addrCmd.shipToSameAsBillTo?addrCmd.billingAddress2:addrCmd.shippingAddress2,
				addrCmd.shipToSameAsBillTo?addrCmd.billingCity:addrCmd.shippingCity,
				addrCmd.shipToSameAsBillTo?addrCmd.billingState:addrCmd.shippingState,
				addrCmd.shipToSameAsBillTo?addrCmd.billingZip:addrCmd.shippingZip,
			)
		payShipCmd.errors.reject("message.code", "Invalid address. ${(upsRtnAddrs?'Suggested addresses:':'')}");
		upsRtnAddrs.each {payShipCmd.errors.reject("message.code", it.addressLine1+', '+(it.addressLine2?it.addressLine2+',':'')+' '+it.city+', '+it.state+' '+it.zip)}
	}
	private void clearSession () {
    	session.cartId = null
    	session.payShipCmd = null
    	session.paypalPayerId = null
	    session.addrCmd = null 
	}
	private BigDecimal getShippingCost(Cart cart, AddressesCommand addr, PayShipCommand payShip) {
		try {
			boolean checkAddress = true
			if (CH.config.rateService.equals('UPS')){
				cart.shippingCost = upsService.getShippingCost(cart, addr, payShip, checkAddress)
			} else if (CH.config.rateService.equals('FEDEX')){
				cart.shippingCost = fedexService.getShippingCost(cart, addr, payShip, checkAddress)
			}
			cart.shippingCost
		} catch (Exception e) {
			def error = e.message 
			if (error == 'Address Validation Error on ShipTo address') {
				error = 'UPS cannot validate your address'
			}
			throw new Exception(error)
		}
   	}
    private Map parsePaypalResponse(String text) {
        text.split('&').inject([:]) {map, kv ->
           def (key, value) = kv.split('=').toList()
           if(value != null) {
              map[key] = URLDecoder.decode(value)
           }
           return map
        }
    }

}
