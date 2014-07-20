<%@ page import="com.kettler.domain.orderentry.share.ConsumerShipTo" %>
<g:javascript src="jquery/jquery-1.4.2.min.js"/> 
<div>
    <br/>
	${shipTo.name}<br/> 
	${shipTo.addr1}<br/> 
    <% if (shipTo.addr2) { %>
        ${shipTo.addr2}<br/> 
    <% } %> 
	${shipTo.city}, ${shipTo.state} ${shipTo.zipCode}<br/>
    <a href="#" id="shipToUpdateLink${shipTo.id}">Edit</a> 
    <a href="#" id="shipToDeleteLink${shipTo.id}">Delete</a> 
</div>
<g:javascript>
    $("#shipToUpdateLink${shipTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'shipToUpdate')}",
            data: 'id=${shipTo.id}',
            success:function(data,textStatus){jQuery('#shipTo${shipTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $("#shipToDeleteLink${shipTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'shipToDelete')}",
            data: 'id=${shipTo.id}',
            success:function(data,textStatus){jQuery('#shipTo${shipTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
</g:javascript>
        
