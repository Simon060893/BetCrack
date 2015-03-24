package com.core.controller.servlets.EnterServlets;

import com.core.controller.servlets.MainServlet;
import com.db.query.ActionsSelectFromEAV;
import com.registration.models.Expense;
import com.registration.models.Usere;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Катя on 15.02.2015.
 */
@WebServlet(value = "/reg")
public class Registration extends MainServlet {
    private boolean dataChecked = true;
    private Usere usere;
    private final static Logger logger = Logger.getLogger(Registration.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (req.getSession().getAttribute("btn") != null) {
                if (!req.getSession().getAttribute("psw").equals(req.getSession().getAttribute("psw_r"))) {
                    printException("your password didn't coincidentally", req, resp);
                }
                userRegister(req,resp,"1");
            }else if(req.getParameter("user") != null){
                userRegister(req, resp, "2");
            }else if(req.getParameter("delay") != null){
                if(!ActionsSelectFromEAV.getActionsSelectFromEAV().checkEnterData(new StringBuilder("LOGIN"),req.getParameter("delay")).isEmpty()){
                    resp.sendError(resp.SC_BAD_REQUEST,"j");
                }
            }else{
            resp.sendRedirect("/main");
        }
    }

    private void userRegister(HttpServletRequest req, HttpServletResponse resp,String var) throws ServletException, IOException {
        try {
            if(var.equals("1")){
            if(getUserActions().register(createUser(req), 0)){
                getUserActions().register(createExp(req),1);
            }
            }
            else{
                if(getUserActions().register(createUser1(req),0)){
                    getUserActions().register(createExp(req),1);
                }
            }
            resp.sendRedirect("index.jsp");
        } catch (Throwable e) {
            resp.sendError(resp.SC_NOT_ACCEPTABLE,e.getMessage().toString());
            printException(e.getMessage(), req, resp);
        }
    }

    private Usere createUser1(HttpServletRequest req) {
        usere = new Usere();
        usere.setLogin(req.getParameter("user"));
        usere.setPassword(req.getParameter("password"));
        usere.setEmail(req.getParameter("email"));
        usere.setPhonenumber(req.getParameter("telephone"));
        usere.setFio(req.getParameter("firstname") + " " + req.getParameter("lastname"));
        usere.setDatReg(req.getParameter("dateB"));
        usere.setAdmins(req.getParameter("admin"));
        usere.setIpReg(req.getHeader("X-Forwarded-For") + 0);
        usere.setIdExp(0);
        usere.setIdUs(0);
        return usere;
    }

    private boolean checkEnterData(HttpServletRequest req, HttpServletResponse resp) {
        for (String s : req.getParameterMap().keySet()) {
            if (req.getParameter(s) != null) {
                if (s.equals("email")) {
                    if (req.getParameter(s).matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
                    } else {
                        return false;
                    }
                } else if (s.equals("phone_number")) {
                    if (req.getParameter(s).matches("[0-9]{13}")) {
                    } else {
                        return false;
                    }
                } else if (!s.equals("psw") && req.getParameter(s).matches("[0-9]+[a-z]+[A-Z]+")) {
                    dataChecked = true;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private Usere createUser(HttpServletRequest req) {
        usere = new Usere();
        usere.setLogin(req.getParameter("login"));
        usere.setPassword(req.getParameter("psw"));
        usere.setEmail(req.getParameter("email"));
        usere.setPhonenumber(req.getParameter("phone_number"));
        usere.setFio(req.getParameter("FIO"));
        usere.setDatReg(new Timestamp(System.currentTimeMillis()).toString());
        usere.setAdmins((String) req.getSession().getAttribute("isAdmin"));
        usere.setIpReg(req.getHeader("X-Forwarded-For") + 0);
        usere.setIdExp(0);
        usere.setIdUs(0);
        return usere;
    }

    private Expense createExp(HttpServletRequest req){
        Expense expense = new Expense();
        expense.setIdExp(System.currentTimeMillis());
        expense.setTypeExp(req.getParameter("typeExp"));
        expense.setCountExp(0);
        return expense;
    }

    private void printException(String str, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getSession().setAttribute("showConfDialog", str);
//        resp.sendRedirect("index.jsp");
        logger.error(str);
        return;
    }
}
