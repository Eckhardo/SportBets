/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eki.buli.bhtpostgres;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ekirschning
 */
@Entity
@Table(name = "competition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competition.findAll", query = "SELECT c FROM Competition c"),
    @NamedQuery(name = "Competition.findById", query = "SELECT c FROM Competition c WHERE c.id = :id"),
    @NamedQuery(name = "Competition.findByName", query = "SELECT c FROM Competition c WHERE c.name = :name"),
    @NamedQuery(name = "Competition.findByIsvalid", query = "SELECT c FROM Competition c WHERE c.isvalid = :isvalid"),
    @NamedQuery(name = "Competition.findByWinmultiplicator", query = "SELECT c FROM Competition c WHERE c.winmultiplicator = :winmultiplicator"),
    @NamedQuery(name = "Competition.findByRemismultiplicator", query = "SELECT c FROM Competition c WHERE c.remismultiplicator = :remismultiplicator"),
    @NamedQuery(name = "Competition.findByCreated", query = "SELECT c FROM Competition c WHERE c.created = :created"),
    @NamedQuery(name = "Competition.findByVersion", query = "SELECT c FROM Competition c WHERE c.version = :version")})
public class Competition implements Serializable {
     private static final Logger log = Logger.getLogger(Competition.class.getName());

    private static final long serialVersionUID = 1L;
    @Id
  @GeneratedValue(generator = "compSeq")
    @SequenceGenerator(name = "compSeq", sequenceName = "comp_id_seq", allocationSize = 5)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isvalid")
    private boolean isvalid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "winmultiplicator")
    private short winmultiplicator;
    @Basic(optional = false)
    @NotNull
    @Column(name = "remismultiplicator")
    private short remismultiplicator;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "version")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")
    private Collection<CompTeam> compTeamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compid")
    private Collection<CompRound> compRoundCollection;
    @JoinColumn(name = "compfamilyid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompFam compfamilyid;

    public Competition() {
    }

    public Competition(Integer id) {
        this.id = id;
    }

    public Competition(Integer id, String name, boolean isvalid, short winmultiplicator, short remismultiplicator, Date created) {
        this.id = id;
        this.name = name;
        this.isvalid = isvalid;
        this.winmultiplicator = winmultiplicator;
        this.remismultiplicator = remismultiplicator;
        this.created = created;
    }

    public Integer getId() {
       
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public short getWinmultiplicator() {
        return winmultiplicator;
    }

    public void setWinmultiplicator(short winmultiplicator) {
        this.winmultiplicator = winmultiplicator;
    }

    public short getRemismultiplicator() {
        return remismultiplicator;
    }

    public void setRemismultiplicator(short remismultiplicator) {
        this.remismultiplicator = remismultiplicator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @XmlTransient
    public Collection<CompTeam> getCompTeamCollection() {
        return compTeamCollection;
    }

    public void setCompTeamCollection(Collection<CompTeam> compTeamCollection) {
        this.compTeamCollection = compTeamCollection;
    }

    @XmlTransient
    public Collection<CompRound> getCompRoundCollection() {
        return compRoundCollection;
    }

    public void setCompRoundCollection(Collection<CompRound> compRoundCollection) {
        this.compRoundCollection = compRoundCollection;
    }

    public CompFam getCompfamilyid() {
        return compfamilyid;
    }

    public void setCompfamilyid(CompFam compfamilyid) {
        this.compfamilyid = compfamilyid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Competition)) {
            return false;
        }
        Competition other = (Competition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.Competition[ id=" + id + " ]";
    }
    
}
