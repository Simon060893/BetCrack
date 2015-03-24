package com.db.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Катя on 29.01.2015.
 */
@Embeddable
public class AttrvaluesPK implements Serializable {
    private Attrtype attrtypeByAttrId;
    private Superobj superobjByObjectId;

    public AttrvaluesPK() {
    }

    public AttrvaluesPK(Attrtype attrtypeByAttrId, Superobj superobjByObjectId) {
        this.attrtypeByAttrId=attrtypeByAttrId;
        this.superobjByObjectId=superobjByObjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttrvaluesPK that = (AttrvaluesPK) o;


        return true;
    }

    @Override
    public int hashCode() {
        int result = attrtypeByAttrId.hashCode();
        result = 31 * result + superobjByObjectId.hashCode();
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ATTR_ID", referencedColumnName = "ATTR_ID", nullable = false)

    public Attrtype getAttrtypeByAttrId() {
        return attrtypeByAttrId;
    }

    public void setAttrtypeByAttrId(Attrtype attrtypeByAttrId) {
        this.attrtypeByAttrId = attrtypeByAttrId;
    }

    @ManyToOne
    @JoinColumn(name = "OBJECT_ID", referencedColumnName = "OBJECT_ID", nullable = false)

    public Superobj getSuperobjByObjectId() {
        return superobjByObjectId;
    }

    public void setSuperobjByObjectId(Superobj superobjByObjectId) {
        this.superobjByObjectId = superobjByObjectId;
    }

}
