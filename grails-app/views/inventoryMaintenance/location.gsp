<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Inventory Maintenance</title>
</head>
<body>
<h2>Inventory for location: ${location}</h2>
<g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
</g:if>
<g:if test="${location.inventories?.size()==0}">
  <p>You have no inventory set up at this location.  Use the "Add Inventory" button
  at the bottom of this page to add inventory.</p>
</g:if>
<g:else>
  <g:form name="invAvailForm" action="invAvail" method="post">
    <g:hiddenField name="division" value="${division}"/>
    <g:hiddenField name="custno" value="${custno}"/>
    <g:hiddenField name="locId" value="${location.id}"/>
    <table>
      <tr>
        <th>Item</th>
        <th>Inventory Available?</th>
      </tr>
      <g:each in="${location.inventories}" var="inv">
        <tr>
          <td>${inv.item?.itemNo} &ndash; ${inv.item?.desc}</td>
          <td>
            <g:checkBox name="cbInvAvail_${inv.id}" value="${false}"/>
          </td>
        </tr>
      </g:each>
    </table>
    <g:submitButton name="submit" value="Submit"/>
  </g:form>
</g:else>
<g:form name="addInvForm" action="add" method="post">
  <g:hiddenField name="division" value="${division}"/>
  <g:hiddenField name="custno" value="${custno}"/>
  <g:hiddenField name="locId" value="${location.id}"/>
  <g:submitButton name="add" value="Add Inventory"/>
</g:form>
<p>Under construction</p>
</body>
</html>