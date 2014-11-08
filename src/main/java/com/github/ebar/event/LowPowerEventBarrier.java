package com.github.ebar.event;


import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class LowPowerEventBarrier extends AtomicEventBusBarrier<LowPowerEvent> {
    public LowPowerEventBarrier(EventBus bus, EventBarrier parent) {
        super(bus, parent, false);
    }

    public void onEventAsync(LowPowerEvent e) {
        handleBusEvent(e);
    }
}
