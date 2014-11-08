package com.github.ebar.barrier;

import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class AtomicEventBusBarrier<T> extends AbstractEventBarrier {
    private final EventBus bus;
    private final boolean isPositive;

    public AtomicEventBusBarrier(EventBus bus,
                                 EventBarrier parent,
                                 boolean isPositive) {
        super(parent);
        this.bus = bus;
        this.isPositive = isPositive;
        bus.register(this);
    }

    public void handleBusEvent(T event) {
        parent.accept(this, new Event(isPositive, event));
    }

    @Override
    public void addChild(EventBarrier child) {
        throw new IllegalStateException("AtomicBarrier cannot have children");
    }

    @Override
    public void accept(EventBarrier child, Event event) {
        throw new IllegalStateException("AtomicBarrier cannot have children");
    }

    @Override
    public void close() {
        bus.unregister(this);
    }
}
