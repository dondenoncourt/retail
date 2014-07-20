// copy this file to /opt/webapps
checkout.type='3page'
iseriesIPAddress='brain' // 192.168.1.50 on some Kettler networks
iseriesUserId='DOND'
iseriesPwd='vo2max'
tomcatDir='/opt/tomcat/apache-tomcat-6.0.20'
paywareClientUserId='USER1'
comapnyName='KETTLER  International'
companyAddress='1355 London Bridge Road'
companyCity='Virginia Beach'
companyState='VA'
companyZip='23453'
upsUserId='kettler'
upsPassword='varsity'
upsAccessCode='3C695F76F47B23A8'
upsEndPointUrl='https://wwwcie.ups.com/webservices/Rate'
upsTrackUrl='https://wwwcie.ups.com/ups.app/xml/Track'
upsAddressUrl='https://onlinetools.ups.com/webservices/XAV'

paypal.user = 'creditmanager_api1.kettlerusa.com'
paypal.pwd = 'A8B58FDQKMJRFLK2'
paypal.signature = 'AFU4gvn4RNVf.CS8n6V9-6F7VG9MAq1cbFtQT6QGEjyzrEsIRg5YOcFa'
paypal.url.nvp = 'api-3t.paypal.com/nvp'
paypal.url.cgi='www.paypal.com'

upsShipConfirmUrl='https://onlinetools.ups.com/ups.app/xml/ShipConfirm'
upsShipAcceptUrl='https://onlinetools.ups.com/ups.app/xml/ShipAccept'
upsVoidShipmentUrl='https://onlinetools.ups.com/ups.app/xml/VoidShipment'

fedexKey='QdzViNRzozsDvUzM'
fedexPassword='zgGTwMDDRu0w9V75FMA6BpOPe'
fedexAccountNumber='510087100'
fedexMeterNumber='118580963'

rateService='FEDEX' //fedEx = FEDEX; ups = UPS

invoiceCutoffMonths=-18
inventoryUpdateDays=14
inventoryEmailDaysBefore=2
inventoryCronExpression='0 0 3 * * ?' // 3AM
mailFromAddress='Kettler Online<webmaster@kettlerusa.com>'
reminderSubject='Reminder: Update your Kettler Online Availability'
reminderMessage='''
<h2>Update Availability at Kettler</h2>
<p>You need to update your inventory availability for all locations
within the next two days.</p>
<p>Use the following link: <a href="https://www.kettlerusa.com/kettler">Kettler
Availability</a>.</p>
'''
expiredSubject='Available inventory has expired at Kettler Online'
expiredMessage='''
<h2>Available Inventory Has Expired</h2>
<p>Available inventory at one or more of your locations has <b>expired</b>
due to inventory not being maintained on Kettler\'s web site.</p>
<p>Please update your inventory as soon as possible using the following
link: <a href="https://www.kettlerusa.com/kettler">Kettler Online</a>.</p>
'''

questionCCID='''This 3 to 4 digit security code.
For Mastercard, Visa, and Discover, it is a 3-digit code located on the back of your credit card.
For American Express, it is a 4-digit code and is located on the front of your card above the credit card number.
'''
questionUpsShippingInfo=''' 
KETTLER offers free Ground shipping on orders over $40 within the continental US.  Expedited delivery services such as Next Day are provided for an additional fee.
''' 
questionInsideDelivery='Deliveries are typically made between 8:00 AM and 5:00 PM, Monday through Friday.  Some trucking companies will give a 4-hour window for delivery.  Inside delivery service will be added to your order for no additional cost.  This service will take the delivery to the first room inside a home, condominium, apartment, shed or garage.   Your item is likely to be heavy and large; therefore, we recommend that you have someone with you to help you carry the item to your room of choice.  Inside delivery service will be provided to floors above or below the level accessible to the delivery vehicle ONLY when an elevator is available.  The door width must accommodate width of the package.  If you have any steps, the driver is not obligated to move the delivery over the steps but MAY assist you if you ask kindly.   This service does not include unpacking or removal of shipping material.'	


questionTruckShippingInfo=''' 
All truck shipments are for curbside delivery. Deliveries are typically made between 8:00 AM and 5:00 PM Monday through Friday.   
Some trucking companies will give a 4-hour window for delivery. Drivers are not required to lower the item to the ground, 
remove the item from the truck, take product inside a house, or dispose of any pallets or packaging material. The customer is responsible for getting their product to its desired location. Your item is likely to be heavy and large; therefore, we recommend that you have someone with you to help you unload the item and carry it inside. If you desire the trucker to assist in unloading (liftgate service) or bringing the shipment inside a first floor (inside delivery), an extra charge will be required to the trucking company and you should contact them prior to delivery to arrange the extra service.
'''
saveAccountHelp=''' 
Enables you to log into Kettler USA at a later date, review your orders, and your billing and shipping information will be retained. 
'''
	
	
autoRegisterWarrantyHelp='''
This feature will register your item(s) purchased for warranty.  If you agree to register your warranty, you do not need to retain any purchase receipt.
'''

//app.error.email.to.addresses=["dondenoncourt@gmail.com", "MAMATO@kettlerusa.com"]
app.error.email.to.addresses=["dondenoncourt@gmail.com"]
warranty.error.email.to.addresses=["dcrocker.usa@gmail.com", "dcrocker@handhtech.com"]
app.error.email.from.address="weborders@kettlerstore.com"

grails {
   mail {
/*
	     host = "smtp.gmail.com"
	     port = 465
	     username = "dondenoncourt@gmail.com"
	     password = "vo2max"
	     props = ["mail.smtp.auth":"true", 					   
	              "mail.smtp.socketFactory.port":"465",
	              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
	              "mail.smtp.socketFactory.fallback":"false"]
*/
		//host = "172.20.1.202"//"mail.kettlerusa.com"
		host = "mail.kettlerusa.com"
		port = 25
	} 
}
grails.mail.default.from="orders@kettlerusa.com"
