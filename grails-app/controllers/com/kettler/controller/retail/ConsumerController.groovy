package com.kettler.controller.retail

import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.GiftCard
import com.kettler.domain.orderentry.share.OrderHeader
import com.kettler.domain.orderentry.share.InvoicedOrderHeader

import com.kettler.domain.actrcv.share.Return
import com.kettler.domain.actrcv.share.ReturnItem

import com.kettler.domain.warranty.share.WarrantyCustomer 
import com.kettler.domain.warranty.share.WarrantyItem

class ConsumerController {

    static allowedMethods = [
                             create:"POST",
                             save:"POST",
                             update:"POST",
                             delete:"POST",
                             headerUpdate:"POST",
                             headerChange:"POST",
                             btChange:"POST",
                             btUpdate:"POST",
                             btDelete:"POST",
                             btSave:"POST",
                             stChange:"POST",
                             stUpdate:"POST",
                             stDelete:"POST",
                             stSave:"POST",
                             billToCreate:"POST",
                             billToUpdate:"POST",
                             billToChange:"POST",
                             billToAdd:"POST",
                             billToDelete:"POST",
                             shipToUpdate:"POST",
                             shipToChange:"POST",
                             shipToAdd:"POST",
                             shipToDelete:"POST"
                     ]


	def beforeInterceptor = {
	     log.debug "action: $actionName params: $params flash: $flash"
	}
	def afterInterceptor = {
	     log.debug "after action: $actionName params: $params"
	}

//    def index = {
//        forward(action: "list", params: params)
//    }

//    def list = {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [consumerList: Consumer.list(params), consumerTotal: Consumer.count()]
//    }

    def create = {
        def consumer = new Consumer()
        consumer.properties = params
        return [consumer: consumer]
    }

    def save = {
        def consumer = new Consumer(params)
        if (consumer.save(flush: true)) {
            forward(action: "show", id: consumer.id)
        } else {
            render(view: "create", model: [consumer: consumer])
        }
    }

    def show = {
        def consumer =  Consumer.get(session?.consumer?.id?:0) // params.id.toInteger())
        if (!consumer) {
            redirect controller:'register', action: "loginPrompt", model:params
        }
        else {
            [consumer: consumer]
        }
    }
	def giftcards = {
		[giftcards:GiftCard.findAllByConsumerId(session?.consumer?.id)]
	}

    def update = {
        def consumer = Consumer.get(session?.consumer?.id?:0)
        if (consumer) {
            if (params.version) {
                def version = params.version.toLong()
                if (consumer.version > version) {
                    
                    consumer.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'consumer.label', default: 'Consumer')] as Object[], "Another user has updated this Consumer while you were editing")
                    render(view: "edit", model: [consumer: consumer])
                    return
                }
            }
            consumer.properties = params
            if (!consumer.hasErrors() && consumer.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'consumer.label', default: 'Consumer'), consumer.id])}"
                forward(action: "show", id: consumer.id)
            }
            else {
                render(view: "edit", model: [consumer: consumer])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'consumer.label', default: 'Consumer'), params.id])}"
            forward(action: "list")
        }
    }

    def delete = {
        def consumer = Consumer.get(session?.consumer?.id?:0)
        if (consumer) {
            try {
                consumer.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'consumer.label', default: 'Consumer'), params.id])}"
                forward(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'consumer.label', default: 'Consumer'), params.id])}"
                forward(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'consumer.label', default: 'Consumer'), params.id])}"
            forward(action: "list")
        }
    }
    def headerUpdate = {
    	render template:'headerUpdate', model:[consumer:Consumer.get(params.id.toInteger())]
    }
    def headerChange = {
    	Consumer consumer = Consumer.get(params.id.toInteger())
    	consumer.properties = params
    	if (consumer.save()) {
    		flash.message = message(code:'localized.changes.saved') // 'Changes saved'
    	} else {
    		render view:'show', model:[consumer: consumer]
    		return
    	}
        forward(action: "show", id: params.id.toInteger(), params:params)
    }

    // Handle Bill To items
    def btChange = {
        def billTo = ConsumerBillTo.get(params.billToId as int)
        return [billTo: billTo, consumer: billTo.consumer, consumerId:params.consumerId as int]
    }

    def btUpdate = {
        def billTo = ConsumerBillTo.get(params.billToId as int)
        if (billTo) {
            if (params.version) {
                def version = params.version.toLong()
                if (billTo.version > version) {

                    billTo.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'consumer.label', default: 'Consumer')] as Object[], "Another user has updated this Consumer while you were editing")
                    render(view: "btChange", model: [billTo:billTo, consumerId: billTo.consumer?.id])
                    return
                }
            }
            billTo.properties = params
            if (!billTo.hasErrors() && billTo.save(flush: true)) {
                flash.message = message(code:'localized.bill.to.changed') //"Bill To changed"
                forward(action: "show", id: params.consumerId as int)
            }
            else {
                render(view: "btChange", model: [billTo: billTo, consumerId: billTo.consumer?.id])
            }
        }
        else {
            flash.message = message(code:'localized.bill.to.invalid') //"Bill To not found"
            forward(action: "show", id: params.consumerId.toInteger())
        }
    }

    def btDelete = {
        def consumer = Consumer.get(session?.consumer?.id?:0)
        def billTo = ConsumerBillTo.get(params.billToId as int)
        consumer.removeFromBillTos(billTo)
        billTo.delete(flush:true)
        consumer.save(flush:true)
        flash.message = message(code:'localized.bill.to.deleted') //'Bill To has been deleted'
        forward(action: "show", id: params.consumerId.toInteger())
    }

    def btCreate = {
        def billTo = new ConsumerBillTo()
        def consumer = Consumer.get(session?.consumer?.id?:0)
        billTo.consumer = consumer
        return [billTo: billTo, consumer: billTo.consumer]
    }

    def btSave = {
        def consumer = Consumer.get(session?.consumer?.id?:0)
        def billTo = new ConsumerBillTo(params)
        billTo.consumer = consumer
        if (billTo.validate()) {
	   		Consumer.withTransaction { status ->
	            consumer.addToBillTos(billTo)
	            consumer.save()
	   		}
			// force updates so RPG update trigger encrypts (insert not working...) 
	   		Consumer.withTransaction { status ->
	   			consumer.billTos.each{bill ->
	   				bill.addr2 = bill.addr2?bill.addr2+' ':' '
	   				bill.save()
	   			}
	   		}
            flash.message = message(code:'localized.bill.to.added') //"Bill To added"
            forward(action:'show', id:consumer.id)
        } else {
            render(view: "btCreate", model: [billTo: billTo, consumerId: consumer.id])
        }
    }

    // Handle Ship To items
    def stChange = {
        def shipTo = ConsumerShipTo.get(params.shipToId as int)
        return [shipTo: shipTo, consumer: shipTo.consumer, consumerId:params.consumerId as int]
    }

    def stUpdate = {
        def shipTo = ConsumerShipTo.get(params.shipToId as int)
        if (shipTo) {
            if (params.version) {
                def version = params.version.toLong()
                if (shipTo.version > version) {

                    shipTo.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'consumer.label', default: 'Consumer')] as Object[], "Another user has updated this Consumer while you were editing")
                    render(view: "stChange", model: [shipTo:shipTo, consumerId: shipTo.consumer?.id])
                    return
                }
            }
            shipTo.properties = params
            if (!shipTo.hasErrors() && shipTo.save(flush: true)) {
                flash.message = message(code:'localized.ship.to.changed') //"Ship To changed"
                forward(action: "show", id: params.consumerId as int)
            }
            else {
                render(view: "stChange", model: [shipTo: shipTo, consumerId: shipTo.consumer?.id])
            }
        }
        else {
            flash.message = message(code:'localized.ship.to.invalid') //"Ship To not found"
            forward(action: "show", id: params.consumerId.toInteger())
        }
    }

    def stDelete = {
        def consumer = Consumer.get(session?.consumer?.id?:0)
        def shipTo = ConsumerShipTo.get(params.shipToId as int)
        consumer.removeFromShipTos(shipTo)
        shipTo.delete(flush:true)
        consumer.save(flush:true)
        flash.message = message(code:'localized.ship.to.deleted') //'Ship To has been deleted'
        forward(action: "show", id: params.consumerId.toInteger())
    }

    def stCreate = {
        def shipTo = new ConsumerShipTo()
        def consumer = Consumer.get(session?.consumer?.id?:0)
        shipTo.consumer = consumer
        [shipTo: shipTo, consumer: shipTo.consumer, consumerId:consumer.id]
    }

    def stSave = {
        def consumer = Consumer.get(session?.consumer?.id?:0)
        def shipTo = new ConsumerShipTo(params)
        shipTo.consumer = consumer
        if (shipTo.validate()) {
            consumer.addToShipTos(shipTo)
            consumer.save(flush: true)
            flash.message = message(code:'localized.ship.to.added') //"Ship To added"
            forward(action:'show')
        } else {
            render(view: "stCreate", model: [shipTo: shipTo, consumerId: consumer.id])
        }
    }

    def billToCreate = {
        Consumer consumer = Consumer.get(params.consumerId.toInteger())
        ConsumerBillTo billTo = new ConsumerBillTo(params)
    	billTo.consumer = consumer
        render template:'billToUpdate', model:[billTo:billTo]
    }
    def billToUpdate = {
    	render template:'billToUpdate', model:[billTo:ConsumerBillTo.get(params.id.toInteger())]
    }
    def billToChange = {
    	ConsumerBillTo billTo = ConsumerBillTo.get(params.billToId.toInteger())
    	billTo.properties = params
    	assert billTo.save()
    	render template:'billToStatic', model:[billTo:billTo]
    }
    def billToAdd = {
    	Consumer consumer = Consumer.get(params.consumerId.toInteger())
    	ConsumerBillTo billTo = new ConsumerBillTo(params)
    	billTo.consumer = consumer
    	if (!billTo.save()) {
    		billTo.errors.each{
                log.error it
                flash.message = it
            }
    	} else {
    	    //render template:'billToStatic', model:[billTo:billTo]
            flash.message = message(code:'localized.bill.to.addr.added') //'Bill To address added'
        }
        forward(action: "show", id: consumer.id)
    }
    def billToDelete = {
    	ConsumerBillTo.get(params.id.toInteger()).delete()
    	render "" // to empty the div
    }
    def shipToUpdate = {
    	render template:'shipToUpdate', model:[shipTo:ConsumerShipTo.get(params.id.toInteger())]
    }
    def shipToChange = {
    	ConsumerShipTo shipTo = ConsumerShipTo.get(params.shipToId.toInteger())
    	shipTo.properties = params
    	assert shipTo.save()
    	render template:'shipToStatic', model:[shipTo:shipTo]
    }
    def shipToAdd = {
    	Consumer consumer = Consumer.get(params.consumerId.toInteger())
    	ConsumerShipTo shipTo = new ConsumerShipTo(params)
    	shipTo.consumer = consumer
    	if (!shipTo.save()) {
    		shipTo.errors.each{log.error it}
    	}
    	render template:'shipToStatic', model:[shipTo:shipTo]
    }
    def shipToDelete = {
    	ConsumerShipTo.get(params.id.toInteger()).delete()
    	render "" // to empty the div
    }

    def orderHst = {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        params.sort = 'lastUpdated'
        params.order = 'desc'
        def crsOrders = [:]
        def consumerId = session?.consumer?.id
        if (!consumerId) {
            consumerId = 0
        }
        log.debug("consumerId: ${consumerId}, params: ${params}")
        def carts = Cart.findAllByConsumerIdAndOrderPlaced(consumerId,true,params)
        carts.each { cart ->
        	def order = OrderHeader.findByCompCodeAndOrderNo('01', cart.orderNo) 
        	if (!order) {
        		order = InvoicedOrderHeader.findByCompCodeAndOrderNo('01', cart.orderNo)
        	}
        	crsOrders.put(cart.id, order)
        }
        def totalCarts = Cart.countByConsumerIdAndOrderPlaced(session?.consumer?.id?:0,true)
        log.debug "totalCarts = ${totalCarts}"
        [carts: carts, cartTotal: totalCarts,
                consumerId: params.consumerId as int, 
                crsOrders: crsOrders]
    }
	
	def warranties = {
		[warranties: WarrantyItem.findAllByCustNo(WarrantyCustomer.findByEmail(session?.consumer?.email)?.id?:9999999)]
	}

    def orderDetail = {
        def cart = Cart.get(params.cartId as int)
        
    	def orderHeader = OrderHeader.findByCompCodeAndOrderNo('01', cart.orderNo) 
    	if (!orderHeader) {
    		orderHeader = InvoicedOrderHeader.findByCompCodeAndOrderNo('01', cart.orderNo)
    	}
        assert orderHeader
        println orderHeader
		log.debug cart.billToId
		PayShipCommand payShip = PayShipCommand.populatePayShipCommand(session?.consumer)
		AddressesCommand addr = AddressesCommand.populateAddressesCommand(session?.consumer)
        [cart: cart, orderHeader: orderHeader, addr:addr, payShip:payShip]
    }
	
	def marketing = {
		Consumer.get(params.id).marketing = params.marketing == 'true'
		render 'ok'	
	}
}
