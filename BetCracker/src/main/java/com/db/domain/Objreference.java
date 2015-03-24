package com.db.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Катя on 29.01.2015.
 */
@Entity
//@IdClass(ObjreferencePK.class)
@Table(name = "ObjReference")
public class Objreference implements Serializable {
    private ObjreferencePK objreferencePK;

    public Objreference() {
    }

    public Objreference(ObjreferencePK objreferencePK) {
        this.objreferencePK = objreferencePK;
    }

    @EmbeddedId
    public ObjreferencePK getObjreferencePK() {
        return objreferencePK;
    }

    public void setObjreferencePK(ObjreferencePK objreferencePK) {
        this.objreferencePK = objreferencePK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objreference that = (Objreference) o;
        if (objreferencePK != that.objreferencePK) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = objreferencePK.hashCode();
        return result;
    }
}
