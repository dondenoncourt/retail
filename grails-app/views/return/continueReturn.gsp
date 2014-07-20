<%@ page import="com.kettler.domain.orderentry.share.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="yourAccount"/>
  <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  <title>Order Return</title>
</head>
<body>
<div id="yourAccountContent">
  <br/>
  <h1>Process Return for Order Number: ${cart.orderNo}</h1>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <div class="dialog">
  
    <dl> 
        <dt>Order Date:</dt><dd><g:formatDate format="MM-dd-yy" date="${orderHeader.dateCreated}"/></dd>
        <dt>Ship Date:</dt><dd><g:formatDate format="MM-dd-yy" date="${orderHeader.dateShipped}"/></dd>
    </dl>
    <g:render template='/shop/verifyOrder' model="[cart:cart]"/>
    <% if (cart.upsServiceCode == Cart.LTL) { %>
        <g:render template='ltlShipment' model="[cart:cart,manifest:manifest,edit:false]"/>
    <% } else { %>
        <g:render template='upsShipment' model="[cart:cart,shippingCost:shippingCost]"/>
    <% } %>
  </div>
  <br/>
  <ul class="buttonList">
      <% if (cart.upsServiceCode == Cart.LTL) { %>
          <li><a href="${createLink(controller:'return', action:'createLtlRA')}" title="Confirm the return" class="clickme createReturn">Create Return</a></li>
      <% } else if (packList.findAll{it.returnIt}.size()) { %>
          <li><a href="${createLink(controller:'return', action:'createUpsRA')}" title="Confirm the return" class="clickme createReturn">Create Return</a></li>
      <% } %>
      <li><a href="${createLink(controller:'consumer', action:'orderHst')}?return=true" class="button" title="Cancel the return process">Cancel</a></li>
  </ul>
  <% if (packList.findAll{it.returnIt}.size()) { %>
		If you would like to ship your own method click here: 
		<a href="${createLink(controller:'return', action:'createSelfShipRA')}" class="button createReturn"
		  onclick="return confirm('Are you sure you want to create a self-shipping return?');"
		>Self-Shipping Return</a>
  <% } %>
</div>
<g:javascript src="jquery/jquery-1.4.2.js"/>
<g:javascript>
    $(".createReturn").click(function() {
         $("#logoImg").attr("src", "${resource(dir:'images',file:'spinner35x35.gif')}");
         $("#createReturn").hide();
    });
    $("#upsPickup").click(function() {
        $("#upsPickupForm").submit()
    });
    
	if ($('.total').html().match(/.\(./)) {
		$('.buttonList').hide();
		$('.createReturn').hide();
	    $('.dialog').before('<div class="message">Return shipping will exceed amount of refund.</div>');
	}
</g:javascript>
</body> 
</html>
