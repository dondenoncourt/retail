<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <title><g:layoutTitle default="Grails" /></title>
    <!--[if IE]>
		<link href="${createLinkTo(dir:'css',file:'style-ie.css')}" rel="stylesheet" type="text/css" /> 
	<![endif]-->
    <% if (request.serverName ==~ /www.kettlerlatinoamerica.com/) { %>
        <link href="${createLinkTo(dir:'css',file:'latino.css')}" rel="stylesheet" type="text/css" /> 
    <% } %>
	<link href="${createLinkTo(dir:'css',file:'style2.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'tabletennis.css')}" rel="stylesheet" type="text/css" />
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
        <g:render template='/layouts/cartMenuLogo' model="${[division:'table tennis']}"/>
        <div class="navSecond"> 
		    <g:render template='/layouts/tabletennisCategories' />
		    <g:render template='/layouts/socialMedia' />
			<div class="clearBoth"></div>
        </div> 
    </div> 
    <g:render template='/layouts/mainFooter' model="${[currentDivision:'table tennis']}"/>
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
