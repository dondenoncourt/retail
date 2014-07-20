<g:hiddenField name="itemNoColorSuffix" value="${cmd?.itemNoColorSuffix}" />
<g:hiddenField name="color" value="${cmd?.color}" />
<% if (item.colorMap()) { %>
     <br/>
     Available colors:
    <ul id="pickColor">
     <%  item.colorMap().each {color, itemNoSuffixAndColorArray  -> 
              def itemNoSuffix =  itemNoSuffixAndColorArray[0]
              def colorName = itemNoSuffixAndColorArray[1]
     %>
             <li> 
              <input type="radio" name="selectAColor"  
                 onclick="$(this).siblings().removeClass('selected');$(this).addClass('selected');$('#itemNoColorSuffix').attr('value', '${itemNoSuffix}');$('#color').attr('value', '${color}');$('#pickColorBox').show();itemWithOtherColor('${item.id}','${itemNoSuffix}');$('#pickColorBox').css('background', '${color}');">
               ${colorName.toLowerCase()}
               </input>
          </li>
     <% } /* item.colorMap().each */ %>
    </ul>
    <div id="pickColorBox"><br/>Color Sample</div>
    <g:javascript>
         $('#buyForm').css('width', '300px');
         $('#buyForm').css('height', '220px');
    </g:javascript>
<% } %>
