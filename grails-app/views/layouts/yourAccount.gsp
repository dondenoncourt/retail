<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="grails.util.Environment" %>

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <title><g:layoutTitle default="Grails" /></title>
    <!--[if IE]>
		<link href="${createLinkTo(dir:'css',file:'style-ie.css')}" rel="stylesheet" type="text/css" /> 
	<![endif]-->
    <link href="${createLinkTo(dir:'css',file:'style2.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'home.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'yourAccount.css')}" rel="stylesheet" type="text/css" /> 
    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <g:javascript src="jquery/jquery-1.4.2.js"/>
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
				<li><a id="aboutUs" href="${createLink(controller:'homePage',action:'aboutUs')}" >About Us</a></li>
				<li><a id="infoEdu" href="${createLink(controller:'homePage',action:'infoEdu')}" >FAQ's &amp; Info</a></li>
				<li><a id="custInfo" href="${createLink(controller:'homePage',action:'custInfo')}" >Testimonials</a></li>
				<li><a id="awards" href="${createLink(controller:'homePage',action:'awards')}" >Awards</a></li>
				<li><a id="manuals" href="${createLink(controller:'homePage',action:'manuals')}" >Manuals</a></li>
				<li><a id="partsService" href="${createLink(controller:'homePage',action:'partsService')}" >Parts and Service</a></li>
			</ul>
			<div class="clearBoth"></div>
        </div>
    </div>
    <div id="yourAccountMenu">
	    <ul>
	         <li>
	             <g:link controller="consumer" action="show" id="${session.consumer.id}">Profile</g:link> 
	             <g:link controller="consumer" action="orderHst" >Order History</g:link>
                 <g:link controller="consumer" action="orderHst" params="[return:true]">Order Return</g:link>
                 <g:link controller="consumer" action="giftcards" >Gift Cards</g:link>
	             <g:link controller="consumer" action="warranties" >Warranties</g:link>
	         </li>
	    </ul>
    </div>
    <g:render template='/layouts/mainFooter' model="${[currentDivision:'kettler usa']}"/>
</div> 
</body> 
</html>
