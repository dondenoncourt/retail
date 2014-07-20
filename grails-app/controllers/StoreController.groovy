import java.io.Serializable;

import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.item.share.WebDivision

import com.kettler.util.Image

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import grails.util.GrailsUtil

class StoreController {
    def beforeInterceptor = {
        log.debug "action: $actionName params: $params flash: $flash"
    }
    def afterInterceptor = {model ->
        log.debug "after action: $actionName model: $model params: $params flash: $flash"
    }
    
    def index = {
    	redirect action:'store'
    }
	def financing = {
		
	}
	def store = {
    	def	lifeStyleImages = []
    	File imageFiles = ApplicationHolder.application.parentContext.getResource("images/store").file
       	imageFiles.eachFile {file ->
			if (file.name.startsWith('life-style') && !file.name.find(/FULL/)) {
				lifeStyleImages << file.name
			} 
		}
		params.division='contract'
		[lifeStyleImages:lifeStyleImages, adImages:getAdImages(), 
		 keywords:WebDivision.findByName('patio')?.keywords, params:params]
	}
    def requestForQuote = {
		if (params.id) {
	   		def item = ItemMasterExt.get(params.id)
            if (item) {
                RfqStoreCommand cmd = new RfqStoreCommand(itemNo:item.itemNo, desc:item.desc)
                [cmd:cmd]
            } else {
                flash.message = message(code:'localized.item.no.invalid', args:[params.id]) //"ItemNo: ${params.id} not found"
    	        redirect action:'store'
            }
		}
    }
    def rfq = {RfqStoreCommand cmd ->
	    if (cmd.hasErrors()) {
	        render (view:'requestForQuote', model:[cmd:cmd])
	        return
	    }
	    def msg = "Request for quote from:\r\r"+
	                  "$cmd.name\r$cmd.addr1\r$cmd.addr2\r$cmd.city, $cmd.state, $cmd.zip\r" +
	                  "phone: $cmd.phone\r "
	                  "email: $cmd.email\r\r"
        try {
			sendMail {
				to GrailsUtil.getEnvironment() == GrailsApplication.ENV_PRODUCTION?'contract@kettlerusa.com':'dondenoncourt@gmail.com'
				subject "KETTLER Store Request for Quote."
				body msg
				from "webmaster@kettlerusa.com"
			}
        } catch (Exception e) {
            log.error "Problem emailing $e.message", e
        }
    	flash.message = message(code:'localized.quote.submitted', args:[params.name,]) //"$params.name, your request for a quote has been submitted."
    	redirect action:'store'
    }
    def directions = {[adImages:getAdImages()]}
    def promotions = {
    	def adImages = getAdImages()
     	[adImages:adImages, keywords:WebDivision.findByName('patio')?.keywords]
    }
    @SuppressWarnings("unchecked")
    private Map getAdImages() {
    	def	adImages = [:]
    	File imageFiles = ApplicationHolder.application.parentContext.getResource("images/store").file
       	imageFiles.eachFile {file ->
       		if (file.name.startsWith('ad')) {
       			try {
					Date to = Date.parse("yyMMdd", file.name[3..8]) + 1
				    if (to > new Date()) {
				    	adImages["$file.name"] = file
				    }
       			} catch (java.text.ParseException pe) {
       				def error = "parse exception on Date.parse(yyMMdd, ${file.name}[3..8])"
       				log.error error
       				sendMail {
       					to CH.config.app.error.email.to.addresses.toArray()
       					subject "KETTLER retail wb app error occurred in StoreController.promotions call to getAdImages()."
       					body error
       					from CH.config.app.error.email.from.address
       				}
       			}
       		}
		}
		return adImages
    }
}
class RfqStoreCommand implements Serializable {
	String name 
	String addr1 
	String addr2 
	String city
	String state
	String zip
	String phone
	String email
	String comment
	String itemNo
	String desc

    static constraints = {
		name blank:false 
		addr1 blank:false 
    	city blank:false 
    	state blank:false 
    	zip blank:false 
    	phone blank:false 
    	email email:true, blank:false 
    	comment blank:false
    }
}
