package com.db.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Катя on 29.01.2015.
 */
@Entity
@Table(name = "Attrtype")
public class Attrtype implements Serializable {
    private int attrId;
    private String name;
    private Objtype objtypeByAttrTypeId;


    public Attrtype() {
    }
    public Attrtype(String name,Objtype objtypeByAttrTypeId) {
        this.name = name;
        this.objtypeByAttrTypeId = objtypeByAttrTypeId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ATTR_ID")
    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attrtype attrtype = (Attrtype) o;

        if (attrId != attrtype.attrId) return false;
        if (name != null ? !name.equals(attrtype.name) : attrtype.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attrId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "OBJECT_TYPE_ID", nullable = false)
    public Objtype getObjtypeByAttrTypeId() {
        return objtypeByAttrTypeId;
    }

    public void setObjtypeByAttrTypeId(Objtype objtypeByAttrTypeId) {
        this.objtypeByAttrTypeId = objtypeByAttrTypeId;
    }

}
