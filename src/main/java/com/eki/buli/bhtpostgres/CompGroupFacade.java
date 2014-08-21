/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eki.buli.bhtpostgres;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ekirschning
 */
@Stateless
public class CompGroupFacade extends AbstractFacade<CompGroup> {
 
        private static final Logger log = Logger.getLogger(CompGroupFacade.class.getName());

  
    
    @PersistenceContext(unitName = "com.eki.buli_BHTPostgres_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompGroupFacade() {
        super(CompGroup.class);
    }

    public List<CompGroup> findForCompRound(CompRound selectedRound) {
        
        log.log(Level.WARNING,"finde for compround");
        Query query = em.createNamedQuery("CompGroup.findByCompRound").setParameter("compRound",
                selectedRound);
log.log(Level.WARNING,"finde for compround 2");
   
        return (List<CompGroup>) query.getResultList();
   
    }
    
}
