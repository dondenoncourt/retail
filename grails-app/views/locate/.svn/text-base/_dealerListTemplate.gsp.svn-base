<div style="padding: 15px;">
  <h2>Found ${locations.size()} Dealer locations</h2>
  <ul id="dealerInfo">
    <g:each var="location" in="${locations}">
      <li>
        <div class="address">
         <h3>${location.name?:location.dealer.name}</h3>
          ${location.location.street}<br/>
          ${location.location.city} ${location.location.state} ${location.location.zip}<br/>
          <% if (location.location.phone) { %>
            <kettler:formatPhone phone="${location.location.phone}"/><br/>
          <% } else if (location.dealer.phone) { %>
            <kettler:formatPhone phone="${location.dealer.phone}"/><br/>
          <% } %>
          Distance: <g:formatNumber number="${location.distance}" type="number" maxFractionDigits="2" roundingMode="HALF_DOWN" />  ${units}<br/>
          <g:if test="location.dealer.website">
            <a href="#" onclick="window.open('http://${location.dealer.website}')">${location.dealer.website}</a><br/>
          </g:if>
        </div>
        <div class="logo">
	        <% if (location.dealer?.logo) { %>
	             <img src="<g:createLink action='renderLogo' controller="dealer" id='${location.dealer?.id}'/>" alt="Dealer Logo">
	        <% } %>
        </div>
          <div class="logo">
              <% if (location.location.storePhoto) { %>
                   <img src="<g:createLink action='renderStorePhoto' controller="dealerLocation" id='${location.location.id}'/>" alt="Store Photo">
              <% } %>
          </div>
        <div style="float: right;">
          <% if (location.inStockItems?.size() > 0) { %>
            <select style="max-width:220px;">
            	<option>----- In stock items ----- </option>
            	<g:each in="${location.inStockItems}" var="desc">
            		<option>${desc}</option>
            	</g:each>
            </select>
          <% } %>
        <% if (location.inventory) { %>
        	<br/><br/>
	        <span class="emphasis">Item in stock</span> <img src="${resource(dir:"images/")}flag_green.png" />
        <% } %>
        </div>
      </li>
    </g:each>
  </ul>
</div>
<g:javascript>
	$("div.logo img").load(function() {
		var imgWidth = this.width;
		var divWidth = $(this.parentElement).width();
		if (imgWidth < divWidth) {
			$(this).css('padding-left', ((divWidth - imgWidth) / 2)+'px');
		}
    });
</g:javascript>