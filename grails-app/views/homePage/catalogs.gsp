<%@ page import="com.kettler.domain.item.share.Dealer" %>
<html>
<head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="layout" content="kettlerusa" />
	<title>Kettler Product Catalogs</title> 
</head> 
	<body>
		<div class="body" id="content" style="height:400px;">
        <p>  Click product category image to display its catalog</p>
        <g:each in="${catalogs}" var="catalog">
           <div class="lifeStyleLinks manualsLinks divManuals${catalog.capitalizeNames()}">
	           <a onclick="window.open('${createLinkTo(dir:'catalogs/'+catalog, file:'view.html')}')" href="#">
                   <h2>${catalog.capitalizeNames()}</h2>
	           </a>
	           <a onclick="window.open('${createLinkTo(dir:'catalogs/'+catalog, file:'view.html')}')" href="#">
                    <img src="${createLinkTo(dir:'images/'+catalog)}/life-style.jpg" width="100%" height="" />
               </a>
           </div>
        </g:each>
        </div>
    </body>
</html>
