package com.kettler.session

import javax.servlet.http.HttpSessionListener; 
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent; 
import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.CartItem
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo

public class RetailSessionListener implements HttpSessionListener { 

	public void sessionCreated(HttpSessionEvent event) { 
	} 
	
	public void sessionDestroyed(HttpSessionEvent event) { 
		HttpSession session = event.getSession();
		if (session.cartId) { // delete if saveAccount false
	   		def cart = Cart.get(session.cartId)
			if (cart && !cart.saveAccount) {
				cart.delete()
				println "deleted cart due to http session timeout"
			}
		}
		if (session.consumer && session.consumer.id && !session.consumer.password) { // delete
			println "deleting consumer:"+session.consumer.name+" due to http session timeout"
			session.consumer.delete()
		}
	} 
}