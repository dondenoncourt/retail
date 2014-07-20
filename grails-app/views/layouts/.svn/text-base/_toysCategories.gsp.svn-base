<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<ul id="toysCatMenu" class="toysCatMenu" >
    <%-- scrunched ids with no hyphens, blanks, or ampersands. --%>
    <li><a id="tricycles" class="tricycles"  href="${kettler.resource(contextPath:'/toys/tricycles',absolute:true)}"  title="${WebCategory.findByName('tricycles')?.title}"><g:message code="menu.tricycles"/></a></li>
    <li><a id="balancebikesandscooters" class="balancebikesandscooters"     href="${kettler.resource(contextPath:'/toys/balance-bikes',absolute:true)}"  title="${WebCategory.findByName('balance bikes and scooters')?.title}"><g:message code="menu.scooters.and.balance.bikes"/></a></li>
	<% if (request.serverName != 'www.kettlerlatinoamerica.com') { %>
        <li><a id="outdoorplay" class="outdoorplay"     href="${kettler.resource(contextPath:'/toys/outdoor-play',absolute:true)}" title="${WebCategory.findByName('outdoor play')?.title}"><g:message code="menu.outdoor.play"/></a></li>
    <% } %>
    <li><a id="pedalvehicles" class="pedalvehicles"     href="${kettler.resource(contextPath:'/toys/pedal-vehicles',absolute:true)}"  title="${WebCategory.findByName('pedal vehicles')?.title}"><g:message code="menu.pedal.vehicles"/></a></li>
	<% if (request.serverName != 'www.kettlerlatinoamerica.com') { %>
        <li><a id="swingsets" class="swingsets"     href="${kettler.resource(contextPath:'/toys/swingsets',absolute:true)}" title="${WebCategory.findByName('swingsets')?.title}">swingsets</a></li>
    <% } %>
	<% if (!hoverMenu && !['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
        <li><a id="giftcard" class="giftcard"     href="${kettler.resource(contextPath:'/toys/giftCard',absolute:true)}" title="Gift Cards">gift card</a></li>
        <% if (ItemMasterExt.division('toys', 0, 'desc', null, true).count()) { %>
           <li><a id="closeouts" class="closeouts"  class="closeouts lastMenuItem"    href="${kettler.resource(contextPath:'/toys/closeouts',absolute:true)}"  title="close out items">specials</a></li>
        <% } %>
    <% } %>
</ul>
