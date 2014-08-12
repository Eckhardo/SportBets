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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "compfamiliy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompFam.findAll", query = "SELECT c FROM CompFam c"),
    @NamedQuery(name = "CompFam.findById", query = "SELECT c FROM CompFam c WHERE c.id = :id"),
    @NamedQuery(name = "CompFam.findByName", query = "SELECT c FROM CompFam c WHERE c.name = :name"),
    @NamedQuery(name = "CompFam.findByHasclubs", query = "SELECT c FROM CompFam c WHERE c.hasclubs = :hasclubs"),
    @NamedQuery(name = "CompFam.findByHasligamodus", query = "SELECT c FROM CompFam c WHERE c.hasligamodus = :hasligamodus"),
    @NamedQuery(name = "CompFam.findByCreated", query = "SELECT c FROM CompFam c WHERE c.created = :created"),
    @NamedQuery(name = "CompFam.findByVersion", query = "SELECT c FROM CompFam c WHERE c.version = :version")})
public class CompFam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "compFamSeq")
    @SequenceGenerator(name = "compFamSeq", sequenceName = "compfamiliy_id_seq", allocationSize = 5)
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
    @Column(name = "hasclubs")
    private boolean hasclubs;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasligamodus")
    private boolean hasligamodus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "version")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compfamilyid")
    private Collection<Competition> competitionCollection;

    public CompFam() {
    }

    public CompFam(Integer id) {
        this.id = id;
    }

    public CompFam(Integer id, String name, boolean hasclubs, boolean hasligamodus, Date created) {
        this.id = id;
        this.name = name;
        this.hasclubs = hasclubs;
        this.hasligamodus = hasligamodus;
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

    public boolean getHasclubs() {
        return hasclubs;
    }

    public void setHasclubs(boolean hasclubs) {
        this.hasclubs = hasclubs;
    }

    public boolean getHasligamodus() {
        return hasligamodus;
    }

    public void setHasligamodus(boolean hasligamodus) {
        this.hasligamodus = hasligamodus;
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
    public Collection<Competition> getCompetitionCollection() {
        return competitionCollection;
    }

    public void setCompetitionCollection(Collection<Competition> competitionCollection) {
        this.competitionCollection = competitionCollection;
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
        if (!(object instanceof CompFam)) {
            return false;
        }
        CompFam other = (CompFam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.CompFam[ id=" + id + " ]";
    }

}
