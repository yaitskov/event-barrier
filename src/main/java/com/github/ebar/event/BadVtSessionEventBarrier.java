package com.github.ebar.event;

import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class BadVtSessionEventBarrier extends AtomicEventBusBarrier<BadVtSessionEvent> {
    public BadVtSessionEventBarrier(EventBus bus, EventBarrier parent) {
        super(bus, parent, false);
    }

    public void onEventAsync(BadVtSessionEvent e) {
        handleBusEvent(e);
    }
}
