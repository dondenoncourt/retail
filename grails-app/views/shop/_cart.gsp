<%@ page import="com.kettler.domain.orderentry.share.Cart" %>
<%@ page import="com.kettler.domain.orderentry.share.SalesTax" %>
<div id="cartTop">

    <% if (verifyOrder) { %>
        <h2>Order Detail:</h2>
    <% } else { %>
        
		<h1>Your Shopping Cart</h1> 
    <% } %>
</div>    
<div id="divYourShoppingCart" >
	<g:hasErrors bean="${cart}">
		<div id="cartErrors" class="errors"><g:renderErrors bean="${cart}" as="list" /></div>
	</g:hasErrors>
	<g:if test="${flash.message}">
		<div id="floatFlashMessage" class="errors">${flash.message}</div>
	</g:if>
	<table class="tableCartMainTable" border="0" cellspacing="0" cellpadding="0">
		<tbody>
			<tr class="tableCartHeader">
				
				<th class="tableHeaderItemImage">Item</th>
				<th class="tableHeaderDescription">Description</th>
				<th class="tableHeaderQuantity">Qty</th>
				<th class="tableHeaderDel"></th>
				<th class="tableHeaderAmount">Amount</th>
			</tr>
			<g:each in="${cart?.items}" var="item">
				<g:hasErrors bean="${item}">
					<div class="errors"><g:renderErrors bean="${item}" as="list" /></div>
				</g:hasErrors>
				<tr class="tableCartContentEntry">
					<td class="imageTinyCI">
						<% if (item.item.arDistrictCode.equals("GFC")) { %>
							<img class="cartCI" src="${resource(dir:"images/giftcard")}/${item.item.itemNo}.jpg"
								 alt="${item.item?.desc}" />
						<% } else { %>
							<img class="cartCI" src="${resource(dir:"images/")}${item.item?.division?.name}/${item.item?.category?.name}/${item.item.itemNo}.jpg"
								alt="${item.item?.desc}" />
						<% } %>
					</td>
					<td class="tableCartDescription"><%-- if available, frame size, else standard item for desc --%>
						${(item.itemWithFrameSize?:item.item).desc} 
						${item.itemWithFrameSize?.size} ${item.itemWithFrameSize?.sizeUom}<%-- won't show unless size selected--%>
					</td>
					<td class="tableCartQuantity">
						<% if (verifyOrder) { %>
							<span class="number">${item.qty}</span>
						<% } else { %>
							<g:form action="updateItem">
								<g:hiddenField name="id" value="${item.id}" />
								<g:hiddenField name="ajax" value="${params.ajax}" />
								<input type="text" id="quantity" name="quantity" class="itemUpdate number" value="${item.qty}" 
										<% if (item.item.arDistrictCode.equals("GFC")) {  %>
											disabled="disabled"
										<% } %> 
								/>
							</g:form>
						<% } %>
					</td>
					<td class="tableCartRemove">
						<% if (params.ajax || ajax) { %> 
							<g:remoteLink  action="removeItem" controller="shop" id="${item.id}" 
								update="floatingCart" params="[id:item.id,ajax:true,checkout:params.checkout,division:params.division?.replaceAll(/ /,'')?:'']"
								onSuccess="setCartClose();subFromMiniCart();"
							><img src="/retail/images/objects/cancel-button.png"></g:remoteLink>
						<% } else if (!verifyOrder) { %>
							<g:link action="removeItem" id="${item.id}" params="[division:params.division?.replaceAll(/ /,'')?:'']" title="Click to remove this item from your cart"><img src="/retail/images/objects/cancel-button.png"></g:link>
						<% } %>
					</td>
					<td class="tableCartNumber">
						<g:formatNumber number="${(item.item.closeoutCode?item.item.specialPrice:item.item.retailPrice) * item.qty}" 
							  type="currency" currencySymbol="\$" />
					</td>
				</tr>
				<% if (item.itemIdWithColor) { %>
					<tr>
						<td>Color:</td>
						<td>
							${item.itemWithColor.color?:'blank'}
						</td>
					</tr>
					<tr> <td/> </tr>
				<% } %>
				<% if (item.coupon) { %>
					<tr>
						<td />
						<td>Item Coupon:</td>
						<td>
							<% if (item.coupon.percent) {  %>
								${(item.coupon?.percent?item.coupon.percent+'%':g.formatNumber(number:item.coupon?.amount, type:'currency',currencyCode:'USD'))}
							<% }  %>
						</td>
						<td/>
						<td class="number">
							<% if (item.coupon.percent) {  %>
								<g:formatNumber number="${(item.item.retailPrice * item.qty) * (item.coupon.percent/100)}" type="currency" currencySymbol="\$" />
							<% } else {  %>
								<g:formatNumber number="${item.coupon.amount}" type="currency" currencySymbol="\$" />
							<% }  %>
						</td>
					</tr>
				<% } %>
			</g:each>

	</table>
	<div class="divCartTableFooter">
		<div class="divCartTableFooterLeft">
			<a href="${resource(contextPath:'/',absolute:true)}" title="Continue shopping">Continue Shopping</a>
		</div>
		<div class="divCartTableFooterRight">
			<a href="#">Update</a>
		</div>
		<div class="clearBoth"></div>
	</div>
	<table class="tableCartRight">
			<tr>
				<%--<th/>--%>
				<th/>
				<%--<th/>--%>
				<th/>
				<th class="tableCartShippingPriceHeader"></th>
			</tr>
			
			<% if (cart?.coupon) { %>
				<tr>
					<%--<td />--%>
					<td>Order Coupon:</td>
					<td>${(cart.coupon.percent?cart.coupon.percent+'%':g.formatNumber(number:cart.coupon.amount, type:'currency',currencyCode:'USD'))}</td>
					<%--<td/>--%>
					<td class="number">
						<% if (cart.coupon.percent) {  %>
							<g:formatNumber number="${(cart.totalItemCost() * (cart.coupon.percent/100))}" type="currency" currencySymbol="\$" />
						<% } else  {  %>
							<g:formatNumber number="${cart.coupon.amount}" type="currency" currencySymbol="\$" />
						<% }  %>
					</td>
				</tr>
			<% } %>
			<tr>
				<td/>
				<td class="tableCartUpsServiceCode">
					<% if (cart.rateService.equals("UPS")) { %>
						${Cart.SHIP_METHODS_UPS[cart?.upsServiceCode]?:''}
						<input type="radio" name="upsServiceCode" value="${cart?.upsServiceCode?:'03'}" checked="checked"/>
							
					<% } else  {  %>
						${Cart.SHIP_METHODS_FEDEX[cart?.upsServiceCode]?:''}
						<input type="radio" name="upsServiceCode" value="${cart?.upsServiceCode?:'21'}" checked="checked"/>
							
					<% }  %>
				</td>
				<%--<td/>--%>
				<%--<td/>--%>
				<td id="shippingPriceTD" class="number tableCartShippingPrice">
				<% if (cart?.shippingCost) { %>
					<g:formatNumber number="${cart.shippingCost}" type="currency" currencySymbol="\$" />
				<% } else if (cart?.items && cart?.total() >= 40g) { %>
					<span class="emphasis">free</span>
				<% } else if (cart?.items && cart?.total() < 40g) { %>
					<img id="cautionReCalc" src="${resource(dir:"images/")}caution24x24.png" alt="shipping not yet calculated" title="Click on Recalc Shipping." />
				<% } %>
				</td>
			</tr>
			<% if (cart?.upsServiceCode != '99') { %>
				<tr>
					<td/> 
					<td class="tableCartUpsServiceCode">
					   
					<% if (cart.rateService.equals("UPS")) { %>
					   <ul style="<%--list-style:none;text-align:left;padding-left:0;margin-top:-1px;--%>">
					   <%-- only show what isn't shown above --%>
						  <g:each in="${Cart.SHIP_METHODS_UPS.findAll{it.key != '99'}}">
							 <% if (it.value != Cart.SHIP_METHODS_UPS[cart?.upsServiceCode]?:'') { %>
							  <li style="display:list-item;margin-left:-1px;">
								${it.value}
								<input type="radio" name="upsServiceCode" id="upsServiceCode${it.key}" value="${it.key}" 
								   ${(payShipCmd?.upsServiceCode == it.key)?'checked=checked':''} />
									
							  </li>
							 <% } %>
						  </g:each>
					   </ul>
					<% } else  {  %>
					   <ul style="<%--list-style:none;text-align:left;padding-left:0;margin-top:-1px;--%>">
					   <%-- only show what isn't shown above --%>
						  <g:each in="${Cart.SHIP_METHODS_FEDEX.findAll{it.key != '99'}}">
							 <% if (it.value != Cart.SHIP_METHODS_FEDEX[cart?.upsServiceCode]?:'') { %>
							  <li style="display:list-item;margin-left:-1px;">
								${it.value}
								<input type="radio" name="upsServiceCode" id="upsServiceCode${it.key}" value="${it.key}" 
								   ${(payShipCmd?.upsServiceCode == it.key)?'checked=checked':''} />
								   
							  </li>
							 <% } %>
						  </g:each>
					   </ul>
					<% } %>
					   
					</td>
				</tr>
			<% } %>
			<% def tax = 0.00g %>
			<% if ((SalesTax.hasSalesTax(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state) || SalesTax.hasSalesTax(addr?.shippingState?:addr?.billingState)) &&
				   !cart?.items.find{it.item.arDistrictCode.equals("GFC")}                                 ) {  
			%>
				<tr>
					<td/>
					<td>Tax:</td>
					<td/>
					<td/>
					<td class="number">
						<% tax = (cart.calcTax(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState)) %>
						<g:formatNumber number="${tax}" type="currency" currencySymbol="\$" />
					</td>
				</tr>
			<% } %>
			<tr/>
			<% if (cart?.giftCard) { %>
				<tr>
					<td />
					<td>Gift Card:</td>
					<td/>
					<td/>
					<td class="number">
						<g:formatNumber number="${cart.giftCardReduced(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState)}" type="currency" currencySymbol="\$" />
					</td>
				</tr>
			<% } %>
	</table>
			<div class="divCartTotal">
				<div class="divCartTotalNumber">
					<g:formatNumber number="${cart.total(cart?.consumerShipTo?.state?:cart?.consumerBillTo?.state?:addr?.shippingState?:addr?.billingState)}" type="currency" currencySymbol="\$" />
				</div>
				<div class="divCartTotalHeadline">Total:</div>
				<div class="clearBoth"></div>
			</div>
	<table class="tableCartAddrRight">
			<% if (cart?.upsServiceCode != '99') { %>
				<tr/>
				<tr class="promptShippingAddr promptShippingCityStreet">
					<td/>
					<td colspan="3">
						Street:&nbsp;
						<input name="shipStreet" size="40" value="${params.shipStreet?:session.shipStreet?:''}"/>&nbsp;
					</td>
					<td/>
					<td/>
				</tr>
				<tr class="promptShippingAddr">
					<td/>
					<td colspan="4">
						<span class="promptShippingCityStreet">
							City:&nbsp;
							<input name="shipCity" size="15" value="${params.shipCity?:session.shipCity?:''}"/>
						</span>
						State:&nbsp;
						<input name="shipState" size="2" value="${params.shipState?:session.shipState?:''}"/>&nbsp;
						Zip:&nbsp;
						<input name="shipZip" size="10" value="${params.shipZip?:session.shipZip?:''}"/>
					</td>
				</tr>
			<% } %>
		</tbody>
	</table>
	<li class="promptShippingAddr calcShippingButton">
     		
			<a title="Recalculate shipping cost">
				<div class="button">
				ReCalc Shipping
				</div>
			</a>
    </li>
	<% if (cart?.items?.size()) { %> 
				<div class="divCartCheckoutButton">
					<div class="clickme"><a href="${createLink(controller:'shop', action:'checkoutPrompt')}?division=${params.division?.replaceAll(' ', '+')?:'kettlerusa'}" >Checkout</a></div>
				</div>
	<% } %>

	<%-- not sure why, but the ternary truth seems to be required --%>
	<p id="recalShipping" class="hide">Recalculating shipping cost...</p>
	<% if ((!checkout?true:false && !(params.checkout?true:false)) && (ajax || params.ajax)) { %>
		<ul class="buttonList">
		<!--
			<li class="notShipping">
				<% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) {  %>
					<a id="otherkettler" href="#" onclick="$('#floatingCart').hide();" class="button" title="Continue shopping">Continue Shopping</a>
				<% } else { %>
				<a id="otherkettler" href="${resource(contextPath:'/',absolute:true)}" class="button" title="Add another gift card">Add another gift card</a>
				<% } %>
			</li>
			<li class="notShipping">
				<% if (!cart?.items.find{it.item.arDistrictCode.equals("GFC")}) {  %>
					<a id="otherkettler" href="#" onclick="$('#floatingCart').hide();" class="button" title="Continue shopping">Continue Shopping</a>
				<% } else { %>
				<a id="otherkettler" href="${resource(contextPath:'/',absolute:true)}" class="button" title="Add another gift card">Add another gift card</a>
				<% } %>
			</li>
			<li class="promptShippingAddr">
				<a class="button" title="Recalculate shipping cost">
					Change Addr
				</a>
			</li>-->
		</ul>
		<br/>
	<% } %>
	<div class="clearBoth"></div>
</div>
<g:javascript>
    var shippingCost = ${cart?.shippingCost?:0};
	$('#recalShipping').hide();
	<% if (!params.shipZip && !session.shipZip) { %>
		$('li.promptShippingAddr div').html('Recalc Shipping');
		$('li.promptShippingAddr div').addClass('<%--clickme--%>');
	  	$('.promptShippingAddr').hide();
        var upsServiceCode = $('input[name=upsServiceCode]:checked').val();
        if ((upsServiceCode == '03' || upsServiceCode == '21') && shippingCost == 0) {
		    $('li.promptShippingAddr div').html('Calc Shipping');
        }
  	<% } %>
	<% if (!params.shipStreet) { %>
	  	$('.promptShippingCityStreet').hide();
  	<% } %>

	<% flash.message = '' %>
	var updateItemRan = false;
	$('.itemUpdate').keyup(function(event) {
		var form = event.target.form;
		var c = String.fromCharCode(event.keyCode);
		if (!c.match(/\d/)) {
			return;
		}
		updateItemRan = true;
		$.ajax({
			type:'POST',
            url: "${createLink(action:'updateItem')}",
			data: $(this.form).serialize(),
			success:function(data,textStatus){
			  if ($('#floatingCart').length) {
				  $('#floatingCart').html(data);
				  setCartClose();
				  addToMiniCart();
                  setFloatingCartHeight();
			  } else {
				  form.submit();
			  }
			},
			error:function(XMLHttpRequest,textStatus,errorThrown){}
	     });
	});
	$("input[name=quantity]").change(function (event) {
		if (updateItemRan == false) {
	        event.target.form.submit();
	    }
        setFloatingCartHeight();
    });
	$('input[name=upsServiceCode]').change(function(event) {
		$('#shippingPriceTD').html('');
        <% if (!cart?.consumerBillTo?.zipCode) {  %>
        	$('.notShipping').hide();
			<% if (!params.shipZip && !session.shipZip) { %>
                var upsServiceCode = $('input[name=upsServiceCode]:checked').val();
                <% if (cart.rateService.equals("UPS")) { System.out.println("why am i here");%>
	                if (upsServiceCode == '03') {
					    getShippingCost();
	                } else if (upsServiceCode == '02') {
	                    $('.promptShippingAddr').show();
	                    $('.promptShippingCityStreet').hide();
	                    $('span.promptShippingCityStreet').hide();
	                } else if (upsServiceCode == '13') {
	                    $('.promptShippingAddr').show();
	                    $('.promptShippingCityStreet').show();
	                    $('span.promptShippingCityStreet').show();
	                }
	        	<% } else { System.out.println("fine alfalfajf;lajfljalfjlasjflajf;lajfl;jalf;ajflajl;ajfdlkask");%>
	                if (upsServiceCode == '21') {
					    getShippingCost();
	                } else if (upsServiceCode == '20') {
	                    $('.promptShippingAddr').show();
	                    $('.promptShippingCityStreet').hide();
	                    $('span.promptShippingCityStreet').hide();
	                } else if (upsServiceCode == '22') {
	                    $('.promptShippingAddr').show();
	                    $('.promptShippingCityStreet').show();
	                    $('span.promptShippingCityStreet').show();
	                }
        	<% }
			 } else { %>
				getShippingCost();
        	<% } 
         } else { %>
        	$('.notShipping').hide();
			getShippingCost();
        <% } %>
        setFloatingCartHeight();
	});

	$('#cautionReCalc').click(function() {
		getShippingCost();
	});
	$('li.promptShippingAddr a').click(function() {
		getShippingCost();
	});
	function getShippingCost() {
		$('.buttonList').hide();
		$('#recalShipping').show();
		$.ajax({
			type:'POST',
            url: "${createLink(action:'shippingCost')}",
			data: {'upsServiceCode': $('input[name=upsServiceCode]:checked').val(), 
				   'ajax':           $('input[name=ajax]').val(),  
				   'shipStreet':      $('input[name=shipStreet]').val(),  
				   'shipCity':      $('input[name=shipCity]').val(),  
				   'shipState':      $('input[name=shipState]').val(),  
				   'shipZip':        $('input[name=shipZip]').val()                },
			success:function(data,textStatus){
              <% if (params.ajax || ajax) { %> 
				  $('#floatingCart').html(data);
				  setCartClose();
				  addToMiniCart();
			  <% } else { %>
				  window.location.replace(data);
			  <% } %>
	    		$('.buttonList').show();
				$('#recalShipping').hide();
			},
			error:function(XMLHttpRequest,textStatus,errorThrown){}
		});
	}
    function showAddress() {
        var upsServiceCode = $('input[name=upsServiceCode]:checked').val();
        <% if (cart.rateService.equals("UPS")) { %>
	        if (upsServiceCode == '02' || (upsServiceCode == '03' && shippingCost == 0)) {
	            $('.promptShippingAddr').show();
	            $('.promptShippingCityStreet').hide();
	        } else if (upsServiceCode == '13') {
	            $('.promptShippingCityStreet').show();
	            $('span.promptShippingCityStreet').show();
	        } else {
	            $('.promptShippingCityStreet').hide();
	            $('span.promptShippingCityStreet').hide();
	        }
    	<% } else { %>
	        if (upsServiceCode == '20' || (upsServiceCode == '21' && shippingCost == 0)) {
	            $('.promptShippingAddr').show();
	            $('.promptShippingCityStreet').hide();
	        } else if (upsServiceCode == '22') {
	            $('.promptShippingCityStreet').show();
	            $('span.promptShippingCityStreet').show();
	        } else {
	            $('.promptShippingCityStreet').hide();
	            $('span.promptShippingCityStreet').hide();
	        }
    	<% } %>
    }
    showAddress();
    <% if (params.ajax || ajax) { %>
        if ($('.buttonList').offset().top > 0) {
            setFloatingCartHeight();
        } else {
           <%-- $('#floatingCart').height($('#floatingCart').height()+($('#floatFlashMessage').height() * 2) + $('#cartErrors').height() + 25);--%>
        }

        function setFloatingCartHeight() {
        	var defaultHeight = 177;
        	var upsServiceCode = ${cart?.upsServiceCode?:""};
        	if (upsServiceCode != '99'){
        		defaultHeight += 81;
        	}

        	<%--if (($('.buttonList').offset().top-$('#floatingCart').offset().top)+$('.buttonList').height() == 0){--%>
        		<%--$('#floatingCart').height(defaultHeight + (54 * (${cart?.items?.size()?:0}-1)));--%>
        	<%--} else{--%>
            	<%--$('#floatingCart').height(($('.buttonList').offset().top-$('#floatingCart').offset().top)+$('.buttonList').height());--%>
            <%--}--%>
        }
    <% } %>
	
</g:javascript>
