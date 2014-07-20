<%@ page import="com.kettler.domain.orderentry.share.ConsumerShipTo" %>
<g:javascript src="jquery/jquery-1.4.2.min.js"/> 

<g:form name="shipToForm${shipTo.id}">
    <g:hiddenField name="shipToId" value="${shipTo.id}"/>
    <g:hiddenField name="consumerId" value="${shipTo.consumer.id}"/>
    <div class="inputTitle"><label for="Name">Name</label></div> 
    <br clear="left" /> 
    <div class="inputBoxL ${hasErrors(bean:cmd,field:'name','errors')}"><g:textField name="name" id="name" value="${shipTo.name}" size="35" maxlength="40" /><span class="required">*</span></div> 
    <br clear="left" /> 
    <div class="inputTitle"><label for="addr1">addr</label></div> 
    <br clear="left" /> 
    <div class="inputBoxL ${hasErrors(bean:cmd,field:'addr1','errors')}"><g:textField name="addr1" id="addr1" value="${shipTo.addr1}" maxlength="50" /><span class="required">*</span></div> 
    <br clear="left" /> 
    <div class="inputBoxL"><g:textField name="addr2" id="addr2" value="${shipTo.addr2}" maxlength="50" /></div> 
    <br clear="left" /> 
    <div class="inputTitle"><label for="city">city</label></div> 
    <div class="inputTitleS"><label for="state">state</label></div> 
    <div class="inputTitle"><label for="zipCode">zipCode</label></div> 
    <br clear="left" /> 
    <div class="inputBox ${hasErrors(bean:cmd,field:'addr1','errors')}"><g:textField name="city" id="city" value="${shipTo.city}" size="15"  maxlength="20" /><span class="required">*</span></div>        
    <div class="inputBoxS ${hasErrors(bean:cmd,field:'state','errors')}"><g:textField name="state" value="${shipTo.state}" size="2" maxlength="2" /><span class="required">*</span></div>        
    <div class="inputBox ${hasErrors(bean:cmd,field:'zipCode','errors')}"><g:textField name="zipCode" value="${shipTo.zipCode}" size="10" maxlength="10" /><span class="required">*</span></div>
    <br clear="left" />
    <% if (shipTo.id) { %>
        <a href="#" id="shipToUpdateLink${shipTo.id}" class="clickme">Update</a> 
    <% } %>
    <a href="#" id="shipToAddLink${shipTo.id}" class="clickme">Add</a> 
</g:form>
<g:javascript>
    $("#shipToUpdateLink${shipTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'shipToChange')}",
            data: $('#shipToForm${shipTo.id}').serialize(),
            success:function(data,textStatus){$('#shipTo${shipTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $("#shipToAddLink${shipTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'shipToAdd')}",
            data: $('#shipToForm${shipTo.id}').serialize(),
            success:function(data,textStatus){$('#shipTo${shipTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    
</g:javascript>
        

