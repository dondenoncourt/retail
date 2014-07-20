<%@ page import="java.awt.Dimension" %>
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="store" />
    <meta name="description" content="KETTLER Retail Outlet Store for ${keywords}"/> 
    <meta name="keywords" content="KETTLER, kettlerstore.com, ${keywords} "/>     
    <title>Kettler Store Virginia Beach: promotions for ${keywords}</title> 
    <link href="${createLinkTo(dir:'css',file:'jquery-ui.css')}" rel="stylesheet" type="text/css" />
</head> 

<div id="promotions">
    <br/>
    <g:each in="${adImages}" var="ad" status="i">
        <img id="adImage${i}" src="${createLinkTo(dir:'images/store', file:ad.key)}"/>
    </g:each> 
</div>
<g:javascript>
    $('#promotions').addClass('current');
</g:javascript>