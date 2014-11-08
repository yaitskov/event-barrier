package com.github.ebar.barrier;

/**
 * Daneel Yaitskov
 */
public class Event {
    public static final Event NEGATIVE = new Event(false, null);
    public static final Event POSITIVE = new Event(true, null);

    public final boolean isPositive;
    public final Object data;

    public Event(boolean isPositive) {
        this.isPositive = isPositive;
        data = null;
    }

    public Event(boolean isPositive, Object data) {
        this.isPositive = isPositive;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event " + data + " positive " + isPositive;
    }
}
