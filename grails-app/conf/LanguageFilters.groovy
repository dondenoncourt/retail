import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class LanguageFilters {
	def httpRequest

    def filters = {
        all(controller:'*', action:'*') {
            before = {
	            if (request.serverName == 'www.kettlerlatinoamerica.com') {
                    session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'] = new Locale("es","ES")
                } else {
                    session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'] = new Locale("en","US")
                }
            }
            after = {}
            afterView = {}
        }
    }
}

