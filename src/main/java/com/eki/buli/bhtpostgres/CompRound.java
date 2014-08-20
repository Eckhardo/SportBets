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
@Table(name = "compround")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompRound.findAll", query = "SELECT c FROM CompRound c"),
    @NamedQuery(name = "CompRound.findById", query = "SELECT c FROM CompRound c WHERE c.id = :id"),
    @NamedQuery(name = "CompRound.findByName", query = "SELECT c FROM CompRound c WHERE c.name = :name"),
    @NamedQuery(name = "CompRound.findByCreated", query = "SELECT c FROM CompRound c WHERE c.created = :created"),
    @NamedQuery(name = "CompRound.findByRoundnumber", query = "SELECT c FROM CompRound c WHERE c.roundnumber = :roundnumber"),
    @NamedQuery(name = "CompRound.findByHasgroups", query = "SELECT c FROM CompRound c WHERE c.hasgroups = :hasgroups"),
    @NamedQuery(name = "CompRound.findByVersion", query = "SELECT c FROM CompRound c WHERE c.version = :version"),
   @NamedQuery(name = "CompRound.findByCompetition", query = "SELECT c FROM CompRound c WHERE c.compid = :competition")})

public class CompRound implements Serializable {
    
       private static final Logger log = Logger.getLogger(CompRound.class.getName());

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "compRoundSeq")
    @SequenceGenerator(name = "compRoundSeq", sequenceName = "compround_id_seq", allocationSize = 5)
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
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "roundnumber")
    private short roundnumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasgroups")
    private boolean hasgroups;
    @Column(name = "version")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comproundid")
    private Collection<CompGroup> compGroupCollection;
    @JoinColumn(name = "compid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Competition compid;

    public CompRound() {
    }

    public CompRound(Integer id) {
        this.id = id;
    }

    public CompRound(Integer id, String name, Date created, short roundnumber, boolean hasgroups) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.roundnumber = roundnumber;
        this.hasgroups = hasgroups;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public short getRoundnumber() {
        return roundnumber;
    }

    public void setRoundnumber(short roundnumber) {
        this.roundnumber = roundnumber;
    }

    public boolean getHasgroups() {
        return hasgroups;
    }

    public void setHasgroups(boolean hasgroups) {
        this.hasgroups = hasgroups;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @XmlTransient
    public Collection<CompGroup> getCompGroupCollection() {
        return compGroupCollection;
    }

    public void setCompGroupCollection(Collection<CompGroup> compGroupCollection) {
        this.compGroupCollection = compGroupCollection;
    }

    public Competition getCompid() {
        return compid;
    }

    public void setCompid(Competition compid) {
        this.compid = compid;
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
        if (!(object instanceof CompRound)) {
            return false;
        }
        CompRound other = (CompRound) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.CompRound[ id=" + id + " ]";
    }
    
}
