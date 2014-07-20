<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <% if (division) { %>
	    <meta name="layout" content="${(['contract','store'].find{it == params.mode})?params.mode:(params.division?.replaceAll(' ',''))}" />
	<% } else { %>
	    <meta name="layout" content="kettlerusa" />
	<% } %>
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
	<div id="content" class="articleContent" style="height:400px;margin-left:10px;">
	    <g:if test="${flash.message}">
		    <div class="top"> 
	    	     <div class="message">${flash.message}</div>
		    </div> 
       	</g:if>
        <div class="giftCardInfo">
            <g:form name="infoForm" action="lookup" params="${params}">
            	<div>
	            	<label for="cardNumber"> Card Number: </label>
	                <input name="cardNumber" type="text"/><br/>
            	</div>
                <recaptcha:ifEnabled>
                    <recaptcha:recaptcha theme="clean"/>
                </recaptcha:ifEnabled>
                <recaptcha:ifDisabled>
                    <p style="font-weight: bold;">CAPTCHA disabled.</p>
                </recaptcha:ifDisabled>
                <input type="submit" class="clickme" value="Get Information" />
            </g:form>
        </div>
	</div>
</body>
</html>
        