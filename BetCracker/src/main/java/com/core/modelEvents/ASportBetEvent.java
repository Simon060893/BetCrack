package com.core.modelEvents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Катя on 28.01.2015.
 */
public abstract class ASportBetEvent {
    //for football
    protected Map<String,Object> bets;
    protected List listBets;
    private final Object nameF;
    private final Object nameS;
    protected  int timeMatch;
    private  Object resultMatch;
    private  Object idRow;
    //for tennis

    //for hockey

    protected ASportBetEvent(HashMap<String, Object> map) {
    bets = map ;
        this.nameS = bets.get("FullWin:ST");
        this.idRow = bets.get("idRow");
        this.nameF = bets.get("FullWin:FT");
        this.resultMatch = bets.get("Result:F")+":"+bets.get("Result:S");
    }
    protected ASportBetEvent(List<String> listBets) {
        this.listBets = listBets ;
        this.nameS = listBets.get(0);
        this.idRow = bets.get("idRow");
        this.nameF = listBets.get(2);
        this.resultMatch = listBets.get(1)+":"+listBets.get(3);
    }

    public Map<String, Object> getBets() {
        return bets;
    }

    public void setBets(HashMap<String, Object> bets) {
        this.bets = bets;
    }

    public Object getNameS() {
        return nameS;
    }

    public Object getNameF() {
        return nameF;
    }

    public int getTimeMatch() {
        return timeMatch;
    }

    public Object getResultMatch() {
        return resultMatch;
    }

    public Object getIdRow() {
        return idRow;
    }

    protected abstract void startMatch() throws InterruptedException;
    protected abstract void startMatchByDefaultBet() throws InterruptedException;
}
