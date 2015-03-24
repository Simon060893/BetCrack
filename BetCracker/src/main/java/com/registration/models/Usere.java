package com.registration.models;

import com.utils.IMapObject;
import com.utils.annotations.MapField;
import com.utils.annotations.SetData;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Катя on 02.02.2015.
 */
@Entity
public class Usere implements IMapObject{
    @MapField
    private int idUs;
    @MapField
    private String fio;
    @MapField
    private String datReg;
    @MapField
    private String admins;
    @MapField
    private String ipReg;
    @MapField
    private String login;
    @MapField
    private String password;
    @MapField
    private String email;
    @MapField
    private Integer idExp;
    @MapField
    private String phonenumber;
    private Expense expenseByIdExp;

    @Id
    @Column(name = "ID_US")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getIdUs() {
        return idUs;
    }
    @SetData
    public void setIdUs(int idUs) {
        this.idUs = idUs;
    }

    @Basic
    @Column(name = "FIO")
    public String getFio() {
        return fio;
    }
    @SetData
    public void setFio(String fio) {
        this.fio = fio;
    }

    @Basic
    @Column(name = "DAT_REG")
    public String getDatReg() {
        return new SimpleDateFormat("yyyy.MM.dd ").format(new Date());
    }
    @SetData
    public void setDatReg(String datReg) {
        this.datReg = datReg;
    }

    @Basic
    @Column(name = "ADMINS")
    public String getAdmins() {
        return admins;
    }
    @SetData
    public void setAdmins(String admins) {
        this.admins = admins;
    }

    @Basic
    @Column(name = "IP_REG")
    public String getIpReg() {
        return ipReg;
    }
    @SetData
    public void setIpReg(String ipReg) {
        this.ipReg = ipReg;
    }

    @Basic
    @Column(name = "LOGIN")
    public String getLogin() {
        return login;
    }
    @SetData
    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }
    @SetData
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }
    @SetData
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "ID_EXP")
    public Integer getIdExp() {
        return idExp;
    }
    @SetData
    public void setIdExp(Integer idExp) {
        this.idExp = idExp;
    }

    @Basic
    @Column(name = "PHONENUMBER")
    public String getPhonenumber() {
        return phonenumber;
    }
    @SetData
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usere usere = (Usere) o;

        if (idUs != usere.idUs) return false;
        if (admins != null ? !admins.equals(usere.admins) : usere.admins != null) return false;
        if (datReg != null ? !datReg.equals(usere.datReg) : usere.datReg != null) return false;
        if (email != null ? !email.equals(usere.email) : usere.email != null) return false;
        if (fio != null ? !fio.equals(usere.fio) : usere.fio != null) return false;
        if (idExp != null ? !idExp.equals(usere.idExp) : usere.idExp != null) return false;
        if (ipReg != null ? !ipReg.equals(usere.ipReg) : usere.ipReg != null) return false;
        if (login != null ? !login.equals(usere.login) : usere.login != null) return false;
        if (password != null ? !password.equals(usere.password) : usere.password != null) return false;
        if (phonenumber != null ? !phonenumber.equals(usere.phonenumber) : usere.phonenumber != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = idUs;
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (datReg != null ? datReg.hashCode() : 0);
        result = 31 * result + (admins != null ? admins.hashCode() : 0);
        result = 31 * result + (ipReg != null ? ipReg.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (idExp!= null ? idExp.hashCode() : 0);
        result = 31 * result + (phonenumber != null ? phonenumber.hashCode() : 0);
        if (result<0){
            return result*(-1);
        }
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_EXP", referencedColumnName = "ID_EXP")
    public Expense getExpenseByIdExp() {
        return expenseByIdExp;
    }

    public void setExpenseByIdExp(Expense expenseByIdExp) {
        this.expenseByIdExp = expenseByIdExp;
    }

    @Override
    public String toString() {
        return "idUs="+idUs+
                ",playerName="+fio+
                ",datReg="+datReg+
                ",isAdmin="+admins+
                ",ipReg="+ipReg+
                ",email="+email+
                ",idExp="+idExp+
                ",phonenumber="+phonenumber;
    }
}
