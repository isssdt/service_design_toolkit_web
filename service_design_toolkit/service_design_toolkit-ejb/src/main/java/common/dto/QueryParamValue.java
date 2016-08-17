/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.dto;

/**
 *
 * @author longnguyen
 */
public class QueryParamValue {
    private String param;
    private Object value;

    public QueryParamValue(String param, Object value) {
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
