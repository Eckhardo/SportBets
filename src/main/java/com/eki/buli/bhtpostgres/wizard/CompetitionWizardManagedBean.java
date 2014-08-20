/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.buli.bhtpostgres.wizard;

import com.eki.buli.bhtpostgres.Competition;
import com.eki.buli.bhtpostgres.CompetitionController;
import com.eki.buli.bhtpostgres.CompetitionFacade;
import com.eki.buli.bhtpostgres.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ekirschning
 */
@Named(value = "competitionWizard")
@SessionScoped
public class CompetitionWizardManagedBean implements Serializable {

    /**
     * Creates a new instance of CompetitionWizardManagedBean
     */
    public CompetitionWizardManagedBean() {
    }
    private static final Logger log = Logger.getLogger(CompetitionController.class.getName());

    @EJB
    private CompetitionFacade compFacade;
    private List<Competition> competitions = null;
    private Competition selectedComp;

    @Inject
    private Event<Competition> competitionChangedEvent;

    private boolean skip;

    public Competition prepareCreate() {
        selectedComp = new Competition();
        initializeEmbeddableKey();
        return selectedComp;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompetitionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            competitions = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompetitionUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompetitionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedComp = null; // Remove selection
            competitions = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Competition> getCompetitions() {
        if (competitions == null) {
            competitions = getCompFacade().findAll();
        }
        // log.log(Level.WARNING, " cometitions size = {0}", competitions == null ? "0" : competitions.size());
        return competitions;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selectedComp != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == JsfUtil.PersistAction.DELETE) {
                    getCompFacade().remove(selectedComp);

                } else if (persistAction == JsfUtil.PersistAction.CREATE) {
                    getCompFacade().create(selectedComp);
                } else {
                    selectedComp = getCompFacade().edit(selectedComp);
                }
                {

                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            log.log(Level.WARNING, "flow event =confirm");

            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            log.log(Level.WARNING, "new flow event = {0}", event.getNewStep());
            log.log(Level.WARNING, " old flow event = {0}", event.getOldStep());
            if (event.getOldStep().equals(ResourceBundle.getBundle("/Bundle").getString("TabComp"))
                    && event.getNewStep().equals(ResourceBundle.getBundle("/Bundle").getString("TabCompRound"))) {
                competitionChangedEvent.fire(selectedComp);
            }
            return event.getNewStep();
        }
    }

    public Competition getCompetition(java.lang.Integer id) {
        return getCompFacade().find(id);
    }

    public Competition getSelectedComp() {
        return selectedComp;
    }

    public void setSelectedComp(Competition selectedComp) {
        this.selectedComp = selectedComp;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompetitionFacade getCompFacade() {
        return compFacade;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    @FacesConverter(forClass = Competition.class)
    public static class CompetitionWizardManagedBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompetitionWizardManagedBean controller = (CompetitionWizardManagedBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "competitionWizard");
            return controller.getCompetition(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Competition) {
                Competition o = (Competition) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Competition.class.getName()});
                return null;
            }
        }

    }
}
