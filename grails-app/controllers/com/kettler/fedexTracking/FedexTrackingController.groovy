package com.kettler.fedexTracking


class FedexTrackingController {
    def fedexService

    def index = { }

    def search = {
        // TODO The index page should get passed an order number or something
        // that can be used as the CustomerContext in the request.
        log.debug "fedexTracking search, params: ${params}"
        def tr
        def resp
//        log.debug "-------------request: ${req}"
        try {
			println params.dump()
            tr = fedexService.getTrackingResponse(params.number)
//            println "-------------response: ${tr.dump()}"
        } catch (Exception e) {
            log.error "**Error in fedex controller: ${e.message}"
            tr = new TrackResponse()
            tr.success = false 
            tr.errorDescription = "Internal error: ${e.message}"
        }
        log.debug "success: ${tr.success}"
        if (tr.freight) {
            render(template:"fedexFreight",model:[:])
        } else if (!tr.success) {
            render(template:"fedexFailure",model:[tr: tr])
        } else {
            [req: null, resp: null, tr:tr]
        }
    }

}
