<%@ page import="com.kettler.domain.orderentry.share.*" %>

<tr class="prop">
  <td valign="top" class="name">Status</td>
  <td valign="top" class="value">${invOrderHeader?.statusCode}</td>
</tr>
<tr class="prop">
  <td valign="top" class="name">Shipped Status</td>
  <td valign="top" class="value">${invOrderHeader?.shippedStatus}</td>
</tr>
<tr class="prop">
  <td valign="top" class="name">Tracking No</td>
  <g:if test="${invOrderHeader?.freightTrackingNo && invOrderHeader?.carrierCode == 'UPSN'}">
    <td valign="top" class="value"><g:link controller="upsTracking" action="search" params="[number:invOrderHeader?.freightTrackingNo,orderNo:invOrderHeader?.orderNo]">${invOrderHeader?.freightTrackingNo}</g:link></td>
  </g:if>
  <g:elseif test="${invOrderHeader?.freightTrackingNo && (invOrderHeader?.carrierCode == 'FDEG' || invOrderHeader?.carrierCode == 'FDE')}">
    <td valign="top" class="value"><g:link controller="fedexTracking" action="search" params="[number:invOrderHeader?.freightTrackingNo,orderNo:invOrderHeader?.orderNo]">${invOrderHeader?.freightTrackingNo}</g:link></td>
  </g:elseif>
  <g:else>
    <td valign="top" class="value">${invOrderHeader?.freightTrackingNo}</td>
  </g:else> 
</tr>
