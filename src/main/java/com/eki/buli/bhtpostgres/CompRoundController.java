package com.eki.buli.bhtpostgres;

import com.eki.buli.bhtpostgres.util.JsfUtil;
import com.eki.buli.bhtpostgres.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named("compRoundController")
@SessionScoped
public class CompRoundController implements Serializable {

    private static final Logger log = Logger.getLogger(CompRoundController.class.getName());

    @EJB
    private CompRoundFacade compRoundFacade;

    private List<CompRound> items = null;
    private List<CompRound> itemsForComp = null;
    private CompRound selected;
    private Competition selectedCompetition;

    public Competition getSelectedCompetition() {
        return selectedCompetition;
    }

    public void setSelectedCompetition(Competition selectedCompetition) {
        this.selectedCompetition = selectedCompetition;
    }

    public CompRoundController() {
    }

    public CompRound getSelected() {
        return selected;
    }

    public void setSelected(CompRound selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompRoundFacade getCompRoundFacade() {
        return compRoundFacade;
    }

    public CompRound prepareCreate() {
        selected = new CompRound();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
    
        selectedCompetition.getCompRoundCollection().add(selected);
        selected.setCompid(selectedCompetition);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompRoundCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Observes an Event from Next/
     *
     * @param selectedCompetition
     */
    public void onCompetitionSelected(@Observes Competition selectedCompetition) {
        log.log(Level.WARNING, " event observed");
        this.selectedCompetition = selectedCompetition;
        this.selected = null;
        itemsForComp=null;

    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompRoundUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompRoundDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            itemsForComp=null;
        }
    }

    public List<CompRound> getItems() {
        if (items == null) {
            items = getCompRoundFacade().findAll();
        }
        return items;
    }

    public List<CompRound> getItemsForComp() {
        if (itemsForComp == null) {
            itemsForComp = getCompRoundFacade().findForCompetition(selectedCompetition);
        }
        return itemsForComp;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == JsfUtil.PersistAction.DELETE) {
                    getCompRoundFacade().remove(selected);
                } else if (persistAction == JsfUtil.PersistAction.CREATE) {
                    getCompRoundFacade().create(selected);
                } else {
                    selected = getCompRoundFacade().edit(selected);
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

    public CompRound getCompRound(java.lang.Integer id) {
        return getCompRoundFacade().find(id);
    }

    public List<CompRound> getItemsAvailableSelectMany() {
        return getCompRoundFacade().findAll();
    }

    public List<CompRound> getItemsAvailableSelectOne() {
        return getCompRoundFacade().findAll();
    }

    @FacesConverter(forClass = CompRound.class)
    public static class CompRoundControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompRoundController controller = (CompRoundController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compRoundController");
            return controller.getCompRound(getKey(value));
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
            if (object instanceof CompRound) {
                CompRound o = (CompRound) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CompRound.class.getName()});
                return null;
            }
        }

    }

}
