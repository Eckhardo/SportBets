/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.buli.bhtpostgres;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ekirschning
 */
@Entity
@Table(name = "compgroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompGroup.findAll", query = "SELECT c FROM CompGroup c"),
    @NamedQuery(name = "CompGroup.findById", query = "SELECT c FROM CompGroup c WHERE c.id = :id"),
    @NamedQuery(name = "CompGroup.findByName", query = "SELECT c FROM CompGroup c WHERE c.name = :name"),
    @NamedQuery(name = "CompGroup.findByGroupnumber", query = "SELECT c FROM CompGroup c WHERE c.groupnumber = :groupnumber"),
    @NamedQuery(name = "CompGroup.findByCreated", query = "SELECT c FROM CompGroup c WHERE c.created = :created"),
    @NamedQuery(name = "CompGroup.findByVersion", query = "SELECT c FROM CompGroup c WHERE c.version = :version"),
    @NamedQuery(name = "CompGroup.findByCompRound", query = "SELECT c FROM CompGroup c WHERE c.comproundid = :compRound")})

public class CompGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "compGroupSeq")
    @SequenceGenerator(name = "compGroupSeq", sequenceName = "compgroup_id_seq", allocationSize = 5)
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
    @Column(name = "groupnumber")
    private int groupnumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "version")
    private BigInteger version;
    @JoinColumn(name = "comproundid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompRound comproundid;

    public CompGroup() {
    }

    public CompGroup(Integer id) {
        this.id = id;
    }

    public CompGroup(Integer id, String name, int groupnumber, Date created) {
        this.id = id;
        this.name = name;
        this.groupnumber = groupnumber;
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

    public int getGroupnumber() {
        return groupnumber;
    }

    public void setGroupnumber(int groupnumber) {
        this.groupnumber = groupnumber;
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

    public CompRound getComproundid() {
        return comproundid;
    }

    public void setComproundid(CompRound comproundid) {
        this.comproundid = comproundid;
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
        if (!(object instanceof CompGroup)) {
            return false;
        }
        CompGroup other = (CompGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eki.buli.bhtpostgres.CompGroup[ id=" + id + " ]";
    }

}
