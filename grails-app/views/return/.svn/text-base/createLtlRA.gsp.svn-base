<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="yourAccount"/>
  <link href="${createLinkTo(dir: 'css', file: 'consumer.css')}" rel="stylesheet" type="text/css"/>
  <title>Order Return</title>
</head>
<body>
<div id="ltlBol">
    <h2>Order Return</h2>

    <p>To arrange pickup, call ${carrier.desc} at ${carrier.phoneNo}</p>
    <p>Please write:</p>
    <dl>
        <dt>RA No:</dt><dd>${raId}</dd>
    </dl>
    <p>on the outside of each returned box.</p>

    <p>Please print 2 copies of the Bill of Lading: One for the carrier and one for your records.</p>
    <%--    <g:link action="ltlBillOfLading" params="[raId:raId,cartId:cartId]">Bill of Lading As HTML for Test</g:link>  --%>
    <a class="pdfAnchor" 
        href="/pdf/show?url=/return/ltlBillOfLading?cartId=${cartId}-AMPERSAND-raId-EQUALS-${raId}&filename=billoflading"
    >Bill of Lading</a>
    <br/>
    <br/>
    <a class="back" href="${createLink(controller:'consumer',action:'orderHst')}?return=true}" title="Back to Order History" >Back</a>
    
</div>
</body>
</html>