/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eki.buli.bhtpostgres;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ekirschning
 */
@Embeddable
public class CompTeamPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "compid")
    private int compid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "teamid")
    private int teamid;

    public CompTeamPK() {
    }

    public CompTeamPK(int compid, int teamid) {
        this.compid = compid;
        this.teamid = teamid;
    }

    public int getCompid() {
        return compid;
    }

    public void setCompid(int compid) {
        this.compid = compid;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compid;
        hash += (int) teamid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompTeamPK)) {
            return false;
        }
        CompTeamPK other = (CompTeamPK) object;
        if (this.compid != other.compid) {
            return false;
        }
        if (this.teamid != other.teamid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.CompTeamPK[ compid=" + compid + ", teamid=" + teamid + " ]";
    }
    
}
