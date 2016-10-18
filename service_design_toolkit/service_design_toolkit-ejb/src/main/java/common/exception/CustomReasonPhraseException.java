/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.exception;

/**
 *
 * @author longnguyen
 */
public class CustomReasonPhraseException extends Exception {

    private static final long serialVersionUID = -271582074543512905L;

    private final int businessCode;

    public CustomReasonPhraseException(int businessCode, String message) {
        super(message);
        this.businessCode = businessCode;
    }

    public int getBusinessCode() {
        return businessCode;
    }
}
