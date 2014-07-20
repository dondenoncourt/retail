<%@ page contentType="text/html"%>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="layout" content="kettlerusa" />
	<title>Confirmation</title>
</head>
<body>
<div id="content"> 
<h1>Confirmation number: ${orderHeader.orderNo}</h1>
<p>Thank you for shopping with KETTLER.</p>
<% if (!emailTask) { %>
    <p>An order confirmation email has been sent to: ${cart.email}.</p>
<% } %>
<p>Kettler will email you when your order ships.</p>
   
<g:render template='verifyOrder' model="[cart:cart]"/>
<g:render template='cart' model="[cart:cart, ajax:false, verifyOrder:true]"/>
<br/>
</div>
<% if (!cart.saveAccount) { %>
    <g:javascript>
        var params = '&id=${cart.id}';
        $.ajax({
           url: "${createLink(action:'deleteCart')}",
           data: params,
           type: 'POST'
        }); 
    </g:javascript>
<% } %>
</body>
</html>
