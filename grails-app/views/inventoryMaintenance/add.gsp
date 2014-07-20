<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Add Inventory</title>
</head>
<body>
<h2>Add Inventory for location: ${location}</h2>
<g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
</g:if>
<p>loc: ${location}, custno: ${custno}</p>

<p>Select items below from those ordered in the past ${months} months.</p>

<g:form name="addInvForm" action="addInv" method="post">
  <table>
    <tr>
      <td>Item</td>
      <td>Add?</td>
    </tr>
    <g:each in="${purchasedItems}" var="pitem">
      <tr>
        <td>${pitem.itemNo} &ndash; ${pitem.desc}</td>
        <td><g:checkBox name="itemId_${pitem.id}" value="${false}"/></td>
      </tr>
    </g:each>
  </table>
  <g:hiddenField name="locId" value="${location.id}"/>
  <g:hiddenField name="custno" value="${custno}"/>
  <g:hiddenField name="division" value="${division}"/>
  <g:submitButton name="submit" value="Submit"/>
</g:form>
<p>Under construction</p>

</body>
</html>