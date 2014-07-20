import java.text.SimpleDateFormat


import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.item.share.ItemMasterExt
import com.kettler.domain.item.share.WebDivision
import com.kettler.domain.item.share.WebCategory
 
class SitemapController {
    def beforeInterceptor = {
    	log.debug "controller: sitemap action: $actionName params: $params flash: $flash"
    }
    def index = {
        redirect(action: "sitemap", params: params)
    }

	def sitemap = {
   		def homeUrls =  
        		["http://www.kettlerusa.com/",
				 "http://www.kettlerusa.com/about-us",
				 "http://www.kettlerusa.com/faq",
				 "http://www.kettlerusa.com/testimonials",
				 "http://www.kettlerusa.com/awards",
				 "http://www.kettlerusa.com/manuals",
				 "http://www.kettlerusa.com/parts",
				 "http://www.kettlerusa.com/articles/ergometer.gsp?division=kettler%20usa",
				 "http://www.kettlerusa.com/articles/ebikes.gsp?division=kettler%20usa",
				 "http://www.kettlerusa.com/articles/chooseBikeSize.gsp?division=kettler%20usa",
				 "http://www.kettlerusa.com/articles/bbbenefits.gsp?division=kettler%20usa",
				 "http://www.kettlerusa.com/articles/ttrules.gsp?division=kettler+usa"
				 
        		]
    	def now = new Date()
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    	SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm")
		def sw = new StringWriter()
		def sitemap = new groovy.xml.MarkupBuilder(sw)
		sitemap.urlset {
    		homeUrls.each {homeUrl ->
			url{ 
					loc(homeUrl)
					lastmod(dateFormat.format(now)+'T'+timeFormat.format(now)+'+00:00') // 2009-06-18T18:22:27+00:00
					changefreq("daily")
					priority("0.50")
				}
	   		}
    		WebDivision.retail.list().each {division ->
				url{ 
					loc("http://www.kettlerusa.com/${division.seoName}")
					lastmod(dateFormat.format(now)+'T'+timeFormat.format(now)+'+00:00') // 2009-06-18T18:22:27+00:00
					changefreq("daily")
					priority("0.50")
				}
				division.categories.each {category ->
					url{ 
						loc("http://www.kettlerusa.com/${category.division.seoName}/${category.seoName}")
						lastmod(dateFormat.format(now)+'T'+timeFormat.format(now)+'+00:00') // 2009-06-18T18:22:27+00:00
						changefreq("daily")
						priority("0.50")
					}
				}
    		}
			ItemMasterExt.list().each {item ->
				if (item.division && item.category) {
					url{ 
						loc("http://www.kettlerusa.com/${item.division.seoName}/${item.category.seoName}/${item.id}")
						lastmod(dateFormat.format(now)+'T'+timeFormat.format(now)+'+00:00') // 2009-06-18T18:22:27+00:00
						changefreq("daily")
						priority("0.50")
					}
				}
			}
		}	

		render(text:'<?xml version="1.0" encoding="UTF-8"?>'+
			sw.toString().replaceAll('<urlset>', '<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.google.com/schemas/sitemap/0.84 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">'),
			contentType:"text/xml",encoding:"UTF-8"
		)
	}
}
