<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="yourAccount"/>
  <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  <link href="${createLinkTo(dir: 'css', file: 'yourAccount.css')}" rel="stylesheet" type="text/css"/>
  <title>Order Return: Self-shipping </title>
</head>
<body>
	<div id="selfShip">
		<h2>Order Return: Self Shipping</h2>
		<p>Please write:</p>
	        <dl>
	            <dt>RA No:</dt><dd>${ra.id}</dd>
	        </dl>
	    <p>on the outside of each returned box.</p>
        <h2>Ship To:</h2>
		<div class="address">
			KETTLER Virginia Beach<br/>
			1355 London Bridge Road<br/>
			Virginia Beach, VA 23453<br/>
		</div> 
		<br/>
        <g:link action="createSelfShipLabel" class="button">Print Label</g:link>
        <dl>
            <dt>KETTLER phone no:</dt><dd>757.427.2400</dd>
        </dl>
		
		<p>Note: You are responsible for purchasing insurance against risk of loss.</p> 
        <a class="back" href="${createLink(controller:'consumer',action:'orderHst')}?return=true}" title="Back to Order History" >Back</a>
	</div>
	
<g:javascript>
    $('#yourAccountMenu').css('height', '500px');
    $('#main').css('height', '700px');
</g:javascript>

</body>
</html>