package com.kettler.upsTracking

class UpsTrackingController {
    def upsService

    def index = { }

    def search = {
        // TODO The index page should get passed an order number or something
        // that can be used as the CustomerContext in the request.
        log.debug "upsTracking search, params: ${params}"
        def tr
        def req = upsService.getRequestXml(params.number,params.orderNo)
        def resp
//        log.debug "-------------request: ${req}"
        try {
            resp = upsService.getTrackingInfo(req)
//            println "-------------response: ${resp}"
            tr = upsService.getTrackResponse(resp)
        } catch (Exception e) {
            log.error "**Error in ups controller: ${e.message}"
            tr = new TrackResponse()
            tr.success = false
            tr.errorDescription = "Internal error: ${e.message}"
        }
        log.debug "success: ${tr.success}"
        if (tr.freight) {
            render(template:"freight",model:[:])
        } else if (!tr.success) {
            render(template:"failure",model:[tr: tr])
        } else {
            [req: req, resp: resp, tr:tr]
        }
    }

}
