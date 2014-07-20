<% 
    def stepTitles = [
         1:'Click to change address, email, and phone number',
         2:'Click to change payment and shipping information',
         3:'Click to view the confirmation page',
         4:'Click to confirm your order' 
    ]
%>

<ul class="checkoutSteps">
    <li class="${(step==1)?'current':''}">
        <% if (step == 1) { %>
            Step One
        <% } else { %>
            <a href="#" id="stepOneLink" title="${stepTitles[1]}" 
                class="left"
            >Step One</a>
        <% } %>
    </li>
    <li class="${(step==2)?'current':''}">
        <% if ([1,3].find{it == step}) { %>
            <a href="#" id="stepTwoLink" title="${stepTitles[2]}" 
                class="${step<2?'right':'left'}"
            >Step Two</a>
        <% } else { %>
            Step Two
        <% } %>
    </li>
    <li class="${(step==3)?'current':''}">
        <% if ([1,3].find{it == step}) { %>
            Step Three
        <% } else { %>
            <a href="#" id="stepThreeLink" title="${stepTitles[3]}" 
                class="right"
            >Step Three</a>
        <% } %>
    </li>

    <% if (step==3) { %>
        <li>
           <a href="#" id="stepFourLink" title="${stepTitles[4]}" 
               class="clickme"
           >Submit</a>
        </li>
    <% } %>
</ul>

