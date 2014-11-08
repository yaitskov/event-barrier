package com.github.ebar.event;

import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class OnlineEventBarrier extends AtomicEventBusBarrier<OnlineEvent> {
    public OnlineEventBarrier(EventBus bus, EventBarrier parent) {
        super(bus, parent, true);
    }

    public void onEventAsync(OnlineEvent e) {
        handleBusEvent(e);
    }
}
