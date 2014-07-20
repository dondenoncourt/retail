<%@ page import="grails.util.Environment" %>
<% if (params.division == 'bikes' && request.serverName != 'www.kettlercanada.com') { %>
    <span id="bikeSpacerForPagination"></span>
<% } %>
<%-- the odd paginate controller is a hack to thwart issues with the SEO-URLs in UrlMappings --%>
<g:paginate action="${action}" controller="paginate" total="${totalItems}" params="${params}" prev="&lt;&lt;" next="&gt;&gt;"/>
<% if (totalItems > params.max) { %>
   <g:form name="increasePageSizeForm" class="increasePageSizeForm" absolute="true" 
   			base="http://${request.serverName}${(Environment.current == Environment.DEVELOPMENT)?':'+request.serverPort+'/retail':''}" 
   			controller="paginate" action="${action}">
       <g:hiddenField name="division" value="${params.division}"/>
       <g:hiddenField name="category" value="${params.category}"/>
       <g:hiddenField name="rowSize" value="${params.rowSize}"/>
       <g:hiddenField name="tablesFilter" value="${params.tablesFilter}"/>
       <g:hiddenField name="chairsFilter" value="${params.chairsFilter}"/>
       <g:hiddenField name="cushionsFilter" value="${params.cushionsFilter}"/>
       <g:hiddenField name="accessoriesFilter" value="${params.accessoriesFilter}"/>
       <input type="hidden" name="bikeFilter" value="${params.bikeFilter}"/> 
       <g:hiddenField name="collection" value="${params.collection}"/>
       <g:hiddenField name="mode" value="${params.mode}"/>                   
       <g:hiddenField name="max" value="${totalItems}"/>                   
       <g:hiddenField name="order" value="${params.order}"/>                   
       <input type="submit" value="Show All"/>
   </g:form>
<% } %>
<% if (['localhost', 'www.kettlerusa.com'].find{it == request.serverName}) { %>
	<g:form name="priceSortForm" class="priceSortForm" absolute="true" 
				base="http://${request.serverName}${(Environment.current == Environment.DEVELOPMENT)?':'+request.serverPort+'/retail':''}" 
				controller="paginate" action="${action}">
	    <g:hiddenField name="division" value="${params.division}"/>
	    <g:hiddenField name="category" value="${params.category}"/>
	    <g:hiddenField name="rowSize" value="${params.rowSize}"/>
	    <g:hiddenField name="tablesFilter" value="${params.tablesFilter}"/>
	    <g:hiddenField name="chairsFilter" value="${params.chairsFilter}"/>
	    <g:hiddenField name="cushionsFilter" value="${params.cushionsFilter}"/>
	    <g:hiddenField name="accessoriesFilter" value="${params.accessoriesFilter}"/>
	    <input type="hidden" name="bikeFilter" value="${params.bikeFilter}"/> 
	    <g:hiddenField name="collection" value="${params.collection}"/>
	    <g:hiddenField name="mode" value="${params.mode}"/>                   
        <g:hiddenField name="max" value="${params.max}"/>                   
	    <select name="order" id="sortPrice">
	        <option value="desc" ${params.order=='desc'?'selected':''}>Price:High to Low</option>
	        <option value="asc" ${params.order=='asc'?'selected':''}>Price:Low to High</option>
	    </select>
	</g:form>
<% } %>
