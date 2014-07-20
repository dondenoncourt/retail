<%@ page import="com.kettler.domain.orderentry.share.*" %>
<%@ page import="com.kettler.domain.actrcv.share.ReturnItem" %>

<%@ page import="grails.util.Environment" %>

<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="yourAccount" />
      <title>Warranties List</title>
</head>
<body>
    <div id="yourAccountContent"> 
       <br/>
	   <h2>Your Warranties</h2>
	   <g:if test="${flash.message}">
	       <div class="message">${flash.message}</div>
	   </g:if>
		<g:if test="${!warranties?.size()}">
		    <p>No warranties are on record for your account.</p>
		</g:if>
		<g:else>
			<div class="list">
			    <table>
			        <thead>
			            <tr>
			                <th>Item No</th>
			                <th>Description</th>
			                <th>Qty</th>
			                <th class="date">Order <br/>Date</th>
			                <th>Warranty</th>
    		            </tr>
			        </thead>
			        <tbody>
			        <g:each in="${warranties}" status="i" var="warranty">
			        	<tr>
			        		<td>${warranty.itemNo}</td>
			        		<td>${warranty.itemDesc}</td>
			        		<td>${warranty.purchaseQty}</td>
                            <td><g:formatDate date="${warranty.purchaseDate}" format="MM/dd/yyyy"/></td>
			        		<td>${warranty.warrantyPeriod.desc}</td>
			        	</tr>
			        </g:each>
			        </tbody>
			    </table>
	        </div>
		</g:else>
        <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    </div>
    </div>
    <br/>
</body>
</html>
