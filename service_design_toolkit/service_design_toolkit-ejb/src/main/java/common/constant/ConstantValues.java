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
    public static final String FIELD_RESEARCHER_ROLE_NAME = "Field Researcher";
    public static final int GENERIC_APP_ERROR_CODE = 5001;
    public static final String BLOG_POST_URL = "/service-design-toolkit-web";
    
    public static final String JOURNEY_FIELD_RESEARCHER_STATUS_DONE = "DONE";
    public static final String JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS = "IN PROGRESS";
    public static final String JOURNEY_FIELD_RESEARCHER_NON_JOURNEY_IN_PROGRESS_ERROR = "There is no in progress Journey for this Field Researcher";
    public static final String JOURNEY_FIELD_RESEARCHER_NON_JOURNEY_IN_PROGRESS_DEV_INFO = "Please verify the username of Field Researcher";
    public static final String JOURNEY_FIELD_RESEARCHER_EXISTS_JOURNEY_IN_PROGRESS_ERROR = "This Field Researcher already registered for a Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_EXISTS_JOURNEY_IN_PROGRESS_DEV_INFO = "Please verify the username of Field Researcher and name of Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_DONE_ALREADY_ERROR = "This Field Researcher already completed this Journey";
    public static final String JOURNEY_FIELD_RESEARCHER_DONE_ALREADY_ERROR_DEV_INFO = "Please verify the username of Field Researcher and name of Journey";
    
    public static final String JOURNEY_NO_REGISTER_ERROR = "There is no Journey to register at this time.";
    public static final String JOURNEY_NO_REGISTER_ERROR_DEV_INFO = "Please check data in DB";
    
    public static final String TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE = "DONE";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS = "IN PROGRESS";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_NON_EXISTS_ERROR = "There is no Touch Point for this Field Researcher";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_NON_EXISTS_ERROR_DEV_INFO = "Please verify the username of Field Researcher and Touch Point ID";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_ALREADY_DONE_ERROR = "This Touch Point is already DONE";
    public static final String TOUCH_POINT_FIELD_RESEARCHER_ALREADY_DONE_ERROR_DEV_INFO = "Please verify the username of Field Researcher and Touch Point ID";
}
