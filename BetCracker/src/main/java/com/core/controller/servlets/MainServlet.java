package com.core.controller.servlets;

import com.core.modelEvents.ASportBetEvent;
import com.registration.models.Usere;
import com.registration.server.UserActions;
import com.utils.StringCrypter;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * Created by Катя on 14.02.2015.
 */
@WebServlet(value = "/main")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // for new clients, <sessionId, streamInBound>
    private static ConcurrentHashMap<String, StreamInbound> clients = new ConcurrentHashMap<String, StreamInbound>();

    protected Set<UserActions> cashOfUser;
    protected ConcurrentMap<Integer, ASportBetEvent> listEvents;
    protected UserActions userActions;
    protected StringCrypter stringCrypter;
    protected int idRow;
    private String captchaS;
    private final static Logger logger = Logger.getLogger(MainServlet.class);

    @Override
    public void init() {
        listEvents = new ConcurrentHashMap();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("entr") != null) {
            checkAutorizedDataFromCookie(req, resp);
        } else if (req.getParameter("rg") != null) {
            req.getSession().setAttribute("isAdmin", req.getParameter("admin"));
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else if (req.getParameter("esc") != null) {
            clearSessionData(req);
            printInfo("inAccess", null, req, resp, "/", true);
        } else if (req.getParameter("entr1") != null) {
            req.removeAttribute("entr1");
            processRequest(req, resp);
        } else if (req.getParameter("captch") != null) {
            loadCaptch(req, resp);
        } else if (req.getParameter("username") != null) {
            uploadUser("username", "pasword", false, req, resp);
        } else {
            resp.sendRedirect("/");
        }
    }

    private void uploadUser(String username, String password, boolean cook, HttpServletRequest req,
                            HttpServletResponse resp) throws ServletException, IOException {
        String lgn = req.getParameter(username);
        String psw = req.getParameter(password);
        req.removeAttribute(username);
        try {
            if (getUserActions().connect(lgn, psw)) {
                req.getSession().setAttribute("psw", psw);
                createCookie(getUserActions(), psw, lgn, resp);
                showUser(getUserActions().getUser(), req, resp, "index.jsp", cook);
            }
        } catch (Exception e) {
            sendError(resp, e.getMessage());
            printInfo("showConfDialog", "not allow " + e.getMessage(), req, resp, "index.jsp", cook);
        }
    }

    private void loadCaptch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.removeAttribute("captch");
        try {
            Thread.currentThread().sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(captchaS);
    }

    private void clearSessionData(HttpServletRequest req) {
        for (String str : req.getParameterMap().keySet()) {
            req.setAttribute(str, null);
        }
        while (req.getSession().getAttributeNames().hasMoreElements()) {
            req.getSession().removeAttribute(req.getSession().getAttributeNames().nextElement());
        }
    }

    private boolean checkAutorizedDataFromCookie(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        stringCrypter = new StringCrypter();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (req.getParameter("entr").equals(URLDecoder.decode(cookie.getName(), "UTF-8"))) {
                    req.removeAttribute("entr");
                    return checkPswInCookie(resp, req, stringCrypter.decrypt(URLDecoder.decode(cookie.getValue(), "UTF-8")).split(","));
                }
            }
        }
            resp.sendError(resp.SC_CONFLICT);
            return false;
    }


    private boolean checkPswInCookie(HttpServletResponse resp, HttpServletRequest req, String[] split) throws IOException, ServletException {
        if (split[0].split("=")[1].equals(req.getParameter("psw"))) {
            req.getSession().setAttribute("psw", req.getParameter("psw"));
            showUser(resp, req, split);
        } else {
            resp.sendError(resp.SC_BAD_REQUEST);
        }
        return true;
    }

    private void showUser(HttpServletResponse resp, HttpServletRequest req, String[] split) throws ServletException, IOException {
        for (String data : split) {
            req.getSession().setAttribute(data.split("=")[0], data.split("=")[1]);
        }
        printInfo("inAccess", "access", req, resp, "index.jsp", true);
    }

    private void createCookie(UserActions usAct, String psw, String lgn, HttpServletResponse resp) throws UnsupportedEncodingException {
        stringCrypter = new StringCrypter();
        String val = URLEncoder.encode(stringCrypter.encrypt("psw=" + psw + "," + usAct.getUser().toString() + "," + usAct.getExpense().toString()), "UTF-8");
        Cookie c = new Cookie(URLEncoder.encode(lgn, "UTF-8"), val);
        c.setMaxAge(3600 * 3600);// время жизни файла
        resp.addCookie(c);
        val = URLEncoder.encode(stringCrypter.encrypt(usAct.getExpense().getCountExp().toString()), "UTF-8");
        c = new Cookie(String.valueOf(usAct.getExpense().getIdExp()), val);
        c.setMaxAge(3600 * 3600);// время жизни файла
        resp.addCookie(c);
    }

    private void sendError(HttpServletResponse resp, String err) throws IOException {
        if (err.substring(err.indexOf("your ") + 5, err.indexOf(" is")).equals("password")) {
            resp.sendError(resp.SC_BAD_REQUEST, "statusCode");
        } else {
            resp.sendError(resp.SC_FOUND, "statusCode");
        }
    }

    private void updateContenetOfEvents(HttpServletRequest req) {
        req.getSession().setAttribute("liveEvents", getListEvents());
        for (Integer i : getListEvents().keySet()) {
            System.out.println(getListEvents().get(i));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public Set<UserActions> getCashOfUser() {
        return cashOfUser;
    }

    public UserActions getUserActions() {
        if (userActions == null) {
            userActions = new UserActions();
        }
        return userActions;
    }

    public ConcurrentMap<Integer, ASportBetEvent> getListEvents() {
        return listEvents;
    }

    public void setListEvents(int key, ASportBetEvent event) {
        listEvents.put(key, event);
    }

    protected void setDataToHtml(HttpServletRequest req, ASportBetEvent event1) {
        for (String table : event1.getBets().keySet()) {
            req.getSession().setAttribute(table, event1.getBets().get(table));
        }
    }

    protected void removeOldData(HttpServletRequest req, ASportBetEvent event) {
        for (String table : event.getBets().keySet()) {
            req.getSession().removeAttribute(table);
        }
    }

    private void showUser(Usere user, HttpServletRequest req, HttpServletResponse resp, String page, boolean cook)
            throws ServletException, IOException {
        req.getSession().setAttribute("money", getUserActions().getExpense().getCountExp());
        req.getSession().setAttribute("playerName", user.getFio());
        req.getSession().setAttribute("isAdmin", user.getAdmins());
        printInfo("inAccess", "access", req, resp, page, cook);
    }

    private void printInfo(String attr, String val, HttpServletRequest req, HttpServletResponse resp, String page, boolean cook)
            throws ServletException, IOException {
        req.getSession().setAttribute(attr, val);
        if (cook == true) {
            resp.sendRedirect(page);
        }
        logger.info("connect was " + val);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int width = 150;
        int height = 50;

        char data[][] = {
                {'z', 'e', 't', 'c', 'o', 'd', 'e', '2'},
                {'l', 'i', 'n', 'u', 'x'},
                {'f', 'r', 'e', 'e', 'b', 's', 'd'},
                {'u', 'b', 'u', 'n', 't', 'u'},
                {'u', '4', 'u', '1', '2', 'u'},
                {'j', 'e', 'e', '4', '1'}
        };

        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        Font font = new Font("Georgia", Font.BOLD, 18);
        g2d.setFont(font);
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        GradientPaint gp = new GradientPaint(0, 0,
                Color.gray, 0, height / 2, Color.lightGray, true);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(new Color(255, 248, 241));
        Random r = new Random();
        int index = Math.abs(r.nextInt()) % 5;
        String captcha = String.copyValueOf(data[index]);
        captchaS = captcha;
//        request.getSession().setAttribute("captcha", captcha);
        int x = 0;
        int y = 0;
        for (int i = 0; i < data[index].length; i++) {
            x += 10 + (Math.abs(r.nextInt()) % 15);
            y = 20 + Math.abs(r.nextInt()) % 20;
            g2d.drawChars(data[index], i, 1, x, y);
        }
        g2d.dispose();
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        os.close();
    }
}
