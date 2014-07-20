<%@ page import="com.kettler.domain.orderentry.share.ConsumerBillTo" %>
<g:javascript src="jquery/jquery-1.4.2.min.js"/> 

<g:form name="headerForm">
    <g:hiddenField name="id" value="${consumer.id}"/>
	<dl>
	    <dt><g:message code="consumer.name.label" default="Name" /></dt>
	    <dd><g:textField name="name" value="${consumer?.name}" size="30"  maxlength="50" /></dd>
	    <dt><g:message code="consumer.email.label" default="Email" /></dt>
        <dd><g:textField name="email" value="${consumer?.email}" size="40"  maxlength="60" /></dd>
        <dt><g:message code="consumer.phone.label" default="Phone" /></dt>
        <dd><g:textField name="phone" value="${consumer?.phone}" size="10"  maxlength="10" /></dd>
	    <dt><g:message code="consumer.password.label" default="Password" /></dt>
        <dd><g:passwordField name="password" value="${consumer?.password}" size="10"  maxlength="20" /></dd>
	</dl>
</g:form>
<a href="#" id="headerUpdateLink" class="clickme">Update</a> 
<g:javascript>
    $("#headerUpdateLink").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'headerChange')}",
            data: $('#headerForm').serialize(),
            success:function(data,textStatus){$('#custInfoHeader').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
</g:javascript>
        

