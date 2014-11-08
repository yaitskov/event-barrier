package com.github.ebar.barrier;

/**
 * Daneel Yaitskov
 */
public interface EventBarrier {
    void addChild(EventBarrier child);
    void accept(EventBarrier child, Event event);
    void close();
}
