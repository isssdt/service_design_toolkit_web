/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.constant;

/**
 *
 * @author longnguyen
 */
public class ConstantValues {
    /**
     * From Address for Reset Password Email
     */
    public static final String MAIL_RESET_PASSWORD_FROM_ADDRESS = "no-reply@nguyenduclong.com";    
    /**
     * To Address for Reset Password Email
     */
    public static final String MAIL_RESET_PASSWORD_TO_ADDRESS = "rock_mozart_ndlong@yahoo.com";
    /**
     * Subject for Reset Password Email
     */
    public static final String MAIL_RESET_PASSWORD_SUBJECT = "Your password of Service Design Toolkit has been reset";
    
    public static final String FIELD_RESEARCHER_ROLE_NAME = "Field Researcher";
    public static final int GENERIC_APP_ERROR_CODE = 5001;
    public static final String BLOG_POST_URL = "/service-design-toolkit-web";
    
    public static final String APP_ERROR = "Something wrong happened";
    public static final String APP_DEV_INFO = "Please check the log file";
    
    
    public static final String JOURNEY_FIELD_RESEARCHER_STATUS_DONE = "DONE";
    public static final String JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS = "IN PROGRESS";
    
    public static final String JOURNEY_FIELD_RESEARCHER_NON_JOURNEY_IN_PROGRESS_ERROR = "There is no in progress Journey for this Field Researcher";
    public static final String JOURNEY_FIELD_RESEARCHER_NON_JOURNEY_IN_PROGRESS_DEV_INFO = "Please verify the username of Field Researcher";
    public static final String JOURNEY_FIELD_RESEARCHER_EXISTS_JOURNEY_IN_PROGRESS_ERROR = "This Field Researcher already registered for a Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_EXISTS_JOURNEY_IN_PROGRESS_DEV_INFO = "Please verify the username of Field Researcher and name of Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_DONE_ALREADY_ERROR = "This Field Researcher already completed this Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_DONE_ALREADY_ERROR_DEV_INFO = "Please verify the username of Field Researcher and name of Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_NOT_EXISTS_ERROR = "This Field Researcher with this Journey does not exists";
    public static final String JOURNEY_FIELD_RESEARCHER_NOT_EXISTS_ERROR_DEV_INFO = "Please verify the username of Field Researcher and name of Journey";
    
    public static final String JOURNEY_NO_REGISTER_ERROR = "There is no Journey to register at this time.";
    public static final String JOURNEY_NO_REGISTER_ERROR_DEV_INFO = "Please check data in DB";
    
    public static final String TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE = "DONE";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS = "IN PROGRESS";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_ERROR_NON_EXISTS = "There is no Touch Point for this Field Researcher";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_NON_EXISTS_ERROR_DEV_INFO = "Please verify the username of Field Researcher and Touch Point ID";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_ALREADY_DONE_ERROR = "This Touch Point is already DONE";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_ALREADY_DONE_ERROR_DEV_INFO = "Please verify the username of Field Researcher and Touch Point ID";
    
    public static final String TOUCH_POINT_FIELD_RESEARCHER_ERROR_NO_RATING = "Rating has not been provided for this Touch Point";
    
    public static final String TOUCH_POINT_FIELD_RESEARCHER_RESPONSE_COMPLETE_JOURNEY = "Please informed that you have completed work for all Touch Points";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_RESPONSE_UPDATE_SUCCESSFUL = "A new research work has been created";
    
    
    /**
     * SELECT s FROM SdtUser s WHERE s.username = :username and s.password = :password  
     */
    public static final String SDT_USER_QUERY_AUTHENTICATE = "SdtUser.00001";    
    /**
     * SELECT s FROM SdtUser s WHERE s.username = :username
     */
    public static final String QUERY_SDT_USER_FIND_USER_BY_USERNAME = "SdtUser.findByUsername";
    /**
     * SELECT t FROM TouchpointFieldResearcher t WHERE t.fieldResearcherId.sdtUser.username = :username 
     * and t.touchpointId.journeyId IN (SELECT J.journeyId FROM JourneyFieldResearcher J 
     * WHERE J.fieldResearcherId.sdtUser.username = :username and J.status = 'IN PROGRESS') 
     */
    public static final String QUERY_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY_OF_FIELD_RESEARCHER = "TouchpointFieldResearcher.00001"; 
    /**
     * SELECT COUNT(1) FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName AND t.ratingId.id = 3
     */
    public static final String QUERY_GET_COUNT_NEUTRAL_RATING_FOR_TOUCH_POINT_OF_JOURNEY = "TouchpointFieldResearcher.00002"; 

    /**
     * SELECT COUNT(1) FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName AND t.ratingId.id in (1,2)
     */
    public static final String QUERY_GET_COUNT_DISLIKE_RATING_FOR_TOUCH_POINT_OF_JOURNEY = "TouchpointFieldResearcher.00003"; 

    /**
     * SELECT COUNT(1) FROM TouchpointFieldResearcher t WHERE t.touchpointId.journeyId.journeyName = :journeyName AND t.ratingId.id in (4,5)
     */
    public static final String QUERY_GET_COUNT_LIKE_RATING_FOR_TOUCH_POINT_OF_JOURNEY = "TouchpointFieldResearcher.00004"; 
    /**
     * SELECT s FROM SdtUser s WHERE s.username = :username 
     */
    public static final String SDT_USER_QUERY_FIND_BY_USERNAME = "SdtUser.findByUsername";
    /**
     * SELECT j FROM JourneyFieldResearcher j WHERE j.fieldResearcherId.sdtUser.username = :username
     */
    public static final String QUERY_JOURNEY_FIELD_RESEARCHER_FIND_JOURNEY_THAT_FIELD_RESEARCHER_REGISTERED = "JourneyFieldResearcher.0001";
    /**
     * SELECT j FROM Journey j WHERE j.startDate <= :startDate and j.endDate >= :endDate and j.canBeRegistered = 'Y'
     */
    public static final String QUERY_JOURNEY_FIND_JOURNEY_THAT_CAN_BE_REGISTERED = "Journey.findJourneyListForRegister";  
    /**
     * SELECT u FROM UserRole u WHERE u.roleName = :roleName
     */
    public static final String QUERY_USER_ROLE_FIND_ROLE_BY_NAME = "UserRole.findByRoleName";    
    /**
     * SELECT m FROM MasterData m WHERE m.dataValue = :dataValue
     */
    public static final String QUERY_MASTER_DATA_FIND_BY_DATA_VALUE = "MasterData.findByDataValue";
    
    
    //Username or password is not correct    
    public static final String SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD = "Username or password is not correct";    
    //Username is not correct    
    public static final String SDT_USER_ERROR_INCORRECT_USERNAME = "Username is not correct";    
    //No Username     
    public static final String SDT_USER_ERROR_NO_USERNAME = "No Username";        
    //New password must be different with old password    
    public static final String SDT_USER_ERROR_NEW_OLD_PASSWORD_SAME = "New password must be different with old password";
    
    /**
     * Authenticated
     */
    public static final String SDT_USER_STATUS_AUTHENTICATED = "Authenticated";
    
    /**
     * Password has been changed
     */
    public static final String SDT_USER_STATUS_PASSWORD_CHANGE = "Password has been changed";
    
    /**
     * Dashboard page
     */
    public static final String URI_DASHBORAD_PAGE = "/dashboard/dashboard.xhtml?faces-redirect=true";
    
    /**
     * Create Journey page
     */
    public static final String URI_CREATE_JOURNEY_PAGE = "/journey/createJourney.xhtml?faces-redirect=true";
    
    /**
     * Add Touch Point page
     */
    public static final String URI_ADD_TOUCH_POINT_PAGE = "/journey/addTouchPoint.xhtml?faces-redirect=true";
    
    /**
     * Specify location on map page
     */
    public static final String URI_MAP_PAGE = "/journey/map.xhtml?faces-redirect=true";
    
    /**
     * View Integration Journey Page
     */
    public static final String URI_INTEGRATED_JOURNEY_PAGE = "/journey/integrated.xhtml?faces-redirect=true";
    
    /**
     * Password change page
     */
    public static final String URI_PASSWORD_CHANGE_PAGE = "/auth/changePassword.xhtml?faces-redirect=true";
    
    /**
     * Login page
     */
    public static final String URI_LOGIN_PAGE = "/auth/login.xhtml?faces-redirect=true";
    
    /**
     * Index page
     */
    public static final String URI_INDEX_PAGE = "/index.xhtml?faces-redirect=true";
    
    /**
     * Name of the DUMMY integration chart model 
     */
    public static final String CHART_DUMMY_NAME = "DUMMY";
    
    /**
     * X Axis of Integration Map
     */
    public static final String CHART_INTEGRATION_X_AXIS = "Touch Point";
    
    /**
     * Y Axis of Integration Map
     */
    public static final String CHART_INTEGRATION_Y_AXIS = "Rating";
    
    /**
     * X Axis of Time Gap Diagram
     */
    public static final String CHART_TIME_GAP_X_AXIS = "Touch Point";
    
    /**
     * Y Axis of Time Gap Diagram
     */
    public static final String CHART_TIME_GAP_Y_AXIS = "Field Researcher time";
    
    
     /**
     * Icon for marker of Field Researcher
     */
    public static final String MARKER_ICON_FIELD_RESEARCHER = "https://maps.google.com/mapfiles/kml/shapes/library_maps.png";
    
    public static final String MARKER_ICON_TOUCH_POINT = "http://www.google.com/mapfiles/marker.png";
    
    public static final String MARKER_ICON_TOUCH_POINT_CURRENT =  "http://maps.google.com/mapfiles/kml/paddle/grn-circle.png";
    
    public static final String DIALOG_LOCATION_CREATE_TOUCH_POINT = "/touchpoint/dialog/create";
    
    public static final String DIALOG_LOCATION_TOUCH_POINT_LOCATION = "/touchpoint/dialog/geomap";

    public static final String CONSTANT_GEO_MAP_ZOOM_LEVEL = "13"; 
    
    public static final String CONSTANT_GEO_MAP_TYPE_ROADMAP = "ROADMAP"; 
    
    public static final String CONSTANT_GEO_MAP_CENTER = "1.2971342, 103.7777567"; 
}
