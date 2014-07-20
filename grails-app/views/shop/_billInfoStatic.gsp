<g:javascript src="jquery/jquery-1.4.2.js"/>

<div id="billInfoStatic">
    <br/>
	${cmd.billingName}<br/> 
	${cmd.billingAddress1}<br/> 
	${cmd.billingAddress2}<br/> 
	${cmd.billingCity}, ${cmd.billingState} ${cmd.billingZip}<br/>
    <a href="#" id="billInfoUpdateLink">Edit</a> 
	<%-- if (session.consumer.billTos.size() > 1   &&     // more than 1 billTo
		  !session.consumer.billTos?.find {!it.id }) { // and none of the billTos do not have null ids
	%> FEATURE DISABLED PENDING PHASE II OF CHECKOUT (MULTI-PAGE CHECKOUT)
	    <g:select name="changeBillToId" from="${session.consumer.billTos}" optionKey="id" 
                 noSelection="${['':'Pick a different address...']}"/>
	<% } --%>
	<g:hiddenField name="billingName" value="${cmd?.billingName}"/>
	<g:hiddenField name="billingAddress1" value="${cmd?.billingAddress1}"/>
	<g:hiddenField name="billingAddress2" value="${cmd?.billingAddress2}"/>
	<g:hiddenField name="billingCity" value="${cmd?.billingCity}"/>
	<g:hiddenField name="billingState" value="${cmd?.billingState}"/>
	<g:hiddenField name="billingZip" value="${cmd?.billingZip}"/>
	<g:hiddenField name="billToId" value="${cmd?.billToId?:session.consumer?.billTos?.toArray()[0]?.id?:null}"/>
</div>
<g:javascript>
    $("#billInfoUpdateLink").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'billInfoUpdate')}",
            data: 'billToId=' + document.getElementById('billToId').value,
            success:function(data,textStatus){$('#billInfo').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $('#changeBillToId').change(function() {    
        $.ajax({
           url: "${createLink(action:'billInfoStatic')}",
           data: 'billToId=' + document.getElementById('changeBillToId').value,
           type: 'POST',                                     //refreshCartPopup() defined in checkout.gsp
           success:  function(data) {$('#billInfo').html(data);refreshCartPopup();},
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
        return false;
    });    
</g:javascript>
    