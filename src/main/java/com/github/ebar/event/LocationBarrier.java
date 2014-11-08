package com.github.ebar.event;


import com.github.ebar.barrier.AllOfBarrier;
import com.github.ebar.barrier.AnyOfBarrier;
import com.github.ebar.barrier.ParentBarrier;
import de.greenrobot.event.EventBus;

/**
 * Daneel Yaitskov
 */
public class LocationBarrier extends ParentBarrier {
    public LocationBarrier(EventBus bus, Runnable onPositive, Runnable onNegative) {
        super(onPositive, onNegative);
        AllOfBarrier allOfBarrier = new AllOfBarrier(parent);

        AnyOfBarrier sessionBarrier = new AnyOfBarrier(allOfBarrier);
        new BadVtSessionEventBarrier(bus, sessionBarrier);
        new NewVtSessionEventBarrier(bus, sessionBarrier);

        AnyOfBarrier powerBarrier = new AnyOfBarrier(allOfBarrier);
        new HighPowerEventBarrier(bus, powerBarrier);
        new LowPowerEventBarrier(bus, powerBarrier);

        AnyOfBarrier connectivityBarrier = new AnyOfBarrier(allOfBarrier);
        new OnlineEventBarrier(bus, connectivityBarrier);
        new OfflineEventBarrier(bus, connectivityBarrier);
    }
}
