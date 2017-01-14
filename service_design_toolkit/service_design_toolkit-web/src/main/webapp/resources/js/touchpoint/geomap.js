var autocomplete;
function initAutocomplete() {
    autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */(document.getElementById('address')),
            {types: ['geocode']});
}

function locateTouchPoint() {
    PF('SCREEN_COMPONENT_GMAP_TOUCH_POINT_LOCATION_ID').geocode(document.getElementById('address').value);
}
