import static groovy.io.FileType.*

target(main: 'Hot Deploy GSP, CSS, HTML, and images') {
	println '-- Hot Deploy GSP, CSS, HTML, and images'
	def webappsDir = '/opt/apache-tomcat-6.0.20/webapps/'
	def apps = ['ROOT', 'retail']
	def viewsDir = 'WEB-INF/grails-app/views'
	def i18nDir = 'grails-app/i18n'

	apps.each {contextRoot ->
		copy(todir:"${webappsDir}${contextRoot}/${viewsDir}", preservelastmodified:true) {
			fileset(dir:"grails-app/views") {
				include(name:"**/*.gsp")
			}
		}
		copy(todir:"${webappsDir}${contextRoot}/css", preservelastmodified:true) {
			fileset(dir:"web-app/css") {
				include(name:"**/*.css")
			}
		}
		copy(todir:"${webappsDir}${contextRoot}/images", preservelastmodified:true) {
			fileset(dir:"web-app/images") {
				include(name:"**/*")
			}
		}
		copy(todir:"${webappsDir}${contextRoot}/manuals", preservelastmodified:true) {
			fileset(dir:"web-app/manuals") {
				include(name:"**/*")
			}
		}
		copy(todir:"${webappsDir}${contextRoot}/WEB-INF/grails-app/i18n", preservelastmodified:true) {
			fileset(dir:"grails-app/i18n") {
				include(name:"**/messages*.properties")
			}
		}
	}
	/*
	* for all GSPs with a mod date greater than the last GSP compile time, 
	* delete the associated .class, _html.data, and _linenumbers.data
	*/
	def lastGspCompileTime = 0l
	new File("${webappsDir}/retail/WEB-INF/classes/").eachFileMatch FILES, ~/gsp_.*\.class/, { File gsp -> 
		if (gsp.lastModified() > lastGspCompileTime ) {
			lastGspCompileTime = gsp.lastModified()
		}
	}
	new File("grails-app/views").eachDirRecurse {dir ->
		dir.eachFileMatch FILES, ~/.*\.gsp/, { File gsp -> 
			if (gsp.lastModified() > lastGspCompileTime) {
				def gspClassNamePrefix = 'gsp_retail_'+
							gsp.parent.replaceAll(/grails-app.views./,'').
							replaceAll("/", '_').   // unix slash
							replace("\\", "_")+     // windows slash
							gsp.name.replaceAll(".gsp", '').replaceAll("-", '_')
				apps.each {contextRoot ->
					new File("${webappsDir}/${contextRoot}/WEB-INF/classes/").eachFileMatch FILES, 
						~/${gspClassNamePrefix}.*/, { File gspEtc ->
						println "deleting ${webappsDir}/${contextRoot}/WEB-INF/classes/"+gspEtc.name
						gspEtc.delete()
					} 
				}
			}
		}
	}
	new File("${webappsDir}/retail/WEB-INF/classes/").eachFileMatch FILES, ~/gsp_.*Copy_of.*\..*/, { File gsp -> 
		gsp.delete()
	}
	println " last GSP Compile Time: ${new Date(lastGspCompileTime)} "
}

setDefaultTarget(main)
