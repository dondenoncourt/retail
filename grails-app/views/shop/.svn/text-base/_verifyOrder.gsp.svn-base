<%@ page import="com.kettler.domain.orderentry.share.Cart" %>

<div id="cartBillTo">
	<h2>Bill-To:</h2>
	${cart.consumerBillTo.name}<br/> 
	${cart.consumerBillTo.addr1}<br/>
	<% if (cart.consumerBillTo.addr2) { %>
	    ${cart.consumerBillTo.addr2}<br/>
	<% } %>
	${cart.consumerBillTo.city}, ${cart.consumerBillTo.state} ${cart.consumerBillTo.zipCode}<br/>
	Card: ${cart.consumerBillTo.cardType.toUpperCase()} <br/>
	**** **** **** ${cart.consumerBillTo.cardNo[12..(cart.consumerBillTo.cardNo.size()-1)]}<br/>
    Exp: ${cart.consumerBillTo.expMonth}/${cart.consumerBillTo.expYear}<br/>
</div>
<div id="cartShipTo">
	<h2>Ship-To:</h2>
	<% if (cart.consumerShipTo) { %>
	    ${cart.consumerShipTo.name}<br/> 
	    ${cart.consumerShipTo.addr1}<br/>
	    <% if (cart.consumerShipTo.addr2) { %>
	        ${cart.consumerShipTo.addr2}<br/>
	    <% } %>
	    ${cart.consumerShipTo.city}, ${cart.consumerShipTo.state} ${cart.consumerShipTo.zipCode}<br/>
	<% } else { %>
	    Same as Bill-To<br/><br/>
	<% } %>
</div>
<div class="forceClearLeft"></div>
