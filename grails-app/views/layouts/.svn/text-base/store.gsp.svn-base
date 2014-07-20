<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
 
<html xmlns="http://www.w3.org/1999/xhtml">  
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <title><g:layoutTitle default="Grails" /></title> 
    <meta name="description" content="Kettler USA online shopping for tricycle, pedal tractors, elliptical, rower, table tennis, ping pong, patio furniture, outdoor furniture, patio furniture"/> 
    <meta name="keywords" content="tricycle, pedal tractors, elliptical, rower, table tennis, ping pong, patio furniture, outdoor furniture, patio furniture"/>     
    <!--[if IE]>
		<link href="${createLinkTo(dir:'css',file:'style-ie.css')}" rel="stylesheet" type="text/css" /> 
	<![endif]-->
	<link href="${createLinkTo(dir:'css',file:'style2.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'contract.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'patio.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'store.css')}" rel="stylesheet" type="text/css" /> 
    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
	<g:javascript library="jquery" plugin="jquery"/> 
    <g:javascript src="DD_roundies_0.0.2a-min.js"/>
    <g:javascript library="application" />
	<g:layoutHead />
	<g:render template='/layouts/googleAnalytics' model="${[mode:params.mode]}"/>
</head> 
<body> 
<div id="container"> 
    <div id="header"> 
		<div class="navTop">
			<ul>
				<li>
				    <a class="kettlerusa" href="${resource(contextPath:'/store',absolute:true)}/store">Kettler Store</a> 
				</li>
	            <li><a id="aluminum"    href="${kettler.resource(contextPath:'/patio-furniture/aluminum',absolute:true)}"  title="${WebCategory.findByName('aluminum')?.title}">Aluminum</a></li> 
	            <li><a id="resin"       href="${kettler.resource(contextPath:'/patio-furniture/resin',absolute:true)}"  title="${WebCategory.findByName('resin')?.title}">Resin</a></li> 
	            <li><a id="wroughtiron" href="${kettler.resource(contextPath:'/patio-furniture/wrought-iron',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('wrought iron')?.title}">Wrought Iron</a></li>
	            <li><a id="mixedmaterial" href="${kettler.resource(contextPath:'/patio-furniture/mixed-material',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('mixed material')?.title}">Mixed Material</a></li>
	            <li><a id="otherkettler" href="#" onclick="window.open('${resource(contextPath:'/homePage',absolute:true)}/home');" title="Bikes, fitness, table tennis, and toys" class="lastMenuItem">
	                Other Products
		            </a>
		        </li>
			</ul>
		</div> 
        <div class="navSecond">
        	<ul>
	           <li>
	            <a id="requestForQuote" href="${createLink(controller:'store',action:'requestForQuote')}" >RFQ</a>
	            <a id="directions" href="${createLink(controller:'store',action:'directions')}" > Directions &amp; Hours</a>
	            <a id="financing" href="${createLink(controller:'store',action:'financing')}" class="${adImages?'':'lastMenuItem'}" > Financing </a>
	            <%-- perhaps put the image in retail-config.groovy --%>
	            <% if (adImages) { %>
	                <a id="promotions" href="${createLink(controller:'store',action:'promotions')}" class="lastMenuItem" >Promotions</a>
	            <% } %>
	           </li>
	        </ul>
			<span id="socialLinks">
				<a href="https://www.facebook.com/kettlershowroom"><img src="${createLinkTo(dir:'images',file:'facebook24x24.png', absolute:true)}" class="socialmedia"></img></a>
			</span>
			<g:javascript>
			function lineUpSocialLinks() {
			  $('#socialLinks').css('right', $('#header div.navSecond').offset().left+10+'px');
			}
			lineUpSocialLinks(); 
			$(window).resize(function() {
			  lineUpSocialLinks();
			});
			</g:javascript>    	
			<div class="clearBoth"></div>
		</div> 
    <g:render template="/layouts/collections" model="${[currentDivision:'patio']}"/>
 	</div>


    <div id="main"> 
        <g:layoutBody />
    </div>
    <div id="footer"> 
 	<ul>        
		<li id="copyright" class="lastItem"> 
		    &copy; 2013 Kettler USA
	    </li>
	</ul>

    </div> 
     
</div> 
</body> 
</html>
<%--http://www.filamentgroup.com/lab/achieving_rounded_corners_in_internet_explorer_for_jquery_ui_with_dd_roundi/ --%>
<g:javascript src="DD_roundies_0.0.2a-min.js"/>
<g:javascript>
    if ($.browser.msie) {
        <%--http://www.filamentgroup.com/lab/achieving_rounded_corners_in_internet_explorer_for_jquery_ui_with_dd_roundi/ --%>
        DD_roundies.addRule('#header .nav a', '10px 10px 0 0');
        DD_roundies.addRule('.lifeStyleLinks', '10px');
        DD_roundies.addRule('.clickme', '5px');
        DD_roundies.addRule('.button', '5px');
    }
    var divPagePos = $('div.page').position();
    if (divPagePos != null) {
        var paginationTop = $('div.page').position().top;
        $('#filters').css('top', (paginationTop)+'px');
        $('#sidebar').height($('#content').height());
        <% if (!params.rowSize || params.rowSize > 4) { %>
	        var collectionMenuHeight = $('ul.menu').height();
            var filtersTop = $('#filters').position().top;
	        $('#filters').css('top', (collectionMenuHeight+filtersTop+20)+'px');
            $('#filters').css('border-top', 'none');
        <% } %>
    } else {
        $('#sidebar').hide();
        $('#content').css('width', '100%');
    }
</g:javascript> 
