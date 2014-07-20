<%@ page import="com.kettler.domain.item.share.WebCategory" %>
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<ul>
      <%-- scrunch ids with no hyphens, blanks, or ampersands. --%>
      <li><a id="aluminum" class="aluminum"     href="${kettler.resource(contextPath:'/patio-furniture/aluminum',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('aluminum')?.title}">Aluminum</a></li> 
      <%-- <li><a id="poly" class="poly"         href="${kettler.resource(contextPath:'/patio-furniture/poly',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('poly')?.title}">Poly</a></li> --%> 
      <li><a id="resin" class="resin"        href="${kettler.resource(contextPath:'/patio-furniture/resin',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('resin')?.title}">Resin</a></li> 
      <li><a id="wroughtiron" class="wroughtiron"  href="${kettler.resource(contextPath:'/patio-furniture/wrought-iron',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('wrought iron')?.title}">Wrought Iron</a></li>
      <li><a id="mixedmaterial" class="mixedmaterial"  href="${kettler.resource(contextPath:'/patio-furniture/mixed-material',absolute:true, mode:params.mode)}"  title="${WebCategory.findByName('mixed material')?.title}">Mixed Material</a></li>
      <% if (!hoverMenu && ['www.kettlerusa.com', 'grailsdev' ].find{ it == request.serverName}) { %>
       <li><a id="giftcard" class="giftcard"     href="${kettler.resource(contextPath:'/patio-furniture/giftCard',absolute:true, mode:mode)}" title="Gift Cards">gift card</a></li>
          <% if (ItemMasterExt.division('patio', 0, 'desc', null, true).count()) { %>
           <li><a id="closeouts" class="closeouts"  class="closeouts"    href="${kettler.resource(contextPath:'/patio-furniture/closeouts',absolute:true, mode:params.mode)}"  title="close out items">specials</a></li>
       <% } %>
      <% } %>
       
</ul>
