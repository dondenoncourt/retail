<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${params.division}</title>
<meta name="layout" content="${params.division?.replaceAll(/ /,'')}" />
</head>

<body>
<div id="verifyOrderMain">
    
    <g:render template='verifyOrder' model="[cart:cart]"/>
    <g:render template='cart' model="[cart:cart, ajax:false, verifyOrder:true]"/>
	<ul class="buttonList">
		<li><g:link action="products" params="[division:params.division]" class="button">Continue Shopping</g:link></li>
		<li><g:link action="cancel" params="[division:params.division]" class="button">Cancel</g:link></li>
        <% if (cart?.items?.size()) { %> 
            <li><a href="${createLink(controller:'shop', action:'checkoutPrompt')}?division=${params.division?.replaceAll(' ', '+')?:'kettlerusa'}" class="button">Edit Shipping or Bill-To</a></li>
            <li><a href="${createLink(controller:'shop', action:'cart')}?division=${params.division}" class="button">Edit Cart</a></li>
            <li><a href="${createLink(controller:'shop', action:'placeOrder')}?division=${params.division?.replaceAll(' ', '+')?:'kettlerusa'}" class="clickme" id="placeOrder">Place Order</a></li>
        <% } %>
	</ul>
</div>
<g:javascript>
    $('#placeOrder').click(function () {
        $('#placeOrder').hide();
    });
</g:javascript>
</body>
