<%@ page import="com.kettler.domain.orderentry.share.ConsumerBillTo" %>
<g:javascript src="jquery/jquery-1.4.2.min.js"/> 

<div>
    <br/>
	${billTo.name}<br/> 
	${billTo.addr1}<br/> 
	<% if (billTo.addr2) { %>
        ${billTo.addr2}<br/>
    <% } %> 
	${billTo.city}, ${billTo.state} ${billTo.zipCode}<br/>
    ${billTo.cardType.toUpperCase()}: **** **** **** ${(billTo.cardNo[12..(billTo.cardNo.size())])}
    <br/>  
</div>        
<a href="#" id="billToUpdateLink${billTo.id}">Edit</a> 
<a href="#" id="billToDeleteLink${billTo.id}">Delete</a> 

<g:javascript>
    $("#billToUpdateLink${billTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'billToUpdate')}",
            data: 'id=${billTo.id}',
            success:function(data,textStatus){$('#billTo${billTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $("#billToDeleteLink${billTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'billToDelete')}",
            data: 'id=${billTo.id}',
            success:function(data,textStatus){jQuery('#billTo${billTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
</g:javascript>