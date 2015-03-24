package com.utils;

import com.db.domain.*;
import com.db.query.ActionsSelectFromEAV;
import com.registration.dao.DomainDao;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Катя on 02.02.2015.
 */
public class SaveClassToDb {
private  List<Serializable> listObjDB;
private  DomainDao domainDao;


    public void savePackegeWithClasses(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                savePackegeWithClasses(fileEntry);
            } else {
                try {
                    createClassForDB(Class.forName((folder.toString().substring(14).replace("\\", ".") + "." + fileEntry.getName().substring(0, fileEntry.getName().indexOf(".")))));
                } catch (ClassNotFoundException e) {
                    System.err.println("Can't save class in db");
                }
            }
        }
    }

    private  void createClassForDB(Class className) {
        for (Iterator iterator = ActionsSelectFromEAV.getActionsSelectFromEAV().getCurrentNameOfClass().iterator(); iterator.hasNext(); ) {
            if (iterator.next().toString().equals(className.getSimpleName().toUpperCase()))
                return;
        }
        domainDao = new DomainDao();
        listObjDB=new LinkedList<Serializable>();
        listObjDB.add(new Objtype(className.getSimpleName().toUpperCase()));
        for (Method f : sortMas(className.getMethods())) {
            if (f.getName().substring(0, 3).equals("get")) {
                for (Annotation a : f.getDeclaredAnnotations()) {
                    if (a.annotationType().getSimpleName().equals("Column")) {
                        listObjDB.add(new Attrtype(f.getName().substring(3).toUpperCase(), ((Objtype) listObjDB.get(0))));
                        break;
                    }
                }
            }
        }
        domainDao.saveObjectToDb(listObjDB);
    }
    public static Method[] sortMas(Method[]m){
        for(int i = m.length-1 ; i > 0 ; i--) {
            for (int j = 0; j < i; j++) {
                if (m[j].getName().hashCode() > m[j + 1].getName().hashCode()) {
                    Method tmp = m[j];
                    m[j] = m[j + 1];
                    m[j + 1] = tmp;
                }
            }
        }
        return m;
    }
}
