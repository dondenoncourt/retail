package creditCard;

import java.math.BigDecimal;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.CharacterFieldDescription;
import com.ibm.as400.access.KeyedDataQueue;
import com.ibm.as400.access.KeyedDataQueueEntry;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;

import creditCard.CreditCardPreAuth;


public class WriteDataQueue {
	private KeyedDataQueue dq;
	private AS400 conn;
	private RecordFormat dataFormat;
	private RecordFormat keyFormat;
	private DataQueueRecordFormat dqrf = new DataQueueRecordFormat();
	private KeyDataQueueRecordFormat kdqrf = new KeyDataQueueRecordFormat();
	private Record dqRec;
	private Record keyRec;
	
	public WriteDataQueue(AS400 conn) {
		QSYSObjectPathName name = new QSYSObjectPathName("RITA", "RITAREQ",
				"DTAQ");
		this.dq = new KeyedDataQueue(conn, name.getPath());
		this.conn = conn;

		this.dataFormat = dqrf.setFormat(conn);
		this.dqRec = new Record(dataFormat);

        this.keyFormat = kdqrf.setFormat(conn);
        this.keyRec = new Record(keyFormat);

	}

	public boolean write(String readFromDatqQueueName, CreditCardPreAuth ccPreAuth,
			String clientUserId, String clientUserPassword) {
		boolean error = false;
		try {
//			System.out.println("user id = " + clientUserId);
//			System.out.println("pwd = " + clientUserPassword);
			 
			setFields(readFromDatqQueueName, ccPreAuth, clientUserId, clientUserPassword);
			
		
			byte [] info = dqRec.getContents();

			//create key record
			keyRec.setField("KEYZ", " ");
			byte[] keyinfo = keyRec.getFieldAsBytes("KEYZ");
			
			dq.write(keyinfo, info);

			
//			while (true) {
//				KeyedDataQueueEntry dqe = dq.read(keyinfo);
//				if (dqe!= null){
//					System.out.println("read entry"+dqe.getKey());
//				} else
//					break;
//			}


		} catch (Exception e) {
			System.out.println("Write to Rita Data Queue operation failed:");
			System.out.println(e);
			error = true;
		}
		return error;

	}
	
	private void setFields(String readFromDatqQueueName, CreditCardPreAuth ccPreAuth,
			String clientUserId, String clientUserPassword){
		String address1 = "";
		if (ccPreAuth.getAddress1().trim().length() > 20){
			address1 = ccPreAuth.getAddress1().substring(0, 20);
		} else{
			address1 = ccPreAuth.getAddress1().trim();
		}
		if (ccPreAuth.getExpireYear().trim().length() == 4){
			ccPreAuth.setExpireYear(ccPreAuth.getExpireYear().trim().substring(2, 4));
		}
		System.out.println("type = " + ccPreAuth.getTypeText());

		dqRec.setField("RRESNM", readFromDatqQueueName);
		dqRec.setField("RRESLB", Constants.DATAQUEUELIBRARY);
		dqRec.setField("RFUNCT", "PAYMENT");
		dqRec.setField("RPAYMT", "CREDIT");
		dqRec.setField("RCOMND", ccPreAuth.getTypeText());
		dqRec.setField("RINVOI", ccPreAuth.getWebOrderNumber());
		dqRec.setField("RCADD1", address1);
		dqRec.setField("RCVDID", ccPreAuth.getSecurityCode());
		dqRec.setField("RCZIPC", ccPreAuth.getZip10());
		dqRec.setField("RACCTN", ccPreAuth.getCreditCard());
		dqRec.setField("REXPMM", ccPreAuth.getExpireMonth());
		dqRec.setField("REXPYY", ccPreAuth.getExpireYear());
		dqRec.setField("RUSRID", clientUserId);
		dqRec.setField("RUSRPW", clientUserPassword);
		dqRec.setField("RCLIEN", "100010001");
		dqRec.setField("RTRAMT", ccPreAuth.getWebOrderAmount());
		
	}
}
