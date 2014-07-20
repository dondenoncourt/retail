<% 
    def steps = [1:'One',2:'Two',3:'Three',4:'Four']
%>

<div class="buttons">
    <% if (step == 3) { %>
        <g:submitButton 
            name="stepOne" 
            value="Back" class="button" 
            title="${stepTitles.find{it.key == 1}.value}"
         />
    <% } %>
    <% if ([2,3].find{it == step}) { %>
        <g:submitButton 
            name="step${steps.find{it.key == (step-1)}.value}" 
            value="Back" class="button" 
            title="${stepTitles.find{it.key == (step-1)}.value}"
         />
    <% } %>
    <a href="${resource(contextPath:'/',absolute:true)}" class="button" title="Click to continue shopping">
    	Continue Shopping
    </a>
    <g:submitButton name="cancel" value="Clear Cart"  class="button" 
        title="Click to clear your shipping cart and abort checkout"
    />
    <% if (step == 2) { %>
        <g:submitButton name="calcShipping" value="calcShipping"  />
    <% } %>
    <g:submitButton
        name="step${steps.find{it.key == (step+1)}.value}" 
        value="${(step==1)?'Next':(step==2?'Confirm':'Submit')}"  
        title="${stepTitles.find{it.key == (step+1)}.value}"
        class="clickme"   
    />
</div>
