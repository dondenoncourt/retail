<%@ page contentType="text/html"%>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kettlerusa" />
    <title>Checkout</title>
  </head>
<body> 
<div id="content"> 
<h1>Confirmation number: ${orderHeader.orderNo}</h1>
<p>Thank you for shopping with KETTLER.</p>
<% if (!emailTask) { %>
    <p>An order confirmation email has been sent to: ${addrCmd.email}.</p>
<% } %>
<% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { %>
	<p>Kettler will email you when your order ships.</p>
<% } %>


<% if (emailTask) { %>
		<g:render template='/layouts/socialMedia' />
<% } %>
   
<g:render template='verifyOrder' model="[addr:addrCmd, payShip:payShipCmd, cart:cart]"/>
<g:render template='cart' model="[cart:cart, addr:addrCmd, payShip:payShipCmd, orderHeader:orderHeader, emailTask:emailTask]"/>
<br/>
<% if (!emailTask) { %>
    <a href="#" onclick="window.print();"><img src="${resource(dir:"images/")}printer.png" alt="print"  /></a>
<% } %>
</div>
<g:javascript>
	$('#content').height($('#container').height());

	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-17400793-4']);
	_gaq.push(['_trackPageview']);
	_gaq.push(['_addTrans',
		'${orderHeader.orderNo}',           // order ID - required
		'Kettler Retail Web',  // affiliation or store name
		'${cart?.total()}',          // total - required
		'${(cart.calcTax(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState))}',           // tax
		'${(cart.shippingCost?:0)}',              // shipping
		'${addrCmd.billingCity}',       // city
		'${addrCmd.billingState}',     // state or province
		'USA'             // country
	]);
	<g:each in="${cart?.items}" var="item">
		_gaq.push(['_addItem',
			'${orderHeader.orderNo}',           // order ID - required
			'${item.item.itemNo}',           // SKU/code - required
			'${item.item.desc}',        // product name
			'${item.item.division.name}',   // category or variation
			'${(item.item.closeoutCode?item.item.specialPrice:item.item.retailPrice)}',          // unit price - required
			'${item.qty}'               // quantity - required
		]);
	</g:each>
	_gaq.push(['_trackTrans']); //submits transaction to the Analytics servers

	(function() {
		var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		// commented out / replaced per Amato:
        // ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';

		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	})();
	
</g:javascript>
</body>
</html>
