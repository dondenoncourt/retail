<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<title>Kettler USA, online shopping for tricycles, pedal tractors, ellipticals, rowers, table tennis, ping pong, patio furniture and outdoor furniture</title>
    <meta name="description" content="Kettler USA online shopping for tricycle, pedal tractors, elliptical, rower, table tennis, ping pong, patio furniture, outdoor furniture, patio furniture"/> 
    <meta name="keywords" content="tricycle, pedal tractors, elliptical, rower, table tennis, ping pong, patio furniture, outdoor furniture, patio furniture"/>     
    <!--[if IE]>
		<link href="${createLinkTo(dir:'css',file:'style-ie.css')}" rel="stylesheet" type="text/css" /> 
	<![endif]-->
	<link href="${createLinkTo(dir:'css',file:'style2.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'contract.css')}" rel="stylesheet" type="text/css" /> 
    <link href="${createLinkTo(dir:'css',file:'patio.css')}" rel="stylesheet" type="text/css" /> 
    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
	<g:javascript library="jquery" plugin="jquery"/> 
    <g:javascript src="DD_roundies_0.0.2a-min.js"/>
    <g:javascript library="application" />
	<g:layoutHead />
	<g:render template='/layouts/googleAnalytics' model="${[mode:'contract']}"/>
</head> 
<body> 
<div id="container"> 
    <div id="header"> 
		<div class="navTop">
			<ul>
				<li>
				    <a class="kettlerusa" href="${kettler.resource(contextPath:'/contract/contract',absolute:true, mode:'contract')}">Kettler Contract</a> 
				</li>
	            <li><a id="aluminum"    href="${kettler.resource(contextPath:'/patio-furniture/aluminum',absolute:true)}"  title="${WebCategory.findByName('aluminum')?.title}">Aluminum</a></li> 
	            <li><a id="poly"         href="${kettler.resource(contextPath:'/patio-furniture/poly',absolute:true)}"  title="${WebCategory.findByName('poly')?.keywords}">Poly</a></li>
	            <li><a id="resin"       href="${kettler.resource(contextPath:'/patio-furniture/resin',absolute:true)}"  title="${WebCategory.findByName('resin')?.title}">Resin</a></li> 
	            <li><a id="wroughtiron" href="${kettler.resource(contextPath:'/patio-furniture/wrought-iron',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('wrought iron')?.title}">Wrought Iron</a></li>
	            <li><a id="otherkettler" href="#" onclick="window.open('http://www.kettlerusa.com');" title="Bikes, fitness, table tennis, and toys" class="lastMenuItem">
	                Other Products
		            </a>
		        </li>
			</ul>
		</div> 
        <div class="navSecond">
        	<ul>
	           <li>
	            <a id="requestForQuote" href="${createLink(controller:'contract',action:'requestForQuote')}" >RFQ</a>
	            <a id="contacts" href="${createLink(controller:'contract',action:'contacts')}" >CONTACTS</a>
	            <a id="dealers" href="${createLink(controller:'contract',action:'dealers')}" >DEALERS</a>
	            <a id="installPics" href="${createLink(controller:'contract',action:'installPics')}" class="lastMenuItem">INSTALL PICS</a>
	           </li>
	        </ul>
			<div class="clearBoth"></div>
	    </div>
    </div> 
 
    <g:render template="/layouts/collections" model="${[currentDivision:'patio']}"/>

    <div id="main"> 
        <g:layoutBody />
    </div>
    <div id="footer"> 
        &copy; 2013 Kettler USA &middot;
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
    if (divPagePos != null && $('#gallery').length == 0) {
        <% if (!specificCollection && params.max?.toInteger() < 5) { %>
            var paginationTop = $('div.page').position().top;
            $('#filters').css('top', (paginationTop)+'px');
        <% } %> 
        $('#sidebar').height($('#content').height());
    } else {
        
		$('#sidebar').hide();
        $('#content').css('width', '100%');
    }
</g:javascript> 
