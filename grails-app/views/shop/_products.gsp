<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>

<% if (params.division == 'bikes') { %>

			<% if (!params.noShowFilter && params.action != 'archives') { %>
					<div id="bikeFilterSidebar">
					   <g:form name="bikeFilterForm" action="${params.category?'category':'products'}">
						   <g:hiddenField name="division" value="${params.division}"/>
						   <g:hiddenField name="category" value="${(params.category == 'electric-bikes')?'e-bike':params.category}"/>
						   <g:hiddenField name="max" value="${params.max}"/>
						   <g:hiddenField name="rowSize" value="${params.rowSize}"/>
						   <g:hiddenField name="mode" value="${params.mode}"/>
						   <input type="hidden" name="bikeFilter" value="${params.bikeFilter}"/>
							<div class="filtersHeadline">
								<div class="filtersHeadlineBorderNone">
									<h1>Filter</h1> 
								</div>
							</div>
							<div class="filtersEntryArea">
							   <ul>
								 <g:each in="['Special Order','In Stock']" var="bikeFilter">
									 <li>
										 <input type="checkbox" name="${bikeFilter}" value="${bikeFilter}"
														${(bikeFilter == params.bikeFilter)?'checked=checked':''} />
										 ${bikeFilter}
									 </li>
								 </g:each>
								</ul>		
							</div>
					   </g:form>
					</div>
<% } %>
		<div id="bikesProdDiv">
			
	<% } %>
	<%  if (params.category) { %>
		<g:javascript>
			var totalCompareItems = ${(session.compareMap?session.compareMap.size():0)};
		</g:javascript>
	<% } %>
	<div class="products">
	  <g:form name="compForm" controller="compare" action="compare" method="POST">
		<g:hiddenField name="division" value="${params.division}"/>
		<g:hiddenField name="category" value="${(params.category == 'electric-bikes')?'e-bike':params.category}"/>
		<g:hiddenField name="mode" value="${params.mode}"/>
		<% if (params.category && items.find{it.comparable} && request.serverName != 'www.kettlerlatinoamerica.com') { %>
			<div id="compareButton">
				<g:submitButton name="compareAction" value="Compare Selected Items"
					class="${(params?.max > 4)?'floatCompareButton':''}"
				 />
			</div>
		<% } %>
		<g:each in="${items}" var="item" status="i">
			<div class="product">
				<div class="productImage">          
				   <a href="${kettler.resource(contextPath:'/',absolute:true, mode:params.mode)}${item?.division?.seoName}/${item?.category?.seoName}/${item?.id}" 
						 title="click to view details on ${item.desc}">
						<img src="${resource(dir:"images/")}${item?.division.name}/${item?.category.name.replaceAll(/-/,'%20')}/${item?.itemNo}.jpg" alt="1"  />
				   </a>
				  <p>${item?.desc}</p>
				   <% if (params.division == 'bikes' && item.specialOrder) { %>
						<p class="specialOrder">Special Order</p> 
				   <% } %>
				   <% if (item.retailPrice && !(['canada', 'store', 'contract', 'latinoamerica'].find{request.serverName ==~ /www.kettler$it\.com/})) { %>
							<div style="height:45px">	
								<% if (item.isWebAvailable(item)) { %>
									 <span class="price"><g:formatNumber number="${(item.specialPrice?:item.retailPrice)}" type="currency" currencySymbol="\$" /></span>
									 <% if (item.division.name == 'bikes' && ItemMasterExt.getBikeFrameSizes(item)) { %>
										   <g:link action="detail" id="${item?.id}" params="[division:params?.division,mode:params.mode]" 
												class="clickme"  title="click to select frame size for ${item.desc}">
												Add to Cart
										   </g:link>
									 <% } else if (item.division.name == 'patio' && ItemMasterExt.getPatioColors(item)) { %>
										   <g:link action="detail" id="${item?.id}" params="[division:params?.division,mode:params.mode]" 
												class="clickme"  title="click to select a color for ${item.desc}">
												Add to Cart
										   </g:link>
									 <% } else { %>
										 
										 <a id="${item.id}" quantity="${item.minQty}" division="${params?.division}" class="clickme addToCart">Add To Cart</a>
									 <% } %>
							   <% } else { %>
								   <span class="outOfStock">Out of stock</span><br/>
							   <% } %>
						   </div>
				  <% } %>
				  <% if (request.serverName == 'www.kettlercanada.com' && item.msrpCanada) { %>
					 <span class="price">MSRP: <g:formatNumber number="${item.msrpCanada}" type="currency" currencySymbol="\$" maxIntegerDigits="4"/></span>
				  <% } %>
				  <%  if (item?.comparable && params.category && request.serverName != 'www.kettlerlatinoamerica.com') { %>
					  <div id="compareSelect">
						  <input type="checkbox" name="compare" id="compare_${item.id}" value="compare_${item.id}" style="margin-left: 50px; text-align: center;"
								<% if (session.compareMap) { %>
									${session.compareMap[item.id.toString()]?'checked=checked':''}
								<% } %>		    	          		
						  >
							Compare
						  </input>
					  </div>
					  <g:javascript>
						  $('#compare_${item.id}').click(function() {
								if ($('#compare_${item.id}').is(':checked')) {
									totalCompareItems++;
								} else {
									totalCompareItems--;
								}
								if (totalCompareItems > 4) {
									alert('Please select no more than four items to compare.');
									return false;
								}
								$.ajax({
								   url: "${createLink(action:'editList',controller:'compare')}",
								   data: 'id=' + ${item.id}+'&checked='+$('#compare_${item.id}').is(':checked'),
								   type: 'POST',                                      
								   success:  function(data) {/*alert(data);*/},
								   error: function(xhr,textStatus, errorThrown) { alert(textStatus) }
								}); 
						  });
					  </g:javascript>  
				  <%  } %>
				</div>
			</div>
		</g:each>
		<div class="clearBoth"></div>
		</g:form>
	</div>
<% if (params.division == 'bikes') { %>
			
		</div>
		<div class="clearBoth"></div>
</div>
<% } %>
	
<g:render template='handleAddToCart_js' model="${[cart:cart]}"/>
<g:javascript>
$('#compForm').submit(function() {
  if (totalCompareItems == 0) {
    alert('Select items to compare by clicking the compare box below the item.');
    return false;
  } else if (totalCompareItems < 2 || totalCompareItems > 4) {
    alert('Please select two to four items to compare.');
    return false;
  }
  totalCompareItems = 0;
  $("input:checkbox").attr('checked', false);
  return true;
});


handleAddToCart();
</g:javascript>
