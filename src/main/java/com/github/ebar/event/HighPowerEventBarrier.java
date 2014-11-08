package com.github.ebar.event;


import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class HighPowerEventBarrier extends AtomicEventBusBarrier<HighPowerEvent> {
    public HighPowerEventBarrier(EventBus bus, EventBarrier parent) {
        super(bus, parent, true);
    }

    public void onEventAsync(HighPowerEvent e) {
        handleBusEvent(e);
    }
}
