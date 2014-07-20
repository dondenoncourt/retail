package creditCard;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.CharacterFieldDescription;
import com.ibm.as400.access.RecordFormat;

public class KeyDataQueueRecordFormat {

	public RecordFormat setFormat(AS400 conn){
		RecordFormat keyFormat = new RecordFormat();
		
		CharacterFieldDescription keyz = new CharacterFieldDescription(
        		new AS400Text(1,conn), "KEYZ");

		keyFormat.addFieldDescription(keyz);
		
		return keyFormat;
	}

}
