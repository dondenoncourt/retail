<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<div id="sidebar">
    <% if (collections) { %>
        <h1>Collections</h1> 
        <ul class="menu"> 
            <g:each in="${collections}" var="category">
                <g:each in="${category}" var="cols">
                    <g:each in="${cols.value}" var="collection">
                       <li><a href="${kettler.resource(contextPath:'/shop/collection',absolute:true)}?collection=${collection?.trim()}&category=${category.key?.trim()}&division=${currentDivision?.trim()}">${collection}</a></li>
                    </g:each> 
                </g:each> 
            </g:each>
        </ul> 
    <% } %>
    <% if (!specificCollection && !item && !search && !dealers && !locator && !onlineDealers) { %>
        <div id="filters">
            <h1>Filter</h1> 
            <ul> 
                <li><label for="tables"><g:checkBox name="tables" name="tables"  value="${params.tablesFilter}"/> Tables</label></li> 
                <li><label for="chairs"><g:checkBox name="chairs" name="chairs"  value="${params.chairsFilter}"/> Chairs</label></li>  
                <% if (!params.category || ItemMasterExt.patioCustionsInCategory(params.category).count()) { %>
                    <li><label for="cushions"><g:checkBox name="cushions" name="cushions"  value="${params.cushionsFilter}"/> Cushions</label></li>
                <% } %>  
                <li><label for="accessories"><g:checkBox name="accessories" id="accessoriesId" value="${params.accessoriesFilter}" /> Accessories</label></li> 
            </ul>
        </div>
    <% } %>
</div>
