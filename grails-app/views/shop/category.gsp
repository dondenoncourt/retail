<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="grails.util.Environment" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${category?.title}</title>
	<% if (request.serverName ==~ /www.kettlerstore.com/) {  %>
    	<meta name="layout" content="store" />
	<% } else if (request.serverName ==~ /www.kettlercontract.com/) {  %>
    	<meta name="layout" content="contract" />
	<% } else { %>
    	<meta name="layout" content="${params.division.replaceAll(' ','')}" />
	<% } %>
	<% if (request.serverName ==~ /www.kettlercanada.com/) {  %>
        <link href="${createLinkTo(dir:'css',file:'canada.css')}" rel="stylesheet" type="text/css" />
	<% } %>
    <meta name="description" content="${category?.descr}"/> 
    <meta name="keywords" content="KETTLER, kettlerusa.com, ${category?.keywords} "/>     
    <g:javascript src="jquery/jquery-1.4.2.js"/>
	<g:javascript src="jquery-ui.min.js"/> 
	<g:javascript src="jquery.cycle.all.min.js"/>
</head>

<body>
<div id="content">
	<div class="divCategoryTop">
		<div class="divCategoryTopCollection">
			<% if (params.division == 'patio' && !noShowFilter && params.NoShowFilter != 'true') { %>
				 <g:render template="newCollections" />		
			<% } %>		
		</div>
		<% if (collections) { %>
			<div class="divContentLifeStyleArea">
		<% } else {%>
			<div class="divContentLifeStyleArea100">
		<% } %>
				<g:if test="${flash.message}">
					<div class="top"> 
					  <div class="message">${flash.message}</div>
					</div> 
				</g:if>

				<% if ((params?.division == 'patio' && params.max < 4) || (params?.division != 'patio' && params.max < 5) ) { %> 
					
					<div class="categoryLifeStyles">
						<div id="catImages"> 
							<% if (collections) { %>
								<span class="slideshow_cat slideshow_catCollection" style="height: auto; width: auto">
							<% } else {%>
								<span class="slideshow_cat" style="height: auto; width: auto">
							<% } %>
									<g:each in="${lifeStyleImages}" var="lifeStyle">
										<% if (specificCollection) { %> 
										   <img src="${createLinkTo(dir:'images/'+params?.division+'/'+params?.category+'/'+specificCollection, file:lifeStyle)}"
											 style="display: block;"
										   />
										<% } else { %>
											<img src="${createLinkTo(dir:'images/'+params?.division+'/'+params?.category, file:lifeStyle)}"
											   style="display: block;"
											 />
										<% } %>
									</g:each>
							</span>
						</div>
						<div id="catDesc">
							<% if (specificCollection) { %> 
								<g:render template="/verbiage/${(params?.division+'/'+params?.category.replaceAll(/ /,'\\ ')+'/'+specificCollection)}/verbiage" /> 
							<% } else { %>
								<g:render template="/verbiage/${params?.division+'/'+params?.category}/verbiage" /> 
							<% } %>
						</div>
						<div class="clearBoth"></div>
					</div> 
				<% } else { %>  
						<%if (params.max < 4) {%>
								
						<% } else {%>
								<% if (collections) { %>
									<div id="catDescHack">
										<% if (specificCollection) { %> 
											<g:render template="/verbiage/${(params?.division+'/'+params?.category.replaceAll(/ /,'\\ ')+'/'+specificCollection)}/verbiage" /> 
										<% } else { %>
											<g:render template="/verbiage/${params?.division+'/'+params?.category}/verbiage" /> 
										<% } %>
									</div>
								<% }%>
						<% }%>
					<% }%>
				<div class="clearBoth"></div>
			</div>
			<div class="clearBoth"></div>
	</div>
	
	<div class="divCategoryBottom">
		<div class="divFiltersPatio">
			<% if (params.division == 'patio' && !noShowFilter && params.NoShowFilter != 'true') { %>
					<g:render template="newFilter" />
			<% } %>
		</div> 
		<div id="divContentProductListArea">
			<div class="page">
				<g:render template="paginate" model="[action:'category',totalItems:totalItems,params:params]"/>
			</div> 
			<g:render template="products" model="[items:items, cart:cart]"/>
		</div>
		<div class="clearBoth"></div>
	</div>
</div>
<div id="floatingCart"></div>

<g:javascript>
    $('#floatingCart').draggable();
    // category link ids are scrunched with no hyphens, blanks, or ampersands.
    $('.${params?.category.replaceAll(/[- \&]/,'')}').addClass('active');
    $('.${params?.category}').addClass('active'); // e-bikes needs the hyphen 
    
    $('.slideshow_cat').cycle({fx: 'fade', speed: 500, timeout:  3000 });
	 $('#sortPrice').change(function() {
         $('#priceSortForm').submit();
     });
     <% if (params.division == 'bikes') { %>
          $('input[value=Special Order]').click(function() {
             $('input[name=bikeFilter]').val('');
             if ($('input[value=Special Order]').attr('checked')) {
                 $('input[name=bikeFilter]').val('Special Order');
             } 
             $('#bikeFilterForm').submit();
          });
          $('input[value=In Stock]').click(function() {
             $('input[name=bikeFilter]').val('');
             if ($('input[value=In Stock]').attr('checked')) {
                 $('input[name=bikeFilter]').val('In Stock');
             } 
             $('#bikeFilterForm').submit();
          });
     <% } %>
     <% if (params.max > params.rowSize ) { %>
        $('#content div.page').css('border-top','none');
        // multiply products div's height based on the number of rows to display
        // to provide for a partial row (based on modulo division's remainder) add 1 to the calculated rows 
        //    via a ternary
        <%--$('div .products').height($('div .products').height()*${(totalItems/params.rowSize+(totalItems%params.rowSize?1:0))})--%>
     <% } %>
	<% if (params?.division == 'patio' && !specificCollection) { %>
        $("#chairs")       .click(function () {window.location = getFilterWinLoc();});
        $("#cushions")     .click(function () {window.location = getFilterWinLoc();});
        $("#tables")       .click(function () {window.location = getFilterWinLoc();});
        $("#accessoriesId").click(function () {window.location = getFilterWinLoc();});
	<% } %>
    <% if (params?.division == 'bikes') { %>
        <g:render template="bikesBackgroundUtil" bean="${items}"/>
    <% } %>

	function getFilterWinLoc() {
        var loc = '${kettler.resource(contextPath:'/shop',absolute:true)}/category?category=${params.category}&division=patio&max=${params.max}';
        if ($('#accessoriesId').is(':checked')) {
            loc += '&accessoriesFilter=true';
        }
        if ($('#chairs').is(':checked')) {
            loc += '&chairsFilter=true';
        }
        if ($('#cushions').is(':checked')) {
            loc += '&cushionsFilter=true';
        }
        if ($('#tables').is(':checked')) {
            loc += '&tablesFilter=true';
        }
        <% if (params.mode) { %> 
            loc += '&mode=${params.mode}';
        <% } %>
        return loc;
	}  
</g:javascript>
</body>		
