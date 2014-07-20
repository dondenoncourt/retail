<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="grails.util.Environment" %>

  
<div id="main"> 
	<%--<% if (currentDivision == 'patio' && !noShowFilter && params.NoShowFilter != 'true') { %>--%>
        <%--<g:render template="/layouts/collections"/> --%>
	<%--<% } %> --%>
    <g:layoutBody />
	
    <!-- fyi 
    		home page : division = "kettler usa" 
    -->
	<ul id="footer"> 
		<% if (['www.kettlerusa.com'].find{ it == request.serverName}) { %>
			<div class="divFooterLinks">
				<div class="divFooterLinksLeft">
					<div class="divOrderInfo">
						<div class="divFooterLinksHeadline">Parts & Warranty</div>
							<div class="divFooterLinksEntry"><a href="/retail/manuals" id="manuals">Manuals</a></div>
							<div class="divFooterLinksEntry"><a href="/retail/parts">Parts and Service</a></div>
							<div class="divFooterLinksEntry">
								<a href="${resource(contextPath:'/warranty',absolute:true)}" title="Register Warranty" 
								class="${(request.serverName ==~ /www.kettlerlatinoamerica.com/)?'lastMenuItem':''}"
								>Register Warranty</a>
							</div>
					</div>
					
					<div class="divInfoService">
						<div class="divFooterLinksHeadline">INFO</div>
							<div class="divFooterLinksEntry"><a title="Gift Cards" href="http://www.kettlerusa.com/retail/bikes/giftCard" class="giftcard active" id="footerGiftCard">gift card</a></div>
							<div class="divFooterLinksEntry">
								<a id="whereToBuy" class="lastMenuItem" title="Click to see where to buy"
									<% if (currentDivision == 'fitness') { %>
										href="${createLink(controller:'dealer',action:'webDealers', params:[division:'fitness',itemId:itemId,mode:params.mode])}"
									<% } else if (division) { %>
										href="${createLink(controller:'locate',action:'index', params:[division:currentDivision,itemId:itemId,mode:params.mode])}"
									<% } else { %>
										href="${createLink(controller:'homePage',action:'pickWhereToBuyDiv')}"
									<% } %>
									>
									<g:message code="menu.where.to.buy"/>
								</a>
							</div>
							<div class="divFooterLinksEntry"><a href="https://www.kettlerusa.com/kettler/" title="click to go to the KETTLER Business-to-Business application">Retailer &amp; Rep Login</a></div>
							<div class="divFooterLinksEntry"><a href="/retail/homePage/catalogs" id="catalogs">Catalogs</a></div>
							<div class="divFooterLinksEntry"><g:link controller="homePage" action="termsAndConditions" params="[division:currentDivision]">Terms &amp; Conditions</g:link></div>
							<div class="divFooterLinksEntry"><g:link controller="shop" action="archives" params="[division:currentDivision]">Archive Items</g:link></div>
					</div>
					
				</div>
				<div class="divFooterLinksRight">
					<div class="divCompany">
						<div class="divFooterLinksHeadline">Company</div>
						<div class="divFooterLinksEntry"><a href="${kettler.resource(contextPath:'/contact-us')}" >Contact Us</a></div>
						<div class="divFooterLinksEntry"><a href="/retail/about-us" id="aboutUs2">About Us</a></div>
						<div class="divFooterLinksEntry"><a href="/retail/awards" id="awards">Awards</a></div>
						<div class="divFooterLinksEntry"><a id="infoEdu" href="#" onclick="window.open('http://www.kettlerusa.com/blog')">Blog</a></div>
						<%--<div class="divFooterLinksEntry"><a href="http://www.kettlerusa.com/testimonials">testimonials</a></div>--%>
						<div class="divFooterLinksEntry"><a href="http://www.kettlercontract.com" title="Go to Kettler Contract">Hospitality</a></div>
						<div class="divFooterLinksEntry"><a href="http://www.kettlercanada.com" title="Go to Kettler Canada">Kettler Canada</a></div>
						<div class="divFooterLinksEntry"><a href="http://www.kettlerlatinoamerica.com" title="Go to Kettler Latino America">Latino America</a></div>
					</div>
				</div>
				<div class="clearBoth"></div>
			</div>
			<div class="divSeals">
				<div class="imageSeals"><img alt="free shipping" src="/retail/images/objects/free-shipping-75.png"></div>
				<div class="imageSeals"><img id="imgPaypal" height=55 alt="paypal" src="/retail/images/objects/PayPal-75.png"></div>
				<div class="imageSeals"><img alt="credit card" src="/retail/images/objects/credit-75.png"></div>
				
				
				
				<div class="clearBoth"></div>
			</div>
			<div class="clearBoth"></div>
		
	        <% if (currentDivision == 'kettler usa') { %>
			<%--
	            <li><g:link controller="homePage" action="termsAndConditions" params="[division:currentDivision]">Terms &amp; Conditions</g:link></li>
	            <li><a href="https://www.kettlerusa.com/kettler/" title="click to go to the KETTLER Business-to-Business application">Retailer &amp; Rep Login</a></li>
	            <li>
	                <a href="${resource(contextPath:'/warranty',absolute:true)}" title="Register Warranty" 
	                    class="${(request.serverName ==~ /www.kettlerlatinoamerica.com/)?'lastMenuItem':''}"
	                >Register Warranty</a>
	            </li>
	   			<li><a href="http://www.kettlercontract.com" title="Go to Kettler Contract">Hospitality</a></li>
		   		<li><a href="http://www.kettlercanada.com" title="Go to Kettler Canada">Kettler Canada</a></li>
		   		<li ><a href="http://www.kettlerlatinoamerica.com" title="Go to Kettler Latino America">Latino America</a></li>
		   		<li class="lastItem"><a id="infoEdu" href="#" onclick="window.open('http://www.kettlerusa.com/blog')">Blog</a></li>
				--%>
			<% } else {%>
				<%--
				<li><g:link controller="homePage" action="termsAndConditions" params="[division:currentDivision]">Terms &amp; Conditions</g:link></li>
	            <li><g:link controller="shop" action="archives" params="[division:currentDivision]">Archive Items</g:link></li>            <li>
	                <a href="${resource(contextPath:'/warranty',absolute:true)}" title="Register Warranty" 
	                    class="${(request.serverName ==~ /www.kettlerlatinoamerica.com/)?'lastMenuItem':''}"
	                >Register Warranty</a>
	            </li>
		        <li ><a href="${kettler.resource(contextPath:'/contact-us')}" >Contact Us</a></li>
                <li class="lastItem">
                    <a id="whereToBuy" class="lastMenuItem" title="Click to see where to buy"
	            		<% if (currentDivision == 'fitness') { %>
		            		href="${createLink(controller:'dealer',action:'webDealers', params:[division:'fitness',itemId:itemId,mode:params.mode])}"
	            		<% } else if (division) { %>
		            		href="${createLink(controller:'locate',action:'index', params:[division:currentDivision,itemId:itemId,mode:params.mode])}"
	            		<% } else { %>
		            		href="${createLink(controller:'homePage',action:'pickWhereToBuyDiv')}"
	            		<% } %>
	            	>
                        <g:message code="menu.where.to.buy"/>
                    </a>
                </li>
	            --%>
	        <% } %>
        <% } %>

        <% if (['www.kettlerlatinoamerica.com'].find{ it == request.serverName}) { %>
            <li><a href="https://www.kettlerusa.com/kettler/" title="click to go to the KETTLER Business-to-Business application">Retailer &amp; Rep Login</a></li>
        	<li class="lastItem"><a href="http://www.kettlerusa.com" title="Go to Kettler USA">Kettler USA</a> </li>
        <% } %>

        <% if (['www.kettlercanada.com'].find{ it == request.serverName}) { %>
            <li><a href="https://www.kettlerusa.com/kettler/" title="click to go to the KETTLER Business-to-Business application">Retailer &amp; Rep Login</a></li>
			<li><a id="infoEdu" href="#" onclick="window.open('http://www.kettlerusa.com/blog')">Blog</a></li>
        	<li><a href="http://www.kettlerusa.com" title="Go to Kettler USA">Kettler USA</a> </li>
	        <li class="lastItem"><a href="${kettler.resource(contextPath:'/contact-us')}" >Contact Us</a></li>
        <% } %>
 
		<li id="copyright" class="lastItem"> 
			<g:set var="today" value="${new Date()}"/>
			
		    &copy; 1999-${today[Calendar.YEAR]} Kettler
	    </li>
		<div class="clearBoth">	</div>
	</ul>
</div> 

<g:javascript>
    if ($.browser.msie) {
		$('#footer #copyright').css('float', 'none'); 
		$('#footer #copyright').css('margin-left', '250'); 
    }
    <% if (Environment.current == Environment.PRODUCTION) { %>
        $(document).bind("contextmenu", function(e) {
         if ($.browser.msie || $.browser.mozilla  ) {
            return false;
         }  
         if (e.srcElement.nodeName == 'IMG') {
            e.preventDefault();
         }
        });
    <% } %>
    if ($.browser.msie) {
        <%--http://www.filamentgroup.com/lab/achieving_rounded_corners_in_internet_explorer_for_jquery_ui_with_dd_roundi/ --%>
        DD_roundies.addRule('#header .nav a', '10px 10px 0 0');
        DD_roundies.addRule('.lifeStyleLinks', '10px');
        DD_roundies.addRule('.clickme', '5px');
        DD_roundies.addRule('.button', '5px');
    }
    <% if (currentDivision == 'patio' && !specificCollection && params.max?.toInteger() < 5) { %>
        var divPage = $('div.page');
        if (divPage) {
            var divPagePosition = $('div.page').position();
            if (divPagePosition) {
                var paginationTop = $('div.page').position().top;
                $('#filters').css('top', (paginationTop)+'px');
            }
        }
    <% } %> 

    $('.navSecond ul li').last().find('a').addClass('lastMenuItem');
    
    var pageErrorsHeight = $('#pageErrors').height();
    var flashMessageHeight = $('div.message').height();
    if (pageErrorsHeight) {
		$('#content').height($('#content').height()+pageErrorsHeight+22);
	}
    if (flashMessageHeight) {
		$('#content').height($('#content').height()+flashMessageHeight+22);
	}<%--
    function resize() {
        var footer_top = parseInt($('#footer').offset().top);
        var header_height = parseInt($('div#header').height());
         $('#main').height((footer_top - header_height)); 
    }
    $(document).ready(function () {
        setTimeout(function() { resize(); }, 2000); 
    });--%>
</g:javascript>
