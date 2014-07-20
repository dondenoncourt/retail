import java.util.Date;
import groovy.sql.Sql

import  com.kettler.domain.orderentry.share.Consumer
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class RegisterController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def sessionFactory
    
    def beforeInterceptor = {
    	log.debug "action: $actionName " // don't show params for security reasons
    }
	def products = {
		redirect controller:'shop', action:'products', params:params
	}    
    def loginPrompt = { 
    	render view:'login', model:params
    }
	def newAccountForWarranty = {
		def consumer = new Consumer(name:'change', password:params.password, email:params.email)
        if (consumer.save(flush: true)) {
			session.consumer = consumer
 	    	forward controller:'homePage', action:'register'
			return
        } else {
			params.password = ''
            render view:'/homePage/preRegister', model:[consumer:consumer, params:params]
			return
        }
		render "new account"
	}
	def login = { 
		def consumer = Consumer.findByEmail(params.email?:'')
		if (!consumer) {
			flash.message = message(code:'localized.invalid.email.or.password') //"Invalid email or password"  
			params.remove('password') 
   			if (params.registerWarranty) {
   	   	    	redirect controller:'homePage', action:'preRegister'
   			} else {
				redirect action:'loginPrompt', params:params
			}
			return	              
		} else if (consumer?.password == params.password) {
			flash.message = message(code:'localized.welcome.back', args:[consumer.name]) //"Welcome back ${consumer.name}"
			session.consumer = consumer
			params.remove('email') 
			params.remove('password') 
   			if (params.division == 'kettlerusa') {
   	   	    	redirect controller:'homePage', action:'home', params:params
   			} else if (params.registerWarranty) {
   	   	    	redirect controller:'homePage', action:'register'
   			} else {
   				redirect controller:'shop', action:'products', params:params
   			}
   	    	return
		} 
		// else good email, bad password
		flash.message = message(code:'localized.invalid.password')+' '+
                        message(code:'localized.click')+' '+
                        "<a href='/register/emailPassword?email=${params.email}'>"+' '+
                        message(code:'localized.here')+
                        '</a>'+' '+
                        message(code:'localized.invalid.password.email')
		params.remove('password') 
		if (params.registerWarranty) {
   	    	redirect controller:'homePage', action:'preRegister'
		} else {
			redirect action:'loginPrompt', params:params
		}
	}
	def emailPassword = {
		def consumer = Consumer.findByEmail(params.email)
		assert consumer
		try {
            sendMail {
                to params.email
                subject "Your KETTLER USA profile"
                body "${consumer.name}:\nYour KETTLER USA password is: ${consumer.password}"
                from "webmaster@kettlerusa.com"
            }
		} catch (e) {
			println("sendMail failed:"+e)
			log.warn("sendMail failed:"+e)
		}
		flash.message = message(code:'localized.password.emailed', args:[params.email])//"Your password has been emailed to ${params.email}" 
		params.remove('password') 
		redirect action:'loginPrompt', params:params
	}
   
    def logout = {
    	session.consumer = null
		if (params.division == 'kettler usa') {
   	    	redirect controller:'homePage', action:'home', params:params
		} else {
			redirect controller:'shop', action:'products', params:params
		}
    }
    def register = {RegisterCommand cmd ->
		Consumer consumer = new Consumer(params)
		consumer.validate()
	    if (cmd.hasErrors() || consumer.hasErrors()) {
	        render view:'register', model:[cmd:cmd, consumer:consumer, params:params]
	        return
	    }
		if (consumer.save()) {
			session.consumer = consumer
		}
		params.division='toys' // TODO should go to home or to page from which the login came...
    	redirect controller:'shop', action:'products', params:params
    }
    
}

class RegisterCommand {
	String email
	String password
	String name
	Date dateCreated
	Date lastUpdated	
	String confirmPassword
    static constraints = {
		confirmPassword validator: { confirmPassword, consumer ->
			if (confirmPassword != consumer.password) {
				return "registerCommand.confirmPassword.validator.error"
			}
		}	
	}
	String toString() {"$email $first $last"}
}
