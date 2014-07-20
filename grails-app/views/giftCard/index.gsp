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
    <title>${division?.title} Gift Cards</title>
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
	<div id="content" class="articleContent" style="width:100%; height:400px;margin-left:10px;">
	    <g:if test="${flash.message}">
		    <div class="top"> 
	    	     <div class="message">${flash.message}</div>
		    </div> 
       	</g:if>
        <div id="giftCardVerbiage">
            <% if ( ['bikes', 'fitness', 'toys', 'patio','table tennis'].find{it == params.division} ) { %>
	              <g:render template="/verbiage/${params?.division}/giftcard" />
            <% } else { %>
	              <g:render template="/verbiage/giftcard" />
            <% } %>
        </div>
        <div id="cardInfo">
            <g:link action="cardInfo" class="button" params="${params}"><g:message code="button.check.gift.card.balance"/></g:link>
        </div>
        <g:each in="${items}" var="item" status="i">
            <div class="product">
                <div class="productImage">
                    <img src="${resource(dir:"images/giftcard")}/${item?.itemNo}.jpg" alt="1"  />
                    <p>${item?.desc}</p>
                    <% // MikeB if (!cart?.items || !cart?.items.size()) { %>
	                    <p><g:remoteLink  class="clickme" action="buy" id="${item.id}"
	                                   update="floatingCart" params="[quantity:item.minQty,ajax:true,division:params?.division]"
	                                   onSuccess="setCartClose();addToMiniCart();" title="click to add ${item.desc} to your cart"
	                    >Add to Cart</g:remoteLink></p>
                    <% // MikeB } %>
                </div>
            </div>
        </g:each>

	</div>
    <div id="floatingCart"></div>
    <g:javascript>
    	try {
	        $('#floatingCart').draggable();
	    } catch { }
    </g:javascript> 
</body> 
</html>
        
