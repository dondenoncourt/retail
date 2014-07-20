<%@ page import="com.kettler.controller.retail.CheckoutCommand" %>
<g:javascript src="jquery/jquery-1.4.2.js"/>
<div id="shipInfoStatic">
     <br/>
     ${cmd.shippingName}<br/> 
     ${cmd.shippingAddress1}<br/> 
     ${cmd.shippingAddress2}<br/> 
     ${cmd.shippingCity}, ${cmd.shippingState} ${cmd.shippingZip}<br/> 
     <a href="#" id="shipInfoUpdateLink">Edit</a> 
     <%-- if (session.consumer.shipTos?.size() > 1) { %>
      FEATURE DISABLED PENDING PHASE II OF CHECKOUT (MULTI-PAGE CHECKOUT)
	   <g:select name="changeShipToId" from="${session.consumer.shipTos}" optionKey="id" 
	           noSelection="${['':'Pick a different address...']}"/>
	 <% } --%>
     <g:hiddenField name="shippingName" value="${cmd?.shippingName}"/>
     <g:hiddenField name="shippingAddress1" value="${cmd?.shippingAddress1}"/>
     <g:hiddenField name="shippingAddress2" value="${cmd?.shippingAddress2}"/>
     <g:hiddenField name="shippingCity" value="${cmd?.shippingCity}"/>
     <g:hiddenField name="shippingState" value="${cmd?.shippingState}"/>
     <g:hiddenField name="shippingZip" value="${cmd?.shippingZip}"/>
     <g:hiddenField name="shipToId" value="${cmd?.shipToId}"/>
</div>
<g:javascript>
    $("#shipInfoUpdateLink").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'shipInfoUpdate')}",
            data: 'shipToId=' + document.getElementById('shipToId').value,
            success:function(data,textStatus){jQuery('#shipInfo').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $('#changeShipToId').change(function() {    
        $.ajax({
           url: "${createLink(action:'shipInfoStatic')}",
           data: 'shipToId=' + document.getElementById('changeShipToId').value,
           type: 'POST',                                     //refreshCartPopup() defined in checkout.gsp
           success:  function(data) {$('#shipInfo').html(data);refreshCartPopup();},
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
        return false;
    });
</g:javascript>
    