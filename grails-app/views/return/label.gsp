<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder as CH" %>

<html>
<head>
	<link href="<g:createLinkTo dir='css' file='consumer.css'/>" rel="stylesheet" type="text/css"/>
	<title>View/Print Label</title>
	<style>
	.small_text {
		font-size: 80%;
	}
	.large_text {
		font-size: 115%;
	}
	</style>
	<style type="text/css" media="print">
	   .button {display:  none;}
	</style>  
</head>

<body bgcolor="#FFFFFF">
<table border="0" cellpadding="0" cellspacing="0" width="600">
	<tr>
		<td height="410" align="left" valign="top"><B class="large_text">View/Print
		Label</B> &nbsp;<br>
		&nbsp;<br>
		<ol class="small_text">
			<li><b>Print the label:</b> &nbsp; Select Print from the File
			menu in this browser window to print the label below.<br>
			<br>
			<li><b> Fold the printed label at the dotted line.</b> &nbsp;
			Place the label in a UPS Shipping Pouch. If you do not have a pouch,
			affix the folded label using clear plastic shipping tape over the
			entire label.<br>
			<br>
			<li><b>GETTING YOUR SHIPMENT TO UPS<br>
			Customers without a Daily Pickup</b>
			<ul>
				<li>Take this package to any location of The UPS Store, UPS
				Drop Box, UPS Customer Center, UPS Alliances (Office Depot or
				Staples) or Authorized Shipping Outlet near you or visit 
				<a href="http://www.ups.com/content/us/en/index.jsx">www.ups.com/content/us/en/index.jsx</a>
				and select Drop Off.
				<li>Air shipments (including Worldwide Express and Expedited)
				can be picked up or dropped off. To schedule a pickup, or to find a
				drop-off location, select the Pickup or Drop-off icon from the UPS
				tool bar.
			</ul>
			<br>
			<b>Customers with a Daily Pickup</b>
			<ul>
				<li>Your driver will pickup your shipment(s) as usual.
			</ul>
		</ol>
		</td>
	</tr>
</table>

<a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
<a href="#" class="button" onclick="window.print(); return false;">Print</a>
<br/><br/><br/><br/>

<table border="0" cellpadding="0" cellspacing="0" width="600">
	<tr>
		<td class="small_text" align="left" valign="top">
		&nbsp;&nbsp;&nbsp; <a name="foldHere">FOLD HERE</a></td>
	</tr>
	<tr>
		<td align="left" valign="top">
		<hr>
		</td>
	</tr>
</table>

<table>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="650">
	<tr>
		<td align="left" valign="top">
			<%-- <IMG SRC="../label${trackingNo}.gif" height="392" width="651">--%>
			<img src="${createLink(action:'renderImage', params:[trackingNo:trackingNo])}" height="392" width="651">
 		</td>
	</tr>
</table>
</body>
</html>
