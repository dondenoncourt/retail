<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Customer Search</title>
</head>
<body>
<g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
</g:if>
<div style="height:200px;">
  <g:form name="myForm" method="post" style="height:200px;">
    <g:hiddenField name="division" value="${division}"/>
    <label for="custno">Customer Number</label>
    <g:textField name="custno" value="" size="30"/>
    <div class="buttons">
      <span class="button"><g:actionSubmit class="save" action="search" value="Search"/></span>
    </div>
  </g:form>
</div>
</body>
</html>