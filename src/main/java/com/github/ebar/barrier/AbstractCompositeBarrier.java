package com.github.ebar.barrier;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Daneel Yaitskov
 */
public abstract class AbstractCompositeBarrier extends AbstractEventBarrier {
    protected final Lock lock = new Lock(new ReentrantLock(true));
    protected final Set<EventBarrier> children = new HashSet();

    private boolean isClosed;

    protected AbstractCompositeBarrier() {
        super();
    }

    protected AbstractCompositeBarrier(EventBarrier parent) {
        super(parent);
    }

    @Override
    public void addChild(final EventBarrier child) {
        lock.in(new Runnable() {
            public void run() {
                if (isClosed) {
                    throw new IllegalStateException("Barrier is closed");
                }
                children.add(child);
                reset();
            }
        });
    }

    @Override
    public void close() {
        lock.in(new Runnable() {
            public void run() {
                for (EventBarrier barrier : children) {
                    barrier.close();
                }
                children.clear();
                isClosed = true;
            }
        });
    }

    protected void reset() {
        parent.accept(AbstractCompositeBarrier.this, Event.NEGATIVE);
    }
}
