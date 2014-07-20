<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>

<div class="navTop">
	<ul>
		<li>
			
			<a class="kettlerusa"  
				  href="${kettler.resource(contextPath:'/',absolute:true)}"
			> 	<div class="divHeaderMenuKettlerUsa">
					<% if (request.serverName ==~ /www.kettlercanada.com/) {  %>
						Kettler canada	
					<% } else if (request.serverName ==~ /www.kettlerstore.com/) {  %>
						Kettler store
					<% } else if (request.serverName ==~ /www.kettlercontract.com/) {  %>
						Kettler contract
					<% } else if (request.serverName ==~ /www.kettlerlatinoamerica.com/) {  %>
						Kettler
					<% } else { %>
						Kettler usa
					<% } %>
				</div>
			</a> 
			
		</li>
		<%
			def divisions = null
			try {
				divisions = WebDivision.retail.list().sort{it.name}
			} catch (e) { }
		%>
		<g:each in="${divisions}" var="division">
			<% if (request.serverName == 'www.kettlerlatinoamerica.com' && division.name == 'patio') return %>
			<li>
				
				<a 
						  <% if (division.name == 'patio') { %>
							  href="${kettler.resource(contextPath:'/patio-furniture',absolute:true)}"
						  <% } else { %>
							  href="${kettler.resource(contextPath:'/'+division.name.replaceAll(' ','-'),absolute:true)}"
						  <% } %>
						  <% if (params.division == division.name ) { %>
								class="active"
						  <% } %>
						> 
						<div class="divHeaderMenuDivisions 
							<% if (params.division == division.name ) { %>
								divHeaderMenuActive
							<% } %>
						
						">
							<%
								def divisionName = (division.name=='bikes')?'bicycles':division.name
								divisionName = message(code:"menu."+divisionName.replaceAll(' ','.'))
							%>
							${divisionName}
						</div>
				</a>
				
				<g:render template="/layouts/${division.name.replaceAll(' ','')}Categories" model="['hoverMenu':true]"/>
			</li>
		</g:each>
		<div class="clearBoth"></div>
	</ul>
	<div id="loginSearchCart"> 
		<div id="loginAndSearch"> 
		    <% if (!['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
			    <% if (session.consumer && session.consumer.saveAccount) { %>
			        <g:link controller="register" action="logout" params="[division:division]">Logout</g:link>
			        <g:link controller="consumer" action="show" id="${session.consumer.id}">Your Account</g:link>
			    <% } else { %>
			        <a href="${createLink(controller:'register')}/register.gsp?division=${division?.replaceAll(/ /,'')?:'kettlerusa'}" 
			            title="Click to create a Kettler account. Note you can auto-register during checkout">Register</a>
			        <a href="${createLink(controller:'register')}/login.gsp?division=${division?.replaceAll(/ /,'')?:'kettlerusa'}"
			            title="Click to log in to your Kettler account with your email and password">Login</a>
			    <% } %>
		    <% } %>	        
		</div>
		<div class="cart"> 
		    <% if (!['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
			    <g:link action="cart" controller="shop" params="[division:division]" title="Click to view your shopping cart details">Cart</g:link>
			    <br/>
			    <% if (session.cartId) { 
			    	def cart = cart?:Cart.get(session.cartId)
			    	%>
			        <div id="miniCart">${(cart?.items?.size())} item${(cart?.items?.size()>1?'s':'')}</div>
			    <% } else { %>
			        <div id="miniCart"></div>
			    <% } %>
		    <% } %>
		</div>
		<g:form name="searchForm" action="search" controller="shop">
	            <input type="hidden" name="division" value="${division}"/>
	            <input type="hidden" name="mode" value="${params.mode}"/>
	            <input type="text" id="where" name="where" size="11" title="Key search words such as tricycle or elliptical"/> 
	            <input type="submit" name="search" value="Search" title="Click to search" />
	    </g:form>
		
	</div>
	<div class="clearBoth"></div>
</div>
<% if (request.serverName == 'www.kettlerusa.com'      ||
       request.serverName == 'www.kettlercontract.com' || 
       request.serverName == 'www.kettlerstore.com'    ||
       request.serverName == 'localhost'               ||
       request.serverName == 'grailsdev'       ) {
    %>
<div id="eightHundred">888.253.8853</div> 
<% } %>
<g:javascript>
    $(document).ajaxStart(function() {$("#logoImg").attr("src", "${resource(dir:'images',file:'spinner35x35.gif')}");})
               .ajaxStop(function()  {$("#logoImg").attr("src", "${resource(dir:'images',file:'kettler_logo.gif')}");});
    $('#searchForm').submit(function() {
         if ($.trim($('#where').val()).length == 0) {
            alert('Please key a search value.');
            $('#where').focus();
            return false;
         }
         return true;
     });

</g:javascript>
