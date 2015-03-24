package com.core.controller.servlets;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * Created by Катя on 23.03.2015.
 */
public class MyInBound extends StreamInbound {
    private String name;

    private WsOutbound myoutbound;

    public MyInBound(HttpServletRequest httpSerbletRequest) {

    }
    @Override
    public void onOpen(WsOutbound outbound) {
        System.out.println("on open..");
        this.myoutbound = outbound;
        try {
            this.myoutbound.writeTextMessage(CharBuffer.wrap("hi, what's your name?"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onClose(int status) {
        System.out.println("Close client");
        //remove from list
    }

    @Override
    protected void onBinaryData(InputStream inputStream) throws IOException {

    }

    @Override
    protected void onTextData(Reader reader) throws IOException {

        System.out.println("Accept msg");
        CharBuffer outbuf = CharBuffer.wrap("- " + this.name + " says : ");
        CharBuffer buf = CharBuffer.wrap((CharSequence) reader);

        if(name != null) {
            this.myoutbound.writeTextMessage(outbuf);
            this.myoutbound.writeTextMessage(buf);
        } else {
            this.name = reader.toString();

            CharBuffer welcome = CharBuffer.wrap("== Welcome " + this.name + "!");
            this.myoutbound.writeTextMessage(welcome);
        }

        this.myoutbound.flush();

    }

}
