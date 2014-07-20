<%@ page import="com.kettler.controller.retail.AddressesCommand" %>

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
        <g:render template='checkout/checkoutSteps' model="[step:1]"/> 
        <% if (addrCmd.type == AddressesCommand.NEW_CUSTOMER) { %>          
            Note: If you already have an account with us, please login at the 
            <g:link action="index" class="button" title="Click to checkout as a returning customer.">Login Page</g:link>
        <% } %>
        
	    <g:hasErrors bean="${addrCmd}">
	        <div id="pageErrors" class="errors">
	            <g:renderErrors bean="${addrCmd}" as="list"  />
	        </div>
	    </g:hasErrors>
        <g:form name="checkoutForm" action="checkout" method="post" >
            <g:hiddenField name="type" value="${addrCmd.type}"/>
            <fieldset id="billToInfo" class="left">
               <legend>Bill To:</legend>
			    <div class="inputTitle"><label for="billingName">Name</label></div> 
			    <br clear="left" /> 
			    <div class="inputBoxL ${hasErrors(bean:addrCmd,field:'billingName','errors')}"><g:textField name="billingName" value="${addrCmd?.billingName}" size="30" maxlength="30" /><span class="required">*</span></div> 
			    <br clear="left" /> 
			    <div class="inputTitle"><label for="billingAddress1">Address</label></div> 
			    <br clear="left" /> 
			    <div class="inputBoxL ${hasErrors(bean:addrCmd,field:'billingAddress1','errors')}"><g:textField name="billingAddress1" value="${addrCmd?.billingAddress1}" size="30" maxlength="30" /><span class="required">*</span></div> 
			    <br clear="left" /> 
			    <div class="inputBoxL"><g:textField name="billingAddress2" value="${addrCmd?.billingAddress2}" size="30" maxlength="30" /></div> 
			    <br clear="left" /> 
			    <div class="inputTitle"><label for="billingCity">City</label></div> 
			    <div class="inputTitleS"><label for="billingState">State</label></div> 
			    <div class="inputTitle"><label for="billingZip">Zip</label></div> 
			    <br clear="left" /> 
			    <div class="inputBox ${hasErrors(bean:addrCmd,field:'billingCity','errors')}"><g:textField name="billingCity" value="${addrCmd?.billingCity}" size="15"  maxlength="15" /><span class="required">*</span></div>        
			    <div class="inputBoxS ${hasErrors(bean:addrCmd,field:'billingState','errors')}">
			       <g:select name="billingState" from="${addrCmd.constraints.billingState.inList}"  
			                    value="${addrCmd?.billingState}" noSelection="['':'']"></g:select>
			       <span class="required">*</span>
			   </div>        
               <div class="inputBox ${hasErrors(bean:addrCmd,field:'billingZip','errors')}"><g:textField name="billingZip" value="${addrCmd?.billingZip}" size="10" maxlength="10" /><span class="required">*</span></div>
                <% if (addrCmd.type == AddressesCommand.GUEST) { %>          
	                <br clear="left" /> 
		            <label for="email">email:</label>
		            <g:textField name="email" value="${addrCmd?.email}"  class="${hasErrors(bean:addrCmd,field:'email','errors')}" size="30"/><span class="required">*</span>
	            <% } %>
                <br clear="left" /> 
	            <label for="phone">Phone:</label>
	            <g:textField name="phone" 
	                value="${(kettler.formatPhone(phone:addrCmd?.phone))}"  
	                class="${hasErrors(bean:addrCmd,field:'phone','errors')}"
	            /><span class="required">*</span>
	       </fieldset>

            <% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { // MFB %>

            <fieldset id="shipToInfo"  class="right">
                <legend>Ship To:</legend>
                <g:checkBox name="shipToSameAsBillTo" checked="${(addrCmd?.shipToSameAsBillTo)}"/>Same as Bill To
	            <div id="shipToDiffDiv">
				    <div class="inputTitle"><label for="shippingName">Name</label></div> 
				    <br clear="left" /> 
				    <div class="inputBoxL ${hasErrors(bean:addrCmd,field:'shippingName','errors')}"><g:textField name="shippingName" id="shippingName" value="${addrCmd?.shippingName}" size="30" maxlength="30" /><span class="required">*</span></div> 
				    <br clear="left" /> 
				    <div class="inputTitle"><label for="shippingAddress1">Address</label></div> 
				    <br clear="left" /> 
				    <div class="inputBoxL ${hasErrors(bean:addrCmd,field:'shippingAddress1','errors')}"><g:textField name="shippingAddress1" id="shippingAddress1" value="${addrCmd?.shippingAddress1}" size="30" maxlength="30" /><span class="required">*</span></div> 
				    <br clear="left" /> 
				    <div class="inputBoxL"><g:textField name="shippingAddress2" id="shippingAddress2" value="${addrCmd?.shippingAddress2}" size="30" maxlength="30" /></div> 
				    <br clear="left" /> 
				    <div class="inputTitle"><label for="shippingCity">City</label></div> 
				    <div class="inputTitleS"><label for="shippingState">State</label></div> 
				    <div class="inputTitle"><label for="shippingZip">Zip</label></div> 
				    <br clear="left" /> 
				    <div class="inputBox ${hasErrors(bean:addrCmd,field:'shippingCity','errors')}"><g:textField name="shippingCity" id="shippingCity" value="${addrCmd?.shippingCity}" size="15"  maxlength="15" /><span class="required">*</span></div>        
				    <div class="inputBoxS ${hasErrors(bean:addrCmd,field:'shippingState','errors')}">
				       <g:select name="shippingState" from="${addrCmd.constraints.billingState.inList}"  
				                    value="${addrCmd?.shippingState}" noSelection="['':'']"></g:select>
				       <span class="required">*</span>
				    </div>        
				    <div class="inputBox ${hasErrors(bean:addrCmd,field:'shippingZip','errors')}"><g:textField name="shippingZip" value="${addrCmd?.shippingZip}" size="10" maxlength="10" /><span class="required">*</span></div>
			    </div>
           </fieldset>
            <% } %>
           <% if (addrCmd.type == AddressesCommand.RETURN_CUSTOMER) { %>
                <g:hiddenField name="email" value="${addrCmd.email}"/>
           <% } else if (addrCmd.type == AddressesCommand.NEW_CUSTOMER) { %>          
		       <fieldset id="loginInfo" class="full">
    		       <legend>Login Info:</legend>
                   <br clear="left" /> 
                   <label for="email">email:</label>
                   <g:textField name="email" value="${addrCmd?.email}"  class="${hasErrors(bean:addrCmd,field:'email','errors')}" size="30"/><span class="required">*</span>
                   <br clear="left" /> 
	               <span id="passwordPrompt" class="${hasErrors(bean:addrCmd,field:'password','errors')} ${hasErrors(bean:addrCmd,field:'password','errors')}"> 
	                  <label for="password">password:</label>
	                  <g:passwordField name="password" value="${addrCmd?.password}"/>
	                  <span class="required">*</span>
					  <label for="confirmPassword">confirm password:</label>
	                  <g:passwordField name="confirmPassword" value="${addrCmd?.confirmPassword}"/>
	               </span><span class="required">*</span>                
	               <br clear="left" />
		       </fieldset>
           <% } %>
             <g:render template='checkout/submitButtons' model="[step:1]"/> 
        </g:form>
    </div>
<g:javascript>
    if ($('#shipToSameAsBillTo:checked').val() !== undefined) {
        $('#shipToDiffDiv').hide();
    }
    $('#shipToSameAsBillTo').click(function(){
        $('#shippingName').val(''); 
        $('#shippingAddress1').val(''); 
        $('#shippingAddress2').val(''); 
        $('#shippingCity').val(''); 
        $('#shippingState').val(''); 
        $('#shippingZip').val(''); 
        if ($('#shipToSameAsBillTo:checked').val() !== undefined) {
	        $('#shipToDiffDiv').hide();
	    } else {
            $('#shipToDiffDiv').show();
	    }
    });
    <% if (!addrCmd.errors) { %>
        $('#shipToInfo').height($('#billToInfo').height());
    <% } %>   
    $('#stepTwoLink').click(function(){
        $("#_eventId_stepTwo").trigger("click");
        $("#_eventId_stepTwo").hide();
    });   
    $('div.body').height('500px');
    <% if (addrCmd.hasErrors()) { %>
        var height = parseInt($('#main').height())+parseInt($('#pageErrors').height()+20);
        $('div.body').height(height+'px');
    <% } %>
    <% if (flash.message) { %>
        var height = parseInt($('#main').height())+parseInt($('#pageErrors').height()+20);
        $('div.body').height(height+'px');
    <% } %>
	$("input").keypress(function (evt) {
		var enterKey = 13;
		if ((evt.charCode || evt.keyCode) == enterKey) {
			return false;
		}
	});    
    $(document).ready( function () {
        $('div#main').height( $('#footer').offset().top - $('div#main').offset().top );
    });
</g:javascript>
</body>
</html>
