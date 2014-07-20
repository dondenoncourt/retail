package com.kettler.controller.retail;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH


public class ErrorController{
	def userAgentIdentService
    def notFound = {
		log.debug "${request.'javax.servlet.error.status_code'}: ${request.'javax.servlet.error.message'}"
		log.debug "query string: ${request.'javax.servlet.forward.query_string'}"
		flash.message = message(code:'localized.invalid.page')
		redirect controller:'homePage', action:'home'
    }
	def error = {
		def send = true
		def agentInfo
		try {
			agentInfo = userAgentIdentService.getUserAgentInfo()
			log.error "Agent info: "+agentInfo?.agentString
			if (agentInfo?.browserType == userAgentIdentService.CLIENT_OTHER) {
				log.error "not sending email due to unknown browser type"
				send = false
				agentInfo = null
			}
		} catch (e) {/* ignore */ }
		if (send) {
	        try {
				sendMail {
					to CH.config.app.error.email.to.addresses.toArray()
					subject "KETTLER retail wb app error occurred."
					body( view:"/error/internalError", 
		                  plugin:"email-confirmation",
		                  model:[fromAddress:CH.config.app.error.email.from.address, 
		                         consumer:session.consumer,
		                         agentInfo:agentInfo])  
					from CH.config.app.error.email.from.address
				}
	        } catch (Exception e) {
	            log.error "Problem emailing $e.message", e
	        }
		}
        render (view:'error',model:[consumer:session.consumer])
	}
	
	def failure = { 
		throw new RuntimeException('Error email test. Please ignore.')
	}
	def download = {
		def file = new File(params.fileDir)    
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")

		response.outputStream << file.newInputStream() // Performing a binary stream copy	
	}
}
