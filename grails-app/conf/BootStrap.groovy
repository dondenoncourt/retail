import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.item.share.WebDivision
import com.kettler.domain.item.share.WebCategory

class BootStrap {

    def init = { servletContext ->
    	String.metaClass.capitalizeNames = { 
    		delegate.split(' ').collect{ it.capitalize() }.join(' ')
    	}

    	Map collections = [:]
		ItemMasterExt.withCriteria {
            projections {
                distinct(['category','collection'])
            }
            ne ('collection', '')
		}.each {
		    if (collections[it[0].name]) {
		    	collections[it[0].name.toLowerCase()] << it[1].trim()
		    } else {
		    	collections[it[0].name.toLowerCase()] = [it[1].trim()]
		    }
		}
		servletContext.collections = collections
		// now sort collections with tables and accessories at the end
		collections = [:]
		servletContext.collections.each{key, value -> 
			collections[key] = value.findAll{it != 'Tables' && it != 'Accessories'}.sort()
			value.findAll{it == 'Tables' || it == 'Accessories'}.sort().each {
				collections[key] << it
			} 
		}
		servletContext.collections = collections
		
		println "collections: $collections"

		def catByDivMap = [:]           
		WebDivision.retail.list().each {division ->
			def cats = []
			WebCategory.findAllByDivision(division).sort {it.name}.each {cat ->
				cats << cat
			}
			catByDivMap[division.name] = cats
		}
		catByDivMap['fitness'] = catByDivMap['fitness'].findAll{it.name != 'more'}.sort{it.name}
		servletContext.catByDivMap = catByDivMap.sort()
		println "servletContext.catByDivMap: ${servletContext.catByDivMap}"
    }
    def destroy = {
    }
}
