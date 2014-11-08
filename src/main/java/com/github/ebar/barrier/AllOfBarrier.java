package com.github.ebar.barrier;

import java.util.HashSet;
import java.util.Set;

/**
 * Daneel Yaitskov
 */
public class AllOfBarrier extends AbstractCompositeBarrier {
    private final Set<EventBarrier> positive = new HashSet();

    public AllOfBarrier(EventBarrier parent) {
        super(parent);
    }

    @Override
    public void accept(final EventBarrier child, final Event event) {
        lock.in(new Runnable() {
            public void run() {
                if (!children.contains(child)) {
                    throw new IllegalArgumentException("Child barrier " + child
                            + " is not registered in " + this);
                }
                if (event.isPositive) {
                    positive.add(child);
                    if (children.size() == positive.size()) {
                        parent.accept(AllOfBarrier.this, Event.POSITIVE);
                    }
                } else {
                    positive.remove(child);
                    parent.accept(AllOfBarrier.this, Event.NEGATIVE);
                }
            }
        });
    }
}
