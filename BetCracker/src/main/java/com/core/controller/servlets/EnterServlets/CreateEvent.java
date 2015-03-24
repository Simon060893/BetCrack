package com.core.controller.servlets.EnterServlets;

import com.core.controller.servlets.MainServlet;
import com.core.modelEvents.ASportBetEvent;
import com.core.modelEvents.typeBet.TypeBet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Катя on 14.02.2015.
 */
@WebServlet(value = "/createEvent")
public class CreateEvent extends MainServlet {
    private List<List<TypeBet>> listOfTypesBet;
    private int indexEl;
    private final static Logger logger = Logger.getLogger(MainServlet.class);



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param;
        if (req.getSession().getAttribute("isAdmin") != null && req.getSession().getAttribute("isAdmin").equals("yeas") ) {
            if (req.getParameter("addNEvnt") != null) {
                makeGoodWork(req, resp, "addNEvnt", "canCreate", "canCreate", "createEvent", "adding");
            } else if (req.getParameter("indexToDrop") != null) {
              getListEvents().remove(Integer.parseInt(req.getParameter("indexToDrop")) - 1);
                makeGoodWork(req, resp, "indexToDrop", null, null, "createEvent", "drop");
            } else if ((param = req.getParameter("createNewEvent")) != null) {
                makeAnotherGoodWork(req, resp, param, "creating", "createNewEvent");
            } else if ((param = req.getParameter("updateEvent")) != null) {
                makeAnotherGoodWork(req, resp, param, "updating", "updateEvent");
            } else if ((param = req.getParameter("back")) != null) {
                req.getRequestDispatcher("main").forward(req, resp);
            }else{
                req.getSession().removeAttribute("canCreate");
                req.getSession().setAttribute("list", getListEvents());
                resp.sendRedirect("/createEvent");
            }
        } else {
            resp.sendRedirect("/main");
        }

    }

    private void makeAnotherGoodWork(HttpServletRequest req, HttpServletResponse resp, String param, String param1,
                                     String attr) throws ServletException, IOException {
        if (param.length() < 2) {
            param = "wrong ";
        } else {
            createTableEvensLsts();
            if(param1.equals("updating")){
                setListEvents(indexEl, createEvent(param.substring(0, 1), param.substring(3).split(" @"), indexEl));
            }else{
                setListEvents(idRow, createEvent(param.substring(0, 1), param.substring(3).split(" @"), idRow++));
            }
            param = "succsesfull ";
        }
        req.getSession().removeAttribute(attr);
        makeGoodWork(req, resp, "canCreate", "list", getListEvents(), "createEvent", param + param1);
    }

    private void createTableEvensLsts() {
        listOfTypesBet=new LinkedList();
        for(int i=0;i<9;i++){
            listOfTypesBet.add(new LinkedList());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param;
        if (req.getSession().getAttribute("isAdmin") != null && req.getSession().getAttribute("isAdmin").equals("yeas") ) {
            if ((param = req.getParameter("matchId")) != null) {
                indexEl = Integer.parseInt(param.substring(0, param.indexOf("/")));
                setDataToHtml(req, listEvents.get(indexEl));
                req.getSession().setAttribute("canCreate", "canCreate");
                req.getSession().setAttribute("typeMatch", listEvents.get(indexEl).toString());
                makeGoodWork(req, resp, "matchId", null, null, "updateEvent", "updating");
                removeOldData(req, listEvents.get(indexEl));
            } else {
                req.setAttribute("list", getListEvents());
                req.getRequestDispatcher("createEvent.jsp").forward(req, resp);
            }
        }else{
            resp.sendRedirect("/main");
        }
    }

    private void makeGoodWork(HttpServletRequest req, HttpServletResponse resp, String attrToDrop, String attrToSet,
                              Object valForAttr, String page, String log) throws ServletException, IOException {
        req.getSession().removeAttribute(attrToDrop);
        if (attrToSet != null) {
            req.getSession().setAttribute(attrToSet, valForAttr);
        }
        req.getRequestDispatcher(page + ".jsp").forward(req, resp);
        logger.info("user " + req.getSession().getAttribute("playerName") + " has " + log + " event");
    }


    private List<TypeBet> addEventsToList(String str, TypeBet typeBet) {
        switch (str) {
            case "FullTotal_Goal":
                listOfTypesBet.get(0).add(typeBet);
                return listOfTypesBet.get(0);
            case "HalfTotal_Goal":
                listOfTypesBet.get(1).add(typeBet);
                return listOfTypesBet.get(1);
            case "FullNext_Goal":
                listOfTypesBet.get(2).add(typeBet);
                return listOfTypesBet.get(2);
            case "FullNext_Hckey":
                listOfTypesBet.get(3).add(typeBet);
                return listOfTypesBet.get(3);
            case "FullNext_Corner":
                listOfTypesBet.get(4).add(typeBet);
                return listOfTypesBet.get(4);
            case "FullTotal_Hckey":
                listOfTypesBet.get(5).add(typeBet);
                return listOfTypesBet.get(5);
            case "FullTotal_Corner":
                listOfTypesBet.get(6).add(typeBet);
                return listOfTypesBet.get(6);
            default:
                listOfTypesBet.get(7).add(typeBet);
                return listOfTypesBet.get(7);

        }
    }

    private ASportBetEvent createEvent(String typeMatch, String[] data,int i) {
        HashMap table = new HashMap();
        table.put("idRow", String.valueOf(i));
        for (String str : data) {
            if (str.split("=")[0].length() < 12) {
                table.put(str.split("=")[0], str.split("=")[1]);
            } else {
                table.put(str.substring(0, str.indexOf(".")), addEventsToList(str.substring(0, str.indexOf(".")),
                        new TypeBet(str.split(" "))));
            }
        }
        return getUserActions().createEvent(typeMatch, table);
    }

}
