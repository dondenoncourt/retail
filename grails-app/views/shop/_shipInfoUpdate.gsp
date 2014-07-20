<%@ page import="com.kettler.controller.retail.CheckoutCommand" %>
<div id="shipInfoUpdate">
    <g:hiddenField name="shipToId" value="${cmd?.shipToId}"/>
	<div class="inputTitle"><label for="shippingName">Name</label></div> 
	<br clear="left" /> 
	<div class="inputBoxL ${hasErrors(bean:cmd,field:'shippingName','errors')}"><g:textField name="shippingName" id="shippingName" value="${cmd?.shippingName}" size="35" maxlength="30" /><span class="required">*</span></div> 
	<br clear="left" /> 
	<div class="inputTitle"><label for="shippingAddress1">Address</label></div> 
	<br clear="left" /> 
	<div class="inputBoxL ${hasErrors(bean:cmd,field:'shippingAddress1','errors')}"><g:textField name="shippingAddress1" id="shippingAddress1" value="${cmd?.shippingAddress1}" maxlength="30" /><span class="required">*</span></div> 
	<br clear="left" /> 
	<div class="inputBoxL"><g:textField name="shippingAddress2" id="shippingAddress2" value="${cmd?.shippingAddress2}" maxlength="50" /></div> 
	<br clear="left" /> 
	<div class="inputTitle"><label for="shippingCity">City</label></div> 
	<div class="inputTitleS"><label for="shippingState">State</label></div> 
	<div class="inputTitle"><label for="shippingZip">Zip</label></div> 
	<br clear="left" /> 
	<div class="inputBox ${hasErrors(bean:cmd,field:'shippingCity','errors')}"><g:textField name="shippingCity" id="shippingCity" value="${cmd?.shippingCity}" size="15"  maxlength="15" /><span class="required">*</span></div>        
	<div class="inputBoxS ${hasErrors(bean:cmd,field:'shippingState','errors')}">
       <g:select name="shippingState" from="${cmd.constraints.billingState.inList}"  
                    value="${cmd?.shippingState}" noSelection="['':'']"></g:select>
	   <span class="required">*</span>
	</div>        
	<div class="inputBox ${hasErrors(bean:cmd,field:'shippingZip','errors')}"><g:textField name="shippingZip" value="${cmd?.shippingZip}" size="10" maxlength="10" /><span class="required">*</span></div>
    <% if (cmd.shipToId) { %>
        <br clear="left" />
        <a id="shipToUpdateCancel" class="button">Cancel</a>
        <a id="shipToUpdate" class="clickme">Update</a>
        <g:hiddenField name="shipToUpdateOrAdd" value="${CheckoutCommand.UPDATE}"/>
    <% } %>
	
</div>                
<g:javascript>
    $('#shipToUpdate').click(function(){
        $('#pageErrors').hide();
        var params = $('#checkout').serialize()+'&shipToOrBillTo=shipTo'
        $.ajax({
           url: "${createLink(action:'addrUpdate')}",
           data: params,
           type: 'POST',                                 
           success:  function(data) {
                $('#shipInfo').html(data); 
                refreshCartPopup();
           },
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
    });
    $('#shipToUpdateCancel').click(function(){
        var params = $('#checkout').serialize()+'&shipToOrBillTo=shipTo'
        $.ajax({
           url: "${createLink(action:'addrUpdateCancel')}",
           data: params,
           type: 'POST',                                        
           success:  function(data) {$('#shipInfo').html(data);},
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
    });

</g:javascript>
