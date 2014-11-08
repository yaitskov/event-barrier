package com.github.ebar.barrier;


/**
 * Daneel Yaitskov
 */
public class AnyOfBarrier extends AbstractCompositeBarrier {
    public AnyOfBarrier(EventBarrier parent) {
        super(parent);
    }

    @Override
    public void accept(final EventBarrier child, final Event event) {
        lock.in(new Runnable() {
            public void run() {
                if (children.contains(child)) {
                    parent.accept(AnyOfBarrier.this, event);
                } else {
                    throw new IllegalArgumentException("Child barrier " + child
                            + " is not registered in " + this);
                }
            }
        });
    }

    @Override
    protected void reset() {
        // do nothing
    }
}
