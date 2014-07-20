package creditCard;

import java.io.UnsupportedEncodingException;

import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.DataQueue;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.ConnectionPoolException;
import com.ibm.as400.access.Record;

import com.kettler.service.retail.CreditCardReturn;

public class ProcessResponse {
	
	/*
	 * if response code = 5; valid credit card
	 */
	public CreditCardReturn processResponse(Record dqRecord){
		CreditCardReturn ccRtn = new CreditCardReturn();
		try {
			System.out.println("cc process response return value  = " + (String)dqRecord.getField("RRESCD"));
			System.out.println("routing ------  = " + (String)dqRecord.getField("RRESTX"));
      // catured
			if (((String)dqRecord.getField("RRESCD")).trim().equals("4")){
				ccRtn.setValid(true);
				ccRtn.setCodeOrRouting((String)dqRecord.getField("RROUTD"));
      // approved
      } else if (((String)dqRecord.getField("RRESCD")).trim().equals("5")){
				ccRtn.setValid(true);
				ccRtn.setCodeOrRouting((String)dqRecord.getField("RROUTD"));
      // declined
			} else if (((String)dqRecord.getField("RRESCD")).trim().equals("6")){
				ccRtn.setValid(false);
				ccRtn.setCodeOrRouting("Declined - Invalid address, expiration date, or security code");
			} else {
				ccRtn.setValid(false);
				ccRtn.setCodeOrRouting((String)dqRecord.getField("RRESTX"));
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			ccRtn.setValid(false);
			ccRtn.setCodeOrRouting("Error validating credit card.  Please contact customer support.");
		}
		return ccRtn;
	}

}
