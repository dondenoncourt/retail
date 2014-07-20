includeTargets << grailsScript("Init")

// test comment 11/27/2012

ant.taskdef(name:"deploy",classname:"org.apache.catalina.ant.DeployTask")
ant.taskdef(name:"list",classname:"org.apache.catalina.ant.ListTask")
ant.taskdef(name:"undeploy",classname:"org.apache.catalina.ant.UndeployTask")

// These need to be adjusted for the Tomcat configuration
def turl = 'http://localhost/manager'
def tuser = 'donat'
def tpassword = 'vo2max'

target(main: '''\
Work with Tomcat manager

grails ktomcat list - list current web applications
grails ktomcat deploy <warName> <contextPath> - deploy <warName> to <contextPath>
grails ktomcat undeploy <contextPath> - undeploy/remove web app at <contextPath>

NOTE: <contextPath> must include the initial '/'.
NOTE: <warName> is assumed local.  Do NOT include '.war'; just the file name.
''') {
    println '-- Starting Tomcat Manager'
    depends(parseArguments)
    def cmd = argsMap.params ? argsMap.params[0] : 'list'
    println "-- argsMap: ${argsMap}"
    switch (cmd) {
        case 'list':
            list(url:turl, username: tuser, password: tpassword)
            break
        case 'deploy':
            def warName = argsMap.params[1]
            def contextPath = argsMap.params[2]
            println "-- Deploying: ${warName}.war to: ${contextPath}"
            list(url:turl, username: tuser, password: tpassword, output:'list.log')
            def f = new File('list.log')
            def lines = f.readLines()
            if (lines[0].startsWith('OK')) {
                lines.each {line ->
                    if (line.contains(contextPath)) {
                        println "-- ${contextPath} already deployed, undeploying first"
                        undeploy(url:turl, username: tuser, password: tpassword, path: contextPath)
                    }
                }
                deploy(url:turl, username: tuser, password: tpassword, path: contextPath, war:'file:'+warName+'.war')
            } else {
                println "** Error communicating with Tomcat: ${lines[0]}"
            }
            break;
        case 'undeploy':
            def contextPath = argsMap.params[1]
            println "-- Undeploying: ${contextPath}"
            undeploy(url:turl, username: tuser, password: tpassword, path: contextPath)
            break;
        default:
            println "** Unknown command: $cmd"
    }
}

setDefaultTarget(main)
