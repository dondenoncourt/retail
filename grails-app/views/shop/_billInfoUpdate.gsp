<%@ page import="com.kettler.controller.retail.CheckoutCommand" %>
<div id="billInfoUpdate">
    <g:hiddenField name="billToId" value="${cmd?.billToId}"/>
	<div class="inputTitle"><label for="billingName">Name</label></div> 
	<br clear="left" /> 
	<div class="inputBoxL ${hasErrors(bean:cmd,field:'billingName','errors')}"><g:textField name="billingName" value="${cmd?.billingName}" size="30" maxlength="30" /><span class="required">*</span></div> 
	<br clear="left" /> 
	<div class="inputTitle"><label for="billingAddress1">Address</label></div> 
	<br clear="left" /> 
	<div class="inputBoxL ${hasErrors(bean:cmd,field:'billingAddress1','errors')}"><g:textField name="billingAddress1" value="${cmd?.billingAddress1}" maxlength="30" /><span class="required">*</span></div> 
	<br clear="left" /> 
	<div class="inputBoxL"><g:textField name="billingAddress2" value="${cmd?.billingAddress2}" maxlength="30" /></div> 
	<br clear="left" /> 
	<div class="inputTitle"><label for="billingCity">City</label></div> 
	<div class="inputTitleS"><label for="billingState">State</label></div> 
	<div class="inputTitle"><label for="billingZip">Zip</label></div> 
	<br clear="left" /> 
	<div class="inputBox ${hasErrors(bean:cmd,field:'billingCity','errors')}"><g:textField name="billingCity" value="${cmd?.billingCity}" size="15"  maxlength="15" /><span class="required">*</span></div>        
	<div class="inputBoxS ${hasErrors(bean:cmd,field:'billingState','errors')}">
       <g:select name="billingState" from="${cmd.constraints.billingState.inList}"  
                    value="${cmd?.billingState}" noSelection="['':'']"></g:select>
	   <span class="required">*</span>
   </div>        
	<div class="inputBox ${hasErrors(bean:cmd,field:'billingZip','errors')}"><g:textField name="billingZip" value="${cmd?.billingZip}" size="10" maxlength="10" /><span class="required">*</span></div>
	<% if (cmd.billToId) { %>
        <br clear="left" />
        <a id="billToUpdateCancel" class="button">Cancel</a>
        <a id="billToUpdate" class="clickme">Update</a>
        <g:hiddenField name="billToUpdateOrAdd" value="${CheckoutCommand.UPDATE}"/>
    <% } %>
</div>
<g:javascript>
    $('#billToUpdate').click(function(){
        $('#pageErrors').hide();
        var params = $('#checkout').serialize()+'&shipToOrBillTo=billTo'
        $.ajax({
           url: "${createLink(action:'addrUpdate')}",
           data: params,
           type: 'POST',                                      
           success:  function(data) {
                $('#billInfo').html(data); 
                refreshCartPopup();
           },
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
    });
    
    $('#billToUpdateCancel').click(function(){
        var params = $('#checkout').serialize()+'&shipToOrBillTo=billTo'
        $.ajax({
           url: "${createLink(action:'addrUpdateCancel')}",
           data: params,
           type: 'POST',                                        
           success:  function(data) {$('#billInfo').html(data);},
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
    });
</g:javascript>
