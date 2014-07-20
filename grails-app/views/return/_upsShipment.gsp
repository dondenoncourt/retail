<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="com.kettler.domain.varsity.share.ShippingManifest" %>
<%@ page import="com.kettler.domain.varsity.share.Packer" %>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.orderentry.share.InvoicedOrderDetailItem" %>



<div id="cartTop">
    <h2>Order Return:</h2>
</div>    
<g:hasErrors bean="${cart}">
    <div class="errors"><g:renderErrors bean="${cart}" as="list" /></div>
</g:hasErrors>
<g:if test="${flash.message}">
    <div class="errors">${flash.message}</div>
</g:if>
<% if (edit && (packList.size() > 1 || packList.find{it.packQuantity.toInteger() > 1})) { %>
    <h2>Please adjust quantities for items returned</h2>
<% } %>
<table>
    <thead>
        <tr>
            <th/><th>Description</th>
            <th/>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Shipping Box</th>
        </tr>
    </thead>
    <tbody>
        <% 
            def numbers = ['ONE', 'TWO', 'THREE', 'FOUR', 'FIVE','SIX','SEVEN','EIGHT','NINE','TEN','ELEVEN','TWELVE']
	        def packageNo
	        def containerId
	        BigDecimal netCredit = 0g
        %>
        <g:each in="${packList.findAll{it.returnIt}}" var="packItem" status="i">
            <% 
	            def item = ItemMasterExt.findByCompCodeAndItemNo('01', packItem.itemNo)
	            def invoicedItem = InvoicedOrderDetailItem.findWhere(compCode:'01', orderNo:cart.orderNo, itemNo:packItem.itemNo, lineType:'I')
	            packageNo = (i==0)?0:((containerId == packItem.containerId)?packageNo:++packageNo)
                containerId = packItem.containerId
                netCredit += (packItem.packQuantity.toInteger() * invoicedItem.unitPrice?:(item.closeoutCode?item.specialPrice:item.retailPrice)  )
	        %>
            <g:hasErrors bean="${item}">
                <div class="errors"><g:renderErrors bean="${item}" as="list" /></div>
            </g:hasErrors>
            <tr>
                <td class="imageTiny"> 
                    <img class="cart" src="${resource(dir:"images/")}${item?.division?.name}/${item?.category?.name}/${(itemWithColor?:item).itemNo}.jpg" 
                        alt="${item?.desc}" />
                </td>
                <td>
                    ${item.desc} 
                </td>
                <td>
                    <% if (edit) { %>
	                   <g:link action="removeItem" params="[orderShipNo:packItem.orderShipNo,containerId:packItem.containerId,itemNo:packItem.itemNo]" 
	                        title="Click to remove this item from your return">
	                        Remove
	                   </g:link>
	                <% } %>
                </td>
                <td>
                    <% if (edit) { %>
	                    <g:form action="reduceQty">
	                        <g:hiddenField name="containerId" value="${packItem.containerId}" />
	                        <g:hiddenField name="itemNo" value="${packItem.itemNo}" />
	                        <g:textField name="quantity" class="number" value="${packItem.packQuantity.toInteger()}" />
	                    </g:form>
                    <% } else { %>
                            ${packItem.packQuantity.toInteger()}
                    <% } %>
                </td>
                <td class="number">
                    <g:formatNumber number="${invoicedItem.unitPrice?:(item.closeoutCode?item.specialPrice:item.retailPrice)}" 
                          type="currency" currencyCode="USD" />
                </td>
                <td class="center">
                    ${numbers[packageNo]}
                </td>
            </tr>
        </g:each>
        <% if (!edit) { %>
	        <tr>
	            <td/>
	            <td>UPS Ground shipping cost</td>
	            <td/>
	            <td/>
	            <td class="number consumerPays">
	                <g:formatNumber number="${shippingCost+(session.upsPickup?6g:0)}" type="currency" currencyCode="USD" />
	            </td>
	        </tr>
            <tr>
                <td/>
                <td>Net credit to your card </td>
                <td/>
                <td/>
                <td class="number total">
                    <g:formatNumber number="${(netCredit - (shippingCost+(session.upsPickup?6g:0) ) )}" type="currency" currencyCode="USD" />
                </td>
            </tr>
            <tr/>
	        <tr>
	            <td/>
	            <td colspan="6">
	                UPS Pickup 
	                <g:form action="upsPickup" name="upsPickupForm">
	                    <g:checkBox name="upsPickup" value="${session.upsPickup}"/> ($6.00 will be added to shipping)
	                </g:form>
	            </td>
	        </tr>
	    <% } %>
    </tbody>
    <tfoot>
        <tr>
            <td/>
            <td colspan="6">Note: UPS shipping charges will be deducted from your credit</td>
        </tr>
        <tr>
            <td/>
            <td colspan="6">This return will be credited to the credit card above</td>
        </tr>
        <tr/>
    </tfoot>
</table>
