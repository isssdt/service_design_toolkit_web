/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import touchpoint.dto.TouchPointDTO;
import common.dto.RatingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import common.dto.MasterDataDTO;
import java.util.Date;

/**
 *
 * @author samru
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TouchPointFieldResearcherDTO {
    private FieldResearcherDTO fieldResearcherDTO;
    private TouchPointDTO touchpointDTO;
    private String comments;
    private String reaction;
    private RatingDTO ratingDTO;
    private String status;
    private Integer duration;
    private String photoLocation;
    private Date actionTime;
    private MasterDataDTO durationUnitDTO;
    private Double convertedToExepectedDuration;

    public Double getConvertedToExepectedDuration() {
        return convertedToExepectedDuration;
    }

    public void setConvertedToExepectedDuration(Double convertedToExepectedDuration) {
        this.convertedToExepectedDuration = convertedToExepectedDuration;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public MasterDataDTO getDurationUnitDTO() {
        return durationUnitDTO;
    }

    public void setDurationUnitDTO(MasterDataDTO durationUnitDTO) {
        this.durationUnitDTO = durationUnitDTO;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

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
