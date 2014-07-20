<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="yourAccount" />
      <link href="${createLinkTo(dir:'css',file:'consumer.css')}" rel="stylesheet" type="text/css" /> 
    <g:javascript src="jquery/jquery-1.4.2.js"/>
    <title>User Profile</title>
</head>
<body>
<div id="yourAccountContent">
  <h2>Customer Information</h2>
  <g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${consumer}">
   <div class="errors">
       <g:renderErrors bean="${consumer}" as="list" />
   </div>
  </g:hasErrors>
  <div id="custInfoHeader">
     <g:render template="headerUpdate" model="["consumer:"consumer]" />
  </div>
  <br clear="left" />
  <br clear="left" />
  Receive email on closeouts and other events: <g:checkBox name="marketing" value="${consumer.marketing}"/>
  <h2>Bill To's:</h2>
  <g:link action="btCreate"  >Add Bill To Account</g:link>
  <ul>
    <g:each in="${consumer.billTos}" var="billTo">
      <li>
        ${billTo}
        <g:form action="btChange" controller="consumer">
            <input type="hidden" name="billToId" value="${billTo.id}"/>
            <input type="hidden" name="consumerId" value="${consumer.id}"/>
            <input type="submit" name="btChange" value="Change" />
        </g:form> 
        <g:form action="btDelete" controller="consumer">
            <input type="hidden" name="billToId" value="${billTo.id}"/>
            <input type="hidden" name="consumerId" value="${consumer.id}"/>
            <input type="submit" name="btDelete" value="Delete" />
        </g:form> 
      </li>
    </g:each>
  </ul>
  <h2>Ship To's:</h2>
  <g:link action="stCreate"  >Add Ship To Address</g:link>
<ul>
  <g:each in="${consumer.shipTos}" var="shipTo">
    <li>
      ${shipTo}
        <g:form action="stChange" controller="consumer">
            <input type="hidden" name="shipToId" value="${shipTo.id}"/>
            <input type="hidden" name="consumerId" value="${consumer.id}"/>
            <input type="submit" name="btChange" value="Change" />
        </g:form> 
        <g:form action="stDelete" controller="consumer">
            <input type="hidden" name="shipToId" value="${shipTo.id}"/>
            <input type="hidden" name="consumerId" value="${consumer.id}"/>
            <input type="submit" name="btDelete" value="Delete" />
        </g:form> 
    </li>
  </g:each>
</ul>
<br/>
</div>
<g:javascript>
	
	$('#marketing').click(function() {
        $.ajax({
            type:'POST', 
            url: "${createLink(action:'marketing')}",
            data: 'id='+$('#id').val()+'&marketing='+$('#marketing').is(':checked'),
            error:function(XMLHttpRequest,textStatus,errorThrown){}
         });
	});
</g:javascript>
</body>
</html>
