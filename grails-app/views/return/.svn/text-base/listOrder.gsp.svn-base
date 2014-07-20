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
        <g:render template='ltlShipment' model="[cart:cart,manifest:manifest,edit:true]"/>
    <% } else { %>
        <g:render template='upsShipment' model="[cart:cart,shippingCost:shippingCost]"/>
    <% } %>
  </div>
  <br/>
  <ul class="buttonList">
      <% if (cart.upsServiceCode == Cart.LTL) { %>
          <li><a href="${createLink(controller:'return', action:'createLtlRA')}" class="clickme createReturn">Create Return</a></li>
      <% } else { %>
	      <li><a href="${createLink(controller:'return', action:'continueReturn')}" class="clickme createReturn">Continue Return</a></li>
      <% } %>
      <li><a href="#" onclick="history.go(-1);return false;" class="button">Cancel</a></li>
      
  </ul>
</div>
<g:javascript src="jquery/jquery-1.4.2.js"/>
<g:javascript>
    $(".createReturn").click(function() {
         $("#logoImg").attr("src", "${resource(dir:'images',file:'spinner35x35.gif')}");
         $("#createReturn").hide();
    });
    
    $("input.number").keyup(function(){
        if ($(this).val() != "") {
             $(this).val( $(this).val().replace(/[^0-9\.]/g, "") );
        }
    });             
    $("input[name=quantity]").change(function (event) {
        event.target.form.submit();
    });
    $("#upsPickup").click(function() {
        $("#upsPickupForm").submit()
    });
</g:javascript>
</body>
</html>
