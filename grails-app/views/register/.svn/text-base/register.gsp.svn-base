<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>

<html>
  <head>
    <meta http-equiv="Content-Type"
           content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${params?.division}" />
    <title>Login</title>
  </head>
  <body> 
    <div class="body">
       <g:if test="${flash.message}">
         <div class="message">${flash.message}</div>
       </g:if>
       <g:hasErrors bean="${cmd}">
           <div class="errors">
               <g:renderErrors bean="${cmd}" as="list"  />
           </div>
       </g:hasErrors>
       <g:hasErrors bean="${consumer}">
           <div class="errors">
               <g:renderErrors bean="${consumer}" as="list"  />
           </div>
       </g:hasErrors>
       <g:form action="register" method="post" >
            <g:hiddenField name="division" value="${params?.division}"/>
            <fieldset>
              <legend>New Customers, please create an account:</legend>
              <dl> 
                <dt><label for="name" >name:</label></dt>
                <dd class="${hasErrors(bean:consumer,field:'name','errors')}"><input type="text" name="name" id="name" value="${consumer?.name}" /></dd>
                <dt><label for="email">email:</label></dt>
                <dd class="${hasErrors(bean:consumer,field:'email','errors')}"><input type="text" name="email" id="email" value="${consumer?.email}"/></dd>
                <dt><label for="password">password:</label></dt>
                <dd class="${hasErrors(bean:consumer,field:'password','errors')}"><input type="password" name="password" id="password" value="${consumer?.password}"/></dd>
                <dt><label for="confirmPassword">confirm password:</label></dt>
                <dd><input type="password" name="confirmPassword" id="confirmPassword" value="${cmd?.confirmPassword}"/></dd>
                <dt>&nbsp;</dt><dd class="${hasErrors(bean:cmd,field:'confirmPassword','errors')}"><input type="submit" value="Create Account" /></dd>
              </dl>
            </fieldset>
       </g:form>
    </div>
  </body>
</html>
