package retail

import grails.util.Environment
import org.springframework.web.servlet.support.RequestContextUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class KettlerTagLib {
	 
    static namespace = "kettler"
    def userAgentIdentService
	
	def resource = { attrs, body ->
		def contextPath = attrs.remove("contextPath").toString()
		def absolute = attrs.remove("absolute").toString()
		def tag = g.resource(contextPath:contextPath, absolute:absolute)
		if (request.serverName ==~ /www.kettlercanada.com/) { 
			tag = tag.replaceAll(/www.kettlerusa.com/, "www.kettlercanada.com")
		} else if (request.serverName ==~ /www.kettlerstore.com/) { 
			tag = tag.replaceAll(/www.kettlerusa.com/, "www.kettlerstore.com")
		} else if (request.serverName ==~ /www.kettlercontract.com/) {
			tag = tag.replaceAll(/www.kettlerusa.com/, "www.kettlercontract.com")
		} else if (request.serverName ==~ /www.kettlerlatinoamerica.com/) {
			tag = tag.replaceAll(/www.kettlerusa.com/, "www.kettlerlatinoamerica.com")
			tag = tag.replaceAll(/localhost/, "www.kettlerlatinoamerica.com")
		}
		tag << body()
		def division = attrs.remove("division").toString()
		division = division?.replaceAll(/null/, '')
		if (division) {
			if (tag.find(/\?/)) {
				tag += "&division=$division"
			} else {
				tag += "?division=$division"
			}
		}
		out << tag
		out
	}
    def translate = { attrs, body ->
		def text = attrs.remove("text")
        if (RequestContextUtils.getLocale(request).language == 'es') {
            out << "cerca"
        } else {
            out << text
        }
    }
    def isMsie = { attrs, body ->
        if (userAgentIdentService.isMsie()) {
            out << body()
        }
    }
 
    def isFirefox = { attrs, body ->
        if (userAgentIdentService.isFirefox()) {
            out << body()
        }
    }
 
    def isChrome = { attrs, body ->
        if (userAgentIdentService.isChrome()) {
            out << body()
        }
    }
 
 
    def isBlackberry = { attrs, body ->
        if (userAgentIdentService.isBlackberry()) {
            out << body()
        }    
    }
    
	def formatPhone = {attrs ->
		def phone = attrs.remove("phone").toString()
		if (phone.size() == 10) {
			out << "${phone[0..2]}.${phone[3..5]}.${phone[6..9]}"
		} else {
			out << phone.trim()
		}
	    out.print()
	}
	// @see http://www.dariopardo.com/grails/jquerydatepickergrailstag/
	def jqDatePicker = {attrs, body ->
		def out = out
		def name = attrs.name    //The name attribute is required for the tag to work seamlessly with grails
		def id = attrs.id ?: name.replaceAll(/\[|\]/,'_').replaceAll(/\./, 'DOT')
		def minDate = attrs.minDate
		def showDay = attrs.showDay
		
		def value = attrs.value //denoncourt
		

		//Create date text field and supporting hidden text fields need by grails
		out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" value=\"${value}\"/>"
		out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" value=\"${value?value[3..4]:''}\"/>"
		out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\"  value=\"${value?value[0..1]:''}\"/>"
		out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\"  value=\"${value?value[6..9]:''}\"/>"

		//Code to parse selected date into hidden fields required by grails
		out.println "<script type=\"text/javascript\"> \$(document).ready(function(){"
		out.println "\$(\"#${id}\").datepicker({"
		out.println "onClose: function(dateText, inst) {"
		out.println "\$(\"#${id}_month\").attr(\"value\",new Date(dateText).getMonth() +1);"
		out.println "\$(\"#${id}_day\").attr(\"value\",new Date(dateText).getDate());"
		out.println "\$(\"#${id}_year\").attr(\"value\",new Date(dateText).getFullYear());"
		out.println "}"

		//If you want to customize using the jQuery UI events add an if block an attribute as follows
		if(minDate != null){
			out.println ","
			out.println "minDate: ${minDate}"
		}

		if(showDay != null){
			out.println ","
			out.println "beforeShowDay: function(date){"
			out.println	"var day = date.getDay();"
			out.println	"return [day == ${showDay},\"\"];"
			out.println "}"
		}

		out.println "});"
		out.println "})</script>"

	}

    def logMsg = { attrs, body ->
        if (attrs['level'] != null) {
            String logLevel = org.apache.log4j.Level.toLevel(attrs['level']).toString().toLowerCase()
            log."$logLevel"(body())
        } else {
            log.debug(body())
        }
   }

    
}
