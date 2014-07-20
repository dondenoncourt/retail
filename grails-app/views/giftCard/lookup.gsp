<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="${(['contract','store'].find{it == params.mode})?params.mode:(params.division?.replaceAll(' ',''))}" />
    <meta name="description" content="KettlerUSA gift cards for ${division} "/>
    <meta name="keywords" content="gift cards "/>
    <title>Gift Cards Information</title>
    <link href="${createLinkTo(dir:'css',file:'jquery-ui.css')}" rel="stylesheet" type="text/css" />
    <g:javascript src="jquery-ui.min.js"/>
    <g:javascript src="jquery/jquery-1.4.2.js"/>
    <g:javascript>
        $(document).ready(function() {
            $('#giftcard').addClass("active")
        });
    </g:javascript>
</head> 
<body> 
	<div id="content" class="articleContent" style="height:400px;">
	    <g:if test="${flash.message}">
		    <div class="top"> 
	    	     <div class="message">${flash.message}</div>
		    </div> 
       	</g:if>

        <h3>Information about your gift card</h3>
        Purchase Date: <g:formatDate format="MM/dd/yyyy" date="${giftCard.dateCreated}"/> <br/>
        Original Value: <g:formatNumber number="${giftCard.originalValue}" type="currency" currencySymbol="\$" /> <br/>
        Current Value: <g:formatNumber number="${giftCard.currentValue}" type="currency" currencySymbol="\$" /> <br/>
        <p><g:link action="cardInfo" params="[division: division]">Check Another Card</g:link></p>
	</div>
</body>
</html>
        
