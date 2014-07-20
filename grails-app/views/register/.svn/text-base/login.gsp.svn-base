<html>
  <head>
    <meta http-equiv="Content-Type"
           content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kettlerusa" />
    <title>Login</title>
  </head>
  <body>
    <div id="custRepLogin">
        <div id="custLogin">
            <br/>
	       <h1>Customer Login</h1>
            <br/>
	       <g:if test="${flash.message}">
	         <div class="errors">${flash.message}</div>
	       </g:if>
	       <g:form action="login" controller="register">
	         <g:hiddenField name="division" value="${params?.division}"/>
	         <div class="dialog">
	           <table>
	             <tbody>
	                <tr class="prop">
	                  <td valign="top" class="name">
	                    <label for="email">email:</label>
	                  </td>
	                  <td valign="top">
	                    <input type="text" id="email" name="email" value="${params?.email}"/>
	                  </td>
	                </tr>
	                <tr class="prop">
	                  <td valign="top" class="name">
	                    <label for="password">password:</label>
	                  </td>
	                  <td valign="top">
	                    <input type="password" id="password" name="password"  value="${params?.password}"/>
	                  </td>
	                </tr>
	             </tbody>
	           </table>
	         </div>
	         <div class="buttons">
	             <input type="submit" class="clickme" value="Login" />
	         </div>
	       </g:form>
	    </div>
        <div class="repLogin">
            <br/>
           <h1>Retailer &amp; Rep Login</h1>
            <br/>
           <form action="/kettler/login/login" method="post" name="loginForm" id="loginForm" > 
               <table>
                 <tbody>
                    <tr class="prop">
                      <td valign="top" class="name">
                        <label for="email">email:</label>
                      </td>
                      <td valign="top">
                        <input type="text" name="email" value="" id="email" />
                      </td>
                    </tr>
                    <tr class="prop">
                      <td valign="top" class="name">
                        <label for="password">password:</label>
                      </td>
                      <td valign="top">
                        <input type="password" name="password" id="password" value="" /> 
                      </td>
                    </tr>
                 </tbody>
               </table>
             <div class="buttons">
                 <input type="submit" class="clickme" value="Login" />
             </div>
           </form> 
        </div>
    </div>
	<% if (params?.division == 'patio') { %>
	    <g:javascript>
	        $('#sidebar').hide();
	    </g:javascript>
	<% } %>
  </body>
</html>
