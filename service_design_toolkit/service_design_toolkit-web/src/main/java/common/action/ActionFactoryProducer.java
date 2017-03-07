/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.action;

/**
 *
 * @author longnguyen
 */
public class ActionFactoryProducer {
    public static ActionAbstractFactory produceActionFactory(String factory) {
        if (ActionActionEventFactory.class.toString().equals(factory)) {
            return new ActionActionEventFactory();
        }
        return null;
    }
}
