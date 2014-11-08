package com.github.ebar.barrier;


/**
 * Daneel Yaitskov
 */
public class ParentBarrier extends AbstractCompositeBarrier {
    private final Runnable onPositive;
    private final Runnable onNegative;

    public ParentBarrier(Runnable onPositive, Runnable onNegative) {
        super();
        this.onPositive = onPositive;
        this.onNegative = onNegative;
    }

    @Override
    public void accept(EventBarrier child, Event event) {
        if (event.isPositive) {
            lock.in(onPositive);
        } else {
            lock.in(onNegative);
        }
    }
}
