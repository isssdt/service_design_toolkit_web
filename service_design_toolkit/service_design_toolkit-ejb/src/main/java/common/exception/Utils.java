/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author longnguyen
 */
public class Utils {

    public static AppException throwAppException(String message, String className, int status) {        
        AppException e = new AppException(status);
        Logger.getLogger(className).log(Level.SEVERE, message);
        return e;
    }
}
