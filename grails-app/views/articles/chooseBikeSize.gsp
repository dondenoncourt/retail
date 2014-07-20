<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <meta name="layout" content="kettlerusa" />
        <title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title> 
</head> 
<body> 
<div id="content" class="articleContent"> 
	<g:render template="/articles/adultBikeSizeChart"/>
	<br/>
    <g:render template="/articles/childBikeSizeChart"/>
	<br/>
    <a class="back" href="${createLink(controller:'homePage',action:'infoEdu')}?mode=${params.mode}" title="Back to Info &amp; Education" >Back</a>
    <br/>
    <br/>
</div>    
<g:javascript>
    $('#infoEdu').addClass('active');
</g:javascript>
</body>
</html> 
