/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.utils;

import java.io.IOException;
import javax.faces.context.FacesContext;

/**
 *
 * @author Leon
 */
public class Utils {
    public static void forwardToPage(String uri) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
    }
}
