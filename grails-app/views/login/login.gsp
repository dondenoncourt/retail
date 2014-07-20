<%@ page import="com.kettler.domain.orderentry.share.Role" %>
<%@ defaultCodec="html" %>

<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta name="layout" content="${division?.replaceAll(/ /,'')?:'kettlerusa'}" />
      <title>Login</title>
  </head>
  
<body >
    <div style="width:910px;height: 100px;">
	    <jsec:notAuthenticated>
			<div id="loginFormDiv">
		       <g:if test="${flash.message}"><div class="errors">${flash.message}</div></g:if>
		        <g:hasErrors bean="${cmd}"><div class="errors"><g:renderErrors bean="${cmd}" as="list" /></div></g:hasErrors>
		        <g:form name="loginForm" action="login" method="post" >
		            eMail:<g:textField name="email" size="30" value="${cmd?.email}"/>
		            Password:<g:passwordField name="password"  size="10" />  
		            <g:submitButton class="create" name="login" value="Login" />
		        </g:form>
		    </div>   
	    </jsec:notAuthenticated>
	   <jsec:isLoggedIn>
			<div id="loginFormDiv">
		        <g:form name="loginForm" action="changePassword" method="post" >
		           <p>Welcome, ${user?.firstname} ${user?.lastname}. You are authenticated as a
				        <jsec:hasRole name="${Role.SUPER_ADMIN}">Super Admininistrator</jsec:hasRole>          
				        <jsec:hasRole name="${Role.CUST_ADMIN}">Customer Admininistrator</jsec:hasRole>          
				        <jsec:hasRole name="${Role.REP}">Customer Rep</jsec:hasRole>          
				        <jsec:hasRole name="${Role.REP_ADMIN}">Rep Admininistrator</jsec:hasRole>          
				        <jsec:hasRole name="${Role.CUSTOMER}">Customer</jsec:hasRole>         
				        <jsec:hasRole name="${Role.KETTLER}">KETTLER Employee</jsec:hasRole>
				   </p>
		            <g:submitButton class="clickme" name="changePassword" value="Change Password" title="Click to change your password" />
		        </g:form>
	        </div>
	    </jsec:isLoggedIn>
    </div>
</body>
</html>
