package locator

class DumpController {
    def geoIpService // depends on geoip plugin.

    def index = {
        redirect(action:"prompt")
    }

    def prompt = {

    }

    def location = {
        println "location, params: ${params}"
        def location = geoIpService.getLocation(params.ip)

        return [ip: params.ip, loc: location]
    }
}
