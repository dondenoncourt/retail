<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.WebDivision" %>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<ul>
	<li><a id="indoor" class="indoor"        href="${kettler.resource(contextPath:'/table-tennis/indoor',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('indoor')?.keywords}"><g:message code="menu.indoor"/></a></li>
	<li><a id="outdoor" class="outdoor"       href="${kettler.resource(contextPath:'/table-tennis/outdoor',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('outdoor')?.keywords}"><g:message code="menu.outdoor"/></a></li>
	<li><a id="accessories" class="accessories"   href="${kettler.resource(contextPath:'/table-tennis/accessories',absolute:true, mode:params.mode)}"  title="${WebCategory.findByDivisionAndName(WebDivision.findByName('table tennis'), 'accessories')?.keywords}"><g:message code="menu.accessories"/></a></li>
	<% if (!hoverMenu && !['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
         <li><a id="giftcard" class="giftcard"      href="${createLink(controller: 'giftCard',action: 'index',params: params)}" title="Gift Cards">gift card</a></li>
         <% if (ItemMasterExt.division('table tennis', 0, 'desc', null, true).count()) { %>
          <li><a id="closeouts" class="closeouts"  class="closeouts"    href="${kettler.resource(contextPath:'/table-tennis/closeouts',absolute:true, mode:params.mode)}"  title="close out items">specials</a></li>
         <% } %>
    <% } %>
</ul>
