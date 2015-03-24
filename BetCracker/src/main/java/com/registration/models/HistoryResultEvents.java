package com.registration.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Катя on 02.02.2015.
 */
@Entity
@Table(name = "HISTORY_RESULT_EVENTS", schema = "SEN13", catalog = "")
public class HistoryResultEvents {
    private Integer idHstr;
    private String firstName;
    private String secName;
    private String resultEvent;

    @Basic
    @Column(name = "ID_HSTR")
    public Integer getIdHstr() {
        return idHstr;
    }

    public void setIdHstr(Integer idHstr) {
        this.idHstr = idHstr;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "SEC_NAME")
    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    @Basic
    @Column(name = "RESULT_EVENT")
    public String getResultEvent() {
        return resultEvent;
    }

    public void setResultEvent(String resultEvent) {
        this.resultEvent = resultEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryResultEvents that = (HistoryResultEvents) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (idHstr != null ? !idHstr.equals(that.idHstr) : that.idHstr != null) return false;
        if (resultEvent != null ? !resultEvent.equals(that.resultEvent) : that.resultEvent != null) return false;
        if (secName != null ? !secName.equals(that.secName) : that.secName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idHstr != null ? idHstr.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secName != null ? secName.hashCode() : 0);
        result = 31 * result + (resultEvent != null ? resultEvent.hashCode() : 0);
        return result;
    }
}
