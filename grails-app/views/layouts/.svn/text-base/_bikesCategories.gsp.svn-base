<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<ul>
	<%-- scrunch ids with no hyphens, blanks, or ampersands. --%>
	<% if ('www.kettlerlatinoamerica.com' == request.serverName) { %>
	 	<li><a id="childcarriers" class="childcarriers"     href="${kettler.resource(contextPath:'/bikes/child-carriers',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('child carriers')?.title}"><g:message code="menu.child.carriers"/> </a></li>
		<li><a id="kids" class="kids"     href="${kettler.resource(contextPath:'/bikes/kids',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('kids')?.title}" ><g:message code="menu.kids"/></a></li>
       <li><a id="adults" class="adults"     href="${kettler.resource(contextPath:'/bikes/adults',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('adults')?.title}"><g:message code="menu.adults"/></a></li>
    <% } else { %>
        <li><a id="city" class="city"     href="${kettler.resource(contextPath:'/bikes/city',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('city')?.title}">City</a></li>
        <li><a id="touring" class="touring"     href="${kettler.resource(contextPath:'/bikes/touring',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('touring')?.title}">Touring</a></li>
        <li><a id="e-bikes" class="e-bikes" href="${kettler.resource(contextPath:'/bikes/electric-bikes',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('e-bikes')?.title}">e-bikes </a> </li>
		<li><a id="kids" class="kids"     href="${kettler.resource(contextPath:'/bikes/kids',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('kids')?.title}" ><g:message code="menu.kids"/></a></li>
	 	<li><a id="childcarriers" class="childcarriers"     href="${kettler.resource(contextPath:'/bikes/child-carriers',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('child carriers')?.title}"><g:message code="menu.child.carriers"/> </a></li>
    <% } %>
    <li><a id="accessories" class="accessories"     href="${kettler.resource(contextPath:'/bikes/accessories',absolute:true, mode: params.mode)}" title="${WebCategory.findByName('accessories')?.title}"><g:message code="menu.accessories"/></a></li>
	<% if (!hoverMenu && !['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
	   <li><a id="giftcard" class="giftcard"     href="${kettler.resource(contextPath:'/bikes/giftCard',absolute:true, mode:mode)}" title="Gift Cards">gift card</a></li>
	   <% if (ItemMasterExt.division('bikes', 0, 'desc', null, true).count()) { %>
	       <li><a id="closeouts" class="closeouts"  class="closeouts lastMenuItem" href="${kettler.resource(contextPath:'/bikes/closeouts',absolute:true, mode: params.mode)}"  title="close out items" >specials</a></li>
	   <% } %>
	<% } %>
</ul>
