package com.db.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Катя on 29.01.2015.
 */
@Embeddable
public class ObjreferencePK implements Serializable {
    private Attrtype attrId;
    private Superobj objectId;
    private Superobj reference;

    public ObjreferencePK() {
    }

    public ObjreferencePK(Attrtype attr, Superobj object, Superobj reference) {
        this.attrId = attr;
        this.objectId = object;
        this.reference = reference;
    }

    @ManyToOne
    @JoinColumn(name = "ATTR_ID", updatable = false, nullable = false)
    public Attrtype getAttrId() {
        return attrId;
    }

    public void setAttrId(Attrtype attrId) {
        this.attrId = attrId;
    }

    @ManyToOne
    @JoinColumn(name = "REFERENCE", updatable = false, nullable = false)
    public Superobj getReference() {
        return reference;
    }

    public void setReference(Superobj reference) {
        this.reference = reference;
    }

    @ManyToOne
    @JoinColumn(name = "OBJECT_ID", updatable = false, nullable = false)
    public Superobj getObjectId() {
        return objectId;
    }

    public void setObjectId(Superobj objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjreferencePK that = (ObjreferencePK) o;

        if (attrId != that.attrId) return false;
        if (objectId != that.objectId) return false;
        if (reference != that.reference) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attrId.hashCode();
        result = 31 * result + reference.hashCode();
        result = 31 * result + objectId.hashCode();
        return result;
    }
}
