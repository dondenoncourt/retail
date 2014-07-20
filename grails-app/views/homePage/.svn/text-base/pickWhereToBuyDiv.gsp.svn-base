<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>

<html xmlns="http://www.w3.org/1999/xhtml">  
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="layout" content="kettlerusa" />
	<title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title> 
	<style type="text/css">
		#content .top { 
    		margin-left: 10px;
    		height: 220px;
    	}
        #content .bottom {
           margin-left: 20px; 
            clear:left;
        }
	</style>
</head> 
<body> 
<div id="content"> 
	<g:if test="${flash.message}">
    	<div class="errors">${flash.message}</div>
	</g:if>

    <div class="top">
	    <h1>Where to Buy KETTLER Products</h1>
	
        <p>Click product category image to find stores:</p>
        <g:each in="${divisions}" var="division">
           <div class="lifeStyleLinks manualsLinks divManuals${(division.name.capitalizeNames())}">
                <% def divLinkName = division.name.replaceAll(/^\/\w*/,'') %>
               <a href="${kettler.resource(contextPath:'/locate',absolute:true, mode:params.mode, division:divLinkName)}" title="${WebDivision.findByName(divLinkName)?.title} Where to Buy"> 
                   <h2>${(division.name.capitalizeNames())}</h2>
               </a>
               <a href="${kettler.resource(contextPath:'/locate',absolute:true, mode:params.mode, division:divLinkName)}" title="${WebDivision.findByName(divLinkName)?.title} Where to Buy"> 
                    <img src="${createLinkTo(dir:'images/'+division.name.replaceAll(/^\/\w*/,''))}/life-style.jpg" width="100%"/>
               </a>
           </div>
        </g:each>
	
	</div> 

    <div class="bottom">
        <p><strong>Store Locator</strong></p>
        <p>To find our KETTLER bicycles, fitness equipment, patio furniture, table tennis equipment and toys, simply choose the category you wish to search and enter your address or zip code to locate the KETTLER dealers nearest you.</p>

        <p><strong>Shop Online</strong></p>
        <p>To shop with KETTLER retailers online, choose the product category of your choice, then select the Online Retailers button on the top right. You will find a list of dealers based on product category that carry KETTLER products.</p>

        <p>We hope you enjoy your KETTLER buying experience as much as you enjoy our products.</p>
        <br/>
    </div>
	
<%--	<g:javascript>--%>
<%--	    var colors = [ "#FF0000", "#00CC00", "#990099", "#3399FF", "#FF9900", "#FF9900"];--%>
<%--	    $('.lifeStyleLinks').each(function(index) {--%>
<%--	       $('.lifeStyleLinks:nth('+index+')').css('background-color', colors[index]);--%>
<%--	    });--%>
<%--    </g:javascript>--%>

</div>

</body> 
</html>
