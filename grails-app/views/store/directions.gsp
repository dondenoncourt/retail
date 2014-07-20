<head> 
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="store" />
    <meta name="description" content="KETTLER Retail Outlet Store for ${keywords}"/> 
    <meta name="keywords" content="KETTLER, kettlerstore.com, ${keywords} "/>     
    <title>Kettler Store Virginia Beach | Directions from Norfolk, Chesapeake & Hampton Roads Areas</title> 
</head> 


<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<div id="storeDirections">
    <h2>Hours:</h2> 
    <div id="hours">
        <dl>
            <dt>Tuesday - Saturday:</dt><dd>10:00am - 6:00pm</dd>
            <dt>Sunday:</dt><dd>12:00pm - 5:00pm</dd>
            <dt>Monday:</dt><dd>Closed</dd>              
        </dl>
    </div>
    <h2>Directions:</h2> 
    <div id="address">
		4117 Virginia Beach Blvd<br/>
		Virginia Beach, VA 23452<br/>
		(757)498-8001<br/>     
        <br/>
	    <a onclick="window.open(driving)">Driving Directions</a>
    </div>
    <br/>
    <div id="map_canvas"></div>
</div>
<g:javascript>
    var driving = 'http://maps.google.com/maps?um=1&ie=UTF-8&q=Kettler+Showroom,+Virginia+Beach+Boulevard,+Virginia+Beach,+VA&fb=1&gl=us&hq=Kettler+Showroom,&hnear=Virginia+Beach+Blvd,+Virginia+Beach,+VA&cid=0,0,95575861363275125&ei=RhlITfHlDIzPgAej4d2QBg&sa=X&oi=local_result&ct=image&resnum=1&ved=0CCAQnwIwAA';
    var latLng = new google.maps.LatLng(36.842, -76.1169)
    var markers = new Array();
	var map = new google.maps.Map(document.getElementById("map_canvas"),
	                           {zoom: 10, mapTypeId: google.maps.MapTypeId.ROADMAP}
	                          );
	map.setCenter(latLng);
    var marker = new google.maps.Marker({
      position: latLng,
      map: map,
      title: 'Click to get driving directions to Kettler Store'
    });
    new google.maps.InfoWindow({content: $('#address').attr('innerHTML')}).open(map, marker);
	
    google.maps.event.addListener(marker, 'click', function() { window.open(driving); });    
    $('#directions').addClass('current');
</g:javascript>    
