<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="layout" content="kettlerusa" />
	<title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title> 
</head> 
<body> 
<div id="content"> 
	<g:if test="${flash.message}">
    	<div class="errors">${flash.message}</div>
	</g:if>

    <div class="top">
        <div id="pdfSearch"> 
	        <g:form action="pdfSearchByItemNo" name="pdfSearchByItemNo">
	            Search by Item No: 
	            <input type="text"name="itemNo"/>
	            <g:submitButton name="update" value="Go" />
	        </g:form>
            <div id="pdfSearchResponse"></div>
        </div>
        <p>Click product category image to list manuals</p>
        <g:each in="${divisions}" var="division">
           <div class="lifeStyleLinks manualsLinks divManuals${(division.name.capitalizeNames())}">
               <a href="${createLink(controller:'homePage', action:'manuals')}?division=${division.name}&mode=${params.mode}" title="Display ${division} manuals"> 
                   <h2>${(division.name.capitalizeNames())}</h2>
               </a>
               <a href="${createLink(controller:'homePage', action:'manuals')}?division=${division.name}&mode=${params.mode}" title="Display ${division} manuals"> 
                    <img src="${createLinkTo(dir:'images/'+division.name?.replaceAll(/^\/\w*/,''))}/life-style.jpg" width="100%"/>
               </a>
           </div>
        </g:each>

        <% if (categoryPdfsMap) { %>  
            <g:each in="${categoryPdfsMap}" var="category" status="i">
               <% if (category.value.size()) { %>
		           <div class="catPdfList" style="${((i==0)?'clear:left':'')}">
	                     <h1>${category.key}</h1>
				            <g:each in="${category.value}" var="pdf">
				                 <% if (pdf instanceof Map) { %>
                                     <a href="#" onclick="window.open('${createLinkTo(dir:'manuals/'+division+'/'+category.key, file:pdf.pdf)}')">
                                     ${pdf.itemNo}</a> 
                                     ${(ItemMasterExt.findByItemNo(pdf.itemNo)?.desc)}
                                     ${(pdf.pdf.matches(/.*\.A.pdf/)?'Computer Manual':'')}
				                 <% } else { %>
					                 <a href="#" onclick="window.open('${createLinkTo(dir:'manuals/'+division+'/'+category.key, file:pdf)}')">
		    			             ${(pdf.toUpperCase()?.replaceAll(/\.PDF/,''))}</a> 
		    			             ${(ItemMasterExt.findByItemNo(pdf.toUpperCase().replaceAll(/\.PDF/,''))?.desc)}
		    			             ${(pdf.matches(/.*\.A.pdf/)?'Computer Manual':'')}
	    			             <% } %> 
	    			             <br/>
				            </g:each>
	    	        </div>
    	        <% } %>
            </g:each>
	   <% } %>
    </div>
</div> 
<div id="unableToFind">
    <p>If you are unable to find your product manual please contact KETTLER Parts:</p>
    <dl>
        <dt>Phone:</dt><dd>866.804.0440 Monday thru Friday 9:00 a.m. to 4:30 p.m. EST</dd>
        <dt>Fax:</dt><dd>757.563.9273</dd>
        <dt>email:</dt><dd>parts@kettlerusa.com</dd>
    </dl>
 </div>
 <br/>
<g:javascript>
    if ($.browser.msie) {
        <%--http://www.filamentgroup.com/lab/achieving_rounded_corners_in_internet_explorer_for_jquery_ui_with_dd_roundi/ --%>
        DD_roundies.addRule('.lifeStyleLinks', '10px');
    }
</g:javascript> 
<g:javascript>
    <%--
	var colors = [ "#FF0000", "#00CC00", "#990099", "#3399FF", "#FF9900", "#FF9900"];
	$('.lifeStyleLinks').each(function(index) {
       $('.lifeStyleLinks:nth('+index+')').css('background-color', colors[index]);
    });
    --%>
    $('#otherkettler').addClass('active');
    $('#manuals').addClass('active');
    $('#pdfSearchByItemNo').submit(function () {
        $.ajax({
           url: "${createLink(action:'pdfSearchByItemNo')}",
           data: $('#pdfSearchByItemNo').serialize(),
           type: 'POST',
           success:function(data) {$('#pdfSearchResponse').html(data);},
           error: function(xhr, textStatus, errorThrown) { alert(textStatus); }
        });
        return false;
    });
    if ($.browser.msie) {
        $('#content img').css('padding', '0');
    }
    <% if (categoryPdfsMap) { %>
        var bigTop = 0;
        // get the biggest top
        $('.catPdfList').each(function(index) {
	        if ($(this).position().top > bigTop) {
	        	bigTop = $(this).position().top;
	        }
	    });
	    var newTopHeight = 0;
	    // for each with the same top, get the highest
	    var bigHeight = 0;
        $('.catPdfList').each(function(index) {
        	if ($(this).position().top == bigTop) {
        		if ($(this).height() > bigHeight) {
        			 bigHeight = $(this).height();
        		}
        	}
        });
        var pdfHeight = bigTop + bigHeight;
        
        var pdfSearchHeight = $('#pdfSearch').height();
        var paraHeight = $('div.top p').height();
        var lifeStyleHeight = $('.lifeStyleLinks').first().height();
        var preambleHeight = pdfSearchHeight+paraHeight+lifeStyleHeight;
        
        $('#content .top').height(preambleHeight+pdfHeight);
        
    <% } %>
</g:javascript>
</body> 
</html>
