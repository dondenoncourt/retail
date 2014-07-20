import java.io.Serializable;

import com.kettler.domain.item.share.ItemMasterExt

import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil

class ContractController {
    def beforeInterceptor = {
        log.debug "action: $actionName params: $params flash: $flash"
    }
    
    def index = {
    	redirect action:'contract'
    }
	def contract = {
    	def	lifeStyleImages = []
    	File imageFiles = ApplicationHolder.application.parentContext.getResource("images/contract").file
       	imageFiles.eachFile {file ->
			if (file.name.startsWith('life-style') && !file.name.find(/FULL/)) {
				lifeStyleImages << file.name
			}
		}
		params.division='contract'
		[lifeStyleImages:lifeStyleImages, params:params]
	}
    def requestForQuote = {
    	if (params.id) {
       		def item = ItemMasterExt.get(params.id)
            if (!item) {
                flash.message = "Item with id ${params.id} not found for quote"
                redirect action:'contract'
                return
            }
    		RfqCommand cmd = new RfqCommand(itemNo:item.itemNo, desc:item.desc)
    		[cmd:cmd]
    	}
    } 
    def rfq = {RfqCommand cmd ->
	    if (cmd.hasErrors()) {
	        render (view:'requestForQuote', model:[cmd:cmd])
	        return
	    }
	    def msg = "Request for quote from:\r\r"+
	                  "$cmd.businessName Type: $cmd.businessType\r"+
	                  "$cmd.firstName $cmd.lastName\r"+
	                  "$cmd.addr1\r$cmd.addr2\r$cmd.city, $cmd.state, $cmd.zip\r" +
	                  "phone: $cmd.phone\r "+
	                  "fax: $cmd.fax\r "+
	                  "email: $cmd.email\r"+
	                  "${cmd.itemNo?:''} ${cmd.desc?:''} \r"+
	                  "comment: $cmd.comment\r\r"
        try {
			sendMail {
				to GrailsUtil.getEnvironment() == GrailsApplication.ENV_PRODUCTION?'contract@kettlerusa.com':'dondenoncourt@gmail.com'
				subject "KETTLER Contract Request for Quote."
				body msg
				from "webmaster@kettlerusa.com"
			}
        } catch (Exception e) {
            log.error "Problem emailing $e.message", e
        }

    	flash.message = message(code:'localized.quote.submitted', args:[cmd.firstName]) //"$cmd.firstName, your request for a quote has been submitted."
    	redirect action:'contract'
    }
    def contacts = {}
    def dealers = {}
    def installPics = {
    	params.max = 6
    	params.offset = params.offset?:'0'  
    	def	installPics = []
    	def count = 0
    	File imageFiles = ApplicationHolder.application.parentContext.getResource("images/contract").file
       	imageFiles.eachFile {file ->
			if (file.name.find(/.jpg/) && !file.name.startsWith('life-style')) {
				if (installPics.size() < params.max && count >= params.offset.toInteger()) {
					installPics << file.name
				}
				count++
			}
		}
    	[installPics:installPics, count:count]
    }
    

}
class RfqCommand implements Serializable {
	String businessName 
	String businessType 
	String firstName 
	String lastName 
	String addr1 
	String addr2 
	String city
	String state
	String zip
	String phone
	String fax
	String email
	String comment
	String itemNo
	String desc

    static constraints = {
		businessName blank:false 
		firstName blank:false 
		lastName blank:false 
    	addr1 blank:false 
    	city blank:false 
    	state blank:false 
    	zip blank:false 
    	phone blank:false 
    	email email:true, blank:false 
    	comment blank:false
    }
}
