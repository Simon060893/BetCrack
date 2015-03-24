package com.db.query;

import com.db.domain.*;
import com.utils.HibernateUtil;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Катя on 28.01.2015.
 */
public class ActionsSelectFromEAV {
    private static Session session;
    private static ActionsSelectFromEAV actionsSelectFromEAV;
    private final static Logger logger = Logger.getLogger(ActionsSelectFromEAV.class);
    static {
        session = HibernateUtil.getFactory().openSession();
    }

    public static ActionsSelectFromEAV getActionsSelectFromEAV() {
        if (actionsSelectFromEAV == null) {
            actionsSelectFromEAV = new ActionsSelectFromEAV();
        }
        BasicConfigurator.configure();
        return actionsSelectFromEAV;
    }

    public Session getSession() {
        return session;
    }

    public void setActionsSelectFromEAV(ActionsSelectFromEAV actionsSelectFromEAV) {
        this.actionsSelectFromEAV = actionsSelectFromEAV;
    }

    public List<Objtype> getCurrentNumberInObjtypeOfClass(String className) {
        return getSession().createQuery("from Objtype  where name='" + className + "'").list();
    }

    public List<String> getCurrentNameOfClass() {
        return getSession().createQuery("select name from Objtype").list();
    }

    public int checkRepeatData(String row, String val) {
        return Integer.parseInt(getSession().createQuery("select count(a.value)from Attrvalues a, Attrtype atr where " +
                "atr.name ='" + row + "'" + "and a.id.attrtypeByAttrId.id=atr.id and a.value='" + val + "'").list().get(0).toString());
    }

    public List<String> checkEnterData(StringBuilder row, String val) {
        return getSession().createQuery("select a.attrvaluesPK.superobjByObjectId.id from Attrvalues a, Attrtype atr where atr.name ='" + row +
                "'" + "and a.attrvaluesPK.attrtypeByAttrId.id=atr.id and a.value='" + val + "'").list();
    }

    public List checkPswByObjId(int objId, String psw) {
        return getSession().createQuery("select a.value from Attrvalues a, Attrtype atr where atr.name ='PASSWORD' " +
                "and a.attrvaluesPK.attrtypeByAttrId.id = atr.id and a.value = '" + psw + "'and a.attrvaluesPK.superobjByObjectId.objectId=" + objId).list();
    }

    public List<Attrtype> getAttrtypesOfObjectType(Objtype objTypeId) {
        return getSession().createQuery("from Attrtype a where  a.objtypeByAttrTypeId.id='" + objTypeId.getObjectTypeId()
                + "'ORDER BY a.id").list();
    }

    public List<Superobj> getSuperobjOfObjectType(Objtype objTypeId) {
        return getSession().createQuery("from Superobj a where  a.objtypeByObjectTypeId='" + objTypeId + "'ORDER BY a.id").list();
    }

    public List<Superobj> getObjFromSupeObj(int id) {
        return getSession().createQuery("from Superobj  where objectId=" + id).list();
    }

    public List<String> getDataForObj(int objId) {
        return getSession().createQuery(" select atrT.name||'_'||atrV.value from Attrvalues atrV,Attrtype atrT where " +
                "atrV.attrvaluesPK.superobjByObjectId.id='" + objId + "' and atrV.attrvaluesPK.attrtypeByAttrId.id" +
                "=atrT.attrId").list();
    }
    public List<Superobj> getChildObj(int objId) {
     return getSession().createQuery("from Superobj where superobjByParentId.objectId = "+objId).list();
    }
}
