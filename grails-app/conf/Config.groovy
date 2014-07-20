import org.apache.log4j.DailyRollingFileAppender

geoip.data.resource= "/WEB-INF/GeoLiteCity.dat"
geoip.data.cache=0 // standard?

grails.mail.default.from="webmaster@kettlerusa.com"
	
// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

grails.config.locations = ["file:/opt/webapps/${appName}-config.groovy"]


// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.views.javascript.library="jquery"

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

//
//iseriesIPAddress='192.168.1.50'
//iseriesUserId='DCROCKER'
//iseriesPwd='CAMPING01'

credit.warning.emails = ["dondenoncourt@gmail.com", "dondenoncourt@comcast.net"]
ra.label.directory = "/opt/webapps/ra_shipping_labels"

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.kettlerusa.com"
		credit.warning.emails = ["creditmanager@kettlerusa.com", "arcredit@kettlerusa.com", "mmurray@kettlerusa.com"]
        paypal.user = 'creditmanager_api1.kettlerusa.com'
        paypal.pwd = 'A8B58FDQKMJRFLK2'
        paypal.signature = 'AFU4gvn4RNVf.CS8n6V9-6F7VG9MAq1cbFtQT6QGEjyzrEsIRg5YOcFa'
        paypal.url.nvp = 'api-3t.paypal.com/nvp'
        paypal.url.cgi='www.paypal.com'
    }
    development {
		grails.serverURL = "http://www.kettlerusa.com/${appName}"
        //grails.serverURL = "http://www.kettlercanada.com/${appName}"
        //grails.serverURL = "http://www.kettlercontract.com/${appName}"
        //grails.serverURL = "http://localhost:8080/${appName}"
        //grails.serverURL = "http://www.kettlerlatinoamerica.com/${appName}"
        //grails.serverURL = "http://www.kettlerlatinoamerica.com/${appName}"
		iseriesIPAddress='194.166.35.3'
		iseriesUserId='DAVID'
		iseriesPwd='GOBAMA' 
        paypal.user = 'ket_1350402565_biz_api1.gmail.com'
        paypal.pwd = '1350402620'
        paypal.signature = 'ASCivRVZIoRFrG3ieMKQqqqlmM2OAaydwXG7.qIz7qWIgL75p0nNkORn'
        paypal.url.nvp = 'api-3t.sandbox.paypal.com/nvp'
        paypal.url.cgi='www.sandbox.paypal.com'
	}
    amato {
        grails.serverURL = "http://grailsdev:8080/${appName}"
		iseriesIPAddress='194.166.35.3'
		iseriesUserId='DAVID'
		iseriesPwd='GOBAMA' 
	}
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    UAT {
        grails.serverURL = "http://216.54.6.24/uat"
    }
    MARSDEV {
        grails.serverURL = "http://localhost:9090/${appName}"
    }

}

// log4j configuration

log4j = {
    appenders {
          rollingFile name:"rollingFile", file:'/opt/webapps/app.log', maxFileSize:'12MB', append:false, layout: pattern(conversionPattern: '%-5p %c{1} %d{dd MMM HH:mm:ss} %m%n')
            console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
//          appender new DailyRollingFileAppender(
//	        		  name: 'dailyAppender',
//	        		  datePattern: "'.'yyyyMMdd",
//	        		  fileName: "/opt/webapps/app.log",
//	        		  layout: pattern(conversionPattern:'%d [%t] %-5p %c{2} %x - %m%n')       
//        		  )
    }
    
    debug rollingFile:"grails.app"
    	 //debug 'org.codehaus.groovy.grails.plugins.searchable'
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}
