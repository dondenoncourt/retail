package com.kettler.controller.retail 
 
import com.kettler.service.retail.CreditCardReturn

import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.item.share.ItemWarehouse
import com.kettler.domain.item.share.ItemMaster
import com.kettler.domain.item.share.WebDivision
import com.kettler.domain.orderentry.share.OrderHeader;
import com.kettler.domain.item.share.WebCategory
import com.kettler.domain.orderentry.share.Coupon
import com.kettler.domain.orderentry.share.Consumer
import com.kettler.domain.orderentry.share.ConsumerBillTo
import com.kettler.domain.orderentry.share.ConsumerShipTo
import com.kettler.domain.orderentry.share.Cart
import com.kettler.domain.orderentry.share.CartItem
import com.kettler.domain.warranty.share.WarrantyCustomer
import com.kettler.domain.warranty.share.WarrantyItem
import com.kettler.domain.warranty.share.WarrantyPeriod
import com.kettler.upsReturnAddress.UpsReturnAddress

import com.kettler.util.Image

import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import grails.util.Environment;

import java.util.List;


import org.compass.core.engine.SearchEngineQueryParseException
import com.kettler.service.orderentry.OrderService

class ShopController { 
   	def searchableService
   	def orderService
   	def upsRateService
   	def upsService
   	def upsAddressService
   	def mailService
   	def preAuthorizationService
    def messageSource
	def fedexService
   	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def beforeInterceptor = {
       if (request.serverName ==~ /www.kettlercanada.com/ && !params.mode) { params.mode = 'canada' }
		if (params?.creditCard) {
	   		log.debug "action: $actionName query string: "+params.toQueryString().replaceAll(/creditCard=\d*/, 'creditCard=**** **** **** ****')
		} else {
			log.debug "action: $actionName params: $params flash: $flash"
		}
    }
    def afterInterceptor = {
		println '&&&&&&&&&&&&&&&&' + params.u
		if (params.creditCard) {
	   		log.debug "after action: $actionName query string: "+params.toQueryString().replaceAll(/creditCard=\d*/, 'creditCard=**** **** **** ****')
		} else {
	        log.debug "after action: $actionName params: $params"
		}
    }
   	
   	def products = {
		try {
	   		params.max = params.max?.toInteger()?:((['bikes', 'patio','table tennis'].find{it == params.division}) ? 3:4)
	   		params.rowSize = params.rowSize?.toInteger()?:params.max
	   		params.offset = params.offset?params.offset.toInteger():0 as int
		} catch(NumberFormatException nfe) {
			log.error nfe.toString()
			render "Your request has been flagged as an SQL hack attack. Please contact Kettler if this is in error."
			return
		}
		if (!params.division?.size() || params.division?.replaceAll(/\s|\+|(%20)/, '') == 'kettlerusa') {			
			log.warn "forward to home due to invalid params.division: $params.division"
			redirect controller:'homePage', action:'home'
			return
		}
		if (params.division?.replaceAll(/\s|\+|(%20)/, '') == 'tabletennis') {//redirect may have mangled
			params.division = 'table tennis'
		}
		def mode = getMode(request.serverName)
	    if (['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName} && params.division == 'bikes') {
			params.noShowFilter = true
	   		params.max = (params.max>3)?params.max:4
	   		params.rowSize = 4
		}
		def items = []
   		int totalItems = 0
    	if (params.division == 'patio' && (params.accessoriesFilter || params.tablesFilter || params.chairsFilter || params.cushionsFilter )) {
    		items = ItemMasterExt.patioFilter(params.accessoriesFilter, params.tablesFilter, params.chairsFilter, params.cushionsFilter,  null, params.order, mode).list(params)
    		totalItems = ItemMasterExt.patioFilter(params.accessoriesFilter, params.tablesFilter, params.chairsFilter, params.cushionsFilter, null, null, mode).count()
    	} else {
    		switch (params.bikeFilter) {
    		case 'Special Order':
    			params.sort = 'retailPrice'
    			params.order = params.order?:'desc'
	    		items =       ItemMasterExt.division(params.division, 0, params.order, mode).findAllBySpecialOrder(true, params)
	    		totalItems =  ItemMasterExt.division(params.division, 0, null, mode).countBySpecialOrder(true)
	    		break
    		case 'In Stock':
    			def inStock = []
	    		ItemMasterExt.division(params.division, 0, params.order, mode).findAllBySpecialOrder(false).each {item ->
	    			if (item.isWebAvailable(item)) {
	    				inStock << item
	    			}
	    		}
    			inStock = inStock.sort{it.retailPrice}
				if (params.order == 'desc') {
					inStock = inStock.reverse()
				}
	    		totalItems = inStock.size()
	    		items = inStock[params.offset..(params.offset+(Math.min(params.max,totalItems-params.offset)).intValue()-1)]
	    		break
    		default:
	    		items =       ItemMasterExt.division(params.division, params.max, params.order, mode).list(params)
	    		totalItems =  ItemMasterExt.division(params.division, 0, null, mode).count()
    		}
    	}
    	def lifeStyleImages = []
    	if (params.max < 5) { 
	    	File imageFiles = ApplicationHolder.application.parentContext.getResource("images/${params.division?.replaceAll(/^\/\w*/,'')}").file
	    	try { 
		       	imageFiles.eachFile {file ->
					if (file.name.startsWith('life-style') && !file.name.find(/FULL/)) {
						lifeStyleImages << file.name
					}
				}
	    	} catch (FileNotFoundException fnfe) {
	   			flash.message = message(code:'localized.lifestyle.image.not.found', args:[params.division]) //"Division: ${params.division} lifestyle image(s) not found."
   	   			log.warn flash.message+" params:$params"
   				redirect controller:'homePage', action:'home'
	    		return
	    	}
    	}
		def categories = servletContext.catByDivMap[params.division]
        if (params.division == 'bikes') { // don't show accessories
        	categories = servletContext.catByDivMap[params.division].findAll{it.name != 'accessories' && it.name != 'child carriers'}
        } else if (params.division == 'table tennis') { // put accessories last
    		categories = servletContext.catByDivMap[params.division].findAll{it.name != 'accessories'}
    		categories += servletContext.catByDivMap[params.division].findAll{it.name == 'accessories'}
        } else if (params.division == 'toys') { // put tricycles first 
        	categories = servletContext.catByDivMap[params.division].findAll{it.name == 'tricycles'}
        	categories += servletContext.catByDivMap[params.division].findAll{it.name != 'tricycles'}
        }
		Map seoCategoryNameMap = [:]
		categories.each{cat ->
			seoCategoryNameMap[seoCategoryName(cat.name)] = cat.name?.replaceAll(' ','-')
		}
		def seoDivisionName = (params.division == 'patio')?'patio-furniture':params.division?.replaceAll(' ','-')
   		render view:'products', 
    		model:[items:items, totalItems:totalItems, 
				   division:params.division, seoDivisionName:seoDivisionName,  seoCategoryNameMap:seoCategoryNameMap,
    		       categories:categories, lifeStyleImages:lifeStyleImages,
				   cart:Cart.get(session.cartId),
    		       division:WebDivision.findByName(params.division), params:params] 
   	}
	private String seoCategoryName(String category) {
		switch (category) {
		case 'e-bikes':
			return 'electric-bikes'
		case 'ride-ons':
			return 'ride-ons'
		case 'balance-bikes':
			return 'balance bikes and scooters'
		default:
			return category?.replaceAll(/ /,'-')
		}
	}
   
   	def accessories = {
   		def item = ItemMasterExt.get(params.id)
   		def accessoryItems = []
   		if (item) {
   			log.error "ItemMasterExt.get($params.id) returned null"
	   		item.accessories.each {
	   			accessoryItems << it.accessory 
	   		}
   		}
   		render template:'products', model:[items:accessoryItems, params:params]
   	}

   	def search = {
   		params.max = params.max?.toInteger()?:8
        if (params.previousWhere) {
        	params.where += ', '+params.previousWhere
        }
    	String searchStr = ItemMasterExt.convertCountry(params.where)
    	searchStr = searchStr?.replaceAll(/"/, '')
    	if (!searchStr.trim()) {
    		searchStr = 'null' // fake empty search so it so doesn't fail
    	}
        def search
        try {
            search = ItemMasterExt.search([max:1000]) {        
                must( queryString(searchStr) ) 
                //must(term("inactive", 'S')) can't figure out how to "searchable" this so used findAll below...
                mustNot(term("parts", true))   
                mustNot(term("commercial", true))   
            }
        } catch (e) {
			flash.message = message(code:'localized.invalid.search', args:[searchStr]) //"invalid search: ${searchStr}"
   			redirect controller:'homePage', action:'home'  
        }
   		params.division = params.division?:'toys'
        [items:search?.results.findAll{it.inactive == 'S'}, cart:Cart.get(session.cartId), params:params, search:true]
   	}   		

   	def archives = {
   		if ( !params.division?.size() ) {
   			log.warn "redirect to home due to null params.division"
   			redirect controller:'homePage', action:'home'  
			return
   		}
        try {
	   		params.max = params.max?.toInteger()?:((['patio','table tennis'].find{it == params.division}) ? 3:4)
	   		params.rowSize = params.rowSize?.toInteger()?:params.max
        } catch (NumberFormatException nfe) {
	   		params.max = (['patio','table tennis'].find{it == params.division}) ? 3:4
	   	    params.rowSize = params.max
        }
		params.division = params.division?.replaceAll(/\+|(%20)/, ' ')
		boolean closeouts = false
		boolean archives = true
		def mode = getMode(request.serverName)
    	def items = []
    	int totalItems = 0
		items =      ItemMasterExt.division(params.division, params.max, params.order, mode, closeouts, archives).list(params)
		totalItems = ItemMasterExt.division(params.division, 0, null, mode, closeouts, archives).count()
		[items:items, totalItems:totalItems, cart:Cart.get(session.cartId), params:params]
   	}
   	def closeouts = {
   		if ( !params.division?.size() ) {
   			log.warn "redirect to home due to null params.division"
   			redirect controller:'homePage', action:'home'  
			return
   		}
        try {
	   		params.max = params.max?.toInteger()?:((['patio','table tennis'].find{it == params.division}) ? 3:4)
	   		params.rowSize = params.rowSize?.toInteger()?:params.max
        } catch (NumberFormatException nfe) {
	   		params.max = (['patio','table tennis'].find{it == params.division}) ? 3:4
	   	    params.rowSize = params.max
        }
		params.division = params.division?.replaceAll(/\+|(%20)/, ' ')
		boolean closeouts = true
		def mode = getMode(request.serverName)
    	def items = []
    	int totalItems = 0
		//items =      ItemMasterExt.division(params.division, params.max, params.order, mode, closeouts).list(params)
		totalItems = ItemMasterExt.division(params.division, 0, null, mode, closeouts).count()
		params.max = totalItems
		items =      ItemMasterExt.division(params.division, params.max, params.order, mode, closeouts).list(params)
		[items:items, totalItems:totalItems, params:params, keywords:WebDivision.findByName(params.division)?.keywords]
   	}
    def category = {
		switch (params.category) {
		case 'closeouts':
			redirect action:'closeouts', params:params
			break
		case 'electric-bikes':
			params.category = 'e-bikes'
			break
		case 'e bikes':
			params.category = 'e-bikes'
			break
		case 'ride ons':
			params.category = 'ride-ons'
			break
		case 'balance-bikes':
			params.category = 'balance bikes and scooters'
			break
		default:
			break
		}
		params.category = (['e-bikes','ride-ons'].find{it == params.category})?params.category:params.category?.replaceAll(/-|\+|(%20)/, ' ')
   		params.offset = params.offset?.toInteger()?:0
   		if ( !params.division?.size() || !params.category) {
   			log.warn "redirect to home due to null params.division or params.category"
   			redirect controller:'homePage', action:'home'  
			return
   		}
   		if (params.collection) {
   			redirect action:'collection', params:params 
			return
   		}
        if (!params.max && params.category == 'accessories' && params.division != 'bikes') {
        	params.max = 4
        }
        try {
	   		params.max = params.max?.toInteger()?:((['bikes', 'patio','table tennis'].find{it == params.division}) ? 3:4)
	   		params.rowSize = params.rowSize?.toInteger()?:params.max
        } catch (NumberFormatException nfe) {
	   		params.max = (['patio','table tennis'].find{it == params.division}) ? 3:4
	   	    params.rowSize = params.max
        }

		if (session.compareMap) {
			def item = ItemMasterExt.get(session.compareMap.find{true}.key) // get first
			if (item.category.name != params.category) {
				session.compareMap = null
			}
		}
		params.sort = 'retailPrice'
		params.order = params.order?:'desc'
//		params.sort = 'articleGroupClass'
//		params.order = 'ASC'
		
		params.division = params.division?.replaceAll(/\+|(%20)/, ' ')
		params.category = (params.category=='e-bikes')?params.category:params.category?.replaceAll(/\+|(%20)/, ' ')
    	def lifeStyleImages = []
    	if (params.max < 5) { 
	    	File imageFiles = ApplicationHolder.application.parentContext.getResource("images/${params.division?.replaceAll(/^\/\w*/,'')}/${params?.category}").file
	    	try { 
		       	imageFiles.eachFile {file ->
					if (file.name.startsWith('life-style') && !file.name.find(/FULL/)) {
						lifeStyleImages << file.name
					}
				}
	    	} catch (FileNotFoundException fnfe) {
	   			flash.message = message(code:'localized.division.category.invalid', args:[params.division,params.category]) //"Division: ${params.division} category: ${params.category} not found."
   	   			log.warn flash.message+" params:$params"
   				redirect controller:'homePage', action:'home'
	    		return
	    	}
    	}
		def mode = getMode(request.serverName)
	    if (params.division == 'bikes' && ['www.kettlerlatinoamerica.com', 'www.kettlercanada.com'].find{ it == request.serverName} ) {
			params.noShowFilter = true
	   		params.max = (params.max>3)?params.max:4
	   		params.rowSize = 4
		}
    	def items = []
    	int totalItems = 0
    	if (params.division == 'patio' && (params.accessoriesFilter || params.tablesFilter || params.chairsFilter || params.cushionsFilter)) {
    		items =      ItemMasterExt.patioFilter(params.accessoriesFilter, params.tablesFilter, params.chairsFilter,  params.cushionsFilter, params.category, params.order, mode).list(params)
    		totalItems = ItemMasterExt.patioFilter(params.accessoriesFilter, params.tablesFilter, params.chairsFilter,  params.cushionsFilter, params.category, null, mode).count()
    	} else {
    		switch (params.bikeFilter) {
    		case 'Special Order':
    			params.sort = 'retailPrice'
      			params.order = params.order?:'desc'
	    		items =       ItemMasterExt.category(params.division, params.category, 0, params.sort, mode).findAllBySpecialOrder(true, params)
	    		totalItems =  ItemMasterExt.category(params.division, params.category, 0, null, mode).countBySpecialOrder(true)
	    		break
    		case 'In Stock':
    			def inStock = []
	    		ItemMasterExt.category(params.division, params.category, 0, params.sort, mode).findAllBySpecialOrder(false).each {item ->
	    			if (item.isWebAvailable(item)) {
	    				inStock << item
	    			}
	    		}
    			inStock = inStock.sort{it.retailPrice}
				if (params.order == 'desc') {
					inStock = inStock.reverse()
				}
	    		totalItems = inStock.size()
	    		if (totalItems) {
	    			items = inStock[params.offset..(params.offset+(Math.min(params.max, totalItems-params.offset))-1)]
	    		}
	    		break
    		default:
        		items =      ItemMasterExt.category(params.division, params.category, params.max, params.order, mode).list(params)
        		totalItems = ItemMasterExt.category(params.division, params.category, 0, null, mode).count()
    		}
    	}
    	def webDivision = WebDivision.findByName(params.division)
		[items:items,
		 totalItems:totalItems,
		 lifeStyleImages:lifeStyleImages,
		 collections:servletContext.collections.find{it.key == params.category},
		 category:WebCategory.findByNameAndDivision(params.category, webDivision), 
     	 cart:Cart.get(session.cartId),
		 params:params, mode:mode
		]
    }
   	
   	def collection = { // always patio
   		params.max = params.max?.toInteger()?:3
   		params.rowSize = params.rowSize?.toInteger()?:params.max
		params.division = 'patio'
		params.category = params.category?.replaceAll(/\+|(%20)/, ' ')
		params.sort = 'retailPrice'
		params.order = params.order?:'desc'
   				
   		def collection = params.collection?.toLowerCase()?.replaceAll(' ', '\\ ')?:'none'
   		File imageFiles = ApplicationHolder.application.parentContext.getResource("images/patio/${(params?.category?.replaceAll(' ', '\\ '))}/${collection}").file    				
   		def lifeStyleImages = []
   		try {
	       	imageFiles.eachFile {file ->
				if (file.name.startsWith('life-style') && !file.name.find(/FULL/)) {
					lifeStyleImages << file.name
				}
			}
		} catch (FileNotFoundException fnfe) {
			flash.message = message(code:'localized.no.div.add.cart', args:[params.division, params.category]) //"Division: ${params.division} category: ${params.category} not found."
  			log.warn flash.message+" params:$params"
			redirect controller:'homePage', action:'home'
			return
		}
    	def webDivision = WebDivision.findByName(params.division)
		def mode = getMode(request.serverName)
   		render view:'category', 
   			model:[items:ItemMasterExt.collection(params.collection, mode).list(params),
					 totalItems:ItemMasterExt.collection(params.collection, mode).count(),
					 lifeStyleImages:lifeStyleImages, specificCollection:collection,
					 category:WebCategory.findByNameAndDivision(params.category, webDivision), 
					 collections:servletContext.collections.find{it.key == params.category},
					 params:params 
					]
   	}
	private getMode(def serverName) {
		['canada', 'store', 'contract', 'latinoamerica'].find{request.serverName ==~ /www.kettler$it\.com/}
   	}
   	def detail = {
   		if (!(params.id ==~ /\d+/)) { // probably not a browser request
   			flash.message = message(code:'localized.invalid.page.request') //"Invalid detail page request"
   			log.warn flash.message+" params:$params"
			redirect controller:'homePage', action:'home'
			return
   		}
   		def item = ItemMasterExt.get(params.id)
   		if (!item) { // probably not a browser request
   			flash.message = message(code:'localized.invalid.item', args:[params.id]) //"Request for detail on an invalid item: $params.id"
   			log.warn flash.message+" params:$params"
			redirect controller:'homePage', action:'home'
			return
   		}
   		
   		[item:item, otherImages:getOtherImages(item), pdfNames:getPdfNames(item),
		 collections:servletContext.collections.find{it.key == item.category.name},
    	 cart:Cart.get(session.cartId),
		 bikeSizes:ItemMasterExt.getBikeFrameSizes(item),
		 patioColors:ItemMasterExt.getPatioColors(item)
   		]   		
   	}
   	private List getOtherImages(ItemMasterExt item) {
   		def divScrunched = item.division.name?.replaceAll(/^\/\w*/,'')
   		File imageFiles = ApplicationHolder.application.parentContext.getResource("images/${divScrunched}/${item.category.name}").file    				
    	def otherImages = []
    	imageFiles.eachFile {file ->
			if (file.name ==~ /${item.itemNo}\.[A-Z]?\..*/) {
				if (otherImages.size() < 4) {
					otherImages << file.name
				}
			}
	   	}
		return otherImages
   	}
   	private List getPdfNames(ItemMasterExt item) {
   		def divScrunched = item.division.name?.replaceAll(/^\/\w*/,'')
   		File pdfs = ApplicationHolder.application.parentContext.getResource("manuals/${divScrunched}/${item.category.name}").file   
    	def pdfNames = []
    	pdfs.eachFile {file ->
    	    if (item.division.name == 'patio' && file.name[0..3] == item.itemNo[0..3] ||
			    file.name ==~ /${item.itemNo}.*/) {
				pdfNames << file.name
			}
	   	}
		return pdfNames
   	}
    	
   	def youtube = {
      def item = ItemMasterExt.get(params.id)
      if (item) {
        render template:'detailYouTube', model:[item:item]
      } else {
        render "youtube video for Item id: ${params.id} not found"
      }
   	}
   	def youTubeAssembly = {
      def item = ItemMasterExt.get(params.id)
      if (item) {
   		  render template:'detailYouTube', model:[item:item, youTubeAssembly:true]
      } else {
        render "youtube video for Item id: ${params.id} not found"
      }
   	}
   	
   	def itemMainImage = {
  		ItemMasterExt item = ItemMasterExt.get(params?.id)
      if (item) {
		render template:'detailMainImage', 
		       model:[division:item.division, category:item.category, youTubeId:item.id, 
		         			      item:item, itemWithOtherColor:item]
      } else {
        render "main image not found"
      }

   	}
   	def itemWithOtherColorImage = {
   		ItemMasterExt item = ItemMasterExt.get(params.id)
  		if (!item) {
  			log.error "null = ItemMasterExt.get($params.id)"
  			render 'not found'
  			return
  		}
   		ItemMaster itemWithOtherColor = getItemWithSelectedColor(item, params.itemNoColorSuffix)
  		File image = ApplicationHolder.application.parentContext.getResource("images/${item.division.name}/${item.category.name?.replaceAll(/ /, '\\ ')}/${itemWithOtherColor.itemNo}.jpg").file
  		if (!image.isFile()) {
  			render 'not found'
  		} else {
  			render template:'detailMainImage', 
  					model:[division:item.division, category:item.category, youTubeId:item.id, 
  					       item:item, itemWithOtherColor:itemWithOtherColor]
  		}
   	}

   	private ItemMaster getItemWithSelectedColor(ItemMasterExt item, String itemNoColorSuffix) {
		if (itemNoColorSuffix) {
			def matcher = item.itemNo =~ "(.*)\\d{${itemNoColorSuffix.size()},${itemNoColorSuffix.size()}}\$"
			def itemColorPrefix = matcher[0][1]
			if (item.itemNo != "$itemColorPrefix$itemNoColorSuffix") {
				log.debug "ItemMaster.findByCompCodeAndItemNo($item.compCode, $itemColorPrefix$itemNoColorSuffix)"
				ItemMaster itemWithSelectedColor = ItemMaster.findByCompCodeAndItemNo(item.compCode, "$itemColorPrefix$itemNoColorSuffix")
				if (!itemWithSelectedColor) {
					flash.message = message(code:'localized.no.inv.for.color') //"Sorry, inventory is unavailable for your selected color."
					log.debug flash.message  
		   	   		render view:'detail', model:[item:item, otherImages:getOtherImages(item), cmd:cmd, cart:cart]   		
	       			return null
				}
				return itemWithSelectedColor
			}
		}
   		return item
   	}
   	def buy = {BuyCommand cmd ->
		if (!params.division) { // probably not a browser request
   			flash.message = message(code:'localized.no.div.add.cart') //"Invalid add to cart request: division not passed"
   			log.warn flash.message+" for buy action params:$params"
			redirect controller:'homePage', action:'home'
			return
   		}
   		flash.message = null
   		ItemMasterExt item = ItemMasterExt.get(params.id)
		if (!item) { 
   			flash.message = message(code:'localized.bad.item.no.add.cart') //"Invalid add to cart request: invalid item number"
   			log.warn flash.message+" for buy action params:$params"
			try {
				sendMail {
					subject "KETTLER ShopController buy action error: invalid item."
					body cmd.toString() 
					from CH.config.app.error.email.from.address
				}
			} catch(e) {log.error "error email for: KETTLER ShopController buy action error: invalid item(${params.id}. failed to send."}
			redirect controller:'homePage', action:'home'
			return
   		}
   		if (!cmd.validate()) {
            flash.message = messageSource.getMessage(cmd.errors.allErrors[0], Locale.getDefault())
			redirect controller:'homePage', action:'home'
   			return
   		}
   		def cart  
   		if (!session.cartId) {
   			cart = new Cart()
   			assert cart
   			assert !cart.hasErrors()
   			if (session.consumer) {
   				cart.consumer = session.consumer
   			}
   		} else {
   			cart = Cart.get(session.cartId)
   		}
	    if (cart?.items.find{it.item.arDistrictCode.equals("GFC")}) {
            flash.message = message(code:'localized.no.giftcard.with.shipment') //"Sorry, an order with a gift card cannot be added to a order that requires shipment"
            render template:'/shop/cart', model:[cart:cart, division:item.division.name, params:params]
			return
		}  
   		ItemMaster itemIdWithColor
   		if (item?.division?.name == 'patio') {
   			itemIdWithColor = ItemMaster.get(params.itemIdWithColor)
   		}  
   		def cartItem = new CartItem(qty:cmd.quantity, item:item)
   		switch (item.division.name) {
   		case 'patio':
   			cartItem.itemIdWithColor = cmd.itemIdWithColor
   			break
   		case 'bikes':
   			cartItem.itemIdWithFrameSize = cmd.itemIdWithFrameSize
   			break
   		}
   		cart.addToItems(cartItem)
   		if (item.truck) {
			cart.upsServiceCode = '99'
   		}
   		if (!cart.save(flush:true)) {
   	   		render view:'detail', model:[item:item, otherImages:getOtherImages(item), cmd:cmd, cart:cart, 
   	   		                             bikeSizes:ItemMasterExt.getBikeFrameSizes(item), 
		                                 patioColors:ItemMasterExt.getPatioColors(item),
                                         params:params
                                        ]
   			return 
   		}
		if (cart.total() < 40g) {
			flash.message = message(code:'localized.increase.order') //'Increase your order to $40 to get free shipping to the continental US'
		}
        if (cart?.consumerBillTo?.zipCode) {
	  		doUpsRate(cart)
        } else if (session.shipZip && !['99', '03', '21'].find{it == cart.upsServiceCode}) { 
			recalcShipping()
        } 
   		session.cartId = cart.id
   		if (params.ajax) {
			if (session.consumer) {
				cart.consumerBillTo = session.consumer?.billTos?.toArray()[0]
				if (session.consumer.shipTos) {                                                              
					cart.consumerShipTo = session.consumer.shipTos?.toArray()[0]
				}
			}
   			render template:'cart', model:[cart:cart, params:params]
   			return
   		}
   		render view:'cart', model:[cart:cart, division:item.division.name]	 
   	}
   	   	
   	def cart = {
   		def cart = Cart.get(session.cartId)
		if (!cart) {
			flash.message = message(code:'localized.cart.empty') //"Your shopping cart is empty"
			redirect action:'products', params:params
			return
		}
   		[cart:cart,division:params.division]
   	}

   	def updateItem = { 
   		def item = CartItem.get(params.id?.toInteger()?:0)
   		if (item) {
			try {              
		   		item.qty = params.quantity?.toInteger()?:0
			} catch (NumberFormatException nfe) {
		   		item.qty = 0
			}
	   		if (!item.qty) {
	   			 redirect action:'removeItem', id:params.id, params:[cart:cart, division:item.item.division.name] 
	   			 return
	   		}
   		}
		if (item && item.qty < item.item.minQty) {
			item.discard()
			flash.message = message(code:'localized.item.min.qty.invalid', args:[item?.item?.minQty?:0]) //"Must purchase a minimum of ${item.item.minQty}"
   			redirect action:'cart', params:[division:item.item.division.name] 
   			return
		}

   		def cart = Cart.get(session.cartId)
        if (cart?.consumerBillTo?.zipCode) {
			doUpsRate(cart) // old webservice style
   		} else if (!['99', '03', '21'].find{it == cart?.upsServiceCode}) { 
			recalcShipping() // new HTTP style and handles transient addresses from popup
        } 
		if (params.ajax) {
   			render template:'cart', model:[cart:cart, params:params]
   			return
		}
   		render view:'cart', model:[cart:cart, division:item?.item?.division?.name?:'kettlerusa']
   	}
   	def clearSession = { // used to test stale session
   		session.cartId = null
   		session.consumer = null
   		assert session.cartId == null
   	}
   	def removeItem = {
		def cart = Cart.get(session.cartId)
		if (!cart) {
			flash.message = message(code:'localized.cart.empty') //"Your shopping cart is empty"
			redirect action:'products', params:params
			return
		}
		def item
		try {  
	   		item = CartItem.get(params.id?.toInteger()?:0)
	   		cart.items.remove(params.id?.toInteger()?:0)
	   	} catch (NumberFormatException e) {
	   		log.warn "NumberFormatException on params.id: $params.id" 
	   	}
   		if (item) {
   	   		cart.items.remove(params.id.toInteger())
   			cart.removeFromItems(item)
			if (cart.truck) {
				cart.upsServiceCode = '99' // Truck
			} else if (cart.upsServiceCode == '99') {
			
				if (cart.rateService.equals("UPS")){
					cart.upsServiceCode = '03' // UPS
				} else {
					cart.upsServiceCode = '21' // FEDEX
				}
				
				
			}
   			if (cart.total() < 40g) {
			    flash.message = message(code:'localized.increase.order') //'Increase your order to $40 to get free shipping to the continental US'
   			}
			if (cart.items.size()) {
		        if (cart?.consumerBillTo?.zipCode) {
					doUpsRate(cart) // old webservice style
		   		} else if (!['99', '03', '21'].find{it == cart.upsServiceCode}) { 
					recalcShipping() // new HTTP style and handles transient addresses from popup
		        } 
			} else {
				cart.shippingCost = 0g
			}
   		}

  		if (params.checkout == '') {
  			params.remove('checkout')
  		}
   		if (params.ajax) {
   			render template:'cart', model:[cart:cart, division:params.division?:'kettlerusa', ajax:true, params:params]
   		} else {
   			render view:'cart', model:[cart:cart, division:params.division?:'kettlerusa', params:params]
   		}
   	}

   	def addCoupon = {
   		def coupon = Coupon.findByNo(params?.couponNo?:'')
   		if (!coupon) {
   			flash.message = message(code:'localized.coupon.no.invalid', args:[params.couponNo]) //"Coupon $params.couponNo is not found"
   			redirect action:'cart', params:params
   			return
   		}
   		/* 
   		 * if coupon for item, item should be on the order
   		 * Closeout items will be excluded from any coupon promotions.
   		 */
		def cart = Cart.get(session.cartId)
   		if (coupon.item) {
   			def cartItem = cart.items.find {it.item.id == coupon.item.id} 
   			if (cartItem) {
   				cartItem.coupon = coupon
   				cartItem.validate()
   			}
   		} else if (coupon.division ){
   			def cartItemDiv = cart.items.find {it.item.division == coupon.division} 
   			if (cartItemDiv) {
   				cartItem.coupon = coupon
   				cartItem.validate()
   			} else {
			   cart.errors.rejectValue('coupon', "cart.coupon.invalid")
			}
   		} else if (coupon.category ){
   			def cartItemCat = cart.items.find {it.item.category == coupon.category} 
   			if (cartItemCat) {
   				cartItem.coupon = coupon
   				cartItem.validate()
   			} else {
			   cart.errors.rejectValue('coupon', "cart.coupon.invalid")
			}
   		} else {
   			cart.couponId = coupon.id
   			cart.validate()
   		}
   		params.couponNo = null
   		render view:'cart', model:[cart:cart, division:params.division]
   	}

   	private cleanup(def session, def cartId) {
   		def cart = Cart.get(cartId)
		if (cart && !cart.saveAccount) {
			cart.delete()
			log.debug "deleted cart"
		}
   		session.cartId = null
		if (session.consumer && !session.consumer.password) { // delete
			log.debug "deleting consumer:"+session.consumer.name
			session.consumer.refresh()
			session.consumer.delete()
		}
   		session.consumer =  null
   	}
   	def cancel = { 
   		cleanup(session, session.cartId)
		forward action:'products', params:params   		
   	}

   	def billInfoStatic = {
   		CheckoutCommand cmd = new CheckoutCommand()
		cmd.setFromConsumer(session.consumer)
		cmd.setBillTo(ConsumerBillTo.get(params.billToId.toInteger())) 
		render template:'billInfoStatic', model:[cmd:cmd]
   	}
   	
   	def shipInfoStatic = {
   		CheckoutCommand cmd = new CheckoutCommand()
		cmd.setFromConsumer(session.consumer)
		cmd.setShipTo(ConsumerShipTo.get(params.shipToId.toInteger())) 
		render template:'shipInfoStatic', model:[cmd:cmd]
   	}
   	   	
   	def billInfoUpdate = {
   		CheckoutCommand cmd = new CheckoutCommand()
		cmd.setFromConsumer(session.consumer)
	    cmd.setBillTo(ConsumerBillTo.get(params.billToId.toInteger()))
   		render template:'billInfoUpdate', model:[cmd:cmd]
   	}

   	def shipInfoUpdate = {
   		CheckoutCommand cmd = new CheckoutCommand()
		cmd.setFromConsumer(session.consumer)
	    cmd.setShipTo(ConsumerShipTo.get(params.shipToId.toInteger()))
   		render template:'shipInfoUpdate', model:[cmd:cmd]
   	}
   	
   	def checkoutPrompt = {
		if (CH.config.checkout.type == '3page') {
			redirect controller:'checkout', action:'index', params:params
			return
		}
   		Cart cart = Cart.get(session.cartId)
		if (!cart) {
			flash.message = message(code:'localized.cart.empty') //"Your shopping cart is empty"
			redirect uri:"/${params.division?.replaceAll(/ /, '-')}/", params:params
			return
		}
   		CheckoutCommand cmd = new CheckoutCommand()
   		cmd.division = params.division
   		if (params.password) {
   			def consumer = Consumer.findByEmail(params.email)
   			if (consumer?.password == params.password) {
   				session.consumer = consumer
   			} else {
   				cmd.errors.reject("message.code","Invalid login credentials");
   				log.debug "Invalid login credentials"
   			}
   		}
   		if (session.consumer) {
   			log.debug "session.consumer: $session.consumer"+session.consumer.billTos
   			if (!session.consumer.billTos) {
   				session.consumer = Consumer.get(session.consumer.id) // in case billTos were added
   			}
   			if (!session.consumer.billTos) { // if billTos still empty
   				flash.message = message(code:'localized.add.bill.to') //"Please add a bill-to before checkout"
   				redirect controller:'consumer', action:'show', id:session.consumer.id, params:params
   				return
   			}
   			cmd.setFromConsumer(session.consumer)
   			cart.consumerBillTo = session.consumer.billTos?.toArray()[0]
   			if (session.consumer.shipTos) {                                                              
   				cart.consumerShipTo = session.consumer.shipTos?.toArray()[0]
   			}
   			doUpsRate(cart)
   		} 
   		
   		if (params.password && session.consumer && !cart.consumer) {
			cart.consumer = session.consumer
   	   		doUpsRate(cart)
   		}
   		
		if (cart.rateService.equals( "UPS")){   
		   cmd.upsServiceCode = cart.upsServiceCode?:'03'
		} else {
			cmd.upsServiceCode = cart.upsServiceCode?:'21'
		}
		    
   		params.checkout = true
   		cart = Cart.get(session.cartId)
   	    render view:'checkout', model:[cmd:cmd, cart:cart, params:params]
   	}
   	private boolean saveAccountOK(Cart cart, CheckoutCommand cmd) {
   		cart.email = cmd.email
   		cart.phone = cmd.phone?.replaceAll(/\D/,'').toLong()
   		cart.registerWarranty = cmd.registerWarranty
   		cart.saveAccount = cmd.saveAccount
		if (session.consumer) {
			session.consumer = Consumer.findByEmail(cmd.email)
		}
   		
		ConsumerBillTo billTo = null
		ConsumerShipTo shipTo = null
   		Consumer consumer = null
   		boolean withTranClosureRanOK = true
   		Consumer.withTransaction { status ->
   			consumer = session.consumer?:new Consumer(email:cmd.email, password:cmd.password, name:cmd.billingName)
			if (cmd.billToId && cmd.billToUpdateOrAdd == CheckoutCommand.UPDATE) {
				billTo = ConsumerBillTo.get(cmd.billToId)
			} else {
				billTo = new ConsumerBillTo()
			}
   			consumer.saveAccount = cmd.saveAccount
			consumer.phone = cmd.phone.toLong()
			billTo.name = cmd.billingName // TODO billTo may be blank or billingName in some scenarios
			billTo.addr1 = cmd.billingAddress1
			billTo.addr2 = cmd.billingAddress2
			billTo.city = cmd.billingCity
			billTo.state = cmd.billingState
			billTo.zipCode = cmd.billingZip
			billTo.cardType = cmd.creditCardType
			billTo.cardNo = cmd.creditCard
			billTo.ccid = cmd.ccid
			billTo.expYear = cmd.year
			billTo.expMonth = cmd.month
			billTo.consumer = consumer
			billTo = billTo.unique(consumer.billTos)
			if (!billTo.id) {
				consumer.addToBillTos billTo
			}
			if (cmd.shipInfoDiff) {
				if (cmd.shipToId && cmd.shipToUpdateOrAdd == CheckoutCommand.UPDATE) {
					shipTo = ConsumerShipTo.get(cmd.shipToId)
				} else {
					shipTo = new ConsumerShipTo()
				}
				shipTo.name = cmd.shippingName
				shipTo.addr1 = cmd.shippingAddress1
				shipTo.addr2 = cmd.shippingAddress2
				shipTo.city = cmd.shippingCity
				shipTo.state = cmd.shippingState
				shipTo.zipCode	 = cmd.shippingZip
				shipTo = shipTo.unique(consumer.shipTos)
				if (!shipTo.id) {
					consumer.addToShipTos shipTo
					//shipTo.save()//flush:true) // to gen id before adding to Cart
				}
				if (Environment.current == Environment.TEST) {// mockDomain doesn't always add an id
					int max = 1
					ConsumerShipTo.list().each {
						if (it.id && it.id > max) {
							max = it.id
						}
					}
					shipTo.id = max++
				}
			}
			if (!consumer.validate()) {
				// consumer is not available to page (in session) so stuff errors in the cmd 
				consumer.errors.each {
					it.getAllErrors().each {
						log.error it.field
						if (it.field == 'email') {
							cmd.errors.reject("message.code", "There already is a user registered with ${consumer.email}, please use a different password or login");
						} else {
							cmd.errors.reject("message.code",it.toString());
						}
					}
				}
		   		withTranClosureRanOK = false
		   		return false
			} 
			if (!consumer.save()) { // flush:true)) { // flush so we can stuff the gen'd id in cart
				consumer.errors.each {println it}
			} 
			session.consumer = consumer // in case we added one on the fly
			if (Environment.current == Environment.TEST) {// mockDomain doesn't always add an id
				int max = 1
				ConsumerBillTo.list().each {
					if (it.id && it.id > max) {
						max = it.id
					}
				}
				billTo.id = max++
			}
   		}
		if (!withTranClosureRanOK) {
			return false
		}
		// force updates so RPG update trigger encrypts (insert not working...) 
   		Consumer.withTransaction { status ->
   			consumer.billTos.each{bill ->
   				bill.addr2 = bill.addr2?bill.addr2+' ':' '
   				bill.save()
   			}
   		}
		if (shipTo) {
			cart.consumerShipTo = shipTo
		}
		if (cart.truck) {
			cart.upsServiceCode = '99'
		} else {
			cart.upsServiceCode = cmd.upsServiceCode
		}
		cart.consumerBillTo = billTo
		cart.consumer = consumer
		if (!cmd.shipInfoDiff) {
			cart.shipToId = 0
		}
		doUpsRate(cart)
		return true
   	}
   	def checkout = {CheckoutCommand cmd ->
   		flash.message = ''
		Cart cart = Cart.get(session.cartId)
		if (!cart) {
			flash.message = message(code:'localized.cart.empty') //"Your shopping cart is empty"
			redirect action:'products', params:params
			return
		}
		if (!cmd.validate() || session?.consumer?.hasErrors()) {
		    render view:'checkout', model:[cart:cart, cmd:cmd, consumer:session?.consumer, params:params]
		    return
		}
		boolean upsRateOK = false
		if (saveAccountOK(cart, cmd)) {
			if (!cart.truck) {
				if (cart.consumerBillTo) {
		   			upsRateOK = doUpsRate(cart)
		   		}
		   		if (upsRateOK) {
					def addr = cart.consumerShipTo?:cart.consumerBillTo
		   			UpsReturnAddress[] upsRtnAddrs = upsAddressService.upsAddress(addr.name, addr.addr1, addr.addr2, addr.city, addr.state, addr.zipCode)	
		   			if (!upsRtnAddrs || !upsRtnAddrs[0].valid) {
						cmd.errors.reject("message.code", "Invalid address. ${(upsRtnAddrs?'Suggested addresses:':'')}");
						upsRtnAddrs.each {cmd.errors.reject("message.code", it.addressLine1+', '+(it.addressLine2?it.addressLine2+',':'')+' '+it.city+', '+it.state+' '+it.zip)}
		   			} 
		   		}
			}
			if (params.next == 'Next' && (cart.truck || !cmd.hasErrors())) {
				render view:'verifyOrder', model:[cmd:cmd, cart:cart, params:params]
			}
		} 
		[cmd:cmd, cart:cart, params:params]
   	}
   	def placeOrder = {
		Cart cart = Cart.get(session.cartId)
		if (!cart) {
			flash.message = message(code:'localized.cart.empty') //"Your shopping cart is empty"
			redirect action:'products', params:params
			return
		}
		CreditCardReturn ccCheck = preAuthorizationService.preAuth(cart)
		if (!ccCheck.valid && CH.config.dataSource.driverClassName == 'com.ibm.as400.access.AS400JDBCDriver') {
			flash.message = ccCheck.codeOrRouting
			redirect action:'checkoutPrompt', params:[division:params.division?:'toys']
			return
		}
		OrderHeader orderHeader = orderService.write(cart, ccCheck.codeOrRouting)
		sendMail {
			to cart.email
			subject "Your KETTLER Order"
			body view:"confirmation", plugin:"email-confirmation", model:[cart:cart, orderHeader:orderHeader, emailTask:true]
			from "orders@kettlerusa.com"
		}
		cart.items.each {item ->
			item.item.qtyAlloc += item.qty
			if (!item.item.save()){
				item.item.errors.each {log.error it}
				assert false
			}
			def itemWhseUpdate = ItemWarehouse.findWhere([compCode:item.item.compCode, itemNo:item.item.itemNo, warehouse:'1'])
			if (itemWhseUpdate) {
				itemWhseUpdate.qtyAlloc += item.qty
				if (!itemWhseUpdate.save()){
					itemWhseUpdate.errors.each {log.error it}
					assert false
				}
			}
		}
		if (cart.registerWarranty) {
			def warrantyCustomer = WarrantyCustomer.findByEmail(cart.email)
			if (!WarrantyCustomer.findByEmail(cart.email)) {
				warrantyCustomer = new WarrantyCustomer(
						email:			cart.email,
						lastOrCorpName:	cart.consumerBillTo.name,
						addr1:			cart.consumerBillTo.addr1,
						addr2:			cart.consumerBillTo.addr2?:'',
						city:			cart.consumerBillTo.city,
						state:			cart.consumerBillTo.state,
						zipCode:		cart.consumerBillTo.zipCode,
						phone:			cart.phone.toString()
						)
				warrantyCustomer.id = orderHeader.orderNo
				if (!warrantyCustomer.save()) {
					warrantyCustomer.errors.each {log.error it}
					assert false
				}
			}
			int seq = 0
			def maxSeq = WarrantyItem.executeQuery( "select max(sequenceNo) from WarrantyItem where custNo = :wcusid",  [wcusid:warrantyCustomer.id])
			if (maxSeq[0]) {
				seq = maxSeq[0]
			}  
			cart.items.each {item ->
				if (item.item.residentialWarrantyCode) {
					def warrantyPeriod = item.item.getResidentialWarrantyPeriod()
					def warrantyItem = new WarrantyItem(
								compCode:		item.item.compCode,
								itemNo:			item.item.itemNo,
								itemDesc:		item.item.desc,
								purchaseDate: 	new Date(),
								purchaseQty:	item.qty,
								period: 		warrantyPeriod,
								expirationDate: new Date() + 
											((warrantyPeriod.years * 365) + warrantyPeriod.days)
								) 
					warrantyItem.custNo = warrantyCustomer.id
					warrantyItem.sequenceNo = ++seq 
					if (!warrantyItem.save()) {
						warrantyItem.errors.each {log.error it}
						assert false
					}
				}
			}
		}
		session.cartId = null 
		params.division='kettler usa'
		// note if cart.saveAccount is false confirmate.gsp auto-invokes ajax to action:deleteCart
		render view:'confirmation', model:[cart:cart, orderHeader:orderHeader, divisions:WebDivision.retail.list().sort(), emailTask:false, params:params]
   	}
   	def deleteCart = { 
		cleanup(session, params.id)
		render 'deleted'
   	}
   	def addrUpdate = {CheckoutCommand cmd ->
	   	if (!cmd.validate()) {
			def template = (params.shipToOrBillTo == 'billTo')?'billInfoUpdate':'shipInfoUpdate'
			render template:template, model:[cmd:cmd]
		    return
	   	}
		Cart cart = Cart.get(session.cartId)
		if (!saveAccountOK(cart, cmd)) {
			def template = (params.shipToOrBillTo == 'billTo')?'billInfoUpdate':'shipInfoUpdate'
			render template:template, model:[cmd:cmd]
		    return
		}
		def template = (params.shipToOrBillTo == 'billTo')?'billInfoStatic':'shipInfoStatic'
		render template:template, model:[cmd:cmd]
   	}
   	def addrUpdateCancel = {CheckoutCommand cmd ->
   		def template = (params.shipToOrBillTo == 'billTo')?'billInfoStatic':'shipInfoStatic'
   		render template:template, model:[cmd:cmd]
   	}
   	def refreshCartPopup = {
		render template:'cart', model:[cart:Cart.get(session.cartId)]
   	}
   	def upsRate = {CheckoutCommand cmd ->
   		flash.message = ''
	   	if (!cmd.validate()) {
			cmd.errors.each {log.debug it}
		    render "error"
		    return
	   	}
		Cart cart = Cart.get(session.cartId)
		if (!saveAccountOK(cart, cmd)) {
		    render "error"
		    return
		}
		doUpsRate(cart)
		render template:'cart', model:[cart:cart]
	}
	// uses the old webservice style but works, replace with recalShipping first time it needs to be tweaked
   	private doUpsRate(Cart cart) {
   		boolean ok = true
		println '****************************    ${cart.dump()}'
   		if (!cart?.consumerBillTo?.zipCode) {
   			return // can't rate without an address
   		}
		cart.shippingCost = 0.00g // recalculated if order is less than $40
		if ('99' == cart.upsServiceCode || ('03' == cart.upsServiceCode && cart.subTotal() >= 40.00g) || ('21' == cart.upsServiceCode && cart.subTotal() >= 40.00g)) {
			cart.shippingCost = 0.00g
			log.debug "shippingCost set to zero"   		
		} else { 
	    	try {
				if (CH.config.rateService.toUpperCase().equals('UPS')){
					cart.shippingCost = upsRateService.upsRate(cart)
					log.debug "$cart.shippingCost = upsRateService.upsRate(cart)"
				} else if (CH.config.rateService.toUpperCase().equals('FEDEX')){
					cart.shippingCost = fedexService.getShippingCost(cart)
		    		log.debug "$cart.shippingCost = fedexService.getShippingCost(cart)"   		
				}
	    	} catch (e) {
	    		log.error e.printStackTrace()
	    		cart.shippingCost = 0.00g
				if (cart.rateServicede.equals("UPS")) {
					cart.upsServiceCode = Cart.GROUND
				} else if (cart.rateService.equals("FEDEX")) {
					cart.upsServiceCode = Cart.GROUNDFEDEX
				}
	    		def zeroDimOrWeightItem = cart.items.find {!it.item.dimLength || !it.item.dimWidth || !it.item.dimHeight || !it.item.unitWeight}
	    		if (zeroDimOrWeightItem) { 
	    			flash.message = message(code:'localized.dim.weight.zero', args:[zeroDimOrWeightItem.item.desc]) //"${zeroDimOrWeightItem.item.desc} has a zero dimension or weight so UPS cannot return a rate"
					sendMail {
						to CH.config.app.error.email.to.addresses.toArray()
						subject "KETTLER retail web app issue (but not failure) occurrent in ${CH.config.rateService.toUpperCase()} rating."
						body "${zeroDimOrWeightItem.item.itemNo}:${zeroDimOrWeightItem.item.desc} has a zero dimension or weight so ${CH.config.rateService.toUpperCase()} could not return a rate"
						from CH.config.app.error.email.from.address
					}
	    		} else {
					if (CH.config.rateService.toUpperCase().equals('UPS')){
						flash.message = message(code:'localized.ups.address.wrong') //"UPS can not return a shipping cost. Your address may be incorrect."
					} else if (CH.config.rateService.toUpperCase().equals('FEDEX')){
						flash.message = message(code:'localized.fedex.address.wrong') //"FEDEX can not return a shipping cost. Your address may be incorrect."
					}
	    		}
	    		ok = false
	    	}
		}
		cart.save()
		return ok
   	}

   	def largerImage = {
   		String imageName  
   		File  imageFile       
   		def item = ItemMasterExt.get(params.id)
   		if (item) {
   	   		imageName = (params.otherImageName?:item.itemNo)+'_FULL.jpg'
   	   		imageFile = ApplicationHolder.application.parentContext.getResource("images/$item.division.name/$item.category.name/$imageName").file      
   		} else {
   			log.error "null = ItemMasterExt.get($params.id)"
   	   		imageName = 'image_not_found.gif'
   	   		imageFile = ApplicationHolder.application.parentContext.getResource("images/$imageName").file      
   		}
  		render template:'largerImage', model:[item:item, imageName:imageName, dim:Image.getImageDimension(imageFile.absolutePath)]
   	}
	       	
   	/** Perform a bulk index of every searchable object in the database */
    def indexAll = {
        Thread.start {searchableService.index()}
        render("bulk index started in a background thread")
    }
    /** Perform a bulk index of every searchable object in the database*/
    def unindexAll = { 
        searchableService.unindex()
        render("unindexAll done")
    }
	// uses the new  HTTP-API style UPS API
	def recalcShipping(params) {
		Cart cart = Cart.get(session.cartId)
		if (!cart) {
			return null
		}
		if (params?.upsServiceCode) {
			cart.upsServiceCode  = params.upsServiceCode
		}
		AddressesCommand addrCmd
		PayShipCommand payShipCmd
	    if (session.consumer) {
	    	addrCmd = session.addrCmd?:upsService.populateAddressesCommand(session.consumer)
	    	payShipCmd = session.payShipCmd?:upsService.populatePayShipCommand(session.consumer)
	    } else {
    	    addrCmd = session.addrCmd?:new AddressesCommand(type:AddressesCommand.GUEST)
			addrCmd.billingName = 'ignore'
			addrCmd.phone = '8882538853'
			addrCmd.billingAddress1 = params?.shipStreet?.toUpperCase()?:session?.shipStreet?.toUpperCase()?:'ignore'
			addrCmd.billingCity = params?.shipCity?.toUpperCase()?:session?.shipCity?.toUpperCase()?:'ignore'
			addrCmd.billingState = params?.shipState?.toUpperCase()?:session?.shipState?.toUpperCase()
			addrCmd.billingZip = params?.shipZip?:session?.shipZip
			addrCmd.shipToSameAsBillTo = true
    	    payShipCmd = session.payShipCmd?:new PayShipCommand()
			if (params?.shipZip) {
				session.shipState = params.shipState
				session.shipZip = params.shipZip
				session.shipCity = params.shipCity
				session.shipStreet = params.shipStreet
			}
	    }
   	    payShipCmd.upsServiceCode = cart.upsServiceCode
		try {
			boolean checkAddress = false

			def rateS = CH.config.rateService
			if (CH.config.rateService.equals('UPS')){
				cart.shippingCost = upsService.getShippingCost(cart, addrCmd, payShipCmd, checkAddress)
			} else if (CH.config.rateService.equals('FEDEX')){
				cart.shippingCost = fedexService.getShippingCost(cart, addrCmd, payShipCmd, checkAddress)
				log.debug "$cart.shippingCost = fedexService.fedexRate(cart)"
			}
			
			
			def zeroWeightItem = cart.items.find {!it.item.unitWeight}
			println ("&&&&&&&&&&&&&&&&&&&&&&&&&&zero weight item = ${zeroWeightItem}")
			if (zeroWeightItem) {
				println("****************email ${zeroWeightItem.dump()}")
				sendMail {
					to CH.config.app.error.zeroWeight.to.address.toArray()
					subject "KETTLER retail web app issue (but not failure) occurred in ${CH.config.rateService.toUpperCase()} rating."
					body "${zeroWeightItem.item.itemNo}:${zeroWeightItem.item.desc} has a zero weight so ${CH.config.rateService.toUpperCase()} weight defaulted to ${CH.config.defaultWeight}}"
					from CH.config.app.error.email.from.address
				}
//				try {
//					mailService.sendMail {
//						to ["dcrocker.usa@gmail.com","dcrocker@handhtech.com"]
//						subject "Your KETTLER Order"
//						body  view:"/checkout/checkout/confirm", plugin:"email-confirmation", model:[cart:cart, addrCmd:addr, payShipCmd:payShip, orderHeader:orderHeader, emailTask:true]
//						from "orders@kettlerusa.com"
//					}
//				} catch (e) {
//					log.warn("sendMail failed:"+e)
//				}
		
			} 

		} catch (Exception e) {
			if (!cart.shippingCost){
				cart.shippingCost = 0g
			}
			println "dang it"
			e.printStackTrace()
			log.warn "upsService.getShippingCost: "+e.getCause()
            if (cart.upsServiceCode == '03' || cart.upsServiceCode == '21') {
			    flash.message = message(code:'localized.invalid.state.zip', args:[addrCmd.billingState, addrCmd.billingZip]) //" Invalid State/Zip: ${addrCmd.billingState}/${addrCmd.billingZip}. Correct your address and reclick the service type"
            } else {
                flash.message = " Invalid address. Please correct your address and reclick the service type"
            }
			
			if (cart.upsServiceCode == '03') {
				cart.upsServiceCode = Cart.GROUND
			} else if (cart.upsServiceCode == '21') {
				cart.upsServiceCode = Cart.GROUNDFEDEX
			}
		}
		try{
			cart.save()
		} catch (Exception e){
			log.warn "cart.Save(): "+e.getCause()
		}
		cart
   	}
	def shippingCost = {
		Cart cart = recalcShipping(params)
		if (params.ajax) {
			render template:'cart', model:[cart:cart, division:params.division?:'kettlerusa', ajax:params.ajax, params:params]
			return
		}
		def divName = 'kettlerusa'
		if (cart?.items?.toArray()?.size()) {
			divName = cart.items.toArray()[0].item.division.name
		}
		render g.createLink(action:'cart')+"?division=${divName}"
   	}

}
class BuyCommand {
	int id
	int quantity
	int itemIdWithFrameSize = 0
	int itemIdWithColor = 0
	boolean mustPickFrameSize = false
	boolean mustPickColor = false
	static constraints = {
        id validator:{id, cmd ->
			def item = ItemMasterExt.get(cmd.id)
            if (!item) {
                return ['default.not.found.message', 'item', cmd.id.toString()]
            }
        }
		quantity min:1, validator:{ quantity, cmd ->
			def item = ItemMasterExt.get(cmd.id)
			if (item && quantity < item.minQty) {
				return ['buyCommand.minQty.notmet', item.minQty]
			}
		}
		itemIdWithFrameSize validator:{ val, cmd ->
			if (cmd.mustPickFrameSize && val == 0) {
				return 'buyCommand.itemIdWithFrameSize.invalid'
			}
		}
		itemIdWithColor validator:{ val, cmd ->
			if (cmd.mustPickColor && val == 0) {
				return 'buyCommand.itemIdWithColor.invalid'
			}
		}
	}
	String toString() {"id: $id quantity: $quantity itemIdWithColor: $itemIdWithColor itemIdWithFrameSize: $itemIdWithFrameSize mustPickFrameSize: $mustPickFrameSize"}
}
