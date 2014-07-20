<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder as CH" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kettlerusa" />
    <title>Checkout</title>
    <link href="${createLinkTo(dir: 'css', file: 'checkout.css')}" rel="stylesheet" type="text/css"/>
    <g:javascript src="jquery/jquery-1.4.2.js"/>
    <g:javascript src="jquery-ui.min.js"/> 
  </head>
<body>
    <div class="body"> 
        <g:render template='checkout/checkoutSteps' model="[step:2]"/> 
	    <g:hasErrors bean="${payShipCmd}">
	        <div id="pageErrors" class="errors">
	            <g:renderErrors bean="${payShipCmd}" as="list"  />
	        </div>
	    </g:hasErrors>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:if test="${message}">
            <div class="message">${message}</div>
        </g:if>
       <g:form name="checkoutForm" action="checkout"  method="post" >
         <g:render template="checkout/address" collection="${addrCmd}" />
         <fieldset id="paymenInfo" class="full">
           <legend>Payment Info:</legend>
               <%-- http://www.credit-card-logos.com/ --%>
                <div class="${hasErrors(bean:payShipCmd,field:'creditCardType','errors')}">
                    <g:radio name="creditCardType" value="visa"            class="radioLink" checked="${(payShipCmd?.creditCardType=='visa')}"/>
                    <a href="#"><img class="radioLink${((payShipCmd?.creditCardType=='visa')?'Selected':'')}" src="${resource(dir:"images/")}visa.gif" alt="visa"  title="Click to select" /></a>
                    <g:radio name="creditCardType" value="american express" class="radioLink" checked="${(payShipCmd?.creditCardType=='american express')}"/>
                    <a href="#"><img class="radioLink${((payShipCmd?.creditCardType=='american express')?'Selected':'')}" src="${resource(dir:"images/")}americanexpress.gif" alt="American Express"  title="Click to select"/></a>
                    <g:radio name="creditCardType" value="master card"      class="radioLink" checked="${(payShipCmd?.creditCardType=='master card')}"/>
                    <a href="#"><img class="radioLink${((payShipCmd?.creditCardType=='master card')?'Selected':'')}" src="${resource(dir:"images/")}mastercard.gif" alt="Master Card"  title="Click to select" /></a>
                    <g:radio name="creditCardType" value="discover"        class="radioLink" checked="${(payShipCmd?.creditCardType=='discover')}"/>
                    <a href="#"><img class="radioLink${((payShipCmd?.creditCardType=='discover')?'Selected':'')}" src="${resource(dir:"images/")}discover.gif" alt="Discover"  title="Click to select" /></a>
                    <g:radio name="creditCardType" value="paypal"        class="radioLink" checked="${(payShipCmd?.creditCardType=='paypal')}"/>
                    <a href="#"><img class="radioLink${((payShipCmd?.creditCardType=='paypay')?'Selected':'')}" src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif" alt="Paypay"  title="Click to select" id="paypalImg"/></a>
                </div>
                <div id="creditCardInfo">
                    <div class="inputTitle"><label for="creditCard">Card No</label></div> 
                    <div class="inputTitleS"><label for="ccid">CCID</label>
                    <a href="#" id="ccidHelp"><img class="radioLink" src="${resource(dir:"images/")}question16x16.png"  /></a></div> 
                    <div class="inputTitleS"><label for="month">Month</label></div> 
                    <div class="inputTitleS"><label for="year">Year</label></div> 
                    <br clear="left" /> 
                    <div class="inputBox ${hasErrors(bean:payShipCmd,field:'creditCard','errors')}"><g:textField name="creditCard" size="16" maxlength="16"  value="${payShipCmd?.creditCard}" /><span class="required">*</span></div> 
                    <div class="inputBoxS ${hasErrors(bean:payShipCmd,field:'ccid','errors')}"><g:textField name="ccid" size="4" maxlength="4" value="${payShipCmd?.zeroPadCCID()}"/><span class="required">*</span></div> 
                    <div class="inputBoxS"><g:select name="month" from="${1..12}" value="${payShipCmd.month}"/><span class="required">*</span></div> 
                    <div class="inputBoxS"><g:select name="year" from="${2011..2020}"  value="${payShipCmd.year}"/><span class="required">*</span></div> 
                    <br clear="left" />
                    <br clear="left" />
                    <% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { // MFB %>
                    <div class="inputTitleS"><label for="coupon">Coupon</label></div>
                    <div class="inputBoxS ${hasErrors(bean:payShipCmd,field:'coupon','errors')}"><g:textField name="coupon" size="20" maxlength="20" value="${payShipCmd?.coupon}"/></div>
                    <br clear="left" />
                    <div class="inputTitleS"><label for="giftCard">Gift Card #</label></div>
                    <div class="inputBoxS ${hasErrors(bean:payShipCmd,field:'giftCard','errors')}"><g:textField name="giftCard" size="20" maxlength="20" value="${payShipCmd?.giftCard}"/></div>
                    <br clear="left" />
                    <% } %>
                    <span id="salesperson_block">
                      <div class="inputTitleS"><label for="spcode">Salesperson</label></div>
                      <div class="inputBoxS">
                        <%--
                        <g:select name="spcode" from="${com.kettler.domain.orderentry.share.WebUser.findAllBySalespersonIsNotNull([sort: 'lastname'])}" 
                                                optionKey="spcode" optionValue="lastFirstName"
                                                noSelection="${['':'Pick salesperson...']}" />
                        --%>
                        <g:select name="spcode" from="${com.kettler.domain.orderentry.share.SalesPerson.list([sort: 'name'])}" 
                                                optionKey="id" optionValue="name"
                                                noSelection="${['':'Pick salesperson...']}" />
                    </span>
                </div>
         </fieldset>
         <% if (!cart?.truck && !cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { // MFB %>
             <fieldset id="shippingMethod"  class="full">
               <legend>Shipping Method:</legend>
            	<% if (cart.rateService.equals("UPS")) { %>
	               <ul>
	                  <g:each in="${Cart.SHIP_METHODS_UPS.findAll{it.key != '99'}}">
	                      <li><input type="radio" name="upsServiceCode" id="upsServiceCode${it.key}" value="${it.key}" 
	                           ${(payShipCmd?.upsServiceCode == it.key)?'checked=checked':''} />
	                            ${it.value}
	                      </li>
	                  </g:each>
	               </ul>
	            <% } else  {  %>
	               <ul>
	                  <g:each in="${Cart.SHIP_METHODS_FEDEX.findAll{it.key != '99'}}">
	                      <li><input type="radio" name="upsServiceCode" id="upsServiceCode${it.key}" value="${it.key}" 
	                           ${(payShipCmd?.upsServiceCode == it.key)?'checked=checked':''} />
	                            ${it.value}
	                      </li>
	                  </g:each>
	               </ul>
	            <% }  %>
             </fieldset>
         <% } %>
         <% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { // MFB %>
         <div class="registerWarranty">
	         <g:checkBox name="registerWarranty" checked="${(payShipCmd?.registerWarranty)}"/>Auto-register warranty
	         <a href="#" id="autoRegisterWarrantyHelp"><img class="radioLink" src="${resource(dir:"images/")}question.png" alt="question auto-warranty" 
	                    title="This option will register your name and address for this item's manufacturer warranty" 
	         ></a> <%-- ie had issues with the short form of close tag /> so I used ></a> --%>
         </div>
         <% } %>
         <g:render template='checkout/submitButtons' model="[step:2]"/> 
        </g:form>
    </div>
    
<g:javascript>
    $('#salesperson_block').hide();
    $(document).bind('keydown', function(event) {
        // control or alt key and 'k'
        if (event.keyCode === 75 && (event.ctrlKey || event.altKey)) {
          $('#salesperson_block').show();
        }
    });

    $('#_eventId_calcShipping').hide();
    $('fieldset.right').height($('fieldset.left').height());   
    $('#autoRegisterWarrantyHelp').click(function() {
        alert('${(CH.config.autoRegisterWarrantyHelp?.replaceAll(/\n/,''))}');
    });
    $('#ccidHelp').click(function() {
        alert('${(CH.config.questionCCID?.replaceAll(/\n/,''))}');
    });
    
    $('#stepOneLink').click(function(){
        $("#_eventId_stepOne").hide();
        $("#_eventId_stepOne").trigger("click");
    });   

    <% if (cart.rateService.equals("UPS")) { 
	     Cart.SHIP_METHODS_UPS.findAll{it.key != '99'}.each { %>
		    $('#upsServiceCode${it.key}').change(function(){
	            $('#_eventId_stepOne').hide();
	            $('#_eventId_stepThree').hide();
	            $("#_eventId_calcShipping").trigger("click");
		    });
		<% } 
     } else  {  
	     Cart.SHIP_METHODS_FEDEX.findAll{it.key != '99'}.each { %>
		    $('#upsServiceCode${it.key}').change(function(){
	            $('#_eventId_stepOne').hide();
	            $('#_eventId_stepThree').hide();
	            $("#_eventId_calcShipping").trigger("click");
		    });
		<% } 
	 } %>
	
    $('#stepThreeLink').click(function(){
        $("#_eventId_stepThree").hide();
        $("#_eventId_stepThree").trigger("click");
    });   
	$("input").keypress(function (evt) {
		var enterKey = 13;
		if ((evt.charCode || evt.keyCode) == enterKey) {
			return false;
		}
	});    
    $('#paypalImg').click( function () {
        $('input[name=creditCard]').val('');
        $('input[name=ccid]').val('');
        $('input:radio[value=paypal]').trigger('click');
    });
    $(document).ready( function () {
        var extraForPaypalImage = 36;
        $('div#main').height( $('#footer').offset().top - $('div#main').offset().top + extraForPaypalImage);
    });
</g:javascript>    
</body>
</html>
