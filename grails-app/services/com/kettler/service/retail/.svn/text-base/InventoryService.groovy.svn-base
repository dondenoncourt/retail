package com.kettler.service.retail
import com.kettler.domain.item.share.DealerLocation
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class InventoryService {

    static transactional = true

    /**
     * Dealers have to update their inventory every CH.config.inventoryUpdateDays days.
     * Find all locations with availabilityUpdated = CH.config.inventoryUpdateDays -
     * CH.config.inventoryEmailDaysBefore.
     *
     * @return List of DealerLocation meeting above criteria.
     */
    def getLocationsNeedingUpdate() {
        GregorianCalendar gc = new GregorianCalendar()
        def offset = (CH.config.inventoryUpdateDays - CH.config.inventoryEmailDaysBefore) * -1
        gc.add(Calendar.DAY_OF_YEAR, offset)
        gc.set(Calendar.HOUR_OF_DAY, 0)
        gc.set(Calendar.MINUTE, 0)
        gc.set(Calendar.SECOND, 0)
        Date testDate = gc.getTime()
        log.debug "getLocationsNeedingUpdate testDate: ${testDate}"
        DealerLocation.withCriteria {
		    lt 'availabilityUpdated', testDate
		    dealer {
		        sqlRestriction "track_inv = 'Y'"
		    }
		}        
    }

    def getExpiredLocations() {
        GregorianCalendar gc = new GregorianCalendar()
        def offset = CH.config.inventoryUpdateDays  * -1
        gc.add(Calendar.DAY_OF_YEAR, offset)
        gc.set(Calendar.HOUR_OF_DAY, 0)
        gc.set(Calendar.MINUTE, 0)
        gc.set(Calendar.SECOND, 0)
        Date testDate = gc.getTime()
        log.debug "getExpiredLocations testDate: ${testDate}"
        DealerLocation.withCriteria {
		    lt 'availabilityUpdated', testDate
		    dealer {
		        sqlRestriction "track_inv = 'Y'"
		    }
		}        
    }

}
