<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="com.kettler.domain.orderentry.share.SalesTax" %>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<%-- this might not be used, consider deleting /show/_cart is used instead --%>
<div id="cartTop">
   <h2>Order Detail:</h2>
<table>
    <tbody>
        <tr>
            <th/><th>Description</th>
            <th/><th>Qty</th><th>Amount</th>
        </tr>
        <g:each in="${cart?.items}" var="item">
            <g:hasErrors bean="${item}">
                <div class="errors"><g:renderErrors bean="${item}" as="list" /></div>
            </g:hasErrors>
            <tr>
                <td class="imageTiny">
                    <% if (item.item.arDistrictCode.equals("GFC")) { %>
                        <img class="cart" src="${resource(dir:"images/giftcard")}/${item.item.itemNo}.jpg"
                             alt="${item.item?.desc}" />
                    <% } else { %>
                        <img class="cart" src="${resource(dir:"images/", absolute:true)}${item.item?.division?.name}/${item.item?.category?.name}/${item.item.itemNo}.jpg"
                            alt="${item.item?.desc}" />
                    <% } %>
                </td>
                <td><%-- if available, use color, or, if available, frame size, else standard item for desc --%>
                    ${(item.itemWithColor?:(item.itemWithFrameSize?:item.item)).desc} 
                    ${item.itemWithFrameSize?.size} ${item.itemWithFrameSize?.sizeUom}<%-- won't show unless size selected--%>
                </td>
                <td>
                </td>
                <td>
                        <span class="number">${item.qty}</span>
                </td>
                <td class="number">
                    <g:formatNumber number="${(item.item.closeoutCode?item.item.specialPrice:item.item.retailPrice) * item.qty}" 
                          type="currency" currencySymbol="\$" />
                </td>
            </tr>
            <% if (item.itemIdWithColor) { %>
                <tr>
                    <td>Color:</td>
                    <td>
                        ${item.itemWithColor.color?:'blank'}
                    </td>
                </tr>
                <tr> <td/> </tr>
            <% } %>
            <% if (item.item.arDistrictCode.equals("GFC")) { %>
            <tr>
                <td/>
                <td/>
                <td>
		            <% if (emailTask) { %>
            			<g:link controller="consumer" action="giftcards" absolute="true" class="button" title="Click to print gift card">Click to print your gift card.</g:link>
					<% } else { %>
			       		<a class="pdfAnchor" href="${ConfigurationHolder.config.grails.serverURL}/pdf/show?url=/giftCard/showCard/${item.giftCardId}">Click to print your gift card.</a>
					<% } %>
				</td>
            </tr>
            <% } %>
            <% if (item.coupon) { %>
                <tr>
                    <td />
                    <td>Item Coupon:</td>
                    <td>${(item.coupon?.percent?item.coupon.percent+'%':g.formatNumber(number:item.coupon?.amount, type:'currency',currencyCode:'USD'))}</td>
                    <td/>
                    <td class="number">
                        <% if (item.coupon.percent) {  %>
                            <g:formatNumber number="${(item.item.retailPrice * item.qty) * (item.coupon.percent/100)}" type="currency" currencySymbol="\$" />
                        <% } else {  %>
                            <g:formatNumber number="${item.coupon.amount}" type="currency" currencySymbol="\$" />
                        <% }  %>
                    </td>
                </tr>
            <% } %>
        </g:each>
        <% if (cart?.coupon) { %>
            <tr>
                <td />
                <td>Order Coupon:</td>
                <td>${(cart.coupon.percent?cart.coupon.percent+'%':g.formatNumber(number:cart.coupon.amount, type:'currency',currencyCode:'USD'))}</td>
                <td/>
                <td class="number">
                    <% if (cart.coupon.percent) {  %>
                        <g:formatNumber number="${(cart.totalItemCost() * (cart.coupon.percent/100))}" type="currency" currencySymbol="\$" />
                    <% } else  {  %>
                        <g:formatNumber number="${cart.coupon.amount}" type="currency" currencySymbol="\$" />
                    <% }  %>
                </td>
            </tr>
        <% } %>
        <% if (cart?.consumerBillTo?.zipCode || cart.shippingCost) {  %>
	        <tr>
	            <td/>
	            <% if (cart.rateService.equals("UPS")) { %>
	            	<td>${Cart.SHIP_METHODS_UPS[cart?.upsServiceCode]?:''}</td>
	            <% } else  {  %>
	            	<td>${Cart.SHIP_METHODS_FEDEX[cart?.upsServiceCode]?:''}</td>
	             <% } %>
	            <td/>
	            <td/>
	            <td class="number">
	            <% if (cart?.shippingCost) { %>
	                <g:formatNumber number="${cart.shippingCost}" type="currency" currencySymbol="\$" />
	            <% } else if (cart?.items && cart?.total() >= 40g) { %>
	                <span class="emphasis">free</span>
	            <% } %>
	            </td>
	        </tr>
        <% } %>
        <% def tax = 0.00g %>
        <% if ((SalesTax.hasSalesTax(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state) || SalesTax.hasSalesTax(addr?.shippingState?:addr?.billingState)) &&
			   !cart?.items.find{it.item.arDistrictCode.equals("GFC")}                                 ) {  
		%>
            <tr>
                <td/>
                <td>Tax:</td>
                <td/>
                <td/>
                <td class="number">
                    <% tax = (cart.calcTax(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState)) %>
                    <g:formatNumber number="${tax}" type="currency" currencySymbol="\$" />
                </td>
            </tr>
        <% } %>
        <tr/>
        <% if (cart?.giftCard && !cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { %>
	        <tr>
	            <td />
	            <td>Gift Card:</td>
	            <td/>
	            <td/>
	            <td class="number">
	                <g:formatNumber number="${cart.giftCardReduced(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState)}" type="currency" currencySymbol="\$" />
	            </td>
	        </tr>
        <% } %>
        <tr>
            <td/><td/>
            <td class="total">Total:</td>
            <td/>
            <td class="number">
            	<g:formatNumber number="${cart.total(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState)}" type="currency" currencySymbol="\$" />
            </td>
        </tr>
    </tbody>
</table>
</div>    
