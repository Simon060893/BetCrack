package com.registration.models;

import com.utils.IMapObject;
import com.utils.annotations.SetData;

import javax.persistence.*;

/**
 * Created by Катя on 02.02.2015.
 */
@Entity
public class Expense implements IMapObject {
    private long idExp;
    private String typeExp;
    private Integer countExp;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID_EXP")
    public long getIdExp() {
        return idExp;
    }
    @SetData
    public void setIdExp(long idExp) {
        this.idExp = idExp;
    }

    @Basic
    @Column(name = "TYPE_EXP")
    public String getTypeExp() {
        return typeExp;
    }
    @SetData
    public void setTypeExp(String typeExp) {
        this.typeExp = typeExp;
    }

    @Basic
    @Column(name = "COUNT_EXP")
    public Integer getCountExp() {
        return countExp;
    }
    @SetData
    public void setCountExp(Integer countExp) {
        this.countExp = countExp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (idExp != expense.idExp) return false;
        if (countExp != null ? !countExp.equals(expense.countExp) : expense.countExp != null) return false;
        if (typeExp != null ? !typeExp.equals(expense.typeExp) : expense.typeExp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result =(int) idExp;
        result = 31 * result + (typeExp != null ? typeExp.hashCode() : 0);
        result = 31 * result + (countExp != null ? countExp.hashCode() : 0);
        if (result<0){
            return result*(-1);
        }
        return result;
    }

    @Override
    public String toString() {
        return "idExp="+idExp+
                ",typeExp="+typeExp+
                ",countExp="+countExp;
    }
}
