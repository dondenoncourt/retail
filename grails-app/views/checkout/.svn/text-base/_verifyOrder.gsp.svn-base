<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="com.kettler.controller.retail.AddressesCommand" %>
<%@ page import="com.kettler.controller.retail.PayShipCommand" %>

<div id="cartBillTo">
	<h2>Bill-To:</h2>
	${addr.billingName}<br/> 
	${addr.billingAddress1}<br/>
	<% if (addr.billingAddress2) { %>
	    ${addr.billingAddress2}<br/>
	<% } %>
	<% if (payShip.creditCardType == 'paypal') { %>
		 PayPal Transaction Id: ${cart.paypalTransactionId}
	<% } else if (payShip.creditCard?.size() > 12) { %>
		${addr.billingCity}, ${addr.billingState} ${addr.billingZip}<br/>
		Card: ${payShip.creditCardType.toUpperCase()} <br/>
		**** **** **** ${payShip.creditCard[12..(payShip.creditCard.size()-1)]}<br/>
	    Exp: ${payShip.month}/${payShip.year}<br/>
	<% } %>
	<% if (payShip.giftCard) { %>
		Gift Card #: ${payShip.giftCard}
	<% } %>
</div>
<% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { // MFB %>
<div id="cartShipTo">
	<h2>Ship-To:</h2>
	<% if (!addr.shipToSameAsBillTo) { %>
	    ${addr.shippingName}<br/> 
	    ${addr.shippingAddress1}<br/>
	    <% if (addr.shippingAddress2) { %>
	        ${addr.shippingAddress2}<br/>
	    <% } %>
	    ${addr.shippingCity}, ${addr.shippingState} ${addr.shippingZip}<br/>
	<% } else { %>
	    Same as Bill-To<br/><br/>
	<% } %>
</div>
<% } %>
<div class="forceClearLeft"></div>
