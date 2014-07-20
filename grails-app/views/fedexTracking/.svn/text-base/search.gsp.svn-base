<%--
  Created by IntelliJ IDEA.
  User: Mike
  Date: Oct 9, 2010
  Time: 9:20:32 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <meta name="layout" content="yourAccount" />
    <title>FedEx Tracking Prototype</title>
    <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="yourAccountContent">
	<br>
      <img src="${resource(dir:'images',file:'WeShipVBox_Clct_Prf_2c_Pos_Plt_150.png')}" width="90" height="70" alt="FedEx Logo" border="0" />
      <h1>Package Tracking Results</h1>
      <h2>Shipped To</h2>
      ${tr.addressLine}<br/>
      <g:if test="${(tr.addressLine2 && tr.addressLine2.length()>0)}">
        ${tr.addressLine2}<br/>
      </g:if>
      ${tr.city}, ${tr.state} ${tr.zip}<br/>
      <p style="margin-top: 10px;">Service: ${tr.service}</p>
      <g:if test="${tr.pickUpDate}">
        <p style="margin-top: 10px;">Picked up: ${tr.pickUpDate}</p>
      </g:if>
      <h2>Activity</h2>
      <ul>
        <g:each var="act" in="${tr.activities}">
          <li>
            ${act.activityDate} ${act.activityTime} - ${act.statusTypeDescription}
            <g:if test="${act.city?.length()>0}">
               <br/>
              Location: ${act.addressLine} ${act.addressLine2} ${act.city} ${act.state} ${act.zip}
            </g:if>
            <g:if test="${act.signedByName?.length()>0}">
              <br/>
              Signed for by: ${act.signedByName}
            </g:if>
          </li>
        </g:each>

      </ul>
      <!--
      <hr/>
      <h1>XML Request</h1>
      ${req}
      <h1>XML Response</h1>
      ${resp}
      -->
      <div style="margin-top: 10px;">
        NOTICE: FedEx service marks used by permission.
      </div>
      <br/>
    <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    </div>
    <br/>
  </body>
</html>