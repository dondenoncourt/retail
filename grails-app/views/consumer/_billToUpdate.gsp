<%@ page import="com.kettler.domain.orderentry.share.ConsumerBillTo" %>
<g:javascript src="jquery/jquery-1.4.2.min.js"/> 

<% String s = "billToForm";
   if (billTo.id) {
     s += billTo.id
   } else {
     s += "0"
   }
%>
<g:form name="${s}">
    <% s %>
    <g:textField name="billToId" value="${billTo.id}"/>
    <g:textField name="consumerId" value="${billTo.consumer?.id}"/>
    <div class="inputTitle"><label for="name">Name</label></div> 
    <br clear="left" /> 
    <div class="inputBoxL ${hasErrors(bean:billTo,field:'name','errors')}"><g:textField name="name" value="${billTo.name}" size="30" maxlength="30" /><span class="required">*</span></div> 
    <br clear="left" /> 
    <div class="inputTitle"><label for="addr1">Address</label></div> 
    <br clear="left" /> 
    <div class="inputBoxL ${hasErrors(bean:billTo,field:'addr1','errors')}"><g:textField name="addr1" value="${billTo.addr1}" maxlength="30" /><span class="required">*</span></div> 
    <br clear="left" /> 
    <div class="inputBoxL"><g:textField name="addr2" value="${billTo.addr2}" maxlength="30" /></div> 
    <br clear="left" /> 
    <div class="inputTitle"><label for="city">City</label></div> 
    <div class="inputTitleS"><label for="state">State</label></div> 
    <div class="inputTitle"><label for="zipCode">Zip</label></div>
    <br clear="left" /> 
    <div class="inputBox ${hasErrors(bean:billTo,field:'city','errors')}"><g:textField name="city" value="${billTo.city}" size="15"  maxlength="20" /><span class="required">*</span></div>        
    <div class="inputBoxS ${hasErrors(bean:billTo,field:'state','errors')}"><g:textField name="state" value="${billTo.state}" size="2" maxlength="2" /><span class="required">*</span></div>        
    <div class="inputBox ${hasErrors(bean:billTo,field:'zipCode','errors')}"><g:textField name="zipCode" value="${billTo.zipCode}" size="10" maxlength="10" /><span class="required">*</span></div>
    <br/><br/>
    <div>
	    <g:radio name="cardType" value="visa"            class="radioLink" checked="${(billTo.cardType=='visa')}"/>
	    <a href="#"><img class="radioLink${((billTo.cardType=='visa')?'Selected':'')}" src="${resource(dir:"images/")}visa.gif" alt="visa"  /></a>
        <g:radio name="cardType" value="american express" class="radioLink" checked="${(billTo.cardType=='american express')}"/>
	    <a href="#"><img class="radioLink${((billTo.cardType=='american express')?'Selected':'')}" src="${resource(dir:"images/")}americanexpress.gif" alt="American Express"  /></a>
        <g:radio name="cardType" value="master card"      class="radioLink" checked="${(billTo.cardType=='master card')}"/>
	    <a href="#"><img class="radioLink${((billTo.cardType=='master card')?'Selected':'')}" src="${resource(dir:"images/")}mastercard.gif" alt="Master Card"  /></a>
        <g:radio name="cardType" value="discover"        class="radioLink" checked="${(billTo.cardType=='discover')}"/>
	    <a href="#"><img class="radioLink${((billTo.cardType=='discover')?'Selected':'')}" src="${resource(dir:"images/")}discover.gif" alt="Discover"  /></a>
	</div>
    <div id="cardInfo${billTo.id}">
        <div class="inputTitle"><label for="cardNo">Card No</label></div> 
        <div class="inputTitleS"><label for="ccid">CCID</label>
        <a href="#"><img class="radioLink" src="${resource(dir:"images/")}question16x16.png"  /></a></div> 
        <div class="inputTitleS"><label for="expMonth">Month</label></div>
        <div class="inputTitleS"><label for="expYear">Year</label></div>
        <br clear="left" /> 
        <div class="inputBox ${hasErrors(bean:billTo,field:'cardNo','errors')}"><g:textField name="cardNo" size="16" maxlength="16"  value="${billTo?.cardNo?:''}" /><span class="required">*</span></div> 
        <div class="inputBoxS ${hasErrors(bean:billTo,field:'ccid','errors')}"><g:textField name="ccid" size="3" maxlength="3" value="${billTo?.ccid?:0}"/><span class="required">*</span></div> 
        <div class="inputBoxS"><g:select name="expMonth" from="${1..12}"/><span class="required">*</span></div> 
        <div class="inputBoxS"><g:select name="expYear" from="${2010..2020}"/><span class="required">*</span></div> 
    </div>        
	
    <br clear="left" />
    <% if (billTo.id) { %>
        <a href="#" id="billToUpdateLink${billTo.id}" class="clickme">Update</a>
    <% } %>
    <a href="#" id="billToAddLink" class="clickme">Add</a>
    <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    
</g:form>
<g:javascript>
    $("#billToUpdateLink${billTo.id}").click(function() {    
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'billToChange')}",
            data: $('#billToForm${billTo.id}').serialize(),
            success:function(data,textStatus){$('#billTo${billTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $("#billToAddLink").click(function() {
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'billToAdd')}",
            data: $('#billToForm0').serialize(),
            success:function(data,textStatus){$('#billTo${billTo.id}').html(data);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
         return false;
    });
    $('img[src$=visa.gif]').click(function(){
        $('[value=visa]').attr('checked', true);
        $(this).removeClass('radioLink').addClass('radioLinkSelected');
        $($(this).parent()).siblings('a').children().removeClass('radioLinkSelected').addClass('radioLink');
    });
    $('img[src$=americanexpress.gif]').click(function(){
        $('[value=american express]').attr('checked', true);
        $(this).removeClass('radioLink').addClass('radioLinkSelected');
        $($(this).parent()).siblings('a').children().removeClass('radioLinkSelected').addClass('radioLink');
    });
    $('img[src$=mastercard.gif]').click(function(){
        $('[value=master card]').attr('checked', true);
        $(this).removeClass('radioLink').addClass('radioLinkSelected');
        $($(this).parent()).siblings('a').children().removeClass('radioLinkSelected').addClass('radioLink');
    });
    $('img[src$=discover.gif]').click(function(){
        $('[value=discover]').attr('checked', true);
        $(this).removeClass('radioLink').addClass('radioLinkSelected');
        $($(this).parent()).siblings('a').children().removeClass('radioLinkSelected').addClass('radioLink');
    });
    
</g:javascript>
        

