<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${params.category} - Kettler ${params.division}</title>
    <meta name="layout" content="${params.division?.replaceAll(/ /,'')}" />
    <g:javascript src="jquery/jquery-1.4.2.min.js"/> 
    <link href="${createLinkTo(dir:'css',file:'jquery-ui.css')}" rel="stylesheet" type="text/css" /> 
    <g:javascript src="jquery-ui.min.js"/> 
	<style>
		#bikeFilterSidebar {
			display:none !important;
		}
		#bikesProdDiv {
			width:100% !important;
		}
</style>
	
</head>
 
<body>


    <div id="content"> 
		<div class="top"> 
		    <h1>Search Results</h1>
		    for: ${params.where} 
		    <g:form action="search">		    
			   <label for="where">Narrow search by:</label>
			   <input type="text" name="where" id="where" />
			   <input type="submit" name="search" value="Search" />
               <input type="hidden" name="previousWhere" value="${params.where}" />
            </g:form> 
		</div> 
        <g:render template="products" model="[items:items, cart:cart]"/>
        
	</div>
	<div id="floatingCart"></div>
    
<g:javascript>
    $('#floatingCart').draggable();
<%--    $('div .products').height($('div .products').height()*${(items.size()/3+(items.size()%3?1:0))})--%>
    <% if (params.division == 'patio') { %>
        $('#sidebar').css('width', '0%');
        $('#content').css('width', '100%');
    <% } %> 
    <% if (params?.division == 'bikes') { %>
        <g:render template="bikesBackgroundUtil" bean="${items}"/>
    <% } %>
</g:javascript>
</body>     
</html>
