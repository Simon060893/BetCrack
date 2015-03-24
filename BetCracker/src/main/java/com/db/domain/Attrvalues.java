package com.db.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Катя on 29.01.2015.
 */
@Entity
//@IdClass(value = AttrvaluesPK.class)
@Table(name = "Attrvalues")
public class Attrvalues implements Serializable {
    private String value;
    private Timestamp dateValue;
    private AttrvaluesPK attrvaluesPK;

    public Attrvalues() {
    }

    public Attrvalues(AttrvaluesPK attrvaluesPK, String value) {
        this.attrvaluesPK = attrvaluesPK;
        this.value = value;
        this.dateValue = new Timestamp(120);
    }


    @Basic
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "DATE_VALUE")
    public Timestamp getDateValue() {
        return dateValue;
    }

    public void setDateValue(Timestamp dateValue) {
        this.dateValue = dateValue;
    }

    @EmbeddedId
    public AttrvaluesPK getAttrvaluesPK() {
        return attrvaluesPK;
    }

    public void setAttrvaluesPK(AttrvaluesPK attrvaluesPK) {
        this.attrvaluesPK = attrvaluesPK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attrvalues that = (Attrvalues) o;

        if (attrvaluesPK != that.attrvaluesPK) return false;
        if (dateValue != null ? !dateValue.equals(that.dateValue) : that.dateValue != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attrvaluesPK.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (dateValue != null ? dateValue.hashCode() : 0);
        return result;
    }


}
