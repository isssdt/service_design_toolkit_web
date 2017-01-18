/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Leon
 */
public class UtilConverter {

    public static List<String> traverseDTOList = new ArrayList<>();

    public static void deepCopy(Object entityObj, Object dtoObj) {
        if (UtilConverter.traverseDTOList.indexOf(dtoObj.getClass().toString()) != -1) {
            return;
        }
        try {
            BeanUtils.copyProperties(dtoObj, entityObj);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(UtilConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Method dtoMethod : dtoObj.getClass().getMethods()) {
            if (dtoMethod.getName().startsWith("get") && dtoMethod.getName().endsWith("DTO")) {
                String entityMethod = dtoMethod.getName().substring(0, dtoMethod.getName().length() - 3);
                if (!checkMethodExist(entityMethod, entityObj)) {
                    entityMethod = entityMethod + "Id";
                    if (!checkMethodExist(entityMethod, entityObj)) {
                        return;
                    }
                }
                System.out.println(dtoMethod.getName() + "|" + entityMethod);
                UtilConverter.traverseDTOList.add(dtoMethod.getReturnType().toString());
                try {
                    try {
                        Object newDTO = dtoMethod.getReturnType().getConstructor();
                        deepCopy(entityObj.getClass().getMethod(entityMethod).invoke(entityObj), newDTO);
                        Method setDTO = dtoObj.getClass().getMethod("s" + dtoMethod.getName().substring(1));
                        setDTO.invoke(dtoObj, newDTO);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(UtilConverter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(UtilConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        UtilConverter.traverseDTOList.clear();
    }

    public static boolean checkMethodExist(String methodName, Object object) {
        for (Method method : object.getClass().getMethods()) {
            if (methodName.equals(method.getName())) {
                return true;
            }
        }
        return false;
    }
}
