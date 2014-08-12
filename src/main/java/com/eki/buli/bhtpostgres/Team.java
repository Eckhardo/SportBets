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
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :id"),
    @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name"),
    @NamedQuery(name = "Team.findByAconym", query = "SELECT t FROM Team t WHERE t.aconym = :aconym"),
    @NamedQuery(name = "Team.findByIsclub", query = "SELECT t FROM Team t WHERE t.isclub = :isclub"),
    @NamedQuery(name = "Team.findByCreated", query = "SELECT t FROM Team t WHERE t.created = :created"),
    @NamedQuery(name = "Team.findByVersion", query = "SELECT t FROM Team t WHERE t.version = :version")})
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
  @GeneratedValue(generator = "teamSeq")
    @SequenceGenerator(name = "teamSeq", sequenceName = "team_id_seq", allocationSize = 5)
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
    @Size(min = 1, max = 3)
    @Column(name = "aconym")
    private String aconym;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isclub")
    private boolean isclub;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "version")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private Collection<CompTeam> compTeamCollection;

    public Team() {
    }

    public Team(Integer id) {
        this.id = id;
    }

    public Team(Integer id, String name, String aconym, boolean isclub, Date created) {
        this.id = id;
        this.name = name;
        this.aconym = aconym;
        this.isclub = isclub;
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

    public String getAconym() {
        return aconym;
    }

    public void setAconym(String aconym) {
        this.aconym = aconym;
    }

    public boolean getIsclub() {
        return isclub;
    }

    public void setIsclub(boolean isclub) {
        this.isclub = isclub;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.Team[ id=" + id + " ]";
    }
    
}
