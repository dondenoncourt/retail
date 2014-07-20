package com.kettler.service.retail

import java.text.SimpleDateFormat
import java.util.Calendar

import com.fedex.track.stub.CompletedTrackDetail
import com.fedex.track.stub.Notification
import com.fedex.track.stub.NotificationSeverityType
import com.fedex.track.stub.TrackDetail
import com.fedex.track.stub.TrackEvent
import com.fedex.track.stub.TrackReply
import com.fedex.track.stub.TrackStatusDetail
import com.kettler.controller.retail.AddressesCommand
import com.kettler.controller.retail.PayShipCommand
import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.fedexTracking.Activity
import com.kettler.fedexTracking.TrackResponse
import com.kettler.service.fedEx.FedExTrackService

class FedexService {
	def fedExRateService
	def fedExTrackSerivce
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
	SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy")
	SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss a")

	public BigDecimal getShippingCost(Cart cart, AddressesCommand addr, PayShipCommand payShip, boolean checkAddr) {
		cart.shippingCost = 0g
		BigDecimal shipCost
		def shipToState = addr.shipToSameAsBillTo?addr.billingState:addr.shippingState
		if (cart.upsServiceCode == Cart.LTL   ||
			(cart.totalWithoutGiftCard(shipToState) >= 40g && !['HI','AK'].find{it == shipToState} &&
			 payShip.upsServiceCode == Cart.GROUNDFEDEX)
			) {
			return 0g
		}
		ConsumerShipTo recipient = new ConsumerShipTo()
		if (addr.shipToSameAsBillTo){
			recipient.addr1 = addr.billingAddress1?:""
			recipient.addr2 = addr.billingAddress2?:""
			recipient.state = addr.billingState?:""
			recipient.zipCode = addr.billingZip?:""
			recipient.city = addr.billingCity?:""
			recipient.name = addr.billingName?:""
		} else {
			recipient.addr1 = addr.shippingAddress1?:""
			recipient.addr2 = addr.shippingAddress2?:""
			recipient.state = addr.shippingState?:""
			recipient.zipCode = addr.shippingZip?:""
			recipient.city = addr.shippingCity?:""
			recipient.name = addr.shippingName?:""
		}
		if (recipient.addr1.toUpperCase().equals("IGNORE")){
			recipient.addr1 = ""
		}
		if (recipient.city.toUpperCase().equals("IGNORE")){
			recipient.city = ""
		}
		println "cart = ${cart.dump()}"
		println "recipient = ${recipient.dump()}"
		return fedExRateService.fedexRate(cart, recipient)

	}
	
	
	public BigDecimal getShippingCost(Cart cart) {
		cart.shippingCost = 0g
		BigDecimal shipCost
		def address = cart.consumerShipTo?:cart.consumerBillTo
		def shipToState = address.state
		if (cart.upsServiceCode == Cart.LTL   ||
			(cart.totalWithoutGiftCard(shipToState) >= 40g && !['HI','AK'].find{it == shipToState} &&
			 cart.upsServiceCode == Cart.GROUNDFEDEX)
			) {
			return 0g
		}
		ConsumerShipTo recipient = new ConsumerShipTo()
		recipient.addr1 = address.addr1?:""
		recipient.addr2 = address.addr2?:""
		recipient.state = address.state?:""
		recipient.zipCode = address.zipCode?:""
		recipient.city = address.city?:""
		recipient.name = address.name?:""
		if (recipient.addr1.toUpperCase().equals("IGNORE")){
			recipient.addr1 = ""
		}
		if (recipient.city.toUpperCase().equals("IGNORE")){
			recipient.city = ""
		}
		return fedExRateService.fedexRate(cart, recipient)

	}
	
	public TrackResponse getTrackingResponse(String trackingNumber){
		def trackResponse = new TrackResponse()
		println ("fedx tack = ${trackingNumber}")
		fedExTrackSerivce = new FedExTrackService()
		TrackReply trackReply =  fedExTrackSerivce.fedexTrack(trackingNumber)
		
		getDetails(trackReply.getCompletedTrackDetails(), trackResponse)
		
		return trackResponse
	}

	private void getDetails(CompletedTrackDetail[] ctd, TrackResponse tr){
		for (int i=0; i< ctd.length; i++) { // package detail information
			boolean cont=true;
			if(ctd[i].getNotifications()!=null){
				cont=isValid(ctd[i].getNotifications(), tr);
			}
			if(cont){
				setTrackDetail(ctd[i].getTrackDetails(), tr);
			}
		}

	}
	private void setTrackDetail(TrackDetail[] td, TrackResponse tr){
		for (int i=0; i< td.length; i++) {
			boolean cont=true;
			if(td[i].getNotification()!=null){
				cont=isValid(td[i].getNotification(), tr);
			}
			if(cont){
				tr.trackingNumber = td[i].getTrackingNumber()
//				print("Carrier code", td[i].getCarrierCode());
				if(td[i].getService()!=null){
					if(td[i].getService().getType()!=null &&
							td[i].getService().getDescription()!=null){
						//tr.service = td[i].getService().getType()
						tr.service = td[i].getService().getDescription()
					}
				}
				if(td[i].getStatusDetail()!=null){
					System.out.println("--Status Details--");
					TrackStatusDetail tsd = td[i].getStatusDetail()
					if (tsd.location.streetLines.length == 2){
						tr.addressLine = tsd.location.streetLines[0]
						tr.addressLine2 = tsd.location.streetLines[1]
					} else {
						tr.addressLine = tsd.location.streetLines[0]
					}
					tr.city = tsd.location.city
					tr.state = tsd.location.stateOrProvinceCode
					tr.zip = tsd.location.postalCode
					tr.countryCode = tsd.location.countryName
	
					System.out.println("--Status Details--");
				}
//				if(td[i].getDestinationAddress()!=null){
//					System.out.println("--Destination Location--");
//					printDestinationInformation(td[i]);
//					System.out.println("--Destination Location--");
//				}
//				if(td[i].getActualDeliveryAddress()!=null){
//					System.out.println("--Delivery Address--");
//					print(td[i].getActualDeliveryAddress());
//					System.out.println("--Delivery Address--");
//				}
//				if(td[i].getActualDeliveryTimestamp()!=null){
//					System.out.println("Delivery Date");
//					print(td[i].getActualDeliveryTimestamp());
//				}
//				if(td[i].getAppointmentDeliveryTimestamp()!=null){
//					System.out.println("Appointment Date");
//					print(td[i].getAppointmentDeliveryTimestamp());
//				}
//				if(td[i].getCommitmentTimestamp()!=null){
//					System.out.println("Commitment Date");
//					print(td[i].getCommitmentTimestamp());
//				}
//				if(td[i].getDeliveryAttempts().shortValue()>0){
//					System.out.println("--Delivery Information--");
//					printDeliveryInformation(td[i]);
//					System.out.println("--Delivery Information--");
//				}
//				if(td[i].getCustomerExceptionRequests()!=null){
//					System.out.println("--Customer Exception Information--");
//					printCustomerExceptionRequests(td[i].getCustomerExceptionRequests());
//					System.out.println("--Customer Exception Information--");
//				}
//				if(td[i].getCharges()!=null){
//					System.out.println("--Charges--");
//					printCharges(td[i].getCharges());
//					System.out.println("--Charges--");
//				}
				if(td[i].getEvents()!=null){
					System.out.println("--Tracking Events--");
					printTrackEvents(td[i], td[i].getEvents(), tr);
					System.out.println("--Tracking Events--");
				}
				System.out.println("--Track Details--");
				System.out.println();
			}
		}
	}

	private void printTrackEvents(TrackDetail td, TrackEvent[] events, TrackResponse tr){
		if(events!=null){
			def activityAr = new Activity[events.length]
			for(int i=0; i<events.length;i++){
				def activity = new Activity()
				TrackEvent event=events[i];
				if (event.address.streetLines){
					if (event.address.streetLines.length == 2){
						activity.addressLine = event.address.streetLines[0]
						activity.addressLine2 = event.address.streetLines[1]
					} else {
						activity.addressLine = event.address.streetLines[0]
					}
				}
			    activity.city = event.address.city
			    activity.state = event.address.stateOrProvinceCode
			    activity.zip = event.address.postalCode
			    activity.countryCode = event.address.countryCode

//			    activity.rescheduledDeliveryDate
			    activity.code = event.eventType
			    activity.description = event.eventDescription
				if (activity.code == "DL")
			    	activity.signedByName = td.deliverySignatureName
			    activity.statusTypeCode = event.statusExceptionCode
			    activity.statusTypeDescription = event.eventDescription
			    activity.statusCode = event.eventType
			    activity.activityDate = sdf2.format(event.timestamp.getTime())
			    activity.activityTime = sdf3.format(event.timestamp.getTime())
				activityAr[i] = activity
				println ("activity = ${activity.dump()}")
			}
			tr.activities = activityAr
		}
	}
	private void printTime(Calendar calendar){
		if(calendar!=null){
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int year = calendar.get(Calendar.YEAR);
			String date = new String(year + "-" + month + "-" + day);
			print("Date", date);
			printDOW(calendar);
		}
	}
	private boolean isResponseOk(NotificationSeverityType notificationSeverityType) {
		if (notificationSeverityType == null) {
			return false;
		}
		if (notificationSeverityType.equals(NotificationSeverityType.WARNING) ||
			notificationSeverityType.equals(NotificationSeverityType.NOTE)    ||
			notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
			return true;
		}
		 return false;
	}

	private static boolean isValid(Object n, TrackResponse tr) {
		boolean cont=true;
		tr.success = true
		if(n!=null){
			Notification[] notifications=null;
			Notification notification=null;
			if(n instanceof Notification[]){
				notifications=(Notification[])n;
				for (int i=0; i < notifications.length; i++){
					if(!success(notifications[i])){
						cont=false;
						tr.success = false
						tr.errorDescription = notification[i].getMessage()
					}
				}
			}else if(n instanceof Notification){
				notification=(Notification)n;
				if(!success(notification)){
					cont=false;
					tr.success = false
					tr.errorDescription = notification.getMessage()
				}
			}

		}
		return cont;
	}
	
	private static boolean success(Notification notification){
		Boolean cont = true;
		if(notification!=null){
			if(notification.getSeverity()==NotificationSeverityType.FAILURE ||
					notification.getSeverity()==NotificationSeverityType.ERROR){
				cont=false;
			}
		}
		
		return cont;
	}

	
			
}
