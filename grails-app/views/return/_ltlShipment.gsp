<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<div id="cartTop">
    <h2>Order Return:</h2>
</div>    
<g:hasErrors bean="${cart}">
    <div class="errors"><g:renderErrors bean="${cart}" as="list" /></div>
</g:hasErrors>
<g:if test="${flash.message}">
    <div class="errors">${flash.message}</div>
</g:if>
<table>
    <tbody>
        <tr>
            <th/><th>Description</th>
            <th/>
            <th>Quantity</th>
        </tr>
        <g:each in="${cart?.items}" var="item">
            <g:hasErrors bean="${item}">
                <div class="errors"><g:renderErrors bean="${item}" as="list" /></div>
            </g:hasErrors>
            <tr>
                <td class="imageTiny">
                    <img class="cart" src="${resource(dir:"images/")}${item.item?.division?.name}/${item.item?.category?.name}/${(item.itemWithColor?:item.item).itemNo}.jpg" 
                        alt="${item.item?.desc}" />
                </td>
                <td>
                    ${(item.itemWithColor?:(item.itemWithFrameSize?:item.item)).desc} 
                    ${item.itemWithFrameSize?.size} ${item.itemWithFrameSize?.sizeUom}<%-- won't show unless size selected--%>
                </td>
                <td class="number">
                    ${item.qty} 
                </td>
                <td class="number">
                    <g:formatNumber number="${(item.item.closeoutCode?item.item.specialPrice:item.item.retailPrice) * item.qty}" 
                          type="currency" currencyCode="USD" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>

<p>Return freight charges of $${manifest.actualFrtChg} will be deducted from your credit.</p>

<p>If you would like to return a portion of your order please phone 866.804.0440 Monday thru Friday from 9:00 a.m. 4:30 p.m. EST </p>