package creditCard;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.DataQueueEntry;
import com.ibm.as400.access.DataQueue;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.QSYSObjectPathName;

public class ReadDataQueue {
	private DataQueue dq;
	private AS400 conn;
	private DataQueueRecordFormat dqrf = new DataQueueRecordFormat();
	private Record dqRec;

	public ReadDataQueue(AS400 conn, String dqName) {
		QSYSObjectPathName name = new QSYSObjectPathName(Constants.DATAQUEUELIBRARY, 
				dqName.trim(),
				"DTAQ");
		this.dq = new DataQueue(conn, name.getPath());
		this.conn = conn;
	}

	public Record read() {

		try {
			RecordFormat dataFormat = dqrf.setFormat(conn);
			DataQueueEntry dqData = null;
			dqData = dq.read(20);
			dqRec = dataFormat.getNewRecord(dqData.getData());

		} catch (Exception e) {
			System.out.println("Data Queue operation failed:");
			System.out.println(e);
			dqRec = null;
		}
		return dqRec;
	}
}
