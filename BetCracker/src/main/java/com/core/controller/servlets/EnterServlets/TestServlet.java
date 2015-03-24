package com.core.controller.servlets.EnterServlets;

/**
 * Created by Катя on 14.02.2015.
 */
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@WebServlet(value = "/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("http://localhost:8082/");
        URL oracle = new URL("http://localhost:8082/");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();

    }


    private String readData(HttpServletRequest request) throws IOException {
        Reader reader = request.getReader();
//    BufferedReader reader = new BufferedReader( new InputStreamReader( request.getInputStream() ) );
        StringBuilder buffer = new StringBuilder();
        int current = reader.read();
        System.out.println((char)current);
        while (current != -1) {
            char ch = (char) current;
            buffer.append(ch);
            current = reader.read();
        }
        reader.close();
        return buffer.toString();
    }

    private void writeInitialPage(HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<script type=\"text/JavaScript\" src=\"/test/js/jquery-1.10.2.min.js\"></script>");
        writer.write("</head>");
        writer.write("<body>");
        writer.write("<button type=\"button\" onclick=\"");
        writer.write("$.ajax('/test/test',{");
        writer.write("'data': 'data1 data2 data3 data4 data5 data6 data7 data8 data9 data10',");
        writer.write("'type': 'POST',");
        writer.write("'processData': false,");
        writer.write("'contentType': 'text/plain'");
        writer.write("});");
        writer.write("\">Send</button>");
        writer.write("</body>");
        writer.write("</html>");
    }
}
