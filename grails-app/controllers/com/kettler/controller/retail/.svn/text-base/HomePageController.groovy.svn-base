package com.kettler.controller.retail

import com.kettler.domain.item.share.WebDivision
import com.kettler.domain.item.share.WebCategory
import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.warranty.share.WarrantyCustomer
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.warranty.share.WarrantyItem
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.converters.JSON
import grails.util.GrailsUtil
import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class HomePageController {

    def beforeInterceptor = {
       if (request.serverName ==~ /www.kettlercanada.com/ && !params.mode) { params.mode = 'canada' }
        log.debug "action: $actionName params: $params serverName: ${request.serverName} flash: $flash"
    }
    def afterInterceptor = {
        log.debug "after action: $actionName params: $params"
    }
    
    def home = {
        if (request.serverName ==~ /www.kettlercontract.com/) {
          forward controller:'contract', action:'contract'
        } else if (request.serverName ==~ /www.kettlerstore.com/) {
          forward controller:'store', action:'store'
        } else if (request.serverName ==~ /www.kettlercanada.com/) {
		  log.debug "kettlerusa.gsp should set params.mode = 'canada'"
        }
        params.division='kettler usa'
        def divisions = null
        try {
            divisions = WebDivision.retail.list().sort{it.name}
        } catch (e) {
            log.error(e)
            flash.message = message(code:'localized.site.down') //"We are sorry but the site is temporarily down for maintenance. Please try again in an hour."
        }
        [params:params, divisions:divisions]
    }
    def products = {
        redirect controller:'shop', action:'products', params:params
    }
    def termsAndConditions = {
        [params:params]    
    }
    def contactUs = {}
    def aboutUs = {[divisions:WebDivision.retail.list().sort(), params:params]}
    def infoEdu = {[divisions:WebDivision.retail.list().sort(), params:params]}
    def custInfo = {[divisions:WebDivision.retail.list().sort(), params:params]}
    def awards = {[divisions:WebDivision.retail.list().sort(), params:params]}
    def partsService = {[divisions:WebDivision.retail.list().sort(), params:params]}
    def pickWhereToBuyDiv = {[divisions:WebDivision.retail.list().sort{it.name}]}
    def manuals = {
        def divisions = WebDivision.retail.list().sort{it.name}
        def categoryPdfsMap = [:]
        if (params.division) {
            // go get manuals
            def division = WebDivision.findByName(params.division)
            if (division) {
	            WebCategory.findAllByDivision(division).each {cat ->
	                def divScrunched = params.division.replaceAll(/^\/\w*/,'')
	                def pdfNames = []
	                try {
	                    File pdfs = ApplicationHolder.application.parentContext.getResource("manuals/${divScrunched}/${cat.name}").file
	                    pdfs.eachFile {file ->
	                        if (file.name.toLowerCase().endsWith('.pdf')) {
	                            if (params.division == 'patio') {
	                                ItemMasterExt.findByItemNoLike("${(file.name.toUpperCase().replaceAll(/\.PDF/,''))}%").each {item ->
	                                    pdfNames <<  [itemNo:item.itemNo, pdf:file.name]
	                                }
	                            } else {
	                                pdfNames <<  file.name
	                            }
	                        }
	                       }
	                    categoryPdfsMap.put(cat.name, pdfNames.sort())
	                } catch (FileNotFoundException fnfe) {
	                }
	            }
	            println categoryPdfsMap
            }
        }
        [divisions:divisions, division:params.division, categoryPdfsMap:categoryPdfsMap, params:params]
    }
    def pdfSearchByItemNo = {
           def item = ItemMasterExt.findByItemNo(params?.itemNo?:'')
           if (item) {
               File pdf = ApplicationHolder.application.parentContext.getResource(
                           "manuals/${item.division.name.replaceAll(/^\/\w*/,'')}/${item.category.name}/${item.itemNo}")
                           .file
               render "<a id='autoClick' href='${createLinkTo(dir:'.')}/manuals/${item.division.name.replaceAll(/^\/\w*/,'')}/${item.category.name}/${item.itemNo}.pdf'>"+
                      "View PDF for ${item.itemNo}</a>"
            return
           } else {
            render "No manuals were found for Item No: $params.itemNo"
            return
           }
    }
    def preRegister = {
        if (session.consumer) {
            redirect action:'register'
        } else {
            render view:'preRegister'
        }
    }
    def register = {
        WarrantyCustomer warrantyCustomer
        WarrantyItem warrantyItem
        RegisterProductCommand registerProductCommand = new RegisterProductCommand()
        if (session.consumer) {
            log.debug "have session.consumer, ${session.consumer.email}"
            ConsumerBillTo billTo
            if (session.consumer.billTos?.size()>0) {
                billTo = session.consumer.billTos.toArray()[0]
                println "found billTo"
            }
            warrantyCustomer = WarrantyCustomer.findByEmail(session.consumer.email)
            if (!warrantyCustomer) {
                registerProductCommand.email = session.consumer.email
                registerProductCommand.name = session.consumer.name
				if (registerProductCommand.name == 'change') {
					registerProductCommand.name = ''
				}
                if (billTo) {
                    registerProductCommand.name = billTo.name
                    registerProductCommand.addr1 = billTo.addr1
                    registerProductCommand.addr2 = billTo.addr2
                    registerProductCommand.city = billTo.city
                    registerProductCommand.state = billTo.state
                    registerProductCommand.zipCode = billTo.zipCode
                    registerProductCommand.phone = (session.consumer.phone ? session.consumer.phone as String : '')
                } else {
                    registerProductCommand.addr1 = ""
                    registerProductCommand.addr2 = ""
                    registerProductCommand.city = ""
                    registerProductCommand.state = ""
                    registerProductCommand.zipCode = ""
                    registerProductCommand.phone = ""
                }
            } else {
                registerProductCommand.name = warrantyCustomer.lastOrCorpName
                registerProductCommand.addr1 = warrantyCustomer.addr1
                registerProductCommand.addr2 = warrantyCustomer.addr2
                registerProductCommand.city = warrantyCustomer.city
                registerProductCommand.state = warrantyCustomer.state
                registerProductCommand.zipCode = warrantyCustomer.zipCode
                registerProductCommand.phone = warrantyCustomer.phone
                registerProductCommand.email = warrantyCustomer.email
            }
        }
        [divisions:WebDivision.retail().list().sort{it.name}, params:params, cmd:registerProductCommand]
    }
    def registerProduct = { RegisterProductCommand cmd ->
        cmd.validate()
        if (cmd.hasErrors()) {
            render view:'register', model:[divisions:WebDivision.retail.listOrderByName(), cmd:cmd, params:params]
			return
        } 
        log.debug "register ${cmd.itemNo} to: ${cmd.name}"
		WarrantyCustomer warrantyCustomer = WarrantyCustomer.findByEmail(cmd.email)
        if (!warrantyCustomer) {
            log.debug "creating warrantyCustomer"
            warrantyCustomer = new WarrantyCustomer(
                    email:            cmd.email,
                    lastOrCorpName:    cmd.name,
                    addr1:            cmd.addr1,
                    addr2:            cmd.addr2?:'',
                    city:            cmd.city,
                    state:            cmd.state,
                    zipCode:        cmd.zipCode,
                    marketing:        cmd.marketing,
                    phone:            cmd.phone.replaceAll(/\D*/, '')    
            )
            int custId = 0
            def maxCustId = WarrantyCustomer.executeQuery( "select max(id) from WarrantyCustomer")
            if (maxCustId[0]) {
                custId = maxCustId[0] as int
                custId++
            }
            warrantyCustomer.id = custId as String
            if (!warrantyCustomer.save()) {
                log.error "error saving warrantyCustomer"
                warrantyCustomer.errors.each {log.error it}
                assert false
            }
        }
        int seq = 0
        def maxSeq = WarrantyItem.executeQuery( "select max(sequenceNo) from WarrantyItem where custNo = :wcusid",  [wcusid:warrantyCustomer.id])
        if (maxSeq[0]) {
            seq = maxSeq[0]
        }
        ItemMasterExt item = ItemMasterExt.findByItemNo(cmd.itemNo)
        if (!item) {
            log.error "could not find item: ${cmd.itemNo}"
		    cmd.errors.reject("message.code", "Invalid item number");
            render view:'register', model:[divisions:WebDivision.retail.listOrderByName(), cmd:cmd, params:params]
			return
        }
        if (item.residentialWarrantyCode) {

            def warrantyPeriod = item.getResidentialWarrantyPeriod()
            def warrantyItem = new WarrantyItem(
                        compCode:        item.compCode,
                        itemNo:            item.itemNo,
                        itemDesc:        item.desc,
                        warrantyCode:     item.residentialWarrantyCode?:'',
                        purchaseDate:     cmd.datePurchased,
                        purchaseQty:    cmd.quantity,
                        period:         warrantyPeriod,
                        expirationDate: new Date() +
                                    ((warrantyPeriod.years * 365) + warrantyPeriod.days)
                        )
            warrantyItem.custNo = warrantyCustomer.id
            warrantyItem.sequenceNo = ++seq
            if (!warrantyItem.save()) {
                warrantyItem.errors.each {log.error it}
                assert false
            }
			if (session.consumer?.name == 'change') {
				session.consumer.name = warrantyCustomer.lastOrCorpName
				session.consumer = session.consumer.merge()
				assert session.consumer.save()
			}
            flash.message = message(code:'localized.thanks.for.registering') //"Thank you for registering your product."
            redirect(action: "home")
        } else {
            def warrantyPeriod = null
            def warrantyItem = new WarrantyItem(
                        compCode:        item.compCode,
                        itemNo:            item.itemNo,
                        itemDesc:        item.desc,
                        warrantyCode:     item.residentialWarrantyCode?:'',
                        purchaseDate:     cmd.datePurchased,
                        purchaseQty:    cmd.quantity,
                        period:         warrantyPeriod,
                        expirationDate: new Date() 
                        )
            warrantyItem.custNo = warrantyCustomer.id
            warrantyItem.sequenceNo = ++seq
            if (!warrantyItem.save()) {
                warrantyItem.errors.each {log.error it}
                assert false
            }
			if (session.consumer?.name == 'change') {
				session.consumer.name = warrantyCustomer.lastOrCorpName
				session.consumer = session.consumer.merge()
				assert session.consumer.save()
			}
            flash.message = message(code:'localized.thanks.for.registering') //"Thank you for registering your product."
            sendMail {
                to CH.config.warranty.error.email.to.addresses.toArray()
                subject "KETTLER retail warranty registration warning"
                body "Item was registered without residential warranty code for item: ${cmd.itemNo} warranty customer id: ${warrantyCustomer.id} warranty item sequence: ${warrantyItem.sequenceNo}.\n "
                from CH.config.app.error.email.from.address
            }
            redirect(action: "home")
        }
        if (session.consumer) {
            Consumer consumer = Consumer.get(session.consumer.id)
            assert consumer
            consumer.marketing = cmd.marketing
            assert consumer.save()
        }
    }
    
    def divisionChanged = {
        log.debug "divisionChanged, params: ${params}"
        def division = WebDivision.get(params.id)
        def cats = WebCategory.findAllByDivision(division, [sort: "name"])
        render g.select(id: "categories", name: "webCategory", noSelection: ['null':'Select One...'],
            from: cats, optionKey: "id", optionValue: "name")
    }
    def autoCompleteProduct = {
        def selectList = []
        def items = ItemMasterExt.withCriteria {
            ilike("itemNo",        params.term+"%")
        }
        items.each { item ->
			println item.division.name
            def dMap = [:]
            dMap.id = item.id
            dMap.name = item.desc
            dMap.imageTag = g.resource(dir:"images/${item.division.name}/${item.category.name.replaceAll(/-/,'%20')}", file: "${item.itemNo}.jpg")
            dMap.itemNo = item.itemNo
            selectList << dMap
        }
        render selectList as JSON
    }
    def catalogs = {
        def catalogs = []
           File  catalogDir = ApplicationHolder.application.parentContext.getResource("/catalogs")?.file
        catalogDir.eachFile { File dir -> 
            if (dir.name =~ /\..*/) {
                return
            }
            catalogs << dir.name
        }
        [catalogs:catalogs]
    }
    def emailUs = {EmailUsCommand cmd ->
	    if (cmd.hasErrors()) {
	        render (view:'contactUs', model:[cmd:cmd])
	        return
	    }
        def emailTo = EmailUsCommand.EMAILS[cmd.emailType]
        log.debug "contact us email to:"+emailTo
		if (GrailsUtil.getEnvironment() == GrailsApplication.ENV_DEVELOPMENT) {
            emailTo = 'dondenoncourt@gmail.com'
        }
	    def message = "From:\r\r"+
	                  "$cmd.name\r$cmd.email\r$cmd.company\r"+
                      "$cmd.addr1\r$cmd.addr2\r$cmd.city $cmd.state $cmd.zip\r" +
	                  "phone: $cmd.phone\r "+
	                  "email: $cmd.email\r\r"+
                      "Comment/Question:\r"+
                      cmd.comment
        try {
			sendMail {
				to emailTo
				subject "Retail web site comment/question"
				body message
				from "webmaster@kettlerusa.com"
			}
        } catch (Exception e) {
            log.error "Problem emailing $e.message", e
        }
    	flash.message = "${params.name}: your email has been sent"
    	redirect action:'home'
    }
}

class RegisterProductCommand {
    String name
    String addr1
    String addr2 = ""
    String city
    String state
    String zipCode
    String phone
    String email
    String itemNo
    Date datePurchased
    int quantity = 1
    boolean marketing

    static constraints = {
        name(blank: false, maxSize:30)
        addr1(blank: false, maxSize:30)
        addr2(maxSize:30)
        city(blank: false, maxSize:15)
        state(blank: false, maxSize:2)
        zipCode(blank: false, maxSize:9)
        phone(blank: false, maxSize:10)
        email(blank: false, email: true)
        itemNo(blank: false, 
            validator: { itemNo, obj ->
                if (!ItemMasterExt.findByItemNo(itemNo)) {
                    return "registerProductCommand.itemNo.invalid" 
                }
                return true
            }
        )
        datePurchased(nullable: false, blank: false)
        quantity(validator: { val, obj ->
            println "quantity validator, val: ${val}"
            return (val >= 1)
        })
    }
}
