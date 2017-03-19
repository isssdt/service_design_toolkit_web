/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.view;

import common.ScreenTitles;
import common.constant.ConstantValues;
import common.ejb.business.ServiceFactory;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.Circle;
import touchpoint.dto.TouchPointDTO;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import touchpoint.controller.TouchPointController;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;

/**
 *
 * @author longnguyen
 */
@Named(value = "touchpoint_ejb_view_GeoMapView")
@ViewScoped
public class GeoMapView extends AbstractView implements Serializable {

    private TouchPointDTO touchPointDTO;
    private String centerGeoMap;
    private MapModel touchPointLocationModel;
    private String radius ="100";
    private LatLng latlng;

    /**
     * Creates a new instance of GeoMapView
     */
    public GeoMapView() {
        super();
    }

    @Override
    public void initController() {
        controller = new TouchPointController(this);
    }

    @Override
    public void initData() {
        touchPointDTO = (TouchPointDTO) Utils.getAttributeOfSession(TouchPointDTO.class.toString());
        centerGeoMap = ConstantValues.CONSTANT_GEO_MAP_CENTER;
        touchPointLocationModel = new DefaultMapModel();
    }

    @Override
    public ServiceFactory getServices() {
        return null;
    }

    public TouchPointDTO getTouchPointDTO() {
        return touchPointDTO;
    }

    public void setTouchPointDTO(TouchPointDTO touchPointDTO) {
        this.touchPointDTO = touchPointDTO;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public MapModel getTouchPointLocationModel() {
        return touchPointLocationModel;
    }

    public void setTouchPointLocationModel(MapModel touchPointLocationModel) {
        this.touchPointLocationModel = touchPointLocationModel;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
    
    private Marker marker;
    Circle circle1;
    
    public void onPointSelect(PointSelectEvent event) {
        latlng = event.getLatLng();
        marker = new Marker(latlng, "helo");
        touchPointDTO.setLatitude(String.valueOf(latlng.getLat()));
        touchPointDTO.setLongitude(String.valueOf(latlng.getLng()));
        touchPointDTO.setRadius(radius);
        circle1  = new Circle(latlng, Double.parseDouble(radius));
        circle1.setStrokeColor("#d93c3c");
        circle1.setFillColor("#d93c3c");
        circle1.setFillOpacity(0.5);
        
        touchPointLocationModel.addOverlay(marker);
        touchPointLocationModel.addOverlay(circle1);
        RequestContext context ;
        try {
            sendGet();
            context = RequestContext.getCurrentInstance();
            context.update(ScreenTitles.SCREEN_COMPONENT_TEXT_FIELD_TOUCH_POINT_LOCATION_ADDRESS_ID);
        } catch (Exception ex) {
            Logger.getLogger(GeoMapView.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        //context.execute(ScreenTitles.SCREEN_COMPONENT_JS_FUNCTION_LOCATE_TOUCH_POINT);
    }
    private void sendGet() throws Exception {
		String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyDTfYgSvgKe9cbMi4RxsmLGUZU5acZpZIo";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		System.out.println("Response  : " + con.getResponseMessage());
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);                       
		}
		in.close();
                System.out.println("Response" + response );
                
                JSONObject jsonObj = new JSONObject(response.toString());
                String loc = jsonObj.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                System.out.println("jsonOBJ: " +loc);      
                touchPointDTO.setChannelDescription(loc.toString());
    }

    
//    private Geocoder geocoder;  
//    private String getAddress(double latitude, double longitude) {
//        StringBuilder result = new StringBuilder();
//        try {
//            geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//                result.append(address.getLocality()).append("\n");
//                result.append(address.getCountryName());
//            }
//        } catch (IOException e) {
//            Log.e("tag", e.getMessage());
//        }
//
//        return result.toString();
//    }
}
