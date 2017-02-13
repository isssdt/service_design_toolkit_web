/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.util;

import common.constant.MasterData;
import common.dto.MasterDataDTO;

/**
 *
 * @author longnguyen
 */
public class Utils {
    public static String getMasterDataID(MasterDataDTO masterDataDTO) {
        if (null == masterDataDTO) return null;
        if (MasterData.TOUCH_POINT_DURATION_DAYS.equals(masterDataDTO.getDataValue())) return MasterData.TOUCH_POINT_DURATION_DAYS_ID;
        if (MasterData.TOUCH_POINT_DURATION_HOURS.equals(masterDataDTO.getDataValue())) return MasterData.TOUCH_POINT_DURATION_HOURS_ID;
        if (MasterData.TOUCH_POINT_DURATION_MINS.equals(masterDataDTO.getDataValue())) return MasterData.TOUCH_POINT_DURATION_MINS_ID;
        return null;
    }    
}
