/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.constraint;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import journey.dto.JourneyDTO;
import journey.ejb.business.JourneyServiceLocal;

/**
 *
 * @author longnguyen
 */

public class JourneyValidator implements ConstraintValidator<Journey, String> {

    @EJB
    JourneyServiceLocal journeyService;

    @Override
    public void initialize(Journey constraintAnnotation) {
        
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(value);
        return null == journeyService.getJourneyByName(journeyDTO);
    }
}
