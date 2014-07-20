package com.kettler.controller.retail
import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.item.share.ItemMaster
import com.kettler.domain.orderentry.share.CartItem
import com.kettler.domain.orderentry.share.GiftCard
import com.kettler.domain.item.share.WebDivision


class GiftCardController {
    def recaptchaService

    def beforeInterceptor = {
        log.debug "giftcard action: $actionName params: $params flash: $flash"
    }
    def afterInterceptor = {
        log.debug "giftcard after action: $actionName params: $params flash: $flash"
    }


    def index = {
        def items = ItemMasterExt.findAllByArDistrictCode('GFC')
        log.debug "found ${items.size()} gift cards"
		def cart
   		if (session.cartId) {
   			cart = Cart.get(session.cartId)
   		}
        [params: params, noShowFilter: true, items: items, division:params.division?WebDivision.findByName(params.division):null, cart:cart]
    }

    def buy = {
        if (!params.division) { // probably not a browser request
            flash.message = message(code:'localized.division.not.passed') //"Invalid add to cart request: division not passed"
            log.warn flash.message+" for gift card buy action params:$params"
            redirect controller:'homePage', action:'home'
            return
        }
        flash.message = null
        ItemMasterExt item = ItemMasterExt.get(params.id)
        def cart
        if (!session.cartId) {
            cart = new Cart()
            assert cart
            assert !cart.hasErrors()
            if (session.consumer) {
                cart.consumer = session.consumer
            }
        } else {
            cart = Cart.get(session.cartId)
        }
        def cartItem = new CartItem(qty:1, item:item)
        cart.addToItems(cartItem)
        if (!cart.save(flush:true)) {
            flash.message = message(code:'localized.cart.save.errors') //"Problem saving shopping cart information"
            log.warn flash.message+" for gift card buy action params:$params"
            redirect controller:'homePage', action:'home'
            return
        }
        log.debug "added: ${cartItem} to shopping cart"
//        doUpsRate(cart)
        session.cartId = cart.id
  //      redirect(controller: 'shop', action: 'cart', params: [params: params, division:WebDivision.findByName(params.division)])
            render template:'/shop/cart', model:[cart:cart, division:item.division.name, params:params]
    }

    def showCard = {
        def card = GiftCard.get(params.id)
        if (!card) {
            flash.message = message(code:'localized.giftcard.not.found') //"Your gift card was not found."
            log.warn flash.message+" for gift card showCard action params:$params"
            redirect controller:'homePage', action:'home'
            return
        }
        [cardNumber: card.cardNumber, origAmount: card.originalValue, remaining: card.currentValue,
        itemNo: card.itemNo]
    }

    def cardInfo = {
        recaptchaService.cleanUp(session)
        session["recaptcha_challenge_field"] = null
        session["recaptcha_response_field"] = null

        [params: params, division:params.division?WebDivision.findByName(params.division):null]
    }

    def lookup = {
        flash.message = null
        def recaptchaOK = true
        log.info "****captcha lookup"
        if (!recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)) {
            recaptchaOK = false
        }
        recaptchaService.cleanUp(session)
//        session["recaptcha_challenge_field"] = null
//        session["recaptcha_response_field"] = null
        def gc
        if (recaptchaOK) {
            try {
                gc = GiftCard.findByCardNumber(params.cardNumber)
            } catch (Exception e) {
                log.error "Error finding card: ${e.toString()}"
                gc = null
            }
            if (gc) {
                [giftCard: gc, division:WebDivision.findByName(params.division)]
            } else {
                flash.message = message(code:'localized.giftcard.no.invalid', args:[params.cardNumber]) //"Could not find gift card with number: ${params.cardNumber}"
                log.error "card not found, params: ${params}"
                redirect(action: "cardInfo", params: [division:WebDivision.findByName(params.division)])
            }
        } else {
            flash.message =message(code:'localized.captcha.invalid') // "CAPTCHA words did not match, please try again."
            log.error "CATCHA match failed, params: ${params}"
            redirect(action: "cardInfo", params: [division:WebDivision.findByName(params.division)])
        }
    }

}
