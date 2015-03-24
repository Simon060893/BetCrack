package com.core.modelEvents;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Катя on 28.01.2015.
 */
public class HockeyEvent extends ASportBetEvent {
    public HockeyEvent(HashMap<String, Object> map) {
        super(map);
    }
    public HockeyEvent(List map) {
        super(map);
    }
    @Override
    protected void startMatch() throws InterruptedException {
        int i=0;
        while(i++<20) {
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
        return "Hockey";
    }
}
