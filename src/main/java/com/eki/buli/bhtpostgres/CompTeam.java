/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eki.buli.bhtpostgres;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ekirschning
 */
@Entity
@Table(name = "competitionteam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompTeam.findAll", query = "SELECT c FROM CompTeam c"),
    @NamedQuery(name = "CompTeam.findByCompid", query = "SELECT c FROM CompTeam c WHERE c.compTeamPK.compid = :compid"),
    @NamedQuery(name = "CompTeam.findByTeamid", query = "SELECT c FROM CompTeam c WHERE c.compTeamPK.teamid = :teamid"),
    @NamedQuery(name = "CompTeam.findByCreated", query = "SELECT c FROM CompTeam c WHERE c.created = :created")})
public class CompTeam implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompTeamPK compTeamPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @JoinColumn(name = "teamid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Team team;
    @JoinColumn(name = "compid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Competition competition;

    public CompTeam() {
    }

    public CompTeam(CompTeamPK compTeamPK) {
        this.compTeamPK = compTeamPK;
    }

    public CompTeam(CompTeamPK compTeamPK, Date created) {
        this.compTeamPK = compTeamPK;
        this.created = created;
    }

    public CompTeam(int compid, int teamid) {
        this.compTeamPK = new CompTeamPK(compid, teamid);
    }

    public CompTeamPK getCompTeamPK() {
        return compTeamPK;
    }

    public void setCompTeamPK(CompTeamPK compTeamPK) {
        this.compTeamPK = compTeamPK;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compTeamPK != null ? compTeamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompTeam)) {
            return false;
        }
        CompTeam other = (CompTeam) object;
        if ((this.compTeamPK == null && other.compTeamPK != null) || (this.compTeamPK != null && !this.compTeamPK.equals(other.compTeamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.CompTeam[ compTeamPK=" + compTeamPK + " ]";
    }
    
}
