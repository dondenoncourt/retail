package com.kettler.retail

import com.kettler.service.retail.InventoryService
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class InventoryUpdateJob {
    def mailService
    def inventoryService

    static triggers = {
        /*
			A cron expression is a string comprised of 6 or 7 fields separated by white space.
			Fields can contain any of the allowed values, along with various combinations of
			the allowed special characters for that field. The fields are as follows:
			
			Field Name 	Mandatory 	Allowed Values 	Allowed Special Characters
			Seconds 	YES 	0-59 	, - * /
			Minutes 	YES 	0-59 	, - * /
			Hours 	YES 	0-23 	, - * /
			Day of month 	YES 	1-31 	, - * ? / L W
			Month 	YES 	1-12 or JAN-DEC 	, - * /
			Day of week 	YES 	1-7 or SUN-SAT 	, - * ? / L #
			Year 	NO 	empty, 1970-2099 	, - * /
         */
        cron name: 'myTrigger', cronExpression: CH.config.inventoryCronExpression // probabaly set to  "0 3 0 * * ?" which is 0:03AM"
    }

    def execute() {
        def dealers = []
        log.debug "running inv job"
        def locs = inventoryService.getLocationsNeedingUpdate()
        log.debug "found ${locs.size()} locations needing reminders"
        if (locs.size() > 0) {
            locs.each {loc ->
                if (!dealers.contains(loc.dealer)) {
                    dealers.add(loc.dealer)
                }
            }
            dealers.each {dealer ->
                log.debug "Sending reminder email to ${dealer.customer.name} at: ${dealer.customer.email}"
                if (dealer.customer.email) {
	                mailService.sendMail {
	                    from CH.config.mailFromAddress
	                    to dealer.customer.email
	                    subject CH.config.reminderSubject
	                    body CH.config.reminderMessage
	                }
                } else {
                	log.error "Dealer ${dealer.customer.custNo} has no email"
        			sendMail {
        				to CH.config.app.error.email.to.addresses.toArray()
        				subject "KETTLER retail no inventory email for ${dealer.customer.custNo}"
        				body "KETTLER retail no inventory email for ${dealer.customer.custNo}, from InventoryUpdateJob"
        				from CH.config.app.error.email.from.address
        			}
                }
            }
        }
        dealers = []
        locs = inventoryService.getExpiredLocations()
        log.debug "found ${locs.size()} expired locations"
        if (locs.size() > 0) {
            locs.each {loc ->
                if (!dealers.contains(loc.dealer)) {
                    dealers.add(loc.dealer)
                }
                loc.availabilityExpired = true
                loc.save()
            }
            log.debug "from address: ${CH.config.mailFromAddress}"
            dealers.each {dealer ->
                log.debug "Sending expired email to ${dealer.customer.name} at: ${dealer.customer.email}"
                if (dealer.customer.email) {
	                mailService.sendMail {
	                	from CH.config.mailFromAddress
	                	  to dealer.customer.email
	                	  subject CH.config.expiredSubject
	                	  body CH.config.expiredMessage
	            	}
                } else {
                	log.error "Dealer ${dealer.customer.custNo} has no email"
        			sendMail {
        				to CH.config.app.error.email.to.addresses.toArray()
        				subject "KETTLER retail expired inventory email for ${dealer.customer.custNo}"
        				body "KETTLER retail expired inventory email for ${dealer.customer.custNo}, from InventoryUpdateJob"
        				from CH.config.app.error.email.from.address
        			}
                }
            }
        }

    }
}
