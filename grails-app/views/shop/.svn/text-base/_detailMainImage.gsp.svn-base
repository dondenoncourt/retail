<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>

<img id="dynaMainImage" width="200" src="${resource(dir:"images/")}${item.division.name}/${item.category.name}/${itemWithOtherColor.itemNo}.jpg" alt="${item.imageAlt?:item.desc}" title="${item.imageTitle?:item.desc}" />
<div>
	<% if (item.youTube) { %>	
	<div class="divMainImageLinksLarge">	
	<% } else { %>
	<div class="divMainImageLinks">	
	<% } %>
		
			<% if (item.youTube) { %>
				<g:remoteLink class="youtube" action="youtube" id="${item.id}" update="mainImageImg">
					<div class="divYouTube">
						Play Video
					</div>
				</g:remoteLink>
			<% } %>
		
		<a href="#" id="largerImageLink">
			<div id="divLargerImageLink">
				Larger Image
			</div>
		</a>
		<div class="clearBoth"></div>
	</div>
</div>

<%--<% if (item.youTube) { %>--%>
<%--<g:javascript>--%>
<%--    $('#largerImageLink').css('float','right');--%>
<%--    $('#largerImageLink').css('margin-right','90px');--%>
<%--    $('#largerImageLink').css('margin-left','0');--%>
<%--    setLargerImageLink(); --%>
<%--</g:javascript>--%>
<%--<% } %>--%>
