// Script to run in grails console to populate dealers.
// Bootstrap was not running in my environment.


import locator.*

        if (Dealer.count() == 0) {
            println "adding dealers"
            Dealer d = new Dealer(name:"Lowe's Home Improvement", website: "http://wwww.lowes.com", phone:"2052051111",
                division:"toys")
            DealerLocation dl = new DealerLocation(street:"375 State Farm Parkway",
                city: "Homewood", state: "AL")
            DealerLocation dl2 = new DealerLocation(street:"2100 Valleydale Road",
                city: "Hoover", state: "AL")
            d.addToLocations(dl)
            d.addToLocations(dl2)
            if (!d.save()) println "save failed"
            d = new Dealer(name:"Little Hardware",phone:"2051235678",division:"toys")
            dl = new DealerLocation(street:"2703 Culver Road",
                city: "Mountain Brook", state: "AL")
            d.addToLocations(dl)
            if (!d.save()) println "save failed"
            d = new Dealer(name:"Rocky Ridge Hardware", website: "http://rockyridgehardware.com",phone:"2059432299",
                division:"toys")
            dl = new DealerLocation(street:"3354 Morgan Drive",
                city: "Birmingham", state: "AL")
            d.addToLocations(dl)
            if (!d.save()) println "save failed"
        }
