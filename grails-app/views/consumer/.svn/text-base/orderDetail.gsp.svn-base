<%@ page import="com.kettler.domain.orderentry.share.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="yourAccount"/>
  <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  <title>Order Detail</title>
</head>
<body>
<div id="yourAccountContent">
  <h1>Order Details for Order Number: ${cart.orderNo}</h1>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <div class="dialog">
  
 	<dl> 
        <dt>Order Date:</dt><dd><g:formatDate format="MM-dd-yy" date="${orderHeader.dateCreated}"/></dd>
		<g:if test="${orderHeader.shipInstructions != 'R-TRAILER' && orderHeader?.freightTrackingNo && orderHeader?.carrierCode == 'UPSN'}">
			<dt>UPS Tracking No:</dt>
			<dd><g:link controller="upsTracking" action="search" params="[number:orderHeader?.freightTrackingNo,orderNo:orderHeader?.orderNo]">${orderHeader?.freightTrackingNo}</g:link></dd>
		</g:if>
		<g:elseif test="${orderHeader.shipInstructions != 'R-TRAILER' && orderHeader?.freightTrackingNo && (orderHeader?.carrierCode == 'FDEG' || orderHeader?.carrierCode == 'FDE')}">
			<dt>FedEx Tracking No:</dt>
			<dd><g:link controller="fedexTracking" action="search" params="[number:orderHeader?.freightTrackingNo,orderNo:orderHeader?.orderNo]">${orderHeader?.freightTrackingNo}</g:link></dd>
		</g:elseif>
    </dl>
    <g:render template='/checkout/verifyOrder' model="[cart:cart]"/>
    <g:render template='/checkout/cart' model="[cart:cart, ajax:false, verifyOrder:true]"/>
    </div>
    <br/>
    <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    <br/>
</div>
</body>
</html>
