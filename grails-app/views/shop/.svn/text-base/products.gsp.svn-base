<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
    <meta name="description" content="${division?.descr}"/> 
    <meta name="keywords" content="${division?.keywords} "/>     
    <title>${division?.title}</title> 
    <link href="${createLinkTo(dir:'css',file:'jquery-ui.css')}" rel="stylesheet" type="text/css" />
    <g:javascript src="jquery-ui.min.js"/>
    <%-- DO NOT include this <g:javascript src="jquery/jquery-1.4.2.js"/> --%>
	<g:javascript src="jquery.cycle.all.min.js"/>
</head> 
<body> 
	<div id="content">
		
		<div class="divCategoryTop">
			<% if (params.division == 'patio' && !noShowFilter && params.NoShowFilter != 'true') { %>
				 <g:render template="newCollections" />		
			<% } %>
			
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
				
					<% if ((params?.division == 'patio' && params.max < 4) || (params?.division != 'patio' && params.max < 5 ) ) { %> 
						<div class="divisionLifeStyles">
							<div id="divisionImages"> 
								
								<% if (collections) { %>
									<span class="slideshowCollection" >
								<% } else {%>
									<span class="slideshow" >
								<% }%>
										<g:each in="${lifeStyleImages}" var="lifeStyle">
											<img src="${createLinkTo(dir:'images/'+params?.division, file:lifeStyle)}"
											   style="display: block;"
											 />
										</g:each>
									</span>
							</div>
							<div id="divisionDesc">
								<g:render template="/verbiage/${params?.division}/verbiage" />
							</div>
							<div class=clearBoth></div>
						</div> 
					<% } else { %>  
						<%if (params.max < 4) {%>
								
						<% } else {%>
								<% if (collections) { %>
								hello
								<% }%>
						<% }%>
					<% }%>
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
				<% if (params.division == 'bikes') { %>
					<div class="divBikeProductArea">
				<% } %>
				<div class="page"> 
					<g:render template="paginate" model="[action:'products',totalItems:totalItems,params:params]"/> 
				</div> 
				<g:render template="products" model="[items:items, cart:cart]"/>
			</div>
			<div class="clearBoth"></div>
		</div>
        
	</div>
    <div id="floatingCart"></div>
    <g:javascript>
        <% if (params.division == 'toys') { %>
            var colors =  [ "#FF0000", "#00CC00", "#990099", "#3399FF", "navy", "#FF9900"];
            $('.lifeStyleLinks').each(function(index) {
               $('.lifeStyleLinks:nth('+index+')').css('background-color', colors[index]);
                if ($('.lifeStyleLinks:nth('+index+') h2').html() == 'Balance Bikes And Scooters') {
                    $('.lifeStyleLinks:nth('+index+') h2').css('margin', '6px');
                    $('.lifeStyleLinks:nth('+index+') h2').css('height', '2em');                }
            });
        <% } %>
        <% if (params.division == 'bikes') { %>
            $('.lifeStyleLinks').each(function(index) {
               $('.lifeStyleLinks:nth('+index+')').css('background-color', '#8FBC8F');
            });
        <% } %>
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
          $(document).ready(function () {
            <%-- $('div .products').height($('#footer').offset().top - $('div.products').offset().top);--%>
			<%-- $('div .products').height($('#footer').offset().top);--%>
          });
     <% } %>
    <% if (params?.division == 'patio' && !specificCollection) { %>
        $("#accessoriesId").click(function () {window.location = getFilterWinLoc();});
        $("#cushions")     .click(function () {window.location = getFilterWinLoc();});
	    $("#chairs")       .click(function () {window.location = getFilterWinLoc();});
	    $("#tables")       .click(function () {window.location = getFilterWinLoc();});
	    <% } %>
	    <% if (params?.division == 'bikes') { %>
	        <g:render template="bikesBackgroundUtil" bean="${items}"/>
	    <% } %>
	    function getFilterWinLoc() {
	        var loc = '${kettler.resource(contextPath:'/shop',absolute:true)}/products?division=${params.division}&max=${params.max}';
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
	        return loc;
	    }
	      
     	$('.slideshow').cycle({fx: 'fade', speed: 500, timeout:  3000 });

        $('#floatingCart').draggable();
	    
    </g:javascript> 
</body> 
</html>
        
