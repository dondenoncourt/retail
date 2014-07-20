<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder as CH" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${cmd.division?.replaceAll(/ /,'')}" />
    <title>Checkout</title>
    <g:javascript src="jquery/jquery-1.4.2.js"/>
    <g:javascript src="jquery-ui.min.js"/> 
  </head>
<body> 
<div class="body">
    <g:if test="${flash.message}">
      <div class="errors">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${cmd}">
        <div id="pageErrors" class="errors">
            <g:renderErrors bean="${cmd}" as="list"  />
        </div>
    </g:hasErrors>
    <g:hasErrors bean="${session.consumer}">
        <div class="errors">
            <g:renderErrors bean="${session.consumer}" as="list"  />
        </div>
    </g:hasErrors>
    <% if (!session.consumer) { %>
     <g:form action="checkoutPrompt" method="post" >
        <h1>Returning Customers</h1>
        <g:hiddenField name="division" value="${cmd?.division}"/>
		<label for="email">email:</label><g:textField name="email" value="${params.email?:''}" }/>
	    <label for="password">password:</label><g:passwordField name="password"  value="${params.password}"  />
	    <input type="submit" value="Login" />
     </g:form>
        <h1>New Customer Checkout</h1>
    <% } else { %>
        <h1>Checkout</h1>
    <% } %>
    <g:form name="checkout" action="checkout" method="post" >
         <g:hiddenField name="division" value="${cmd?.division}"/>
         <g:hiddenField name="loggedIn" value="${(session.consumer?'true':'false')}"/>
         <fieldset>
           <legend>Bill To:</legend>
           <div id="billInfo">
            <% if (session.consumer) { %>
                  <g:render template="billInfoStatic" collection="${cmd}" />
            <% } else { %>
                  <g:render template="billInfoUpdate" collection="${cmd}" />
            <% } %>	                     
           </div>
         </fieldset>
         <fieldset id="paymenInfo">
           <legend>Payment Info:</legend>
	           <%-- http://www.credit-card-logos.com/ --%>
		        <div class="${hasErrors(bean:cmd,field:'creditCardType','errors')}">
			        <g:radio name="creditCardType" value="visa"            class="radioLink" checked="${(cmd?.creditCardType=='visa')}"/>
                    <a href="#"><img class="radioLink${((cmd?.creditCardType=='visa')?'Selected':'')}" src="${resource(dir:"images/")}visa.gif" alt="visa"  title="Click to select" /></a>
			        <g:radio name="creditCardType" value="american express" class="radioLink" checked="${(cmd?.creditCardType=='american express')}"/>
                    <a href="#"><img class="radioLink${((cmd?.creditCardType=='american express')?'Selected':'')}" src="${resource(dir:"images/")}americanexpress.gif" alt="American Express"  title="Click to select"/></a>
			        <g:radio name="creditCardType" value="master card"      class="radioLink" checked="${(cmd?.creditCardType=='master card')}"/>
                    <a href="#"><img class="radioLink${((cmd?.creditCardType=='master card')?'Selected':'')}" src="${resource(dir:"images/")}mastercard.gif" alt="Master Card"  title="Click to select" /></a>
			        <g:radio name="creditCardType" value="discover"        class="radioLink" checked="${(cmd?.creditCardType=='discover')}"/>
			        <a href="#"><img class="radioLink${((cmd?.creditCardType=='discover')?'Selected':'')}" src="${resource(dir:"images/")}discover.gif" alt="Discover"  title="Click to select" /></a>
		        </div>
		        <div id="creditCardInfo">
		            <div class="inputTitle"><label for="creditCard">Card No</label></div> 
		            <div class="inputTitleS"><label for="ccid">CCID</label>
		            <a href="#"><img class="radioLink" src="${resource(dir:"images/")}question16x16.png"  /></a></div> 
		            <div class="inputTitleS"><label for="month">Month</label></div> 
		            <div class="inputTitleS"><label for="year">Year</label></div> 
		            <br clear="left" /> 
		            <div class="inputBox ${hasErrors(bean:cmd,field:'creditCard','errors')}"><g:textField name="creditCard" size="16" maxlength="16"  value="${cmd?.creditCard?:''}" /><span class="required">*</span></div> 
		            <div class="inputBoxS ${hasErrors(bean:cmd,field:'ccid','errors')}"><g:textField name="ccid" size="4" maxlength="4" value="${cmd?.ccid?:0}"/><span class="required">*</span></div> 
		            <div class="inputBoxS"><g:select name="month" from="${1..12}" value="${cmd.month}"/><span class="required">*</span></div> 
		            <div class="inputBoxS"><g:select name="year" from="${2010..2020}"  value="${cmd.year}"/><span class="required">*</span></div> 
		        </div>
         </fieldset>
         <fieldset id="registrationInfo">
           <legend>Contact Info:</legend>
            <label for="email">email:</label>
            <g:textField name="email" value="${cmd?.email}"  class="${hasErrors(bean:cmd,field:'email','errors')}" size="30"/><span class="required">*</span>
            <label for="phone">Phone:</label>
            <g:textField name="phone" 
                value="${(kettler.formatPhone(phone:cmd?.phone))}"  
                class="${hasErrors(bean:cmd,field:'phone','errors')}"
            /><span class="required">*</span>
            <br clear="left" /> 
           <% if (session.consumer) { %>
                <g:hiddenField name="saveAccount" value="${session.consumer.saveAccount}"/>
           <% } else { %>
	           <g:checkBox name="saveAccount" checked="${cmd?cmd.saveAccount:true}"/>Save account for later use&nbsp;
	           <a href="#" id="saveAccountHelp"><img class="radioLink" src="${resource(dir:"images/")}question.png" alt="question save account for later use"  /></a>
	           <span id="passwordPrompt" class="${hasErrors(bean:cmd,field:'password','errors')} ${hasErrors(bean:cmd,field:'password','errors')}"> 
	              <label for="password">password:</label>
	              <g:passwordField name="password" value="${cmd?.password}"/>
	              <label for="confirmPassword">confirm password:</label>
	              <g:passwordField name="confirmPassword" value="${cmd?.confirmPassword}"/>
	           </span>                
	           <br clear="left" />
           <% } %> 
           <g:checkBox name="registerWarranty" checked="${cmd?.registerWarranty?:true}"/>Auto-register warranty&nbsp;
              <a href="#" id="autoRegisterWarrantyHelp"><img class="radioLink" src="${resource(dir:"images/")}question.png" alt="question auto-warranty" 
                    title="This option will register your name and address for this item's manufacturer warranty" 
               /></a>
         </fieldset>
         
         <fieldset id="shippingInfo">
           <legend>Ship To:</legend>
            <g:checkBox name="shipInfoDiff" checked="${cmd?.shipInfoDiff?:cart?.shipToId?true:false}"/>Specify a different address
            <div id="shipInfo">
            <% if (session.consumer && cmd.shipToId) { %>
                  <g:render template="shipInfoStatic" collection="${cmd}" />
            <% } else { %>
               <g:render template="shipInfoUpdate" collection="${cmd}" />
               <% } %>        
            </div>
         </fieldset>
         <% if (!cart.truck) { %>
	         <fieldset id="shippingMethod">
	           <legend>Shipping Method:</legend>
            	<% if (cart.rateService.equals("UPS")) { %>
		           <ul>
		              <g:each in="${Cart.SHIP_METHODS_UPS.findAll{it.key != '99'}}">
		                  <li><g:radio name="upsServiceCode" value="${it.key}" checked="${(cmd?.upsServiceCode == it.key)}"/>${it.value}</li>
		              </g:each>
		           </ul>
	            <% } else  {  %>
		           <ul>
		              <g:each in="${Cart.SHIP_METHODS_FEDEX.findAll{it.key != '99'}}">
		                  <li><g:radio name="upsServiceCode" value="${it.key}" checked="${(cmd?.upsServiceCode == it.key)}"/>${it.value}</li>
		              </g:each>
		           </ul>
	            <% }  %>
	         </fieldset>
         <% } %>
         <div class="buttons">
             <%-- DD_roundies don't seem to work on <input type=submit/> but it does on <button>
                be carefule to use the appropriate name attribute, or, uncomment below, view html source, 
                copy-and-paste <input type=submit/> then convert to <button>  
            <button type="submit" name="_action_products" value="Continue Shopping" class="button">Continue Shopping</button> 
            <button type="submit" name="_action_cancel" value="Cancel" class="button">Cancel</button> 
            <button type="submit" name="_action_cart" value="Edit Cart" class="button">Edit Cart</button> 
            <button type="submit" class="clickme" name="next" action="checkout" value="Next" id="next" >Next</button>           
            --%>
                
            <g:actionSubmit action="products" value="Continue Shopping" class="button"/>
            <g:actionSubmit action="cancel" value="Cancel" class="button"/>
            <g:actionSubmit action="cart" value="Edit Cart" class="button"/>
            <g:submitButton class="clickme" name="next" action="checkout" value="Next" />
         </div>
    </g:form>
    <div id="floatingCart">
        <g:render template='cart' model="[cart:cart,ajax:true,checkout:true,params:params]"/>
    </div>
    <a href="#" id="confidentiality">Confidentiality and security guarantee</a>
</div>
<g:javascript>
    if (false == document.getElementById('saveAccount').checked) { 
        $("#passwordPrompt").hide(); 
    }
    $('[name=saveAccount]').click(function(){
        if (this.checked) {
            $('#passwordPrompt').show();
        } else {
            $('[name=password]').val('')
            $('[name=confirmPassword]').val('')
            $('#passwordPrompt').hide();
        }  
    });
    $('#confidentiality').click(function(){
        alert("KETTLER will only use your personal information to contact you about this order. Your information will not be given to other entities.");
    });
    $("#shipInfoDiff").click(function(){
        if (this.checked) {
            $("#shipInfo").show();
        } else {
            $("#shipInfo").hide();
            refreshCartPopup();
        }
    });
    if (false == document.getElementById('shipInfoDiff').checked) { 
        $("#shipInfo").hide();
    }
    $('img[src$=visa.gif]').click(function(){
	    $('[value=visa]').attr('checked', true);
    });
    $('img[src$=americanexpress.gif]').click(function(){
        $('[value=american express]').attr('checked', true);
    });
    $('img[src$=mastercard.gif]').click(function(){
        $('[value=master card]').attr('checked', true);
    });
    $('img[src$=discover.gif]').click(function(){
        $('[value=discover]').attr('checked', true);
    });
    $('#changeBillToId').change(function() {
        var params = '&billToId=' + this.value;
        $.ajax({
           url: "${createLink(action:'billInfoStatic')}",
           data: params,
           type: 'POST',
           success:  function(data) {$('#billInfo').html(data);},
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
    });
    $('#changeShipToId').change(function() {
        var params = '&shipToId=' + this.value;
        $.ajax({
           url: "${createLink(action:'shipInfoStatic')}",
           data: params,
           type: 'POST',
           success:  function(data) {$('#shipInfo').html(data);},
           error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
        }); 
    });

    <% if (cmd.division == 'patio') { %>
        $('#sidebar').hide();
    <% } %>           
    $('[name=upsServiceCode]').click(function(){refreshCartPopup();});
    setCartClose();
    $('#floatingAddress').draggable();
    $('#floatingCart').draggable();
    $('#saveAccountHelp').click(function() {
        alert('${(CH.config.saveAccountHelp?.replaceAll(/\n/,''))}');
    });
    $('#autoRegisterWarrantyHelp').click(function() {
        alert('${(CH.config.autoRegisterWarrantyHelp?.replaceAll(/\n/,''))}');
    });
    //$('#billingFirstName').focus() TODO focus on first input field unless errors, if error focus on first error
</g:javascript>
</body>
</html>
