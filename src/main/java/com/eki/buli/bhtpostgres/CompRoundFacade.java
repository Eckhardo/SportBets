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
public class CompRoundFacade extends AbstractFacade<CompRound> {

      private static final Logger log = Logger.getLogger(CompRoundFacade.class.getName());

    @PersistenceContext(unitName = "com.eki.buli_BHTPostgres_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompRoundFacade() {
        super(CompRound.class);
    }
       public List<CompRound> findForCompetition(Competition selectedCompetition) {
        Query query = em.createNamedQuery("CompRound.findByCompetition").setParameter("competition",
                selectedCompetition);

       log.log(Level.WARNING, "query= {0}",  query );
        return (List<CompRound>) query.getResultList();
    }
    
}
