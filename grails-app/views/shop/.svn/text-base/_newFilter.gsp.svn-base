	<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
	<% if (!specificCollection && !item && !search && !dealers && !locator && !onlineDealers) { %>
        <div id="filters">
            <div class="filtersHeadline">
				<% if (collections) { %>
					<div class="filtersHeadlineBorder">
				<% } else { %>
					<div class="filtersHeadlineBorderNone">
				<% } %>
						<h1>Filter</h1>
					</div>
			</div>
			<div class="filtersEntryArea">
				<ul> 
					<li><label for="tables"><g:checkBox name="tables" name="tables"  value="${params.tablesFilter}"/> Tables</label></li> 
					<li><label for="chairs"><g:checkBox name="chairs" name="chairs"  value="${params.chairsFilter}"/> Chairs</label></li>  
					<% if (!params.category || ItemMasterExt.patioCustionsInCategory(params.category).count()) { %>
						<li><label for="cushions"><g:checkBox name="cushions" name="cushions"  value="${params.cushionsFilter}"/> Cushions</label></li>
					<% } %>  
					<li><label for="accessories"><g:checkBox name="accessories" id="accessoriesId" value="${params.accessoriesFilter}" /> Accessories</label></li> 
				</ul>
			</div>
        </div>
    <% } %>
