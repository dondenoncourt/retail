<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${params?.division} closeouts and specials: online retailer for ${keywords} &amp; more </title>
    <meta name="layout" content="${params.mode?:(params.division.replaceAll(' ',''))}" />
    <meta name="description" content="Online shopping for ${keywords}"/> 
    <meta name="keywords" content="${keywords} "/>     
    <g:javascript library="jquery" plugin="jquery"/>
	<g:javascript src="jquery-ui.min.js"/> 
	<g:javascript src="jquery.cycle.all.min.js"/>
</head>

<body>
<div id="content">
    <div class="page closeouts"> 
		<g:paginate action="closeouts" total="${totalItems}" params="${params}" prev="&lt;&lt;" next="&gt;&gt;"/>
        <% if (totalItems > params.max) { %>
	        <g:form name="increasePageSizeForm" action="closeouts">
	            <g:hiddenField name="division" value="${params.division}"/>
                <g:hiddenField name="rowSize" value="${params.rowSize}"/>
                <g:hiddenField name="mode" value="${params.mode}"/>               
	  	       <g:hiddenField name="max" value="${totalItems}"/>                   
		        <input type="submit" value="Show All"/>
	        </g:form>
	    <% } %>
    </div> 
    <g:render template="products" model="[items:items, cart:cart]"/>
</div>
<div id="floatingCart"></div>

<g:javascript>
     $('#floatingCart').draggable();
	 $('#sortPrice').change(function() {
         $('#priceSortForm').submit();
     });
     <% if (params.max > params.rowSize ) { %>
        $('#content div.page').css('border-top','none');
        // multiply products div's height based on the number of rows to display
        // to provide for a partial row (based on modulo division's remainder) add 1 to the calculated rows 
        //    via a ternary
       
     <% } %>
     <% if (params?.division == 'patio') { %>
        $('#sidebar').hide();
        $('#content').width('100%');
     <% } else if (params?.division == 'bikes') {%>
        $('#bikeFilterSidebar').hide();
		var itemsSize = ${(items?.size()?:0)};
		var rowHeight = $('.productImage').height()+50;
		if (itemsSize > 4) {  
		    if ((itemsSize / 4) >= 1) {
		        rowHeight *= (Math.round(itemsSize / 4 + .49) );
		    }
		} else if (!itemsSize) {
		   rowHeight = 80;
		} 
		
     <% } %>
    $('#closeouts').addClass('active');
     
</g:javascript>
</body>		