<g:javascript>
      var _gaq = _gaq || [];
	  <% if (mode == 'canada' || request.serverName ==~ /www.kettlercanada.com/) { %>
	      _gaq.push(['_setAccount', 'UA-17400793-5']);
	  <% } else if (mode == 'store' || request.serverName ==~ /www.kettlerstore.com/) { %>
	      _gaq.push(['_setAccount', 'UA-17400793-6']);
	  <% } else if (mode == 'contract' || request.serverName ==~ /www.kettlercontract.com/) { %>
	      _gaq.push(['_setAccount', 'UA-17400793-7']);
	  <% } else if (request.serverName ==~ /www.kettlerlatinoamerica.com/) { %>
	      _gaq.push(['_setAccount', 'UA-17400793-8']);
      <% } else { %>
	      _gaq.push(['_setAccount', 'UA-17400793-4']);
      <% } %>
      _gaq.push(['_trackPageview']);
      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		// commented out / replaced per Amato:
        // ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();

</g:javascript>   
