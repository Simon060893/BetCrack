package com.core.controller;

import com.core.modelEvents.ASportBetEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Катя on 28.01.2015.
 */
public final class ActionsBet {
    private static ActionsBet actionsBet;
    private List<ASportBetEvent> betEventsList;

    public static List<ASportBetEvent> getActionsBet() {
        if (actionsBet != null) {
            actionsBet = new ActionsBet();
        }
        return actionsBet.getBetEventsList();
    }

    private ActionsBet() {
        betEventsList = new LinkedList<ASportBetEvent>();
    }

    private List<ASportBetEvent> getBetEventsList() {
        return betEventsList;
    }

    public void addNewEvent(ASportBetEvent asport) {
        this.betEventsList.add(asport);
    }

    public boolean createBetEvents() {
        return false;
    }
}
