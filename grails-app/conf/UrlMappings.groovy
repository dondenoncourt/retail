class UrlMappings {

	static mappings = {
		"/"(controller:'homePage', action:'home')
		"/sitemap"(controller:'sitemap', action:'sitemap')
		//"500"(view:'/error')
		"500"(controller:'error', action:'error')	
		"404"(controller:'error', action:'notFound')
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		// these two oddities are required for pagination tag that began to work oddly are adding the SEO-URL mappings
		"/paginate/products"  {controller='shop';  action='products'; }
		"/paginate/category"  {controller='shop';  action='category'; }
		
		"/bikes"  {controller='shop';  action='products'; division='bikes';	}
		"/bikes/$category/"  {controller='shop';action='category';division='bikes';}
		"/bikes/$category/$id"  {controller='shop';action='detail';division='bikes';}
		"/bikes/giftCard"  {controller='giftCard';action='index';division='bikes';}
		"/fitness"  {controller='shop';  action='products'; division='fitness';	}
		"/fitness/$category/"  {controller='shop';action='category';division='fitness';}
		"/fitness/$category/$id"  {controller='shop';action='detail';division='fitness';}
		"/fitness/giftCard"  {controller='giftCard';action='index';division='fitness';}
		"/table-tennis"  {controller='shop';  action='products'; division='table+tennis';	}
		"/table-tennis/$category/"  {controller='shop';action='category';division='table+tennis';}
		"/table-tennis/$category/$id"  {controller='shop';action='detail';division='table+tennis';}
		"/table-tennis/giftCard"  {controller='giftCard';action='index';division='table+tennis';}
		"/toys"  {controller='shop';  action='products'; division='toys';	}
		"/toys/$category/"  {controller='shop';action='category';division='toys';}
		"/toys/$category/$id"  {controller='shop';action='detail';division='toys';}
		"/toys/giftCard"  {controller='giftCard';action='index';division='toys';}
		"/patio"  {controller='shop';  action='products'; division='patio';	}
		"/patio-furniture"  {controller='shop';  action='products'; division='patio';	}
		"/patio-furniture/$category/"  {controller='shop';action='category';division='patio';}
		"/patio-furniture/$category/$id"  {controller='shop';action='detail';division='patio';}
		"/patio-furniture/giftCard"  {controller='giftCard';action='index';division='patio';}
		"/contact-us"  {controller='homePage';action='contactUs';}

		"/about-us" {controller='homePage';  action='aboutUs';	}
		"/faq" {controller='homePage';  action='infoEdu';	}
		"/testimonials" {controller='homePage';  action='custInfo';	}
		"/awards" {controller='homePage';  action='awards';	}
		"/manuals" {controller='homePage';  action='manuals';	}
		"/parts" {controller='homePage';  action='partsService';	}
		"/warranty" {controller='homePage';  action='preRegister';	}
		"/warranty/preRegister" {controller='homePage';  action='preRegister';	}
		"/warranty/register" {controller='homePage';  action='register';	}
	}
}
