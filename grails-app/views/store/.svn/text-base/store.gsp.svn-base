<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>   

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <meta name="layout" content="store" />
    <meta name="description" content="KETTLER Retail Outlet Store for ${keywords}"/> 
    <meta name="keywords" content="KETTLER, kettlerstore.com, ${keywords} "/>     
    <title>Kettler Store | Virginia Beach, Norfolk, Chesapeake | Buy Patio, Deck & Outdoor Furniture</title> 
    <g:javascript library="jquery" plugin="jquery"/>
    <g:javascript src="jquery-ui.min.js"/> 
    <g:javascript src="jquery.cycle.all.min.js"/>
</head> 
<body> 
<div id="content"> 

    <div class="top"> 
       <g:if test="${flash.message}">
         <div class="message">${flash.message}</div>
       </g:if>
       <div class="categoryLifeStyles">
           <div id="catImages"> 
               <span class="slideshow" >                                                     
                   <g:each in="${lifeStyleImages}" var="lifeStyle">
                       <img src="${createLinkTo(dir:'images/store', file:lifeStyle)}"
                         style="display: block;"
                       />
                   </g:each>
               </span>
           </div>
           <div id="verbiage"><g:render template="/store/verbiage" /></div> 
		   <div class="clearBoth"></div>
       </div> 
    </div> 
</div>	 
<g:javascript>
     $('.slideshow').cycle({fx: 'fade', speed: 500, timeout:  3000 });
</g:javascript>
</body> 
