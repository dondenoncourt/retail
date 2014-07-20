<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<div id="youtube">
	<object width="271" height="220">
	    <param name="movie" value="${youTubeAssembly?item.youTubeAssembly:item.youTube}"></param>
	    <param name="allowFullScreen" value="true"></param>
	    <param name="allowscriptaccess" value="always"></param>
	    <embed src="${youTubeAssembly?item.youTubeAssembly:item.youTube}" 
	           type="application/x-shockwave-flash" 
	           allowscriptaccess="always" 
	           allowfullscreen="true" 
	           width="325" height="244">
	     </embed>
	</object>
    <g:remoteLink class="youtubeEnd" action="itemMainImage" id="${item.id}" update="mainImageImg">End Video</g:remoteLink>
</div>
<g:javascript>
     $('.youtubeEnd').click(function() {
        $('#youtube').hide();
     });
</g:javascript>
    