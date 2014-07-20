<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="${(['contract', 'store'].find {it == params.mode}) ? params.mode : 'kettlerusa'}"/>
    <title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title>
    <link href="${createLinkTo(dir: 'css', file: 'checkout.css')}" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
    </script>
    <style type="text/css">
    	div {overflow: hidden;}
    	#content {margin-left: 5px;}
    	#footer {margin-bottom: 0;
    			 margin-top:0;}	
    </style>
</head>

<body>
	<div id="content" class="body">
	    <h1>Register Warranty</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
	    <g:hasErrors bean="${consumer}">
	        <div id="pageErrors" class="errors">
	            <g:renderErrors bean="${consumer}" as="list"  />
	        </div>
	    </g:hasErrors>
        
		<div>
			<br/>
			<h2> Benefits of Warranty Registration </h2>
			<p>
			Filling out this short form will help us to provide you with more efficient warranty service. This is also a convenient confirmation of ownership in case of an insured loss such as fire, flood, or theft. 
			</p>
			  
			<h2> Personal Information </h2>
			<p>
			KETTLER respects your personal information and your privacy. We think your gender, age, income, marital status and blood type is none of our business! The personal information we do collect will not be shared with outside companies except as necessary in the normal course of business (for example shipping companies in order to deliver a shipment). 
			</p>
			  
			<h2> Purchase Receipt </h2>
			<p>
			A copy of the purchase receipt may be required if the product was not purchased directly from kettlerusa.com. 
			</p>
			  
		</div>
		
	    <fieldset id="existingCustomers"  class="left" style="height:222px;">
	        <legend>Sign into Your Account</legend>
	        <p> Please sign in to save your warranty information for future reference.<br/>
	         If you do not have an account, you can create one by clicking Register New Account.</p>
			<g:form url="[action:'login',controller:'register']"  method="post"  >
                <g:hiddenField name="registerWarranty" value="true"/>
	            <dl>
	                <dt>Email Address:</dt>
	                <dd><g:textField name="email" size="30" value="${params.email}"/></dd>
	                <dt>Password:</dt>
	                <dd><g:passwordField name="password" size="15" value="${params.password}"/></dd>
	            </dl>		
				<g:submitButton name="login" value="Login and Register" class="button clickme" />
				<g:actionSubmit action="newAccountForWarranty" value="Register New Account" class="button clickme" />
			</g:form>
		</fieldset>
		
		<fieldset id="guests" class="right" style="height:222px;">
		    <legend>Proceed as Guest</legend>
		    <p>
				Proceeding as a guest allows you to register your product without establishing an account. 
				Without an account, you will not be able to retrieve or verify your information, 
				but our service team will still be able to access your information. 
				We encourage you to create an account to store your product information for future reference. 
		    </p>
		    <br/>
		    <br/> <br/>
	        <g:link action="register" class="button" title="Click to register a warranty as a Guest." >Register as a Guest</g:link>
	         <br/>
	         <br/>
		</fieldset>
		
	     </div>  
	    <br/>

	</div>
</body>
</html>
