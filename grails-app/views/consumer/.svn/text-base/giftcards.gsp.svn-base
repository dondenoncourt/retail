<%@ page import="com.kettler.domain.orderentry.share.*" %>
<%@ page import="grails.util.Environment" %>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>

<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="yourAccount" />
      <title>Gift Cards</title>
</head>
<body>
    <div id="yourAccountContent"> 
       <br/>
	   <h2>Gift Cards</h2>
	   <g:if test="${flash.message}">
	       <div class="message">${flash.message}</div>
	   </g:if>
		<g:if test="${!(giftcards?.size())}">
		    <p>No orders have been placed with this account.</p>
		  </g:if>
		<div class="list">
		    <table>
		        <thead>
		            <tr>
		                <th>Gift Card No.</th>
		                <th>Order Date</th>
		                <th>Purchased Value</th>
		                <th>Current Value</th>
		                <th>Print</th>
    		        </tr>
		        </thead>
		        <tbody>
		        <g:each in="${giftcards}" status="i" var="giftcard">
		         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		         	<td>${giftcard.cardNumber}</td>
		         	<td><g:formatDate format="MM/dd/yyyy" date="${giftcard.dateCreated}"/></td>
				    <td><g:formatNumber number="${giftcard.originalValue}" type="currency" currencySymbol="\$" /></td>
				    <td><g:formatNumber number="${giftcard.currentValue}" type="currency" currencySymbol="\$" /></td>
				    <td>
				       <a class="pdfAnchor" href="${ConfigurationHolder.config.grails.serverURL}/pdf/show?url=/giftCard/showCard/${giftcard.id}" >Print</a>
    				</td>
		         </tr>
		        </g:each>
		        </tbody>
		    </table>
        </div>
        <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    </div>
    </div>
    <br/>
</body>
</html>
