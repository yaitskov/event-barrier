package com.github.ebar;


import com.github.ebar.barrier.AtomicEventBusBarrier;
import com.github.ebar.barrier.EventBarrier;
import com.github.ebar.barrier.ParentBarrier;
import de.greenrobot.event.EventBus;

import org.junit.Assert;
import org.junit.Test;

/**
 * Daneel Yaitskov
 */
public class AtomicEventBusBarrierTest {
    public static class EventA {}
    public static class EventB {}

    public static class BarrierA extends AtomicEventBusBarrier<EventA> {
        public BarrierA(EventBus bus, EventBarrier parent) {
            super(bus, parent, true);
        }
        public void onEvent(EventA e) {
            handleBusEvent(e);
        }
    }

    public static class BarrierB extends AtomicEventBusBarrier<EventB> {
        public BarrierB(EventBus bus, EventBarrier parent) {
            super(bus, parent, true);
        }
        public void onEvent(EventB e) {
            handleBusEvent(e);
        }
    }

    int goodCounter, badCounter;

    @Test
    public void inheritedMethod() {
        EventBus bus = new EventBus();

        ParentBarrier parentBarrier = new ParentBarrier(
                new Runnable() {
                    public void run() {
                        ++goodCounter;
                    }
                },
                new Runnable() {
                    public void run() {
                        ++badCounter;
                    }
                });

        new BarrierA(bus, parentBarrier);
        new BarrierB(bus, parentBarrier);
        Assert.assertEquals(0, goodCounter);
        Assert.assertEquals(2, badCounter);

        bus.post(new EventA());
        Assert.assertEquals(1, goodCounter);
        Assert.assertEquals(2, badCounter);

        bus.post(new EventB());
        Assert.assertEquals(2, goodCounter);
        Assert.assertEquals(2, badCounter);
    }
}
