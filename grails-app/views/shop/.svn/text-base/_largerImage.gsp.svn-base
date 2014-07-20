<%@ page import="java.awt.Dimension" %>

<% if (item) { %>
	<img src="${createLinkTo(dir:'images/'+item.division.name+'/'+item.category.name, file:imageName)}" 
	   alt="${item.desc} larger image"  height="97%" width="97%"
	/> 
<% } else { %>
    <img src="${createLinkTo(dir:'images/')}${imageName}" 
       alt="Image not found"  height="97%" width="97%"
    /> 
<% } %>

<g:javascript>
     <% float ratio = 1
	     if (dim) {
	    	  ratio = dim.width / dim.height
	     }
     %>
     var height = $('#largerImage').height();
     $('#largerImage').width(${ratio} * height);
</g:javascript>
