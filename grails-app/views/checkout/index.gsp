<%@ page import="com.kettler.controller.retail.AddressesCommand" %>
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
    <div>
	    <h1>Checkout</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
	    <g:hasErrors bean="${loginCmd}">
	        <div id="pageErrors" class="errors">
	            <g:renderErrors bean="${loginCmd}" as="list"  />
	        </div>
	    </g:hasErrors>
		
	    <fieldset id="returningCustomers"  class="left">
	        <legend>Returning Customers</legend>
	        <p>In order to continue, please login to your account</p>
			<g:form action="returning" method="post" >
			    <g:hiddenField name="type" value="${AddressesCommand.NEW_CUSTOMER}"/>
	            <dl>
	                <dt>Email Address:</dt>
	                <dd><g:textField name="email" size="30"/></dd>
	                <dt>Password:</dt>
	                <dd><g:passwordField name="password" size="15"/></dd>
	            </dl>		
				<g:submitButton name="login" value="Login and Checkout" class="button" />
			</g:form>
	    </fieldset>
	    
		<fieldset id="newCustomers" class="right">
		    <legend>New Customers</legend>
		    <p>
		       Create a customer profile to store your information. 
		       Also track your current orders, review previous orders, and simplify returns
		    </p>
		    <br/>
            <br/>
            <br/>
            <br/>
	        <g:link action="checkout" class="button clickme" title="Click to checkout as a new customer." params="[type:AddressesCommand.NEW_CUSTOMER]">New Customer</g:link>
		</fieldset>
	</div>
	<div id="asGuestDiv">
        <% if (containsGiftCard) { %>
            <p>Your cart contains a gift card, which does not allow you to proceed as a guest.  Please log in or create an account. </p>
        <% } else { %>
	    <g:link action="checkout" class="button" title="Click to checkout as a guest. Your personal information will not be retained." 
	           params="[type:AddressesCommand.GUEST]">Continue as Guest</g:link>
        <% } %>
	</div>    
</div>
<g:javascript>
    $('#newCustomers').height($('#returningCustomers').height());
    /*
	    $('#saveAccountHelp').click(function() {
	        alert('${(CH.config.saveAccountHelp?.replaceAll(/\n/,''))}');
	    });
    */
</g:javascript>    
</body>
</html>
