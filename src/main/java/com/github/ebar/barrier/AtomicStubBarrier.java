package com.github.ebar.barrier;

/**
 * Daneel Yaitskov
 */
public class AtomicStubBarrier extends AbstractEventBarrier {
    private boolean isClosed;
    private boolean isPositive;

    public void setPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public AtomicStubBarrier(EventBarrier parent, boolean isPositive) {
        super(parent);
        this.isPositive = isPositive;
    }

    public void trigger() {
        parent.accept(this, new Event(isPositive));
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
        isClosed = true;
    }
}
