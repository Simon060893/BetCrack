package com.core.modelEventsHead;

import com.core.modelEvents.ASportBetEvent;

import java.util.List;

/**
 * Created by Катя on 28.01.2015.
 */
public class LiveEvents implements BetEvents {
    @Override
    public boolean showEvents(List<ASportBetEvent> betEventsList) {
        return false;
    }
}
