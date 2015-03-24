package com.core.modelEvents;

import java.util.HashMap;

/**
 * Created by Катя on 28.01.2015.
 */
public class BasketBallEvent extends ASportBetEvent
{
    public BasketBallEvent(HashMap<String, Object> map) {
        super(map);
    }

    @Override
    protected void startMatch() throws InterruptedException {
        int i=0;
        while(i++<10) {
            Thread.sleep(10000);
            getBets().put("time", String.valueOf(i));
            getBets().put("winFirst", String.valueOf((i+0.9*i)));
            getBets().put("winSecond", String.valueOf(i+0.3*i));
            getBets().put("draw", String.valueOf( (i+0.5*i)));
            getBets().put("nextGoal", String.valueOf((i+0.3*i)));
        }
    }
    @Override
    protected void startMatchByDefaultBet() throws InterruptedException {
        int i=0;
        while(i++<10) {
            Thread.sleep(10000);
            getBets().put("time", String.valueOf(i));
            getBets().put("winFirst", String.valueOf((i+0.9*i)));
            getBets().put("winSecond", String.valueOf(i+0.3*i));
            getBets().put("draw", String.valueOf( (i+0.5*i)));
            getBets().put("nextGoal", String.valueOf((i+0.3*i)));
        }
    }

    @Override
    public String toString() {
        return " BasketBall";
    }
}
