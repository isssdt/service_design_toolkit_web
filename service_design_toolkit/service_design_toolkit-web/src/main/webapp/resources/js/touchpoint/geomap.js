    var autocomplete;
    function initAutocomplete() {
        autocomplete = new google.maps.places.Autocomplete(
                /** @type {!HTMLInputElement} */(document.getElementById('address')),
                {types: ['geocode']});
    }

    function locateTouchPoint() {    
        PF('SCREEN_COMPONENT_GMAP_TOUCH_POINT_LOCATION_ID').geocode(document.getElementById('address').value);
    }

     var currentMarker = null;
 
    function handlePointClick(event) {
        if(currentMarker === null) {
            document.getElementById('lat').value = event.latLng.lat();
            document.getElementById('lng').value = event.latLng.lng();
 
            currentMarker = new google.maps.Marker({
                position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
            });
 
            PF('map').addOverlay(currentMarker);
 
            PF('dlg').show();
        }   
    }
 
    function markerAddComplete() {
        var title = document.getElementById('title');
        currentMarker.setTitle(title.value);
        title.value = "";
 
        currentMarker = null;
        PF('dlg').hide();
    }
 
    function cancel() {
        PF('dlg').hide();
        currentMarker.setMap(null);
        currentMarker = null;
 
        return false;
    }