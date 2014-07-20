<fieldset id="billToInfo" class="left">
    <legend>Bill To:</legend>
    ${addrCmd.billingName}<br/> 
    ${addrCmd.billingAddress1}<br/> 
    ${addrCmd.billingAddress2}<br/> 
    ${addrCmd.billingCity}, ${addrCmd.billingState} ${addrCmd.billingZip}<br/>
    <dl>
        <dt>email:</dt><dd>${addrCmd?.email}</dd>
        <dt>phone:</dt><dd>${addrCmd?.phone}</dd>
    </dl>
    <br/>
    
</fieldset>
<% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { // MFB %>
<fieldset id="shipToInfo" class="right">
    <legend>Ship To:</legend>
    <% if (addrCmd.shipToSameAsBillTo) { %>
        Same as Bill To
    <% } else { %>
        ${addrCmd.shippingName}<br/> 
        ${addrCmd.shippingAddress1}<br/> 
        ${addrCmd.shippingAddress2}<br/> 
        ${addrCmd.shippingCity}, ${addrCmd.shippingState} ${addrCmd.shippingZip}<br/> 
    <% } %>
</fieldset>
<% } %>