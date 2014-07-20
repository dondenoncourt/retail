<%@ page import="com.kettler.domain.orderentry.share.ConsumerBillTo" %>
<g:javascript src="jquery/jquery-1.4.2.min.js"/> 

<dl>
    <dt><g:message code="consumer.name.label" default="Name" /></dt>
    <dd>${fieldValue(bean: consumer, field: "name")}</dd>
    <dt><g:message code="consumer.email.label" default="Email" /></dt>
    <dd>${fieldValue(bean: consumer, field: "email")}</dd>
    <dt><g:message code="consumer.phone.label" default="Phone" /></dt>
    <dd><kettler:formatPhone phone="${consumer.phone}"/></dd>
    <dt><g:message code="consumer.password.label" default="Password" /></dt>
    <dd><g:passwordField name="password" value="${consumer?.password}" size="10"  maxlength="20" disabled="disabled"/></dd>
</dl>
<a href="#" id="headerUpdateLink" class="clickme">Edit</a> 
<g:javascript>
    $("#headerUpdateLink").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'headerUpdate')}",
            data: 'id=${consumer.id}',
            success:function(data,textStatus){$('#custInfoHeader').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
</g:javascript>
        
