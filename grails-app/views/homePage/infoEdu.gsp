<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.WebCategory" %>

<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <meta name="layout" content="kettlerusa" />
        <title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title> 
</head> 
<body> 
<div id="content"> 
   
      <div id="articles">
              <div class="article">
      	        <a href="${createLink(controller:'articles')}/ebikes.gsp?division=kettler+usa&mode=${params.mode}"
                     title="view article">
      	            <img src="${createLinkTo(dir:'images/fitness')}/tab.jpg" />
      	        </a>
                  <a href="${createLink(controller:'articles')}/ergometer.gsp?division=kettler+usa&mode=${params.mode}"
                     title="view article">
    	        <h2>What is an Ergometer?</h2>
    
      
      </a>
              </div>
        <hr/>
   
   <div id="articles">
           <div class="article">
   	        <a href="${createLink(controller:'articles')}/ebikes.gsp?division=kettler+usa&mode=${params.mode}"
                  title="view article">
   	            <img src="${createLinkTo(dir:'images/bikes')}/tab.jpg" />
   	        </a>
               <a href="${createLink(controller:'articles')}/ebikes.gsp?division=kettler+usa&mode=${params.mode}"
                  title="view article">
    	        <h2>Benefits of Electric Bikes</h2>
   
   </a>
           </div>
        <hr/>
   <div id="articles">
        <div class="article">
	        <a href="${createLink(controller:'articles')}/chooseBikeSize.gsp?division=kettler+usa&mode=${params.mode}"
               title="view article">
	            <img src="${createLinkTo(dir:'images/bikes')}/tab.jpg" />
	        </a>
            <a href="${createLink(controller:'articles')}/chooseBikeSize.gsp?division=kettler+usa&mode=${params.mode}"
               title="view article">
    	        <h2>How to Choose the Proper Size Bicycle</h2>
            </a>
        </div>
        <hr/>
        <div class="article">
            <a href="${createLink(controller:'articles')}/bbbenefits.gsp?division=kettler+usa&mode=${params.mode}"
               title="view article">
                <img src="${createLinkTo(dir:'images/toys')}/tab2.jpg" />
            </a>
            <a href="${createLink(controller:'articles')}/bbbenefits.gsp?division=kettler+usa&mode=${params.mode}"
               title="view article">
                <h2>Why Buy a Balance Bike?</h2>
            </a>
        </div>
        <hr/>
        <div class="article">
            <a href="${createLink(controller:'articles')}/ttrules.gsp?division=kettler+usa&mode=${params.mode}"
               title="view article">
                <img src="${createLinkTo(dir:'images/table tennis')}/tab.jpg" />
            </a>
            <a href="${createLink(controller:'articles')}/ttrules.gsp?division=kettler+usa&mode=${params.mode}"
               title="view article">
                <h2>Table Tennis Rules</h2>
            </a>
        </div>
        <hr/>
    </div> 
</div>    
<g:javascript>
    $('#infoEdu').addClass('active');
</g:javascript>
</body>
</html> 
