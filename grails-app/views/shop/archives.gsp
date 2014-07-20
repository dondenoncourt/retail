<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="grails.util.Environment" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${params?.category} - Kettler ${params?.division} online retailer for ${keywords} &amp; more </title>
	<% if (request.serverName ==~ /www.kettlerstore.com/) {  %>
    	<meta name="layout" content="store" />
	<% } else if (request.serverName ==~ /www.kettlercontract.com/) {  %>
    	<meta name="layout" content="contract" />
	<% } else { %>
    	<meta name="layout" content="${params.division.replaceAll(' ','')}" />
	<% } %>
    <meta name="description" content="Online shopping for ${keywords}"/> 
    <meta name="keywords" content="KETTLER, kettlerusa.com, ${keywords} "/>     
    <g:javascript library="jquery" plugin="jquery"/>
	<g:javascript src="jquery-ui.min.js"/> 
	<g:javascript src="jquery.cycle.all.min.js"/>
</head>

<body>
<div id="content">
    <div class="page"> 
        <g:paginate action="archives" total="${totalItems}" params="${params}"  prev="&lt;&lt;" next="&gt;&gt;"/>
        <% if (totalItems > params.max) { %>
	        <g:form name="increasePageSizeForm" action="archives" absolute="true" 
	   			base="http://${request.serverName}${(Environment.current == Environment.DEVELOPMENT)?':'+request.serverPort+'/retail':''}" 
	        >
	            <g:hiddenField name="division" value="${params.division}"/>
                <g:hiddenField name="rowSize" value="${params.rowSize}"/>
                <g:hiddenField name="mode" value="${params.mode}"/>
		        <g:hiddenField name="max" value="${totalItems}"/>                   
		        <g:hiddenField name="order" value="${params.order}"/>                   
		        <input type="submit" value="Show All"/>
	        </g:form>
	    <% } %> 
    </div> 
    <g:render template="products" model="[items:items, cart:cart]"/>
    <div style="clear:left; margin-bottom:10px;">
		<a href="${kettler.resource(contextPath:'/',absolute:true, mode: params.mode)}shop/products?division=${params.division}" class="clickme">Show Current Items</a>
    </div>
</div>
<div id="floatingCart"></div>

<g:javascript>
    $('#floatingCart').draggable();
	 $('#sortPrice').change(function() {
         $('#priceSortForm').submit();
     });
     <% if (params.max > params.rowSize ) { %>
     	$(document).ready(function () {
	        $('#content div.page').css('border-top','none');
	        // multiply products div's height based on the number of rows to display
	        // to provide for a partial row (based on modulo division's remainder) add 1 to the calculated rows 
	        //    via a ternary
	        $('#content').height($('div .products').height()*${(totalItems/params.rowSize+(totalItems%params.rowSize?1:0))});
	     });
     
     <% } else { %>
        $('#content').height(370);
     <% } %>
     $('div.page').css('border-top', 'none');
     $('#filters').css('border-top', 'none');
</g:javascript>
</body>		
