var Ajax;
if (Ajax && (Ajax != null)) {
	Ajax.Responders.register({
	  onCreate: function() {
        if($('spinner') && Ajax.activeRequestCount>0)
          Effect.Appear('spinner',{duration:0.5,queue:'end'});
	  },
	  onComplete: function() {
        if($('spinner') && Ajax.activeRequestCount==0)
          Effect.Fade('spinner',{duration:0.5,queue:'end'});
	  }
	});
}

function setCartClose() {
    $('#cartTop').append('<a id="closeCart" title="Close">X</a>');
    $('#floatingCart').show();
    $('#closeCart').show();
    $('#closeCart').click(function(){
        $('#floatingCart').hide();
    });
}
function addToMiniCart() {
    var miniCart = $('#miniCart').html();
    var re = new RegExp(/^\d* /);
    var match = miniCart.match(re) 
    if (match) {
      var items = parseInt(match[0])+1;
      $('#miniCart').html(items+' items');
    } else {
        $('#miniCart').html('1 item');
    }
}
function subFromMiniCart() {
    var miniCart = $('#miniCart').html();
    var re = new RegExp(/^\d* /);
    var match = miniCart.match(re) 
    if (match) {
      var items = parseInt(match[0])-1;
      $('#miniCart').html(items+' items');
    } 
}
// simply refresh the cart popup
// note: this is called from several template GSPs
function refreshCartPopup (){
    $('[name=next]').val('UPS');
    $('[name=next]').hide();
    $('#checkout').submit();
}