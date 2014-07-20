package com.kettler.service.retail

import com.kettler.domain.orderentry.share.CreditCardClient;
import com.kettler.domain.orderentry.share.Cart;

import com.kettler.controller.retail.AddressesCommand
import com.kettler.controller.retail.PayShipCommand

import com.ibm.as400.access.AS400 
import com.ibm.as400.access.AS400SecurityException 
import com.ibm.as400.access.CommandCall 
import com.ibm.as400.access.ConnectionPoolException 
import com.ibm.as400.access.DataQueue 
import com.ibm.as400.access.ErrorCompletingRequestException 
import com.ibm.as400.access.Record 
import com.kettler.domain.orderentry.share.CommercialBin 
import creditCard.Constants 
import creditCard.CreditCardInformation 
import creditCard.CreditCardPreAuth 
import creditCard.ProcessResponse 
import creditCard.ReadDataQueue 
import creditCard.WriteDataQueue 
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import com.kettler.service.retail.CreditCardReturn;


class PreAuthorizationService implements Serializable {
    def as400ConnPool
    def non_grails_config

	static transactional = true

	CreditCardReturn preAuth(Cart cart){
		String routingNumber = null;
		CreditCardPreAuth ccPreAuth = new CreditCardPreAuth();
		ccPreAuth.webOrderNumber = cart.orderNo
		ccPreAuth.webOrderAmount = cart.total()
		ccPreAuth.address1 = cart.consumerBillTo.addr1
		ccPreAuth.zip10 = cart.consumerBillTo.zipCode
		ccPreAuth.creditCard = cart.consumerBillTo.cardNo.trim()
		ccPreAuth.expireMonth = cart.consumerBillTo.expMonth
		ccPreAuth.expireYear = cart.consumerBillTo.expYear
		ccPreAuth.securityCode = cart.consumerBillTo.zeroPadCCID() 
		ccPreAuth.typeText = "PRE_AUTH"
		if (non_grails_config) if (!non_grails_config) log.debug "ccPreAuth: $ccPreAuth"
		return preAuthInvocation(ccPreAuth)
    }
	CreditCardReturn preAuth(Cart cart, AddressesCommand addr, PayShipCommand payShip){
		String routingNumber = null;
		CreditCardPreAuth ccPreAuth = new CreditCardPreAuth();
		ccPreAuth.webOrderNumber = cart.orderNo
		ccPreAuth.webOrderAmount = cart.total()
		ccPreAuth.address1 = addr.billingAddress1
		ccPreAuth.zip10 = addr.billingZip
		ccPreAuth.creditCard = payShip.creditCard.trim()
		ccPreAuth.expireMonth = payShip.month
		ccPreAuth.expireYear = payShip.year
		ccPreAuth.securityCode = payShip.zeroPadCCID()
		ccPreAuth.typeText = "PRE_AUTH"
		if (!non_grails_config) log.debug "ccPreAuth: $ccPreAuth"
		return preAuthInvocation(ccPreAuth)
    }
    CreditCardReturn preAuthInvocation(CreditCardPreAuth ccPreAuth) {
    	CreditCardReturn ccRtn = new CreditCardReturn()
		AS400 conn = getConnection()
		println("connection = " + conn)
		CreditCardInformation cci = new CreditCardInformation();
		boolean error = false;
		if (!conn) { // for testing purposes...
			if (ccPreAuth.getCreditCard().equals("4111111111111111")){
				cci.setCode("5");
				cci.setValid(true);
				cci.setText("forced valid for testing purposes");
				if (!non_grails_config) log.warn "forced valid for testing purposes."
				ccRtn.valid = true;
				ccRtn.codeOrRouting = "99999"
			} else {
				cci.setCode("99");
				cci.setValid(false);
				cci.setText("Do not have connection to iSeries.");
				if (!non_grails_config) log.warn "Do not have connection to iSeries."
				ccRtn.valid = false;
				ccRtn.codeOrRouting = "99999"
				return ccRtn;
			}
		}
		String dqName = createDataQ(conn);
		
		if (error){
			cci.setCode("x");
			cci.setValid(false);
			cci.setText("internal error : Cannot create data queue.");
			if (!non_grails_config) log.error "Cannot create data queue."
			ccRtn.valid = false;
			ccRtn.codeOrRouting = "Error validating credit card.  Please contact customer support."
			return ccRtn;
		} else {
			ccPreAuth.setCardType(getCreditCardType(ccPreAuth.getCreditCard()));
			ccPreAuth.setCommercialCard(isCommercialCard(ccPreAuth.getCreditCard(), ccPreAuth.getCardType()));
			//submit credit card for validation
			WriteDataQueue dqW = new WriteDataQueue(conn);
            String userPasswordForInternalPaywareUser = ''
            if (non_grails_config) {
                userPasswordForInternalPaywareUser = getConfig().userPasswordForInternalPaywareUser
            } else {
                userPasswordForInternalPaywareUser = CreditCardClient.findByCompCodeAndUserForPayware('01', getConfig().paywareClientUserId).userPasswordForInternalPaywareUser
            }
			error = dqW.write(dqName, ccPreAuth, getConfig().paywareClientUserId, userPasswordForInternalPaywareUser, getConfig().paywareTerminal)
		
			//receive information from credit card vendor.
			if (error){
				cci.setCode("x");
				cci.setValid(false);
				cci.setText("internal error : Error writing to Rita data queue.");
				if (!non_grails_config) log.error "writing to Rita data queue."
				ccRtn.valid = false;
				ccRtn.codeOrRouting = "Error validating credit card.  Please contact customer support."
			} else {
				ReadDataQueue dqR = new ReadDataQueue(conn, dqName);
				Record dqRec = dqR.read();
				if (dqRec == null){
					cci.setCode("x");
					cci.setValid(false);
					cci.setText("internal error : Error reading from response data queue.");
					if (!non_grails_config) log.error "Error reading from response data queue."
					ccRtn.valid = false;
					ccRtn.codeOrRouting = "Error validating credit card.  Please contact customer support."
				} else {
					ProcessResponse pr = new ProcessResponse();
					
					ccRtn = pr.processResponse(dqRec);
					if (!non_grails_config) log.debug "info sent back = " + ccRtn.dump()
					println("dareq = " + dqRec.toString())
					println("cc return = " + ccRtn.dump())
				}
			}
			deleteDataQ(conn, dqName);
		}
		returnConnection(conn)
		if (!non_grails_config) log.debug ("ccRtn = " + ccRtn)
		return ccRtn;
	}

	public CreditCardReturn sale(Cart cart){
		CreditCardReturn ccRtn = new CreditCardReturn()
		String routingNumber = null;
		CreditCardPreAuth ccPreAuth = new CreditCardPreAuth();
		ccPreAuth.webOrderNumber = cart.orderNo
		ccPreAuth.webOrderAmount = cart.total()
		ccPreAuth.address1 = cart.consumerBillTo.addr1
		ccPreAuth.zip10 = cart.consumerBillTo.zipCode
		ccPreAuth.creditCard = cart.consumerBillTo.cardNo.trim()
		ccPreAuth.expireMonth = cart.consumerBillTo.expMonth
		ccPreAuth.expireYear = cart.consumerBillTo.expYear
		//ccPreAuth.securityCode = cart.consumerBillTo.ccid
		ccPreAuth.securityCode = cart.consumerBillTo.zeroPadCCID()
		ccPreAuth.typeText = "SALE"

		AS400 conn = getConnection()
		println("connection = " + conn)
		CreditCardInformation cci = new CreditCardInformation();
		boolean error = false;
		if (!conn) { // for testing purposes...
			if (ccPreAuth.getCreditCard().equals("4111111111111111")){
				cci.setCode("5");
				cci.setValid(true);
				cci.setText("forced valid for testing purposes");
				if (!non_grails_config) log.warn "forced valid for testing purposes."
//				return "99999";
				ccRtn.valid = true;
				ccRtn.codeOrRouting = "99999"
				return ccRtn;
			} else {
				cci.setCode("99");
				cci.setValid(false);
				cci.setText("Do not have connection to iSeries.");
				if (!non_grails_config) log.warn "Do not have connection to iSeries."
//				return "99999";
				ccRtn.valid = false;
				ccRtn.codeOrRouting = "99999"
				return ccRtn;
			}
		}
		String dqName = createDataQ(conn);
		
		if (error){
			cci.setCode("x");
			cci.setValid(false);
			cci.setText("internal error : Cannot create data queue.");
			if (!non_grails_config) log.error "Cannot create data queue."
			ccRtn.valid = false;
			ccRtn.codeOrRouting = "Error validating credit card.  Please contact customer support."
			return ccRtn;
		} else {
			ccPreAuth.setCardType(getCreditCardType(ccPreAuth.getCreditCard()));
			ccPreAuth.setCommercialCard(isCommercialCard(ccPreAuth.getCreditCard(), ccPreAuth.getCardType()));
			//submit credit card for validation
			WriteDataQueue dqW = new WriteDataQueue(conn);
		    def creditCardClient = CreditCardClient.findByCompCodeAndUserForPayware('01', getConfig().paywareClientUserId)
			error = dqW.write(dqName, ccPreAuth, getConfig().paywareClientUserId,
                              creditCardClient.userPasswordForInternalPaywareUser, creditCardClient.paywareTerminal );
		
			//receive information from credit card vendor.
			if (error){
				cci.setCode("x");
				cci.setValid(false);
				cci.setText("internal error : Error writing to Rita data queue.");
				if (!non_grails_config) log.error "writing to Rita data queue."
				ccRtn.valid = false;
				ccRtn.codeOrRouting = "Error validating credit card.  Please contact customer support."
			} else {
				ReadDataQueue dqR = new ReadDataQueue(conn, dqName);
				Record dqRec = dqR.read();
				if (dqRec == null){
					cci.setCode("x");
					cci.setValid(false);
					cci.setText("internal error : Error reading from response data queue.");
					if (!non_grails_config) log.error "Error reading from response data queue."
					ccRtn.valid = false;
					ccRtn.codeOrRouting = "Error validating credit card.  Please contact customer support."
				} else {
					ProcessResponse pr = new ProcessResponse();
					
					ccRtn = pr.processResponse(dqRec);
					if (!non_grails_config) log.debug "info sent back = " + ccRtn.dump()
					println("dareq = " + dqRec.toString())
					println("cc return = " + ccRtn.dump())
				}
			}
			deleteDataQ(conn, dqName);
		}
		returnConnection(conn)
		if (!non_grails_config) log.debug ("return = " + routingNumber)
		return ccRtn;
	}

	
	private String createDataQ(AS400 conn){
		String dqName = "";
		dqName = getDataQueueName();
		String createDQ = "CRTDTAQ DTAQ(" + Constants.DATAQUEUELIBRARY + "/" +
				dqName.trim() + ") MAXLEN(" + Constants.DATAQUEUEMAXLENGTH + ")";
		CommandCall cmd1 = new CommandCall(conn, createDQ);
		try{
			cmd1.run();
			System.out.println("data queue name created = "  + dqName);
		}catch (AS400SecurityException se){
			System.out.println(se);
			dqName = null;
		}catch (InterruptedException ie) {
			System.out.println(ie);
			dqName = null;
		}catch (ErrorCompletingRequestException ecre){
			System.out.println(ecre);
			dqName = null;
		}catch (IOException ioe){
			System.out.println(ioe);
			dqName = null;
		}
		
		return dqName;
	}

	private boolean deleteDataQ(AS400 conn, String dqName){
		boolean error = false;
		String deleteDQ = "DLTDTAQ DTAQ(" + Constants.DATAQUEUELIBRARY + "/" +
				 dqName.trim() + ")";
		CommandCall cmd1 = new CommandCall(conn, deleteDQ);
		try {
			cmd1.run();
		} catch (e) {
			println e
		}
		return error;
	}

	
	private String getDataQueueName(){
		Date dt = new Date();
		Long time = dt.getTime();
		String dqName = "WEB" + Long.toString(time).substring(0, 7);
		return dqName;
	}
	
	private String getCreditCardType(String ccNum){
		String cardType = "";
		
		if (ccNum.substring(0, 1).equals("4")){
			if (!ccNum.substring(0, 4).equals("4903") &&
					!ccNum.substring(0, 4).equals("4905") &&
					!ccNum.substring(0, 4).equals("4911") &&
					!ccNum.substring(0, 4).equals("4936") &&
					!ccNum.substring(0, 4).equals("4026") &&
					!ccNum.substring(0, 4).equals("4508") &&
					!ccNum.substring(0, 4).equals("4844") &&
					!ccNum.substring(0, 4).equals("4913") &&
					!ccNum.substring(0, 4).equals("4917") &&
					!ccNum.substring(0, 6).equals("417500")){
				cardType = Constants.VISA;
			}
		} else if (ccNum.substring(0, 2).equals("51") ||
				ccNum.substring(0, 2).equals("55")){
			cardType = Constants.MASTERCARD;
		} else if (ccNum.substring(0, 2).equals("34") ||
				ccNum.substring(0, 2).equals("37")){
			cardType = Constants.AMERICANEXPRESS;
		} else if (ccNum.substring(0, 1).equals("6")) {
			if (!ccNum.substring(0, 4).equals("4903")) {
				cardType = Constants.DISCOVERY;
			}
			if ((ccNum.substring(0, 4).compareTo("622126")) >= 0
					&& (ccNum.substring(0, 4).compareTo("622625")) <= 0) {
				cardType = Constants.DISCOVERY;
			}
			if ((ccNum.substring(0, 3).compareTo("644")) >= 0
					&& (ccNum.substring(0, 3).compareTo("649")) <= 0) {
				cardType = Constants.DISCOVERY;
			}
			if (ccNum.substring(0, 2).equals("65")) {
				cardType = Constants.DISCOVERY;
			}
		}
		
		return cardType;
	}


/*
 * select  CM_BI00001 into :CM_BI00001 from comme00002     
		where  PAYME00001 = :cardtype and LOWER00001 = :card6  and  UPPER00001 = ' '  ;                                      
   if sqlstate = goodsql  commercialflag = *on ;                                 
   else   ;                                               
  select  CM_BI00001 into :CM_BI00001 from comme00002   
		where  PAYME00001 = :cardtype and :card6 between lower00001  and UPPER00001   ;                                           
   ENDIF;                                                   
    if sqlstate = goodsql   commercialflag = *on ;                                 
    endif   ;                                                 	
 */
	
	private boolean isCommercialCard(String ccNum, String cardType){
        if (non_grails_config) {
            return false
        }
		if (cardType.equals(Constants.VISA) ||
				cardType.equals(Constants.MASTERCARD)){	
			CommercialBin cb = CommercialBin.findWhere(paymentMedia:cardType, lowerBound:ccNum[0..5], upperBound:' ')
			if (!cb){
				def countArray = CommercialBin.executeQuery("select count(*) from CommercialBin where paymentMedia = ? and ? between lowerBound and upperBound", [cardType, ccNum[0..5]])
				if (countArray[0] == 0)
					return false
				else
					return true
			} else {
				return true
			}
				
		}
	}

	private void getClientInfo(){
		String clientUserId = getConfig().paywareClientUserId
		String clientUserPassword = CreditCardClient.findByCompCodeAndUserForPayware('01', clientUserId).userPasswordForInternalPaywareUser
		if (!non_grails_config) log.debug "user passwrod = ${clientUserPassword}"
	}

	
	private AS400 getConnection(){
println as400ConnPool
println getConfig().dump()
println getConfig().iserisIPAddress
		if (as400ConnPool) {
			try {
				return as400ConnPool.getConnection(getConfig().iseriesIPAddress,
					getConfig().iseriesUserId,
					getConfig().iseriesPwd, AS400.COMMAND)
			} catch (ConnectionPoolException e) {
				if (!non_grails_config) log.error e.toString()
				return null
			}
		}
	}
	
	private void returnConnection(AS400 conn){
		as400ConnPool.returnConnectionToPool(conn);
	}

    private def getConfig() {
        if (non_grails_config) {
            return non_grails_config
        }
        CH.config
    }

}
