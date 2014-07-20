<%@ page import="com.kettler.domain.orderentry.share.*" %>
<%@ page import="com.kettler.domain.actrcv.share.ReturnItem" %>

<%@ page import="grails.util.Environment" %>

<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="yourAccount" />
      <title>${params.return?'Order Returns':'Order History'}</title>
</head>
<body>
    <div id="yourAccountContent"> 
       <br/>
	   <h2>${params.return?'Order Returns':'Order History'}</h2>
	   <g:if test="${flash.message}">
	       <div class="message">${flash.message}</div>
	   </g:if>
		<g:if test="${carts.size() == 0}">
		    <p>No orders have been placed with this account.</p>
		  </g:if>
		<div class="list">
		    <table>
		        <thead>
		            <tr>
		                <th class="date">Order <br/>Date</th>
		                <th>Order <br/>No</th>
		                <th>Items</th>
		                <th>Ship-To Name and Address</th>
		                <th class="date">Shipped</th>
                        <% if (params.return) { %>
                            <th>Return Status</th>
	                    <% } else { %>
                            <th>Tracking Details</th>
	                    <% } %>
    		            </tr>
		        </thead>
		        <tbody>
		        <g:each in="${carts}" status="i" var="cart">
                 <% def orderHeader = crsOrders.get(cart.id) %>
		         <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		             <td><g:formatDate format="MM-dd-yy" date="${orderHeader?.dateCreated}"/></td>
		             <td><g:formatNumber number="${cart.orderNo}" /></td>
		             <td class="number">${cart.items?.size()}</td>
		             <g:if test="${cart.shipToId}">
		               <td>${ConsumerShipTo.get(cart.shipToId)}</td>
		             </g:if>
		             <g:else>
		               <td>${(ConsumerBillTo.get(cart.billToId)?.address()?:'Address Not Found')}</td>
		             </g:else>
                     <td>
                          <% if (orderHeader?.dateShipped?.getDate() != 1 && orderHeader?.dateShipped > (new Date()-90)) { %>
                              <g:formatDate format="MM-dd-yy" date="${orderHeader?.dateShipped}"/>
                          <% } %>
                     </td>
                     <% if (params.return) { %>
                         <%-- if ((Environment.current == Environment.DEVELOPMENT || ['dondenoncourt@gmail.com', 'smannix@kettlerusa.com'].find{it == session?.consumer?.email}) --%>
                         <% if (orderHeader?.invoiceNo) { %>
                              <% if (orderHeader?.dateShipped > (new Date()-90)) { %>
                                   <%
                                       ReturnItem raItem = ReturnItem.findByOrderNo(orderHeader?.orderNo)
                                   %>
                                   <td>
                                        <% if (raItem) { %>
                                            <% if (raItem.ra?.status.id == 'NEWRA') { %>
                                                RA# ${raItem.ra.id} in Transit 
                                            <% } else if (raItem.ra?.status.id == 'CMFIN') { %>
                                                Your account has been credited for the return
                                            <% } else { %>
                                                RA# ${raItem.ra?.id} ${raItem.ra?.status}
                                            <% } %>
                                        <% } else { %>
                                       <a class="button" id="processReturn"
                                            href="/return/orderReturn?cartId=${cart.id}"
                                                class="button" title="click to begin processing a return"
                                        >Process a Return</a>
                                        <% } %>
                                   </td>
                                   <% if (raItem?.ra?.status?.id == 'NEWRA') { %>
	                                   <td>
								           <g:link controller="return" action="label" 
								           	   params="[trackingNo:raItem.ra.freightTrackingNo]"
							                   title="Click this link to view and print the UPS label"
								            >
								                Reprint Label
								            </g:link>
								            <%-- http://grailsdev:8080/retail/return/label?trackingNo=1Z2303340395597359 
								                 http://grailsdev:8080/retail/consumer/label?trackingNo=1Z2303340395597359--%>
	                                   </td>
		                           <% } %>
                              <% } %>
                         <% } %>
                     <% } else { %>
	                     <g:if test="${orderHeader?.shipInstructions != 'R-TRAILER' && orderHeader?.freightTrackingNo && cart.upsServiceCode != Cart.LTL && orderHeader?.carrierCode == 'UPSN'}">
				             <td><g:link controller="upsTracking" action="search" class="button"
				                    params="[number:orderHeader.freightTrackingNo,orderNo:orderHeader.orderNo]"
				                    title="Click to view UPS tracking information"
				                 >Track</g:link>
				             </td>
				         </g:if>
	                     <g:elseif test="${orderHeader?.shipInstructions != 'R-TRAILER' && orderHeader?.freightTrackingNo && cart.upsServiceCode != Cart.LTL && (orderHeader?.carrierCode == 'FDEG' || orderHeader?.carrierCode == 'FDE')}">
				             <td><g:link controller="fedexTracking" action="search" class="button"
				                    params="[number:orderHeader.freightTrackingNo,orderNo:orderHeader.orderNo]"
				                    title="Click to view FedEx tracking information"
				                 >Track</g:link>
				             </td>
				         </g:elseif>
						 <g:else>
						    <td></td>
						 </g:else> 
	                     <td><g:link action="orderDetail" params="[cartId:cart.id]" class="button"
	                               title="Click to view details on the order"
	                          >View</g:link> </td>
                     <% } %>
		            </tr>
		        </g:each>
		        </tbody>
		    </table>
        </div>
	    <div class="page">
	        <g:paginate total="${cartTotal}" params="[consumerId:consumerId]" prev="&laquo; Prev" next="Next &raquo;"/>
	    </div>
        <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
    </div>
    </div>
    <br/>
<g:javascript>
    $('#processReturn').click(function() {
        $("#logoImg").attr("src", "${resource(dir:'images',file:'spinner35x35.gif')}");
    });
</g:javascript>
</body>
</html>
