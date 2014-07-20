<% if (request.serverName != 'www.kettlerlatinoamerica.com') { %>
    <span id="socialLinks">
        <a href="http://twitter.com/#!/kettler_usa"><img src="${createLinkTo(dir:'images',file:'twitter24x24.png', absolute:true)}" class="socialmedia"></img></a>
        <a href="https://www.facebook.com/kettlerusa"><img src="${createLinkTo(dir:'images',file:'facebook24x24.png', absolute:true)}" class="socialmedia"></img></a>
        <a href="http://www.youtube.com/user/KETTLERUSA"><img src="${createLinkTo(dir:'images',file:'youtube24x24.png', absolute:true)}" class="socialmedia"></img></a>
    </span>
    <g:javascript>
    function lineUpSocialLinks() {
      $('#socialLinks').css('right', $('#header div.navSecond').offset().left+10+'px');
    }
    lineUpSocialLinks(); 
    $(window).resize(function() {
      lineUpSocialLinks();
    });
    </g:javascript>
<% } %>
