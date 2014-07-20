<g:javascript>
function handleAddToCart() {
	$('.addToCart').click(function(event) {
		<% if (cart?.items?.find{it.item.arDistrictCode.equals("GFC")}) { %>
			alert('Your order has a gift card and shippable items cannot be on a gift card order.');
			return false;
		<% } %>
		$.ajax({
		type:'POST',
		data:{'quantity': $(this).attr('quantity'),'ajax': 'true','division': $(this).attr('division')},
		url:'${createLink(action:'buy')}/'+$(this).attr('id'),
		success:function(data,textStatus){
		  jQuery('#floatingCart').html(data);
		  jQuery('#floatingCart').css('top', jQuery(event.target).offset().top-200+'px');
		  setCartClose();
		  addToMiniCart();
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){}
	  });
	  return false;
	  
	});
}
</g:javascript>
