package com.core.modelEvents;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Катя on 28.01.2015.
 */
public class FootballEvent extends ASportBetEvent {
    public FootballEvent(HashMap<String, Object> map){
        super(map);
    }
    public FootballEvent(List map){
        super(map);
    }
    @Override
    protected void startMatch() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                timeMatch =timeMatch+1;
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        int i=0;
        while(i++<45) {
            Thread.sleep(10000);
            getBets().put("time", String.valueOf(i));
            getBets().put("winFirst", String.valueOf((i+0.9*i)));
            getBets().put("winSecond", String.valueOf(i+0.3*i));
            getBets().put("draw", String.valueOf( (i+0.5*i)));
            getBets().put("nextGoal", String.valueOf((i+0.3*i)));
        }
    }
    @Override
    protected void startMatchByDefaultBet() throws InterruptedException { }

    @Override
    public String toString() {
        return "Soccer";
    }
}
