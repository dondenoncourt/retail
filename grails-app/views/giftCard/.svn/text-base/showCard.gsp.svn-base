<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Gift Card</title>
  <style>
  	.amount {
  		position: absolute;
  		top: 185px;
  		left: 80px;
  		color: green;
  		font-size: 40px;
  	}
  	.kettler {
		font-weight: bold;
		font-family: "Franklin Gothic Heavy", Verdana, Arial, sans-serif;
  		position: absolute;
  		top: 180px;
  		left: 220px;
  		color: black;
  		font-size: 44px;
  		text-transform: uppercase;
  	}
  	.giftcardtext {
  		position: absolute;
  		top: 198px;
  		left: 445px;
  		color: black;
  		font-size: 24px;
  		text-transform: uppercase;
  	}
  	.cardNumber {
  		position: absolute;
  		top: 260px;
  		left: 340px;
  		color: white;
  		background-color: black;
  		font-size: 36px;
  		text-transform: uppercase;
  	}
  </style>
</head>
<body>
<a href="${createLink(uri: '/')}"><img src="<g:resource dir='images' file='giftcard_header.jpg' />" alt="logo" title="Kettler Logo" /></a>
<img class="cart" src="${resource(dir:'images', file:'giftcard.jpg')}" alt="Gift Card Image"/>
<%--
 --%>
<div class="amount"><g:formatNumber number="${origAmount}" type="currency" currencyCode="USD" /> </div>
<div class="kettler">Kettler</div>
<div class="giftcardtext">gift card</div>
<div class="cardNumber">${cardNumber}&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>
<% if (origAmount < remaining) { %>
	<p>
	    <em>Card Number:</em> ${cardNumber} <br/>
	    <em>Original Amount:</em> <g:formatNumber number="${origAmount}" type="currency" currencyCode="USD" /> <br/>
	    <em>Amount Remaining:</em> <g:formatNumber number="${remaining}" type="currency" currencyCode="USD" /> <br/>
	</p>
<% } %>
</body>
</html>