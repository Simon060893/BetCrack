package com.registration.dao;

import com.utils.HibernateUtil;
import com.utils.exceptions.IncorectInputDataException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;

//import org.apache.log4j.Logger;

/**
 * Created by Катя on 28.01.2015.
 */
public class DomainDao {
    private HibernateUtil hibernateUtil;
    private final static Logger logger = Logger.getLogger(DomainDao.class);
    public DomainDao(){
//        Session session = ActionsSelectFromEAV.getActionsSelectFromEAV().getSession();
        BasicConfigurator.configure();
        hibernateUtil = new HibernateUtil();
    }

    public boolean saveObjectToDb(List<Serializable> inputObj){
        hibernateUtil.startSession();
        hibernateUtil.startTransaction();
        for(Serializable obj : inputObj){
            hibernateUtil.getSession().save(obj);
            logger.info("Save Objects to EAV "+ obj);
        }
        try {
            hibernateUtil.commitTransaction();
            logger.info("Commit was successesfully completed!!!");
            return true;
        } catch (IncorectInputDataException incorectInputData) {
            hibernateUtil.rollbackTransaction();
            logger.error("Commit was incorect completed!!!");
            return false;
        }

    }

    public boolean updateToDb(List<Serializable> inputObj){
        hibernateUtil.startSession();
        hibernateUtil.startTransaction();
        for(Serializable obj : inputObj){
            hibernateUtil.getSession().update(obj);
        }
        try {
            hibernateUtil.commitTransaction();
            return true;
        } catch (IncorectInputDataException incorectInputData) {
            hibernateUtil.rollbackTransaction();
        }
        return false;
    }
}
