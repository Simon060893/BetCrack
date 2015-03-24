package com.registration.server;

import com.core.modelEvents.ASportBetEvent;
import com.core.modelEvents.BasketBallEvent;
import com.core.modelEvents.FootballEvent;
import com.core.modelEvents.HockeyEvent;
import com.db.domain.*;
import com.db.query.ActionsSelectFromEAV;
import com.registration.dao.DomainDao;
import com.registration.models.Expense;
import com.registration.models.Usere;
import com.utils.IMapObject;
import com.utils.SaveClassToDb;
import com.utils.annotations.ActUser;
import com.utils.exceptions.IncorectInputDataException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.logi.crypto.sign.Fingerprint;
import org.logi.crypto.sign.MD5State;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Катя on 28.01.2015.
 */
public class UserActions {
    private Usere user;
    private Expense expense;
    private boolean isConect;
    private List<ASportBetEvent> aSportBetEvent;
    private DomainDao domainDao;
    private ActionsSelectFromEAV actionsSelectFromEAV;
    private final static Logger logger = Logger.getLogger(UserActions.class);
    private Superobj superobjUser;

    public UserActions() {
        BasicConfigurator.configure();
        logger.info("Start USERACTONS!!!");
        actionsSelectFromEAV = ActionsSelectFromEAV.getActionsSelectFromEAV();
        domainDao = new DomainDao();
    }

    public Usere getUser() {
        return user;
    }

    public void setUser(Usere user) {
        this.user = user;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public boolean isConect() {
        return isConect;
    }

    public boolean register(IMapObject iMapObj,int parentId ) throws Exception {
        List<String> listDataOfIMapObj = setDataFromIMapObjToList(iMapObj);
        List<Serializable> listObjForSave = new LinkedList<Serializable>();
        Objtype objType = actionsSelectFromEAV.getCurrentNumberInObjtypeOfClass(iMapObj.getClass().getSimpleName().toUpperCase()).get(0);
        List<Attrtype> listOfAttrtype = actionsSelectFromEAV.getAttrtypesOfObjectType(objType);
        listObjForSave.add(checkParentId(parentId,objType,iMapObj));
        for (int i = 0; i < listDataOfIMapObj.size(); i++) {
            listObjForSave.add(new Attrvalues(new AttrvaluesPK(listOfAttrtype.get(i), (Superobj) listObjForSave.get(0)), listDataOfIMapObj.get(i)));
        }
        return domainDao.saveObjectToDb(listObjForSave);
    }

    private Serializable checkParentId(int parentId, Objtype objType, IMapObject iMapObj) {
       if(parentId == 1) {
           return new Superobj(iMapObj.getClass().getSimpleName()+"_" + iMapObj.hashCode(), objType,superobjUser);
       }else{
          return  (superobjUser=new Superobj(iMapObj.getClass().getSimpleName()+"_" + iMapObj.hashCode(), objType,null));
       }
    }

    public boolean connect(String lgn, String psw) throws IncorectInputDataException {
        StringBuilder rowName = new StringBuilder();
        if (Pattern.compile("[0-9]{13}").matcher(lgn).matches()) {
            rowName.append("PHONENUMBER");
        } else if (Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$").matcher(lgn).matches()) {
            rowName.append("EMAIL");
        } else {
            rowName.append("LOGIN");
        }
        return isConect = isConnect(rowName, lgn, psw);
    }

    private boolean isConnect(StringBuilder rowName, String lgn, String psw) throws IncorectInputDataException {
        List listLgn = checkLgn(rowName, lgn);
        if (!listLgn.isEmpty()) {
            logger.info("operation by searching login "+lgn+"  worked");
            return checkPasswordByObjId(Integer.parseInt(listLgn.get(0).toString()), psw);
        }
        throw new IncorectInputDataException("your " + rowName + " is not correct");
    }

    private List<String> checkLgn(StringBuilder rowName, String lgn) {
        return actionsSelectFromEAV.checkEnterData(rowName, lgn);
    }

    private boolean checkPasswordByObjId(int objId, String psw) throws IncorectInputDataException {
        String nameObj = actionsSelectFromEAV.getObjFromSupeObj(objId).get(0).getName();
            if (!actionsSelectFromEAV.checkPswByObjId(objId, encryptString(psw, Integer.parseInt(nameObj.substring(nameObj.indexOf("_") + 1)))).isEmpty()) {
                return createObjFromDb(objId);
            }
        logger.error("user "+objId+ " signed incorect password");
        throw new IncorectInputDataException("your password is incorect");
    }

    private boolean createObjFromDb(int objId) throws IncorectInputDataException {
        try {
            setUser((Usere)getObjFromDB(objId, Usere.class));
            setExpense((Expense)findExpForUser(objId));
        } catch (Exception e) {
            throw new IncorectInputDataException("something wrong with Obj from DB");
        }finally {
            logger.info("read user-object  by "+objId+" completed");
        }
        return true;
    }

    private List<String> setDataFromIMapObjToList(IMapObject user) throws Exception {
        Class classN = user.getClass();
        List list = new LinkedList<>();
        for (Method m : SaveClassToDb.sortMas(classN.getDeclaredMethods())) {
            if (m.getName().substring(0, 3).equals("get")) {
                for (Annotation a : m.getDeclaredAnnotations()) {
                    if (a.annotationType().getSimpleName().equals("Column")) {
                        list.add(checkIMapObj(m.getName().substring(3).toUpperCase(), m.invoke(user), user.hashCode()));
                        break;
                    }
                }
            }
        }
        return list;
    }
    private IMapObject findExpForUser(int objId)throws Exception {
        List<Superobj> list= actionsSelectFromEAV.getChildObj(objId);
        if(!list.isEmpty()) {
            return getObjFromDB(list.get(0).getObjectId(), Expense.class);
        }
            throw new IncorectInputDataException("can not load data of Expence");

    }
    private IMapObject getObjFromDB(int objId, Class obj) throws Exception {
        List<String> listObj = actionsSelectFromEAV.getDataForObj(objId);
        IMapObject object = (IMapObject) obj.newInstance();
        for (Method m : obj.getDeclaredMethods()) {
            if (m.getName().startsWith("set")) {
                for (Annotation a : m.getDeclaredAnnotations()) {
                    if (a.annotationType().getSimpleName().equals("SetData")) {
                        String methodName = m.getName().substring(3).toUpperCase();
                        for (Iterator iterator = listObj.iterator(); iterator.hasNext(); ) {
                            String method1 = (String) iterator.next();
                            if (methodName.equals(method1.substring(0, method1.indexOf("_")))) {
                                m.invoke(object,castObjectTotypeOfParam(m.getParameterTypes()[0].getSimpleName(),
                                        method1.substring(method1.indexOf("_") + 1)));
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return  object;
    }

    private Object castObjectTotypeOfParam(String name, String value) {
        if (name.equals("Integer") || name.equals("int")) {
            return Integer.parseInt(value);
        } else if(name.equals("long")){
            return Long.parseLong(value);
        }else{
            return value;
        }
    }

    private String checkIMapObj(String rowName, Object lgnEx, int salt) throws IncorectInputDataException {
        if (lgnEx != null) {
            String lgnExist = lgnEx.toString();
            if (rowName.equals("PHONENUMBER")) {
                if (checkDataInDb(rowName, lgnExist)) {
                    return lgnExist;
                }
            } else if (rowName.equals("EMAIL")) {
                if (Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$").matcher(lgnExist).matches()
                        && checkDataInDb(rowName, lgnExist)) {
                    return lgnExist;
                }
            } else if (rowName.equals("PASSWORD")) {
//                if (Pattern.compile("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$").matcher(lgnExist).matches()) {
                    return encryptString(lgnExist, salt);
//                }
            } else if (rowName.equals("LOGIN")) {
                if (checkDataInDb(rowName, lgnExist)) {
                    return lgnExist;
                }
            } else {
                if (!Pattern.compile("\\s{1,99}").matcher(lgnExist).matches()) {
                    return lgnExist;
                } else {
                    throw new IncorectInputDataException("check please your correct " + rowName + " input ");
                }
            }
        }
        throw new IncorectInputDataException("check please your correct " + rowName + " input ");
    }

    private boolean checkDataInDb(String rowName, String lgnExist) throws IncorectInputDataException {
        if (actionsSelectFromEAV.checkRepeatData(rowName, lgnExist) > 0) {
            throw new IncorectInputDataException(" your " + rowName + " has already identified");
        } else {
            return true;
        }
    }

    private String encryptString(String sourseString, int salt) {
        MD5State digest = new MD5State();
        digest.update(sourseString.getBytes());
        Fingerprint hash = digest.calculate();
        String encryptedString = hash.toString();

        encryptedString = encryptedString.substring(
                encryptedString.indexOf(",") + 1, encryptedString.length() - 1);

        MD5State digest1 = new MD5State();
        digest1.update((encryptedString + salt).getBytes());
        Fingerprint hash1 = digest1.calculate();
        String encryptedString1 = hash1.toString();

        encryptedString1 = encryptedString1.substring(
                encryptedString1.indexOf(",") + 1, encryptedString1.length() - 1);
        return encryptedString1;
    }

    @ActUser(isAdmin = true)
    public ASportBetEvent createEvent(String i,HashMap<String,Object>map) {
        if (i.equals("0")) {
            return new FootballEvent(map);
        } else if (i.equals("1")) {
            return new BasketBallEvent(map);
        } else  {
            return new HockeyEvent(map);
        }
    }

    @ActUser()
    public boolean makeBet(ASportBetEvent event, int typeEvent) {
        return false;
    }
}
