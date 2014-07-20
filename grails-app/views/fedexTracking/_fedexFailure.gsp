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
  <title>FedEx Tracking Failure</title>
</head>
  <body>
  <div class="body">
    <img src="${resource(dir:'images',file:'WeShipVBox_Clct_Prf_2c_Pos_Plt_150.png')}" width="90" height="70" alt="FedEx Logo" border="0" /> 
    <h1>Service Error</h1>
    <p>
      We received the following error from FedEx:.
    </p>
    <p>
      ${tr.errorDescription}
    </p>
    <div style="margin-top: 10px;">
      NOTICE: FedEx service marks used by permission.
    </div>
  </div>
  </body>
</html>