<%--
  Created by IntelliJ IDEA.
  User: Mike
  Date: Oct 10, 2010
  Time: 9:20:18 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main" />
  <title>UPS Tracking Failure</title>
</head>
  <body>
  <div class="body">
    <img src="${resource(dir:'images',file:'LOGO_M.gif')}" alt="UPS Logo" border="0" />
    <h1>Service Error</h1>
    <p>
      We received the following error from UPS:.
    </p>
    <p>
      ${tr.errorDescription}
    </p>
    <div style="margin-top: 10px;">
      UPS, the UPS brandmark, and the Color Brown are trademarks of United
Parcel Service of America, Inc. All Rights Reserved
    </div>
  </div>
  </body>
</html>