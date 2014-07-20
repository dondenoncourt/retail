<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <meta name="layout" content="kettlerusa" />
    <g:javascript src="jquery-ui.min.js"/>
    <g:javascript src="jquery/jquery-1.4.2.js"/>
	<g:javascript src="jquery.cycle.all.min.js"/>
</head>
<body> 
<div id="content"> 
    <div class="top"> 
       <g:if test="${flash.message}">
         <div class="message">${flash.message}</div>
       </g:if>
       <g:if test="${divisions}">
	        <div class="divisionLifeStyles">
	            <div id="divisionImages"> 
				    <span class="slideshow" >
				        <g:each in="${divisions}" var="division">
	            	        <img src="${createLinkTo(dir:'images/'+division.name?.replaceAll(/^\/\w*/,''))}/life-style.jpg"
                         style="display: block;"
                       />
				        </g:each>
				    </span>
			    </div>
			    <div id="divisionDesc"> 
			    	<g:render template="/verbiage/verbiage" />
			    </div> 
				<div class=clearBoth></div>
	        </div> 
		</g:if>
    </div> 
    <g:javascript>

     	$('.slideshow').cycle({fx: 'fade', speed: 500, timeout:  3000 });

        var colors = [ "#808080", "#808080", "#808080", "#808080", "#808080", "#808080"];
        $('.lifeStyleLinks').each(function(index) {
           $('.lifeStyleLinks:nth('+index+')').css('background-color', colors[index]);
        });

         $('#otherkettler').addClass('active');
    </g:javascript>
</div>

</body>
