<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page import="com.kettler.domain.item.share.WebDivision" %>

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <% if (request.serverName ==~ /www.kettlercanada.com/ ) { 
    	params.mode = 'canada' 
    %>
        <title>Kettler Canada Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title>
    <% } else if (request.serverName ==~ /www.kettlerlatinoamerica.com/ ) {
    	params.mode = 'latino'
    %>
        <title>Kettler Latino America Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title>
    <% } else { %>
		<title>Kettler USA, online shopping for tricycles, pedal tractors, ellipticals, rowers, table tennis, ping pong, patio furniture and outdoor furniture</title>
	    <meta name="description" content="Kettler USA online shopping for tricycle, pedal tractors, elliptical, rower, table tennis, ping pong, patio furniture, outdoor furniture, patio furniture"/> 
	    <meta name="keywords" content="tricycle, pedal tractors, elliptical, rower, table tennis, ping pong, patio furniture, outdoor furniture, patio furniture"/>     
    <% } %> 
    <!--[if IE]>
		<link href="${createLinkTo(dir:'css',file:'style-ie.css')}" rel="stylesheet" type="text/css" /> 
	<![endif]-->
	<link href="${createLinkTo(dir:'css',file:'style2.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'home.css')}" rel="stylesheet" type="text/css" /> 
    <% if (params.mode == 'canada' || request.serverName ==~ /www.kettlercanada.com/) { %>
        <link href="${createLinkTo(dir:'css',file:'canada.css')}" rel="stylesheet" type="text/css" /> 
    <% } %>
    <% if (request.serverName ==~ /www.kettlerlatinoamerica.com/) { %>
        <link href="${createLinkTo(dir:'css',file:'latino.css')}" rel="stylesheet" type="text/css" /> 
    <% } %>
    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
	<g:javascript src="jquery/jquery-1.4.2.min.js"/> 
    <g:javascript src="DD_roundies_0.0.2a-min.js"/>
    <g:javascript library="application" />
	<g:layoutHead />
	<g:render template='/layouts/googleAnalytics' model="${[mode:params.mode]}"/>
</head> 
<body> 
<div id="container"> 
    <div id="header"> 
        <g:render template='/layouts/cartMenuLogo' model="${[division:'kettler usa']}"/>
        <div class="navSecond"> 
        	<ul>
		    	<li><a id="aboutUs2" href="${kettler.resource(contextPath:'/about-us', absolute:true)}" ><g:message code="menu.about.us"/></a></li>
	            <li><a id="awards" href="${kettler.resource(contextPath:'/awards', absolute:true)}" ><g:message code="menu.awards"/></a></li>
	            <li><a id="manuals" href="${kettler.resource(contextPath:'/manuals', absolute:true)}" ><g:message code="menu.manuals"/></a></li>
	            <% if (request.serverName != 'www.kettlerlatinoamerica.com') { %>
                    <li><a id="partsService" href="${kettler.resource(contextPath:'/parts', absolute:true)}">Parts and Service</a></li>
                    <li><a id="catalogs" href="${createLink(controller:'homePage',action:'catalogs')}" >Catalogs</a></li>
		        <% } %>
	            <% if (!['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
		            <li><a id="giftcard"    href="${createLink(controller: 'giftCard',action: 'index')}" class="${lastMenuItem}" title="Gift Cards">gift card</a></li>
			        <li><a href="${kettler.resource(contextPath:'/contact-us')}" >Contact Us</a></li>
		        <% } %>
	            <% if (['www.kettlercanada.com'].find{ it == request.serverName}) { %>
			        <li><a href="${kettler.resource(contextPath:'/contact-us')}" >Contact Us</a></li>
		        <% } %>
           		<%-- sometimes division is a String sometimes it is WebDivision, downcast to a String --%>
 	            <% if (!['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
	            	<li><a id="whereToBuy" class="lastMenuItem" title="Click to see where to buy"
	            		<% if (divName == 'fitness') { %>
		            		href="${createLink(controller:'dealer',action:'webDealers', params:[division:'fitness',itemId:itemId,mode:params.mode])}"
	            		<% } else if (division) { %>
		            		href="${createLink(controller:'locate',action:'index', params:[division:divName,itemId:itemId,mode:params.mode])}"
	            		<% } else { %>
		            		href="${createLink(controller:'homePage',action:'pickWhereToBuyDiv')}"
	            		<% } %>
	            	>
	                    <g:message code="menu.where.to.buy"/>
	            	</a></li>
            	<% } %>
            </ul>
		    <g:render template='/layouts/socialMedia' />
			<div class="clearBoth"></div>
        </div>
    </div> 
    <g:render template='/layouts/mainFooter' model="${[currentDivision:'kettler usa']}"/>

</div> 
<% if (request.serverName == /www.kettlerusa.com/ ) { %>
<script type="text/javascript">
     setTimeout(function(){var a=document.createElement("script");
     var b=document.getElementsByTagName("script")[0];
     a.src=document.location.protocol+"//dnn506yrbagrg.cloudfront.net/pages/scripts/0014/2172.js?"+Math.floor(new Date().getTime()/3600000);
     a.async=true;a.type="text/javascript";b.parentNode.insertBefore(a,b)}, 1);
</script>
<% } %>
</body> 
</html>
<g:javascript>
    $(document).ready(function () {
        $('#main').height($('#footer').offset().top - $('div#header').height());
    });
</g:javascript>
