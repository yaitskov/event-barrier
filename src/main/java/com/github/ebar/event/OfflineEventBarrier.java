package com.github.ebar.event;

import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class OfflineEventBarrier extends AtomicEventBusBarrier<OfflineEvent> {
    public OfflineEventBarrier(EventBus bus, EventBarrier parent) {
        super(bus, parent, false);
    }

    public void onEventAsync(OfflineEvent e) {
        handleBusEvent(e);
    }
}
