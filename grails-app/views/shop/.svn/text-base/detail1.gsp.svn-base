<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<%@ page import="com.kettler.domain.item.share.ItemMaster" %>
<%@ page import="com.kettler.domain.item.share.Dealer   " %>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder as CH" %>
 <%
	  def mode = ''
	  if (params.mode == 'canada' || request.serverName ==~ /www.kettlercanada.com/) { 
	 	mode = 'canada'
	  } else if (params.mode == 'contract' || request.serverName ==~ /www.kettlercontract.com/) { 
	 	mode = 'contract'
	  } else if (params.mode == 'store' || request.serverName ==~ /www.kettlerstore.com/) { 
	 	mode = 'store'
	  } 
 %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${item.desc} | ${item.division.name} | ${item.category.name} </title>
    <meta name="description" content="${item.metaDesc?:item.desc}"/> 
    <meta name="keywords" content="${(item.keywords?:'')}"/>     
    <meta name="layout" content="${(['contract','store'].find{it == mode})?mode:(item.division.name.replaceAll(' ',''))}" />
</head>
  
<body>        
    <g:javascript library="jquery" plugin="jquery"/>
    <link href="${createLinkTo(dir:'css',file:'jquery-ui.css')}" rel="stylesheet" type="text/css" /> 
    <g:javascript src="jquery-ui.min.js"/> 
    <g:if test="${flash.message}">
        <div class="errors">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${cart}">
        <div class="errors"><g:renderErrors bean="${cart}" as="list"  /></div>
    </g:hasErrors>
    <g:hasErrors bean="${cmd}">
        <div class="errors"><g:renderErrors bean="${cmd}" as="list"  /></div>
    </g:hasErrors>
    <!--  test hot-deploy -->
    <div id="buy">
		<div id="detailProductTitle"><h1>${item?.desc}</h1></div>
        <div id="otherImages">  
            <g:each in="${otherImages}" var="otherImage">
                <div>
                    <a href="#">
                       <img class="otherImage" id="${otherImage?.replaceAll(/.jpg/,'')}" 
                            src="${resource(dir:'/images/'+item.division.name+'/'+item.category.name, file:otherImage)}" 
                            alt="${item.imageAlt?:item.desc}" title="${item.imageTitle?:item.desc}"/>
                    </a>
                </div>
            </g:each> 
            <div>
                <a href="#"><img class="otherImage" id="${item.itemNo}" 
                	src="${resource(dir:"images/")}${item.division.name}/${item.category.name}/${item.itemNo}.jpg" 
                	alt="${item.imageAlt?:item.desc} other image" title="${item.itemNo}:${item.imageTitle?:item.desc}" /></a>
            </div>
        </div>
		<div id="mainImageImg">
                <g:render template='detailMainImage' 
                          model="${[division:item.division, category:item.category, itemNo:item.itemNo, youTubeId:item.id, itemWithOtherColor:item]}"/>
			
		</div>
		
		<div id="divMadeIn">
				 <% if (item.countryOfOrigin == 'DEU') { %>
				<img class="madeIn" src="${resource(dir:'/images')}/made_in_germany.png" 
					alt="made in germany" title="made in Germany"
				/>
			<% } else if (item.countryOfOrigin == 'ITA') { %>
				<img class="madeIn" src="${resource(dir:'/images')}/made_in_italy.png" 
					alt="made in Italy" title="made in Italy"
				/>
			<% } else if (item.countryOfOrigin == 'USA') { %>
				<img class="madeIn" src="${resource(dir:'/images')}/made_in_usa.png" 
					alt="made in united states of america" title="made in United States of America"
				/>
			<% } %>
		</div>
		
        <div id="mainImage">
			
            
            <% if (mode) { %>
                <div id="divAfter"> 
	                <% if (['contract','store'].find{it == mode}) { %>
	                    <g:link controller="${mode}" action="requestForQuote" id="${item.id}" class="button" title="Click to fill out a RFQ form for this item.">
	                      Request for Quote
	                    </g:link>
	                <% } else if (mode == 'canada') { %>
                       <% if (item.division.name == 'toys') { %>
	                        <g:form name="locate" action="index" controller="locate">
	                              <input type="hidden" name="division" value="${item.division.name}"/>
	                              <g:hiddenField name="itemId" value="${item.id}" />
	                              <g:hiddenField name="mode" value="${mode}" />
	                              <g:submitButton name="locate" value="Where to Buy"  class="button"/>
	                        </g:form>
                       <% } %>
                       <% if (item.msrpCanada) { %>
                            <br/>
                           <span class="price">MSRP: <g:formatNumber number="${item.msrpCanada}" type="currency" currencySymbol="\$" maxIntegerDigits="4"/></span>
                       <% } %>

	                <% } %>
                </div>
            <% } else { %> 
                <div id="divAfter">
                    <% if (item.dealerId) { %>
                        <div id="exclusiveDealer">
                            <h2>Available exclusively</h2>
                            <h2>in the US at:</h2>
                            <a href="#" onclick="window.open('http://${Dealer.get(item.dealerId).website}')"> 
                                <img src="<g:createLink action='renderLogo' controller="dealer" id='${item.dealerId}'/>" alt="Dealer Logo">
                            </a>
                        </div>
                    <% } else { %>
                        <% if (request.serverName != 'www.kettlerlatinoamerica.com') { %>
                            <div id="fb-root"></div>
                            <div id="likeFacebook" class="iLike">
                            <script>(function(d){
                              var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
                              js = d.createElement('script'); js.id = id; js.async = true;
                              js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
                              d.getElementsByTagName('head')[0].appendChild(js);
                            }(document));</script>
                            <div class="fb-like" data-send="false" data-layout="button_count" data-width="100" data-show-faces="true"></div>
                            </div>
                            <% if (!(item.division.name ==~ /.*\s.*/) && !(item.category.name ==~ /.*\s.*/) ) { %>
                                <div id="likePinterest" class="iLike">
                                     <a href="http://pinterest.com/pin/create/button/?url=http://www.kettlerusa.com/${item.division.name.replaceAll(/\s/, '-')}/${item.category.name.replaceAll(/\s/, '-')}/${item.id}&media=${resource(absolute:true,dir:"images/")}${item.division.name}/${item.category.name}/${item.itemNo}.jpg&description=${item.imageTitle?:item.desc}" 
                                        class="pin-it-button" count-layout="horizontal">i
                                        Pin It
                                     </a>
                                     <script type="text/javascript" src="http://assets.pinterest.com/js/pinit.js"></script>
                                </div>
                            <% } %>
                            <g:javascript>
                                function lineUpLikeLinks() {
                                  $('.iLike').css('left', $('#header div.navSecond').offset().left+700+'px');
                                }
                                lineUpLikeLinks(); 
                                $(window).resize(function() {
                                  lineUpLikeLinks();
                                });
                            </g:javascript>						
                            <g:form name="buyForm" action="buy" controller="shop">
                                <input type="hidden" name="division" value="${item.division.name}"/>
                                <% if (item.retailPrice) { %>
                                       <% if (item.isWebAvailable(item)) { %>
                                            <g:hiddenField name="id" value="${item.id}" />
                                            <dl>
                                                <% if (item.specialPrice) { %>
                                                    <dt>MSRP:</dt>
                                                    <dd class="crossout">
                                                        <span class="crossoutnot">
                                                            <g:formatNumber number="${item.msrp}" type="currency" currencySymbol="\$" />
                                                        </span>
                                                    </dd>
                                                    <dt class="important">Special Price:</dt>
                                                    <dd class="important"><g:formatNumber number="${item.specialPrice}" type="currency" currencySymbol="\$" /></dd>
                                                <% } else { %>
                                                    <dt class="dtPrice">Price:</dt>
                                                    <dd class="ddPriceValue"><g:formatNumber number="${item.retailPrice}" type="currency" currencySymbol="\$" /></dd>
                                                <% } %>
                                            </dl>
                                        <% } else { %>
                                        Out of stock<br/>
                                        <% } %>
                                        <% if (item.specialOrder) { %>
                                            <br/>
                                            <p><span class="important">Special Order:</span></p>
                                            <p>&nbsp;This item is a special order and will be delivered in ${item.leadTimeFrom} - ${item.leadTimeTo} weeks. 
                                              Credit card will be billed at time of purchase.</p>
                                           <g:javascript>
                                            $('#mainImage').height($('#mainImage').height()+60);
                                            $('#buyForm').css('width', '380px');
                                           </g:javascript>
                                        <% } %>
                                <% } %>
                                <% if (item.retailPrice && item.isWebAvailable(item)) { %>
                                   <dl id="framesizeQty">
                                        <% if (bikeSizes) { %>
                                            <dt>Frame Size:</dt>
                                            <dd>
                                                <select name="itemIdWithFrameSize">
                                                    <option value="0">-pick one-</option> 
                                                    <g:each var="size" in="${bikeSizes}">
                                                       <option value="${size.id}">${size.size} ${(size.sizeUom=='IN'?'"':'CM')}</option> 
                                                    </g:each>
                                                </select>
                                                <a href="#" id="questionFrameSize" title="Click to view a how-to on selecting frame sizes"><img src="${resource(dir:"images/")}question16x16.png"  /></a> 
                                                <input type="hidden" name="mustPickFrameSize" value="true"/>
                                            </dd>                                       
                                        <% } else if (patioColors) { %>
                                            <dt>Color:</dt>
                                            <dd>
                                                <select name="itemIdWithColor">
                                                    <option value="0">-pick one-</option> 
                                                    <g:each var="color" in="${patioColors}">
                                                       <option value="${color.id}">${color.color?:'invalid'}</option> 
                                                    </g:each>
                                                </select>
                                                <input type="hidden" name="mustPickColor" value="true"/>
                                            </dd>                                       
                                        <% } %>
										</div>
                                        <dt class="dtQuantity">Quantity:</dt><dd><g:textField name="quantity" class="number" value="${cmd?.quantity?:item.minQty}" tabindex="1"/>
                                                          </dd>
                                   </dl>
                                   <g:submitButton name="buy" id="buyId" value="Add to Cart" class="button clickme"/><br/>
                                   
                                   <span class="emphasis">free</span>
                                    <% if (item.retailPrice < 40g) { %>
                                        Shipping on orders over $40.00 to the continental US
                                    <% } else if (item.truck) { %>
                                        Truck Shipping ${((item.retailPrice >= 999g)?'and Inside Delivery':'')}
                                        <a href="#" id="questionTruckShipping"><img src="${resource(dir:"images/")}question16x16.png"  /></a> 
                                        <g:javascript> 
                                            var message = '${(CH.config.questionTruckShippingInfo.replaceAll(/\n/,''))}';
                                            <% if (item.retailPrice >= 999g) { %>
                                               // $('#buyForm').css('width', '400');
                                                message = '${(CH.config.questionInsideDelivery.replaceAll(/\n/,''))}';
                                            <% } %>
                                            $('#questionTruckShipping').click(function() {
                                                alert(message);
                                            });
                                        </g:javascript>        
                                    <% } else { %>
                                        Ground Shipping 
                                        <a href="#" id="questionUpsShipping"><img src="${resource(dir:"images/")}question16x16.png"  /></a> 
                                        <g:javascript>
                                            $('#questionUpsShipping').click(function() {
                                                alert('${(CH.config.questionUpsShippingInfo.replaceAll(/\n/,''))}');
                                            });
                                        </g:javascript>        
                                    <% } %>
                                    <% if (item.truck) { %>
                                        <br/><span class="fineprint">(Usually arrives in 4-10 business days)</span> 
                                    <% } else { %>
                                        <br/><span class="fineprint">(Usually arrives in 4-8 business days)</span> 
                                    <% } %>
                                <% } else { %>
                                   <g:javascript>
                                    $('#buyForm').css('border', 'none');
                                   </g:javascript>
                                <% } %>
                            </g:form>
							<g:form name="locate" action="index" controller="locate">
                                <% if (item.division.name == 'fitness') { %>
                                    <g:link controller="dealer" action="webDealers" params="[division:'fitness',itemId:item.id]" class="button">
                                      Where to Buy
                                    </g:link>
                                <% } else  { %>
                                    <input type="hidden" name="division" value="${item.division.name}"/>
                                    <g:hiddenField name="itemId" value="${item.id}" />
                                    <g:submitButton name="locate" value="Where to Buy"  class="button"/>
                                <% } %>
                            </g:form>
                        <% } /* not latino */ %>
                    <% } /* no exclusive dealer */ %>
                </div>
            <% } /* !mode */ %>
        <div style="clear:both"></div>
		</div>
        <% if (bikeSizes) { %>
            <div id="bikeSizeChart"></div>
        <% } %>
        <div id="largerImage"></div>
    </div> 
      <div id="tabs">
          <ul>
              <li><a href="#features"><span>Features</span></a></li>
              <li><a href="#specifications" onclick="resetWhite();"><span>Specifications</span></a></li>
              <li><a href="#partsAndService" onclick="resetWhite();"><span>Parts and Service</span></a></li>
              <% if (item.accessories.size()) { %>
                  <li><a href="#accessories2" id="accessoriesLink"><span>Accessories</span></a></li>
              <% } %>
          </ul>
          <div id="features">
              <% try { %>
                <g:render template="/features/${item.itemNo}" />
              <% } catch (e) { %>
                <p>Features to be added.</p>
              <% } %>
          </div>
          <div id="specifications">
             <dl>
                 <dt><g:message code="features.item_no"/>:</dt><dd>${item.itemNo}</dd>
                 <dt><g:message code="features.description"/>:</dt><dd>${item.desc}</dd>
                 <% if (item.countryOfOrigin) { %>
                     <dt><g:message code="features.country_of_origin"/>:</dt><dd>${ItemMasterExt.COUNTRIES[item.countryOfOrigin]?.capitalizeNames()?:'unspecified'}</dd>
                 <% } %>
                 <% if (item.setupHeight || item.setupWidth || item.setupLength) { %>
                     <dt><g:message code="features.setup_length_width_height"/>:</dt><dd>${item.setupLength} x ${item.setupWidth} x ${item.setupHeight} inches</dd>
                 <% } %>
                 <% if (item.setUpWeight) { %>
                     <dt><g:message code="features.setup_weight"/>:</dt><dd>${item.setUpWeight} lbs</dd>
                 <% } %>
                 <% if (item.dimLength || item.setupWidth || item.setupLength) { %>
                     <dt><g:message code="features.package_length_width_height"/>:</dt><dd>${item.dimLength} x ${item.dimWidth} x ${item.dimHeight} inches</dd>
                 <% } %>
                 <% if (item.unitWeight) { %>
                     <dt><g:message code="features.package_weight"/>:</dt><dd><g:formatNumber number="${item.unitWeight}" format="####0.##" /> lbs</dd>
                 <% } %>
                 <% if (item.commercialWarrantyCode) { %>
                     <dt>Commercial Warranty:</dt><dd>${item.commercialWarrantyPeriod?.desc}</dd>
                 <% } %>
                 <% if (item.residentialWarrantyCode) { %>
                     <dt><g:message code="features.residential_warranty"/>:</dt><dd>${item.residentialWarrantyPeriod?.desc}</dd>
                 <% } %>
                 <% if (item.ageFrom && item.ageTo) { %>
                     <dt><g:message code="features.age_recommendation"/>:</dt>
                     <dd>
                      <g:formatNumber number="${item.ageFrom}" format="##0.#" />
                      -
                      <g:formatNumber number="${item.ageTo}" format="##0.#" />
                      years
                      </dd>
                 <% } %>
                 <% if (item.weightLimit) { %>
                     <dt><g:message code="features.weight_limit"/>:</dt><dd>${item.weightLimit} lbs</dd>
                 <% } %>
                 <% if (item.assemblyRequired) { %>
                     <dt><g:message code="features.assembly_required"/>:</dt><dd>${(item.assemblyRequired?'Yes':'No')}</dd>
                 <% } else { %>
                     <dt>Ships Assembled:</dt><dd>Yes</dd>
                 <% } %>
                 <% if (item.commercial) { %>
                     <dt>Commercial Item:</dt><dd>Yes</dd>
                 <% } %>
                 <% if (item.division.name == 'patio' && item.closeoutCode && item.color) { %>
                     <dt><g:message code="features.country_of_origin"/>Color:</dt><dd>${item.color}</dd>
                 <% } %>
                     <dt><g:message code="features.ship_via"/>:</dt><dd>${(item.truck?'Truck':CH.config.rateService)}</dd>

              </dl>
          </div>
          <div id="partsAndService">
              <ul class="pdf">
                  <g:each in="${pdfNames}" var="pdf" status="index">
                      <li><a class="pdf" href="${createLinkTo(dir:'manuals/'+item.division.name+'/'+item.category.name, file:pdf)}">
                            ${(pdf.matches(/.*\.A.pdf/)?'Computer':'Assembly')} Manual 
                          </a>
                      </li>
                  </g:each> 
                   <% if (item.youTubeAssembly) { %>
                      <li>
                          <g:remoteLink class="youtubeAssembly" action="youTubeAssembly" id="${item.id}" update="mainImageImg">Play Assembly Video</g:remoteLink>
                      </li>
                   <% } %>
              </ul>
             <dl>
                 <dt>Phone:</dt><dd>866.804.0440 Monday thru Friday 9:00 a.m. to 4:30 p.m. EST</dd>
                 <dt>Fax:</dt><dd>757.563.9273</dd>
                 <dt>email:</dt><dd>parts@kettlerusa.com</dd>
              </dl>
          </div>
          <% if (item.accessories.size()) { %>
              <div id="accessories2"></div>
          <% } %>
      </div>
    <div id="floatingCart"></div>
        

	<g:render template='handleAddToCart_js' model="${[cart:cart]}"/>
    <g:javascript>
        $("#tabs").tabs();
        var mainImageName = '${item.itemNo}';
        function setOtherImageHover() {
            var mainImageSrc = $("#dynaMainImage").attr("src");
            $(".otherImage").hover(
              function () {$("#dynaMainImage").attr("src", this.src);},
              function () {$("#dynaMainImage").attr("src", mainImageSrc);}
            );
        }
        setOtherImageHover();
        $(".otherImage").click(
          function () {
            mainImageName = $(this).attr('id');
            $(this).unbind('mouseenter mouseleave');
            var otherImageSrc = $(this).attr("src");
            $("#dynaMainImage").attr("src", otherImageSrc);
            mainImageSrc = otherImageSrc;
            setOtherImageHover();
          }
        );
        $("#accessoriesLink").click(
            function () {
                $.ajax({
                    type:"GET",
                    url: "${resource(contextPath:'/shop',absolute:true)}/accessories",
                    data: "id=${item.id}&division=${params.division}&mode=${mode}&noShowFilter=true",
                    error: function(xhr, ajaxOptions, thrownError){ alert('Error'+xhr.statusText);},
                    success: function(msg){
                    console.log(msg);
                    	$("#accessories2").attr('innerHTML', msg);
						handleAddToCart();
                    }
                 });
            }
        );
        <% if (item.division.name == 'patio' && 
        	   ['poly', 'wrought iron'].find {it == item.category.name} &&
        	   !item.closeoutCode) { 
        %>
            $('#buyForm').submit(function() {
				<% if (cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { %>
					alert('Your order has a gift card and shippable items cannot be on a gift card order.');
					return false;
				<% } %>
	            if ($('select[name=itemIdWithColor]').val() == 0) {
	               alert('Please select a color');
	               $('select[name=itemIdWithColor]').focus();
	               return false;
	            }
                return true;
            });
        <% } else if (bikeSizes) { %>
            $('#buyForm').submit(function() {
                <% if (cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { %>
                  alert('Your order has a gift card and shippable items cannot be on a gift card order.');
                  return false;
                <% } %>
	            if ($('select[name=itemIdWithFrameSize]').val() == 0) {
	               alert('Please select a frame size');
	               $('select[name=itemIdWithFrameSize]').focus();
	               return false;
	            }
	            return true;
            });
        <% } else if (patioColors) { %>
            $('#buyForm').submit(function() {
                <% if (cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { %>
                  alert('Your order has a gift card and shippable items cannot be on a gift card order.');
                  return false;
                <% } %>
	            if ($('select[name=itemIdWithColor]').val() == 0) {
	               alert('Please select a color');
	               $('select[name=itemIdWithColor]').focus();
	               return false;
	            }
	            return true;
            });
        <% } else { %>
            $('#buyForm').submit(function() {
				<% if (cart?.items.find{it.item.arDistrictCode.equals("GFC")}) { %>
					alert('Your order has a gift card and shippable items cannot be on a gift card order.');
					return false;
				<% } %>
	            return true;
            });
        <% } %>
        $.ajax({ // prefetch larger image
           url: "${createLink(action:'largerImage')}",
           data: '&id=${item.id}&otherImageName='+mainImageName,
           type: 'POST',
           success:function(data) {
              $('#largerImage').html(data);
              $('#largerImage').show;
           }
        }); 
        $('#largerImage').click(function() {
              $('#largerImage').hide();
              setOtherImageHover(); 
        });
        function setLargerImageLink() {        
            $('#largerImageLink').click(function() {
                var params = '&id=${item.id}&otherImageName='+mainImageName;
                $.ajax({
                   url: "${createLink(action:'largerImage')}",
                   data: params,
                   type: 'POST',
                   success:function(data) {
                                $('#largerImage').html(data); 
                                $('#largerImage').show();
                                $(".otherImage").unbind('mouseenter mouseleave');
                           },
                   error: function(xhr,textStatus, errorThrown) { alert(textStatus); }
                }); 
            });
        }
        setLargerImageLink(); 
        function itemWithOtherColor(id, itemNoSuffix) {
            var params = '&id='+id+'&itemNoColorSuffix='+itemNoSuffix;
            $.ajax({
               url: "${createLink(action:'itemWithOtherColorImage')}",
               data: params,
               type: 'POST',
               success:function(data) {
                    if (data != 'not found') { 
                        $('#mainImageImg').html(data);
                        //$('#pickColorBox').hide();
                    }
                    setLargerImageLink(); 
               }
            }); 
        }
        <% if (bikeSizes) { %>
            $('#bikeSizeChart').hide();
            $('#questionFrameSize').click(function() {
                $.ajax({
                   url: "${resource(dir:'/articles/')}_${(item.category.name=='kids'?'child':'adult')}BikeSizeChart.gsp",
                   type: 'GET',
                   success:function(data) {
						data = '<a href="#" id="bikeSizeChartCloseLink">Close</a>'+data;
						$('#bikeSizeChart').html(data); 
						$('#bikeSizeChart').show();
						$('#bikeSizeChartCloseLink').click(function() {$('#bikeSizeChart').hide();});
                   },
                   error: function(xhr,textStatus, errorThrown) { alert(textStatus); }
                }); 
            });
        <% } %>
        
        <% if (item.minQty > 1) { %>
	        $('#quantity').change(function () {
	        	if ($(this).val() < ${item.minQty}) {
	        		alert('This item has a minimum order quantity of ${item.minQty}.')
	        		$(this).val(${item.minQty});
	        	}
	        });
	        
        <% } %>
        $('#largerImage').hide();
        if ($('select[name=itemIdWithFrameSize]').length == 0) {
            $('#quantity').focus();
        } else {
            $('select[name=itemIdWithFrameSize]').focus();
        }
        if ($('select[name=itemIdWithColor]').length == 0) {
            $('#quantity').focus();
        } else {
            $('select[name=itemIdWithColor]').focus();
        }
        $('#footer').css('float','left');

		<% if (params?.division == 'patio') { %>
		    $('#sidebar').hide();
		    $('#content').width('100%');
		<% } %>

        $('#floatingCart').draggable();

        <% if (['DEU', 'USA', 'ITA'].find{it == item.countryOfOrigin} ) { %>
            function setMadeInImg() {
                var mainImgLeft = $('#dynaMainImage').offset().left
                var mainImgWidth = $('#dynaMainImage').width();
                $('img.madeIn').css('left', mainImgLeft + mainImgWidth+20);
            }
            setMadeInImg();
            $(window).resize(function() {
                setMadeInImg();
            });
		<% } %>
        function resetWhite() {
            $('#main').height($('#tabs').offset().top - $('div#header').height());
        }

     </g:javascript> 
</body>
