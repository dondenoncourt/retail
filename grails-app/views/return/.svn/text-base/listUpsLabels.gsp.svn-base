<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="yourAccount"/>
  <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  <title>Order Return</title>
</head>
<body>
<div id="upsLabels">
  <% 
      def numbers = ['ONE', 'TWO', 'THREE', 'FOUR', 'FIVE','SIX','SEVEN','EIGHT','NINE','TEN','ELEVEN','TWELVE']
  %>
   <h1>Order Return Via UPS</h1>

    <% if (trackingNos.size() == 1) { %> 
        <p>You will need to print a UPS shipping label by clicking the following link.</p> 
    <% } else { %>
        <p>You will need to print UPS shipping labels for ${(trackingNos?.size())} packages by clicking the following links.</p>
    <% } %>
    <ul id="trackingLabels">
	    <g:each in="${trackingNos}" var="trackingNo" status="i">
	        <li>
	           <g:link action="label" params="[trackingNo:trackingNo]"
                   title="Click this link to view and print the UPS label for box {numbers[i]}"
	            >
	                Click to print label for box ${(numbers[i])} with UPS Tracking Id: ${trackingNo} 
	            </g:link>
	        </li>    
	    </g:each>
    </ul>
    <p>
        After printing the above label${((trackingNos.size()>1)?'s':'')} and affixing ${((trackingNos.size()>1)?'them':'it')} 
        to the package${((trackingNos.size()>1)?'s':'')}, 
        <% if (session.upsPickup) { %>
            call 1-800-742-5877 for pickup or 
            visit <a href="www.ups.com/content/us/en/index.jsx">www.ups.com</a> and select Schedule a Pickup.
        <% } else {  %>
	        take the package${((trackingNos.size()>1)?'s':'')} to 
	        any location of The UPS Store, UPS Drop Box, UPS Customer Center, 
	        UPS Alliances (Office Depot or Staples) or Authorized Shipping Outlet near you or 
	        visit <a href="www.ups.com/content/us/en/index.jsx">www.ups.com</a> and select Drop Off.
	    <% } %>    
    </p>
    <p>Please write:</p>
       <dl>
           <dt>RA No:</dt><dd>${raId}</dd>
       </dl>
    <p>on the outside of each returned box.</p>
    <br/>
    <a class="back" href="${createLink(controller:'consumer',action:'orderHst')}?return=true}" title="Back to Order History" >Back</a>
    
</div>
<g:javascript>
    $('#yourAccountMenu').css('height', '500px');
    $('#upsLabels').css('height', '500px');
</g:javascript>
</body>
</html>
