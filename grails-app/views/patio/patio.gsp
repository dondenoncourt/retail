<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <meta name="layout" content="patio" />
        <title>Kettler Patio</title> 
</head> 
 
<body> 
            <div class="page"> 
                <a href="#" class="inactive">&laquo; Prev</a> 
                <a href="#" class="active">1</a> 
                <a href="#">2</a> 
                <a href="#">3</a> 
                ...
                <a href="#">20</a> 
                <a href="#">Next &raquo;</a> 
            </div> 
            
            <div class="products"> 
                <img src="${createLinkTo(dir:'images/patio',file:'1.png')}" alt="1" /> 
                <img src="${createLinkTo(dir:'images/patio',file:'2.png')}" alt="2" /> 
                <img src="${createLinkTo(dir:'images/patio',file:'3.png')}" alt="3" /> 
                <img src="${createLinkTo(dir:'images/patio',file:'4.png')}" alt="4" /> 
                <br /><br /> 
                
                <img src="${createLinkTo(dir:'images/patio',file:'5.png')}" alt="5" /> 
                <img src="${createLinkTo(dir:'images/patio',file:'6.png')}" alt="6" /> 
                <img src="${createLinkTo(dir:'images/patio',file:'7.png')}" alt="7" /> 
                <img src="${createLinkTo(dir:'images/patio',file:'8.png')}" alt="8" /> 
            </div> 

</body> 
