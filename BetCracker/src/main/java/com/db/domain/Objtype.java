package com.db.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Катя on 29.01.2015.
 */
@Entity
@Table(name = "ObjType")
public class Objtype implements Serializable {
    private int objectTypeId;
    private Objtype parentId;
    private String name;

    public Objtype() {
    }

    public Objtype(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "OBJECT_TYPE_ID")
    public int getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(int objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", updatable = false,insertable = false)
    public Objtype getParentId() {
        return parentId;
    }

    public void setParentId(Objtype parentId) {
        this.parentId = parentId;
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

        Objtype objtype = (Objtype) o;

        if (objectTypeId != objtype.objectTypeId) return false;
        if (name != null ? !name.equals(objtype.name) : objtype.name != null) return false;
        if (parentId != null ? !parentId.equals(objtype.parentId) : objtype.parentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectTypeId;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
