package com.github.ebar.barrier;

/**
 * Daneel Yaitskov
 */
public abstract class AbstractEventBarrier implements EventBarrier {
    protected final EventBarrier parent;

    protected AbstractEventBarrier() {
        parent = this;
    }

    protected AbstractEventBarrier(EventBarrier parent) {
        this.parent = parent;
        parent.addChild(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
