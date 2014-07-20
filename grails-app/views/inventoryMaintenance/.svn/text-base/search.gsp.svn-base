<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Customer Locations</title>
</head>
<body>
<h2>${dealer} Locations</h2>
<g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
</g:if>
<table>
  <g:each in="${locs}" var="loc">
    <tr>
      <td>${loc}</td>
      <td>
        <g:form action="location" id="${loc.id}" method="post">
          <g:hiddenField name="division" value="${division}"/>
          <g:hiddenField name="custno" value="${custno}"/>
          <g:submitButton name="review" value="Review Inventory"/>
        </g:form>
      </td>
    </tr>
  </g:each>
</table>
<p>Under construction</p>
</body>
</html>