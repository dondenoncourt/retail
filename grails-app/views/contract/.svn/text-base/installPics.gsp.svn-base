<h1>Installation Photo Gallery</h1>
<div id="gallery">
	<g:each in="${installPics}" var="photo">
	   <div class="column">
           <img src="${createLinkTo(dir:'images/contract', file:photo)}" height="200" width="300"/>
 		   <h3>${(photo?.replaceAll(/\d*.jpg/, ''))}</h3>
	   </div>
	</g:each>
    <div class="page"> 
	    <g:paginate action="installPics" total="${count}" prev="&laquo; Prev" next="Next &raquo;"/>
    </div>
</div>
