var autocomplete;
function initAutocomplete() {
    autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */(document.getElementById('address')),
            {types: ['geocode']});
}

function locateTouchPoint() {
    alert('locateTouchPoint');
    PF('geomap').geocode(document.getElementById('address').value);
}
