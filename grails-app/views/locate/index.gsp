<%--
  Created by IntelliJ IDEA.
  User: Mike Brown
  Date: Aug 17, 2010
  Time: 7:55 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    
    <!--  meta name="layout" content="${params.mode?:(params.division?.replaceAll(' ','')?:'kettlerusa')}" / -->
    <meta name="layout" content="${(params.division?.replaceAll(' ',''))?:'kettlerusa'}" />
    <title>Map Locator</title>
    <!-- g:javascript library="jquery" plugin="jquery"/ MikeB -->
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
      var map;
      var bndsTimeout;
      var topeka = new google.maps.LatLng(39.055824, -95.68902);
      var initialLocation = topeka;
      var browserSupportFlag =  new Boolean();
      var markers = new Array();

      function getMarkers(data,textStatus) {
//        if (typeof console != 'undefined') {console.log("getMarkers called");}
        if (data.success) {
          //remove existing markers
          for (i in markers) {
            markers[i].setMap(null);
          }
          markers.length = 0;
          var arraySize = data.markers.length;
          if (arraySize > 0) {
            for (i=0;i<arraySize;i=i+1) {
              (function () {  /* HACK TO ENSURE INFO WINDOWS ARE UNIQUE */
                var mData = data.markers[i];
                var mLatlng = new google.maps.LatLng(mData.lat,mData.lng);
                var marker = new google.maps.Marker({
                  position: mLatlng,
                  map: map,
                  title: mData.dName
                });
                markers.push(marker);
                var infoWindow = new google.maps.InfoWindow({content: mData.infowin});
                google.maps.event.addListener(marker, 'click', function(){
                     infoWindow.open(map, marker);
                });
              })(); /* HACK TO ENSURE INFO WINDOWS ARE UNIQUE */
            }
          }
          $('#dealerList').html(data.dealerList);
        } else {
          alert(data.error);
        }
      };
      function bndsError(xhr,textStatus,errorThrown) {
        alert(textStatus);
      };
      function bndsChanged() {
//        if (typeof console != 'undefined') {console.log("bndsChanged called");}
        var ne = map.getBounds().getNorthEast();
        var sw = map.getBounds().getSouthWest();
        var center = map.getCenter();
        var params = 'neLat='+ne.lat()+'&neLng='+ne.lng()+'&swLat='+sw.lat()+'&swLng='+sw.lng()
                   +'&cLat='+center.lat()+'&cLng='+center.lng()+'&mode='
                   +'${params.mode}';
        <g:if test="${division}">
          params = params + '&division=' + '${division}'
        </g:if>
        <g:if test="${itemId}">
          params = params + '&itemId=' + '${itemId}'
        </g:if>
        $.ajax({
           url: "${createLink(action:'bndsChanged')}",
           data: params,
           dataType: 'json',
           type: 'POST',
           success: function(data,textStatus) { getMarkers(data,textStatus) },
           error: function(xhr,textStatus, errorThrown) { bndsError(xhr,textStatus,errorThrown) }
        });
      };
      function initialize() {
        var myOptions = {
          zoom: 10,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map_canvas"),myOptions);
//        if (typeof console != 'undefined') {console.log("initialize called");}
        setInitialLoc();
//        if (typeof console != 'undefined') {console.log("after setInitialLoc call");}

        google.maps.event.addListener(map, 'bounds_changed', function() {
          if (bndsTimeout) {
            window.clearTimeout(bndsTimeout);
          }
          bndsTimeout = window.setTimeout(bndsChanged, 500);
        });
      };
      function handleSearchResults(data, textStatus) {
    	//if (typeof console != 'undefined') {console.log("handleSearchResults called");}
        //$('#junk').html("XmlHttpRequest = success:" + data.success + ", lat: " + data.lat + ", lng: " + data.lng);
        if (data.success) {
          var myLatlng = new google.maps.LatLng(data.lat, data.lng);
          map.setCenter(myLatlng);
          bndsChanged(); // in case map center didn't change
          //map.setZoom(10);
        } else {
          $('#message').html(data.error);
        }
      };
      function setInitialLoc() {
//        if (typeof console != 'undefined') {console.log("setInitialLoc called");}
        if(navigator.geolocation) {
//          if (typeof console != 'undefined') {console.log('using navigator.geolocation');}
          browserSupportFlag = true;
          navigator.geolocation.getCurrentPosition(function(position) {
            initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
            map.setCenter(initialLocation);
//            if (typeof console != 'undefined') {console.log("setInitialLoc, latlng: " + initialLocation.toString());}
          }, function() {
            handleNoGeolocation(browserSupportFlag);
          });
        } else {
          browserSupportFlag = false;
          handleNoGeolocation(browserSupportFlag);
        }
        function handleNoGeolocation(errorFlag) {
//          if (typeof console != 'undefined') {console.log('handleNoGeolocation, setting to topeka');}
          initialLocation = topeka;
          map.setCenter(initialLocation);
        }

      };
      $(document).ready(function() {
        initialize();
      });
    </script>
    <style type="text/css" media="screen">
    #map_canvas{
            width: 800px;
            height: 450px;
        }

    </style>    
  </head>
  <body>
  <div class="body">
    <div id="errors"></div>
    <g:formRemote name="locSearch" url="[action:'search']" onSuccess="handleSearchResults(data,textStatus)">
        Address or zip: <g:textField name="data" />
        Search radius: <select name="distance">
        <% if (params.mode?.equals("canada")) { %>
        <option value="5">5 km</option>
        <option value="10">10 km</option>
        <option value="20" selected="selected">20 km</option>
        <option value="60">60 km</option>
        <option value="100">100 km</option>
        <% } else { %>
        <option value="5">5 Miles</option>
        <option value="10">10 Miles</option>
        <option value="20" selected="selected">20 Miles</option>
        <option value="60">60 Miles</option>
        <option value="100">100 Miles</option>
        <% } %>
        </select>
        <g:hiddenField name="itemId" value="${itemId}"/>
        <g:hiddenField name="mode" value="${params.mode}"/>
        <g:actionSubmit action="search" value="Search" />
    </g:formRemote>
	<a href="#" onclick="history.go(-1);return false;" class="retailer button">Back</a>
    <g:link controller="dealer" action="webDealers" params="[division:division,itemId:itemId,mode:params.mode]" class="retailer button">
     Online Retailers
    </g:link>
    <div id="junk"></div>
    <div id="map_canvas"></div>
    <br/>
    <br/>
    <div id="dealerList"></div>
  </div>
  <br/>
  </body>
</html>
