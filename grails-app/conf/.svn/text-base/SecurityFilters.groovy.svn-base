import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class SecurityFilters {
	def httpRequest

    def filters = {
        all(controller:'*', action:'*') {
            before = {
            	httpRequest = request 
//println "filters getHeader:"+request.getHeader('referer')             	
//				if (actionName != 'error' && actionName != 'login' && actionName != 'requestLogin' && actionName != 'loginRedirect' 
//					&& actionName != 'createCreditMemo' && actionName != 'total'
//					&& !params.pdf) {
//	                accessControl {
//	            		role("Customer") | role("Rep") | role("Kettler") | role("Cust Admin") | role("Rep Admin") | role("Super Admin") 
//	            	}
//            	}
            }
            after = {}
            afterView = {}
        }
        
//        consumerBillTo(controller:'consumerBillTo',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } } 
//        	after = {}; afterView = {};
//        }
//        dealerInventory(controller:'dealerInventory',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } } 
//        	after = {}; afterView = {};
//        }
//        dealer(controller:'dealer',action:'*') {
//        	before = { 
//        		if (!['webDealers', 'renderLogo'].find{actionName == it}) {
//        			accessControl { role("Kettler") | role("Super Admin") } 
//        		}
//        	} 
//        	after = {}; afterView = {};
//        }
//        dealerLocation(controller:'dealerLocation',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } } 
//        	after = {}; afterView = {};
//        }
//        inventoryMaintenance(controller:'inventoryMaintenance',action:'*') {
//        	before = {
//        		if (!(request.getHeader('referer') ==~ /.*kettler.*/)) {	
//        			accessControl { role("Kettler") | role("Super Admin") }
//        		}
//        	} 
//        	after = {}; afterView = {};
//        }
//        consumer(controller:'consumer',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } }
//        	after = {}; afterView = {};
//        }
//        itemMasterExt(controller:'itemMasterExt',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } } 
//        	after = {}; afterView = {};
//        }
//        coupon(controller:'coupon',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } } 
//        	after = {}; afterView = {};
//        }
//        consumerShipTo(controller:'consumerShipTo',action:'*') {
//        	before = { accessControl { role("Kettler") | role("Super Admin") } } 
//        	after = {}; afterView = {};
//        }

    }
//    def onNotAuthenticated(subject, d) {
//		log.debug "onNotAuthenticated onNotAuthenticated onNotAuthenticated request URI: ${httpRequest.forwardURI}"
//		httpRequest.request.session.preLoginURI = httpRequest.forwardURI.replaceAll(/^\/\w*/, '')
//		log.debug httpRequest.request.session.preLoginURI
//		if (httpRequest.queryString?.size()) {
//			httpRequest.request.session.preLoginURI += '?'+httpRequest.queryString
//		}
//        d.redirect(url:"${CH.config.grails.serverURL}/login/loginRedirect")
//    }

}
