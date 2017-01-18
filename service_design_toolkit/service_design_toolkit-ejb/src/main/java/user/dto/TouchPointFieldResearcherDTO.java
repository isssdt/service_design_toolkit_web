/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.dto;

import touchpoint.dto.TouchPointDTO;
import common.dto.RatingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import user.dto.FieldResearcherDTO;

/**
 *
 * @author samru
 */

@JsonInclude(Include.NON_NULL)
public class TouchPointFieldResearcherDTO {
    private FieldResearcherDTO fieldResearcherDTO;
    private TouchPointDTO touchpointDTO;
    private String comments;
    private String reaction;
    private RatingDTO ratingDTO;
    private String status;

    public FieldResearcherDTO getFieldResearcherDTO() {
        return fieldResearcherDTO;
    }

    public void setFieldResearcherDTO(FieldResearcherDTO fieldResearcherDTO) {
        this.fieldResearcherDTO = fieldResearcherDTO;
    }

    public TouchPointDTO getTouchpointDTO() {
        return touchpointDTO;
    }

    public void setTouchpointDTO(TouchPointDTO touchpointDTO) {
        this.touchpointDTO = touchpointDTO;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public RatingDTO getRatingDTO() {
        return ratingDTO;
    }

    public void setRatingDTO(RatingDTO ratingDTO) {
        this.ratingDTO = ratingDTO;
    }   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
