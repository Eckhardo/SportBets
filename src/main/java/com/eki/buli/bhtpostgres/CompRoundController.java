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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("compRoundController")
@SessionScoped
public class CompRoundController implements Serializable {

    @EJB
    private com.eki.buli.bhtpostgres.CompRoundFacade ejbFacade;
    private List<CompRound> items = null;
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

    private CompRoundFacade getFacade() {
        return ejbFacade;
    }

    public CompRound prepareCreate() {
        selected = new CompRound();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompRoundCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompRoundUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompRoundDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CompRound> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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
        return getFacade().find(id);
    }

    public List<CompRound> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CompRound> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
   public void onCompetitionSelected(@Observes Competition selectedCompetition){
       
          items= getFacade().findForCompetition(selectedCompetition);
          this.selectedCompetition=selectedCompetition;
          
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
