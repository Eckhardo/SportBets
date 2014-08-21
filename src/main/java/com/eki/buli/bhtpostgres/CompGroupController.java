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

@Named("compGroupController")
@SessionScoped
public class CompGroupController implements Serializable {

    private static final Logger log = Logger.getLogger(CompGroupController.class.getName());

    @EJB
    private com.eki.buli.bhtpostgres.CompGroupFacade ejbFacade;
    private List<CompGroup> items = null;
    private List<CompGroup> itemsForRound = null;
    private CompGroup selected;
    private CompRound selectedRound;

    public CompGroupController() {
    }

    public CompGroup getSelected() {
        return selected;
    }

    public void setSelected(CompGroup selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompGroupFacade getFacade() {
        return ejbFacade;
    }

    public CompGroup prepareCreate() {
        selected = new CompGroup();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompGroupCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            itemsForRound = null;
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompGroupUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompGroupDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            itemsForRound = null;
        }
    }

    public List<CompGroup> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /**
     * Observes an Event from Next/
     *
     * @param selectedCompRound
     */
    public void onRoundSelected(@Observes CompRound selectedCompRound) {
        log.log(Level.WARNING, " event observed");
        this.selectedRound = selectedCompRound;
        this.selected = null;
        itemsForRound = null;

    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == JsfUtil.PersistAction.DELETE) {
                    getFacade().remove(selected);
                } else if (persistAction == JsfUtil.PersistAction.CREATE) {
                    getFacade().create(selected);
                } else {
                    selected = getFacade().edit(selected);
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

    public CompGroup getCompGroup(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<CompGroup> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CompGroup> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<CompGroup> getItemsForRound() {
        if (itemsForRound == null) {
              log.log(Level.WARNING, " selected round {0}", selectedRound!=null ? selectedRound.getName() :"nothing" );
            itemsForRound = getFacade().findForCompRound(selectedRound);
        }
        return itemsForRound;
    }

    public void setItemsForRound(List<CompGroup> itemsForRound) {
        this.itemsForRound = itemsForRound;
    }

    @FacesConverter(forClass = CompGroup.class)
    public static class CompGroupControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompGroupController controller = (CompGroupController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compGroupController");
            return controller.getCompGroup(getKey(value));
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
            if (object instanceof CompGroup) {
                CompGroup o = (CompGroup) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CompGroup.class.getName()});
                return null;
            }
        }

    }

}
