grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.5'
    }
}

// Add test for hudson builds to specify location of plugins
def loc = System.getenv('hudson.sharedDomain')
if (loc) {
    println "******* setting sharedDomain plugin loc to: ${loc}"
    grails.plugin.location.'shared-domain-plugin' = loc
} else {
    println "******* setting sharedDomain plugin loc to: ../SharedDomainPlugin"
    grails.plugin.location.'shared-domain-plugin' = "../SharedDomainPlugin"
}

def loc2 = System.getenv('hudson.upsWebService')
if (loc2) {
    println "******* setting upsWebService plugin loc to: ${loc2}"
    grails.plugin.location.'ups-web-service' = loc2
} else {
    println "******* setting upsWebService plugin loc to: ../UpsWebService"
    grails.plugin.location.'ups-web-service' = "../UpsWebService"
}

def loc3 = System.getenv('hudson.FedExsWebService')
if (loc3) {
	println "******* setting upsWebService plugin loc to: ${loc2}"
	grails.plugin.location.'fedex-web-service' = loc3
} else {
	println "******* setting upsWebService plugin loc to: ../FedExWebService"
	grails.plugin.location.'fedex-web-service' = "../FedExWebService"
}

