<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${division}</title>
		<meta name="layout" content="${(division?.replaceAll(/ /,'')?:'toys')}" />
        <link href="${createLinkTo(dir:'css',file:'consumer.css')}" rel="stylesheet" type="text/css" /> 
        <title>User Profile</title>
    </head>
    <body>
        <div   class="body">
            <h1>Customer Information</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div id="custInfoHeader">
               <g:render template="headerStatic" model="["consumer:"consumer]" />
            </div>
            <h2>Bill Tos:</h2>
            <g:if test="${consumer.billTos?.size()==0}">
              <div id="billToCreateDiv">
                <a href="#" id="billToCreateLink" class="clickme">Add</a>
              </div>
            </g:if>
            <g:else>
              <g:each in="${consumer.billTos}" var="billTo">
                 <div id="billTo${billTo.id}">
                    <g:render template="billToStatic" model="[billTo:billTo]" />
                 </div>
              </g:each>
              <a href="#" id="billToCreateLink" class="clickme">Add</a>
            </g:else>
            <h2>Ship Tos:</h2>
            <g:each in="${consumer.shipTos}" var="shipTo">
               <div id="shipTo${shipTo.id}">
                    <g:render template="shipToStatic" model="[shipTo:shipTo]" />
               </div>
            </g:each>
        </div>
    <g:javascript>
        $("#billToCreateLink").click(function() {
            $.ajax({
                type:'POST',
                url: "${createLink(action:'billToCreate',params:[consumerId:consumer.id])}",
                success:function(data,textStatus){$('#billToCreateDiv').html(data);},
                error:function(XMLHttpRequest,textStatus,errorThrown){}
             });
             return false;
        });
    </g:javascript>
    </body>
</html>
