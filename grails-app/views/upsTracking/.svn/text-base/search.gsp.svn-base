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
    <title>UPS Tracking Prototype</title>
    <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="yourAccountContent">
      <img src="${resource(dir:'images',file:'LOGO_M.gif')}" alt="UPS Logo" border="0" />
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
        NOTICE: The UPS package tracking systems accessed via this service (the
      “Tracking Systems”) and tracking information obtained through this service (the
      “Information”) are the private property of UPS. UPS authorizes you to use the Tracking
      Systems solely to track shipments tendered by or for you to UPS for delivery and for no
      other purpose. Without limitation, you are not authorized to make the Information
      available on any web site or otherwise reproduce, distribute, copy, store, use or sell the
      Information for commercial gain without the express written consent of UPS. This is a
      personal service; thus, Your right to use the Tracking Systems or Information is nonassignable.
      Any access or use that is inconsistent with these terms is unauthorized and
      strictly prohibited.
      </div>
      <div style="margin-top: 10px;">
        UPS, the UPS brandmark, and the Color Brown are trademarks of United
            Parcel Service of America, Inc. All Rights Reserved
      </div>
      <br/>
    <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    </div>
    <br/>
  </body>
</html>