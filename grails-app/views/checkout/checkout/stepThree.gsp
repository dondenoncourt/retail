<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder as CH" %>

<html> 
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kettlerusa" />
    <title>Place Order</title>
    <link href="${createLinkTo(dir: 'css', file: 'checkout.css')}" rel="stylesheet" type="text/css"/>
    <g:javascript src="jquery/jquery-1.4.2.js"/>
    <g:javascript src="jquery-ui.min.js"/> 
  </head>
<body>
    <div class="body"> 
        <% if (payShipCmd.creditCardType != 'paypal' || session.paypalPayerId) { %>
            <g:render template='checkout/checkoutSteps' model="[step:3]"/> 
        <% } else { %>
            <div class="message">Redirecting to Paypal</div>
        <% } %>
        <g:render template="checkout/address" collection="${addrCmd}" />
         <fieldset id="paymenInfo" class="left">
           <legend>Payment Info:</legend>
            <% if (payShipCmd.creditCardType == 'paypal') { %>
                Paypal
            <% } else if (payShipCmd?.creditCard.size() > 12) { %>
                Card: ${payShipCmd?.creditCardType.toUpperCase()}<br/>
                **** **** **** ${(payShipCmd.creditCard[12..(payShipCmd.creditCard.size()-1)])}<br/>
                Expiration: ${payShipCmd.month}/${payShipCmd.year}
            <% } %>
       		<% if (payShipCmd?.giftCard) { %>
                Gift Card #: ${payShipCmd?.giftCard}<br/>
            <% } %>
         </fieldset>
		<%-- I don't know why template=/checkout/cart won't work.... it locks up when running the page???? --%>
        <g:render template='/checkout/cart' model="[cart:cart, addr:addrCmd, ajax:false, verifyOrder:true]"/> 
		
        <% if (payShipCmd.creditCardType != 'paypal' || session.paypalPayerId) { %>
            <g:form name="checkoutForm" action="checkout"  method="post" >
                 <g:render template='checkout/submitButtons' model="[step:3]"/> 
            </g:form>
        <% } %>
        <div id="hourglass" class="hourglass-center" >
        	<img src="${resource(dir:'images',file:'spinner35x35.gif')}"/>
        </div>
    </div>
<g:javascript>
    <% if (payShipCmd.creditCardType == 'paypal' && !session.paypalPayerId) { %>
        $(".hourglass-center").show();
    <% } %>
    $('#shipToInfo').height($('#billToInfo').height());
   	$('div.body').height($('div.body').height()+ (${(cart.items.size())} * 50));
    $('#_eventId_stepOne').hide();
    $('#stepOneLink').click(function(){
        $("#_eventId_stepOne").trigger("click");
        $("#_eventId_stepOne").hide();
    });   
    $('#stepTwoLink').click(function(){
        $(".hourglass-center").show();
        $("#_eventId_stepTwo").trigger("click");
        $("#_eventId_stepTwo").hide();
    });   
    $('#stepThreeLink').click(function(){
        $("#_eventId_stepThree").trigger("click");
        $("#_eventId_stepThree").hide();
    });   
    $('#stepFourLink').click(function(){
        $("#_eventId_stepFour").trigger("click");
        $("#_eventId_stepFour").hide();
    });   
    $('#checkoutForm').submit(function() {
        $(".hourglass-center").show();
        $("#_eventId_stepFour").hide();
	    $('#stepFourLink').hide();
        return true;
    });
    $(document).ready( function () {
        var few_for_late_paypal_image_load = 15;
        $('div#main').height( ( $('#footer').offset().top - $('div#main').offset().top) + few_for_late_paypal_image_load ); 
        <% if (payShipCmd.creditCardType == 'paypal' && !session.paypalPayerId) { %>
            <kettler:logMsg level="debug">PP_Sandbox_UserGuide Chap 5 Step 3: stepThree.gsp onload</kettler:logMsg>
            // need to use window.location because a redirect in webflow terminates the webflow
            // a filter maybe could redirect, but that would be harder to find in maintenance
            window.location = "https://${CH.config.paypal.url.cgi}/cgi-bin/webscr?token=${cart.paypalToken.encodeAsURL()}&cmd=_express-checkout"
        <% } %>
    });
</g:javascript>
</body>
</html>
