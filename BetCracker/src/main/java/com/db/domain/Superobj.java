package com.db.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Катя on 29.01.2015.
 */
@Entity
@Table(name = "Superobj")
public class Superobj implements Serializable {
    private int objectId;
    private String name;
    private Objtype objtypeByObjectTypeId;
    private Superobj superobjByParentId;

    public Superobj( ) {
    }
    public Superobj(String s, Objtype objTypeId, Superobj parentId) {
        this.name=s;
        this.objtypeByObjectTypeId=objTypeId;
        this.superobjByParentId=parentId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "OBJECT_ID")
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
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

        Superobj superobj = (Superobj) o;

        if (objectId != superobj.objectId) return false;
        if (name != null ? !name.equals(superobj.name) : superobj.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "OBJECT_TYPE_ID", referencedColumnName = "OBJECT_TYPE_ID", nullable = false)
    public Objtype getObjtypeByObjectTypeId() {
        return objtypeByObjectTypeId;
    }

    public void setObjtypeByObjectTypeId(Objtype objtypeByObjectTypeId) {
        this.objtypeByObjectTypeId = objtypeByObjectTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "OBJECT_ID")
    public Superobj getSuperobjByParentId() {
        return superobjByParentId;
    }

    public void setSuperobjByParentId(Superobj superobjByParentId) {
        this.superobjByParentId = superobjByParentId;
    }
}
