<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<ul>
    <%-- scrunch ids with no hyphens, blanks, or ampersands. --%>
	<li><a id="crosstrainers" class="crosstrainers"     href="${kettler.resource(contextPath:'/fitness/crosstrainers',absolute:true, mode:params.mode)}" title="${WebCategory.findByName('crosstrainers')?.title}"><g:message code="menu.crosstrainers"/></a></li>
	<li><a id="exercisebikes" class="exercisebikes"     href="${kettler.resource(contextPath:'/fitness/exercise-bikes',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('exercise bikes')?.title}"><g:message code="menu.exercise.bikes"/></a></li>
    <li><a id="rowers" class="rowers"     href="${kettler.resource(contextPath:'/fitness/rowers',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('rowers')?.title}"><g:message code="menu.rowers"/></a></li>
	<% if (!['www.kettlerlatinoamerica.com'].find{ it == request.serverName}) { %>
        <li><a id="treadmills" class="treadmills"     href="${kettler.resource(contextPath:'/fitness/treadmills',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('treadmills')?.title}">treadmills</a></li>
    <% } %>
	<li><a id="more" class="more"     href="${kettler.resource(contextPath:'/fitness/more',absolute:true, mode:params.mode)}" title="${WebCategory.findByName('accessories')?.title}"><g:message code="menu.more"/></a></li>
	<% if (!hoverMenu && !['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName}) { %>
     <li><a id="giftcard" class="giftcard"     href="${kettler.resource(contextPath:'/fitness/giftCard',absolute:true, mode:mode)}" title="Gift Cards">gift card</a></li>
        <% if (params.mode != 'canada' && ItemMasterExt.division('fitness', 0, 'desc', null, true).count()) { %>
            <li><a id="closeouts" class="closeouts"  class="closeouts"    href="${kettler.resource(contextPath:'/fitness/closeouts',absolute:true, mode:params.mode)}"  title="close out items">specials</a></li>
        <% } %>
    <% } %>

</ul>
