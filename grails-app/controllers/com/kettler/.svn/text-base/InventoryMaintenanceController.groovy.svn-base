package com.kettler
import grails.util.Environment
import com.kettler.domain.item.share.Dealer
import com.kettler.domain.actrcv.share.Customer
import com.kettler.domain.item.share.DealerLocation
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.orderentry.share.InvoicedOrderDetailItem
import com.kettler.domain.item.share.DealerInventory

class InventoryMaintenanceController {

    def beforeInterceptor = {
    	log.debug "action: $actionName params: $params flash: $flash $Environment.current"
    }

    // Dummy page just to get custno
    def index = {
        if (!params.division) {
            params.division = 'patio'
        }
        [division: params.division]
    }

    // Find dealer by custno, displays view showing locations
    def search = {
        def model = [division: params.division, custno: params.custno]
        if (!params.custno || params.custno?.trim().length()==0) {
            flash.message = message(code:'localized.cust.no.required') //"Customer number is required"
            redirect(action: 'index')
        } else {
            Customer c = Customer.findByCustNo(params.custno)
            if (c) {
                Dealer d = Dealer.findByCustomer(c)
                if (d) {
                    model.dealer = d
                    // This is a hack to make list be in same order each time
                    def mc= [
                      compare: {a,b-> a.toString().compareTo(b.toString()) }
                    ] as Comparator
                    def locs = d.locations.sort(mc)
                    model.locs = locs

                }
            } else {
                flash.message = message(code:'localized.no.dealer.found', args:[params.custno]) //"No dealer found for customer number: ${params.custno}"
                redirect(action: 'index')
            }
        }
        model
    }

    // User selected location, display inventory at location
    def location = {
        def model = [division: params.division, custno: params.custno]
        def loc = DealerLocation.get(params.id)
        if (loc) {
            model.location = loc
            loc.inventories.each { inv ->
                inv.available = false
                inv.save(flush:true)
            }
        } else {
            flash.message = message(code:'localized.err.find.loc') //"Error finding location"
            redirect(action:"search",params:model)
        }
        model
    }

    // Displays items ordered within x months to dealer so they can add
    // items to a location.
    def add = {

        def model = [division: params.division, custno: params.custno]
        DealerLocation loc = DealerLocation.get(params.locId)
        model.location = loc

        def cal = new GregorianCalendar()
        cal.add(Calendar.MONTH, CH.config.invoiceCutoffMonths)
        model.months = CH.config.invoiceCutoffMonths * -1
        def cutoffDate = cal.getTime()
        log.debug "Cutoff order date: ${cutoffDate}"
        def l = InvoicedOrderDetailItem.findAllByCustNoAndInvoiceDateGreaterThanEquals(params.custno, cutoffDate)
        log.debug "order detail count: $l.size"
        def purchasedItems = []
        l.each {
            def itm = ItemMasterExt.findByItemNo(it.itemNo)
            if (itm) {
//                log.debug "$it.itemNo, $it.desc, $it.orderQty, $it.invoiceDate"
//                log.debug "itm class: ${itm.class}"
                if (!purchasedItems.contains(itm)) {
                    purchasedItems.add(itm)
                }
            }
        }
        def mc= [
          compare: {a,b-> a.desc.compareTo(b.desc) }
        ] as Comparator

        model.purchasedItems = purchasedItems.sort(mc)
        model
    }

    // Dealer select what DealerInventory is still available, update it
    // then redirect back to search so they can pick another location.
    def invAvail = {
        def model = [custno: params.custno, division: params.division]
        boolean updated = false
        def keys = params.keySet()
        keys.each { key ->
            if (key.startsWith('cbInvAvail_')) {
                def sId = key.substring(key.indexOf('_')+1)
                def di = DealerInventory.get(sId as long)
                di.available = true
                updated = true
                di.save(flush:true)
            }
        }
        if (updated) {
            DealerLocation dl = DealerLocation.get(params.locId)
            GregorianCalendar gc = new GregorianCalendar()
            gc.set(Calendar.HOUR_OF_DAY,0)
            gc.set(Calendar.MINUTE,0)
            gc.set(Calendar.SECOND,0)
            dl.availabilityUpdated = gc.getTime()
            dl.availabilityExpired = false
            dl.save(flush:true)
        }
        flash.message = message(code:'localized.availability.updated') //"Availability has been updated for selected locaation"
        redirect(action:'search', params: model)
    }

    // Adds items selected to a DealerLocation.  Redirect back to location for Dealer
    // to select inventory available.
    def addInv = {
        def model = [id: params.locId, division: params.division, custno: params.custno ]
        def loc = DealerLocation.get(params.locId)
        def keys = params.keySet()
        keys.each { key ->
            if (key.startsWith('itemId_')) {
                def sId = key.substring(key.indexOf('_')+1)
                def itm = ItemMasterExt.get(sId as long)
                log.debug itm
                DealerInventory di = new DealerInventory(item:itm)
                loc.addToInventories(di)
            }
        }
        loc.save(flush:true)
        flash.message = message(code:'localized.items.added.to.inv') //"Items added to inventory list"
        redirect(action:"location", params: model)
    }
}
