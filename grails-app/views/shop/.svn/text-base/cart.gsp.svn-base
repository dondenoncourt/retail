<%@ page import="com.kettler.domain.item.share.ItemMasterExt"%>
<%@ page import="com.kettler.domain.item.share.ItemMaster"%>
<%@ page import="com.kettler.domain.orderentry.share.Coupon"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${division}</title>
<meta name="layout" content="${division?.replaceAll(/ /,'')}" />
</head>

<body>
<div id="cartMain">
   <g:render template='cart' model="[cart:cart, ajax:false]"/>
	<ul class="buttonList">
		<%--<li>--%>
        <%--   <a id="otherkettler" href="${resource(contextPath:'/',absolute:true)}" class="button" title="Continue shopping">Continue Shopping</a>--%>
		<%--</li>--%>
		<%--<li><a class="button" href="#">Update</a></li>--%><%-- quantity onchange will invoke --%>
		<%--<li><g:link action="cancel" params="[division:division]" class="button">Clear Cart</g:link></li>--%>
        <%--<li class="promptShippingAddr">--%>
     		<%--<a class="button" title="Recalculate shipping cost">--%>
				<%--ReCalc Shipping--%>
			<%--</a>--%>
     	<%--</li>--%>
		<% if (cart?.items?.size()) { %> 
	    	<%--<li><a href="${createLink(controller:'shop', action:'checkoutPrompt')}?division=${division?.replaceAll(' ', '+')?:'kettlerusa'}" class="clickme">Checkout1</a></li>--%>
			
		<% } %>
	</ul>
</div>

<g:javascript src="jquery/jquery-1.4.2.js"/>
<g:javascript>
    $("input.number").keyup(function(){
        if ($(this).val() != "") {
             $(this).val( $(this).val().replace(/[^0-9\.]/g, "") );
        }
    });
    $("a[href$=cancel]").click(function(){
        if (!confirm("Are you sure")) {
            $("a[href$=cancel]").attr('href', '');
        }
    });
    <% if (division == 'patio') { %>
        $('#sidebar').hide();
    <% } %>
	$('li.promptShippingAddr a').click(function() {
		getShippingCost();
	});

    <%--<% if (!params.shipZip && !session.shipZip) { %>--%>
        <%--$('li.promptShippingAddr a').html('Recalc Shipping');--%>
        <%--$('li.promptShippingAddr a').addClass('clickme');--%>
          <%--$('.promptShippingAddr').hide();--%>
      <%--<% } %>--%>

</g:javascript>

</body>
