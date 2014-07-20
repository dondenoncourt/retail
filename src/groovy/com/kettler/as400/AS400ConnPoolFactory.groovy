package com.kettler.as400

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean

import com.ibm.as400.access.AS400
import com.ibm.as400.access.AS400ConnectionPool

class AS400ConnPoolFactory implements FactoryBean {
	String ip
	String userId
	String password

	public Object getObject() throws Exception {
		println "AS400ConnPoolFactory.getObject with $ip, $userId, $password called from Grails/Spring bean DSL"
		try {
			AS400ConnectionPool as400ConnPool = new AS400ConnectionPool()
			as400ConnPool.setMaxConnections(128)
			as400ConnPool.fill(ip, userId, password, AS400.COMMAND, 5)
			return as400ConnPool
		} catch (Exception e) {
			println("AS400ConnPoolFactory.getObject error:"+e.toString())
			//throw e
		}
	}
    public Class getObjectType() {AS400ConnectionPool.class}
	public boolean isSingleton() {true}
}
