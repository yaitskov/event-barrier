package com.github.ebar.event;

import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class NewVtSessionEventBarrier extends AtomicEventBusBarrier<NewVtSessionEvent> {
    public NewVtSessionEventBarrier(EventBus bus, EventBarrier parent) {
        super(bus, parent, true);
    }

    public void onEventAsync(NewVtSessionEvent e) {
        handleBusEvent(e);
    }
}
