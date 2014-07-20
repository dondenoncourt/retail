<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <meta name="layout" content="kettlerusa" />
        <title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title> 
</head> 
<body> 
<div id="content"> 
	<div id="aboutUs">
		<div class="left">
			<p><g:message code="verbiage.about.us.p.1"/>
			</p>
		</div>
		<div class="right">
		  <img src="${createLinkTo(dir:'images')}/world HQ.jpg"/>
		</div>
        <div class="left">
            <img src="${createLinkTo(dir:'images')}/US HQ.jpg"  />
        </div>
        <div class="right">
            <br/>
			<p><g:message code="verbiage.about.us.p.2"/></p>
	        <p>		
				<g:message code="verbiage.about.us.p.3"/>
			</p> 	 
        </div>
	</div>
</div>
<g:javascript>
    $('#aboutUs2').addClass('active');
</g:javascript>
</body> 
</html>
