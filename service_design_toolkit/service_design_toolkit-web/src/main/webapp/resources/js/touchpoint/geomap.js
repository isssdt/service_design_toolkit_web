    var autocomplete;
    function initAutocomplete() {
        autocomplete = new google.maps.places.Autocomplete(
                /** @type {!HTMLInputElement} */(document.getElementById('address')),
                {types: ['geocode']});
    }

    function locateTouchPoint() {    
        PF('SCREEN_COMPONENT_GMAP_TOUCH_POINT_LOCATION_ID').geocode(document.getElementById('address').value);
    }
//    var draw_circle = null;
//    var rad =1000;
//    var currentMarker = null;
//    function handlePointClick(event) {
//        var LatLng = event.latLng;
//        if (currentMarker !== null){
//           currentMarker.setMap(null);
//           document.getElementById('lat').value = LatLng.lat();
//            document.getElementById('lng').value = LatLng.lng();
//            currentMarker = new google.maps.Marker({
//                position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
//            });
//            PF('map').addOverlay(currentMarker);
////            PF('dlg').show();
//            onbeforeunload
//            getAddress(LatLng);
//            drawCircle(rad,LatLng);
//        }
//        if(currentMarker === null) {
//            document.getElementById('lat').value = event.latLng.lat();
//            document.getElementById('lng').value = event.latLng.lng();
//            currentMarker = new google.maps.Marker({
//                position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
//            });
//            PF('map').addOverlay(currentMarker);
////            PF('dlg').show();
//            onbeforeunload
//            getAddress(LatLng);
//            drawCircle(rad,LatLng);
//        }   
//    }
//    function getAddress(LatLng) {
//    geocoder = new google.maps.Geocoder();
//    geocoder.geocode( {'latLng': LatLng},
//      function(results, status) {
//        if(status === google.maps.GeocoderStatus.OK) {
//          if(results[0]) {
//            document.getElementById("address").value = results[0].formatted_address;
//          }
//          else {
//            document.getElementById("address").value = "No results";
//          }
//        }
//        else {
//          document.getElementById("address").value = status;
//        }
//      });
//    } 
//    function drawCircle(rad,LatLng){ 
//    if (draw_circle != null) {
//        draw_circle.setMap(null);
//    }
//    draw_circle = new google.maps.Circle({
//        center: LatLng,
//        radius: rad,
//        strokeColor: "#FF0000",
//        strokeOpacity: 0.8,
//        strokeWeight: 2,
//        fillColor: "#FF0000",
//        fillOpacity: 0.35,
//        map:SCREEN_COMPONENT_GMAP_TOUCH_POINT_LOCATION_ID
//    });
//        draw_circle.bindTo(currentMarker);
//    }
    
    //    function markerAddComplete() {
//        var title = document.getElementById('title');
//        currentMarker.setTitle(title.value);
//        title.value = "";
// 
//        currentMarker = null;
//        PF('dlg').hide();
//    }
// 
//    function cancel() {
//        PF('dlg').hide();
//        currentMarker.setMap(null);
//        currentMarker = null;
// 
//        return false;
//    }