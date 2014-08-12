/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eki.buli.bhtpostgres;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ekirschning
 */
@Stateless
public class CompFamFacade extends AbstractFacade<CompFam> {
    @PersistenceContext(unitName = "com.eki.buli_BHTPostgres_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompFamFacade() {
        super(CompFam.class);
    }
    
}
