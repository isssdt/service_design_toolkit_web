/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.controller;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import dashboard.view.IntegratedView;
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
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import touchpoint.dto.TouchPointFieldResearcherListDTO;

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

        integratedView.getLineModel1().setTitle("Integrated Map");
        integratedView.getLineModel1().setLegendPosition("e");
        integratedView.getLineModel1().setShowPointLabels(true);
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

        for (FieldResearcherDTO f : fieldResearcherDTOList) {

            for (TouchPointFieldResearcherDTO t : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
                t.getTouchpointDTO();
                if (f.getSdtUserDTO().getUsername().equals(t.getFieldResearcherDTO().getSdtUserDTO().getUsername())) {
                    ChartSeries frSeries = initFrSeries(t);
                    integratedView.getLineModel1().addSeries(frSeries);
                }

            }

        }
    }

    private ChartSeries initFrSeries(TouchPointFieldResearcherDTO t) {

        System.out.println("initCategoryModel");
        ChartSeries tFR = new ChartSeries();
        tFR.setLabel(t.getFieldResearcherDTO().getSdtUserDTO().getUsername());

        if (null != t.getRatingDTO().getValue() && !t.getRatingDTO().getValue().isEmpty()) {
            tFR.set("TP1", Integer.parseInt(t.getRatingDTO().getValue()));
        }

        return tFR;
    }
}
