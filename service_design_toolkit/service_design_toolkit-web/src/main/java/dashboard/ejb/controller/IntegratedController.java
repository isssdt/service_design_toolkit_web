/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.controller;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import dashboard.ejb.view.IntegratedView;
import java.io.Serializable;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.ejb.business.TouchPointServiceLocal;

import user.dto.FieldResearcherDTO;

/**
 *
 * @author samru
 */
@Named(value = "integratedController")
@RequestScoped
public class IntegratedController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private IntegratedView integratedView;
    @EJB
    private JourneyServiceLocal journeyService;
   @EJB
    private TouchPointServiceLocal touchPointService;

    private Map<String, String> journeyNameMap;

    public Map<String, String> getJourneyNameMap() {
        return journeyNameMap;
    }

    public void setJourneyNameMap(Map<String, String> journeyNameMap) {
        this.journeyNameMap = journeyNameMap;
    }

    @PostConstruct
    public void init() {

        journeyNameMap = new HashMap<>();
        List<JourneyDTO> journeyDTOList;
        try {
            journeyDTOList = getActiveJourneyList();
            for (JourneyDTO journeyDTO : journeyDTOList) {
                journeyNameMap.put(journeyDTO.getJourneyName(), journeyDTO.getJourneyName());
                //integratedView.setJourneyNameMap(journeyNameMap);
            }
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(IntegratedController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public IntegratedView getIntegratedView() {
        return integratedView;
    }

    public void setIntegratedView(IntegratedView integratedView) {
        this.integratedView = integratedView;
    }

    public List<JourneyDTO> getActiveJourneyList() throws AppException, CustomReasonPhraseException {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setIsActive('Y');
        return journeyService.getAllJourney();
    }

    public void onJourneyChange() {
        System.out.println("in jouney change" + integratedView.getJourneyName());
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(integratedView.getJourneyName());

        createLineModels(journeyDTO);

        integratedView.getLineModel1().setTitle("Integrated Map for "+journeyDTO.getJourneyName()+" journey");
        integratedView.getLineModel1().setLegendPosition("ne");
        //integratedView.getLineModel1().setShowPointLabels(true);
        integratedView.getLineModel1().getAxes().put(AxisType.X, new CategoryAxis("Touch Point"));
        Axis yAxis = integratedView.getLineModel1().getAxis(AxisType.Y);
        yAxis.setLabel("Rating");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    private void createLineModels(JourneyDTO journeyDTO) {
        System.out.println("inside integrated craete line model");
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);
        
        System.out.println("no of touch point filed researcher"+touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().size());
        System.out.println("no of filed researcher"+fieldResearcherDTOList.size());
        
        for (FieldResearcherDTO f : fieldResearcherDTOList) {
        ChartSeries tFRseries = new ChartSeries();
        tFRseries.setLabel(f.getSdtUserDTO().getUsername());
            for (TouchPointFieldResearcherDTO t : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
                
                if (f.getSdtUserDTO().getUsername().equals(t.getFieldResearcherDTO().getSdtUserDTO().getUsername()) && t.getStatus().equals("DONE")) {
                    initFrSeries(tFRseries,t,journeyDTO);
                   }

            }
             integratedView.getLineModel1().addSeries(tFRseries);

        }
    }

    private ChartSeries initFrSeries(ChartSeries tFRseries,TouchPointFieldResearcherDTO tPfr,JourneyDTO journeyDTO) {

        System.out.println("initCategoryModel series"+tPfr.getFieldResearcherDTO().getSdtUserDTO().getUsername());
       if (null != tPfr.getRatingDTO().getValue() && !tPfr.getRatingDTO().getValue().isEmpty() && tPfr.getStatus().equals("DONE")) {
            System.out.println("initFrSeries"+tPfr.getRatingDTO().getValue()+"user"+tPfr.getFieldResearcherDTO().getSdtUserDTO().getUsername()+tPfr.getStatus());
           List<TouchPointDTO> touchPointList=touchPointService.getTouchPointListJourney(journeyDTO);
            for(TouchPointDTO t:touchPointList){
                if(t.getTouchPointDesc().equals(tPfr.getTouchpointDTO().getTouchPointDesc()))
            tFRseries.set(t.getTouchPointDesc(), Integer.parseInt(tPfr.getRatingDTO().getValue()));
            }
        }
        else { 
     tFRseries.set(0, 0);
}

        return tFRseries;
    }
}
