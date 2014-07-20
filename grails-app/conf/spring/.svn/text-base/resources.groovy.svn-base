import com.ibm.as400.access.AS400 
import com.ibm.as400.access.AS400ConnectionPool
import com.ibm.as400.access.ConnectionPoolException 
import com.kettler.as400.AS400ConnPoolFactory
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

// Place your Spring DSL code here
beans = {
		if (CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') {
			as400ConnPool(AS400ConnPoolFactory) {bean ->
				ip = CH.config.iseriesIPAddress
				userId = CH.config.iseriesUserId
				password = CH.config.iseriesPwd
			}
		}
}
