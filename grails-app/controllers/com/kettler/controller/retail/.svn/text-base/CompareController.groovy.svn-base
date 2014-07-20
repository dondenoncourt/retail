package com.kettler.controller.retail
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import com.kettler.domain.item.share.ItemMasterExt
 
class CompareController {
    static allowedMethods = [compare: "POST"]

    def beforeInterceptor = {
        log.debug "action: $actionName params: $params flash: $flash"
    }

    def afterInterceptor = {
        log.debug "after action: $actionName params: $params"
    }

    def compare = {
        def compareItems = session.compareMap?.keySet() as List
		if (!compareItems) {
			flash.message = message(code:'localized.compare.count.invalid') // "Please select two to four items to compare"
			redirect uri:"/$params.division/$params.category", params:params
			return
		}
		session.compareMap = null
//        log.debug "comparing ${compareItems.size()}"
        def items = []
        compareItems.each {
            def itemId = it.substring(it.indexOf("_")+1)
//            log.debug "looking for item id: ${itemId}"
            def anItem = ItemMasterExt.get(itemId as Long)
//            log.debug "${anItem.itemNo} : ${anItem.desc}"
            items << ItemMasterExt.get(itemId as Long)
        }
        def imageLabel = ''

        def theTable="<table id='compareTable'><tr><td>${imageLabel}</td>"
        items.each {
            theTable += "<td>"
            def imageName = "${g.resource(dir: "images/")}${it?.division.name}/${it?.category.name.replaceAll(/-/,' ')}/${it?.itemNo}.jpg"
            theTable += "<img src=\"" + imageName +"\" width=\"90%\" alt=\"" + it.desc + "_image\"  />"
            theTable += "</td>"
        }
        def productLabel = g.message(code: "compare.${params.category}.productLabel", default: "Name")
        theTable += "</tr><tr><td class='label'>${productLabel}: </td>"
        items.each {
            theTable += "<th>${it?.desc}</th>"
        }
        theTable += "</tr>"

        // Now add all the comparison features
        def labels = []
        def compareMap = [:]
        buildCompareItems(items, labels, compareMap)
        (0..labels.size()-1).each { i ->
            theTable += "<tr>"
            theTable += "<td class='label'>${labels[i]}: </td>"
            items.each { item ->
                def values = compareMap.get(item.itemNo)
                theTable += "<td>${values.get(labels[i])}</td>"
            }
            theTable += "</tr>"
        }
        theTable += "<tr><td class='label'>Price: </td>"
        items.each { item ->
//            log.debug("retailPrice: ${item.retailPrice}, mode: ${params.mode}, WebAv: ${item.isWebAvailable(item)}")
            theTable += "<td>"
            if (item.retailPrice && !params.mode && item.isWebAvailable(item)) {
                def price = g.formatNumber(number:(item.closeoutCode?item.specialPrice:item.retailPrice), type:"currency",
                        currencyCode:"USD")
//                log.debug("price: ${price}")
                theTable += "<span class=\"price\">${price}</span>"
            }
            theTable += "</td>"
        }
        theTable += "</tr>"
		theTable += "<tr><td/></tr>" // a cheap way of spacing before the Add to Cart button row
        theTable += "<tr><td></td>"
        items.each { item ->
            theTable += "<td>"
            if (item.retailPrice && !params.mode) {
                if (item.isWebAvailable(item)) {
                    if (item.division.name == 'bikes' && ItemMasterExt.getBikeFrameSizes(item)) {
                        def linkParams = [division:params?.division,mode:params?.mode]
                          theTable += g.link(action:"detail", controller:"shop", id:item?.id, params:linkParams,
                               class:"clickme",  title:"click to select frame size for ${item.desc}","Add to Cart")
                    } else {
                        def linkParams = [quantity:item.minQty,ajax:true,division:params?.division]
//                        log.debug("cart params: ${linkParams}")
                        theTable += g.remoteLink(class:"clickme", action:"buy", id:item.id, controller:"shop",
                           update:"floatingCart", params:linkParams,
                           onSuccess:"setCartClose();addToMiniCart();",title:"click to add ${item.desc} to your cart",
                        "Add to Cart")
                    }
                } else {
                    theTable += "<span class=\"outOfStock\">Out of stock</span><br/>"
                }
            }
            theTable += "</td>"
        }
        theTable += "</tr>"
        theTable += "</table>"
        [division: params.division, category: params.category, compareTable: theTable]
    }

	def editList = {
		if (!session.compareMap) {
			session.compareMap = [:]
		} 
		if (params.checked == 'true') {
			session.compareMap.put params.id, ItemMasterExt.get(params.id)?.desc
		} else {
			session.compareMap.remove(params.id)
		} 
		println session.compareMap
		render 'changed'
	}

    /**
     * Give a list of items, return a list of comparison labels and a map (key itemNo, value is map
     * key is comparison label and value is text for comparison table)
     * @param items list of ItemMasterExt
     * @param labels list of comparison labels
     * @param m map (key is itemNo, value is list of values for each label)
     */
    private void buildCompareItems(List<ItemMasterExt> items, List<String> labels, Map<String,List<String>> m) {
        def parser = new org.cyberneko.html.parsers.SAXParser()
        parser.setFeature('http://xml.org/sax/features/namespaces', false)
        def slurper = new XmlSlurper(parser)
        boolean first = true
        def numberOfLabels
        items.each { item ->
            def s = g.render(template:"/features/${item.itemNo}",model:[theItem:item])
//            log.debug "template: ${s}"
            def html = slurper.parseText("<html><body>${s}</body></html>")
            def div = html.BODY.DIV
            def dts
            if (first) {
                first = false
                dts = div.DL.DT
                numberOfLabels = dts.size()
                (0..numberOfLabels-1).each { index ->
                    labels << dts[index].text()
                }
            }
            def dds = div.DL.DD
            def values = [:]
            (0..numberOfLabels-1).each { index ->
                values.put(labels[index], dds[index].text())
            }
            m.put(item.itemNo, values)
        }
    }
}
