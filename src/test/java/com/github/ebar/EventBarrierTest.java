package com.github.ebar;


import com.github.ebar.barrier.AllOfBarrier;
import com.github.ebar.barrier.AnyOfBarrier;
import com.github.ebar.barrier.AtomicStubBarrier;
import com.github.ebar.barrier.ParentBarrier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Daneel Yaitskov
 */
public class EventBarrierTest {
    private static class Counter implements Runnable {
        public int count;
        public void run() {
            ++count;
        }
    }

    private Counter positiveCounter;
    private Counter negativeCounter;
    private ParentBarrier parent;

    @Before
    public void setUp() throws Exception {
        positiveCounter = new Counter();
        negativeCounter = new Counter();
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(0, negativeCounter.count);
        parent = new ParentBarrier(positiveCounter, negativeCounter);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(0, negativeCounter.count);
    }

    @Test
    public void allOfBarrier() {
        AllOfBarrier all = new AllOfBarrier(parent);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(1, negativeCounter.count);

        AtomicStubBarrier b1 = new AtomicStubBarrier(all, true);
        AtomicStubBarrier b2 = new AtomicStubBarrier(all, true);
        AtomicStubBarrier b3 = new AtomicStubBarrier(all, true);

        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);

        b1.trigger();
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);
        b2.trigger();
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);
        b3.trigger();
        Assert.assertEquals(1, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);
        b2.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);
        b2.setPositive(false);
        b2.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(5, negativeCounter.count);
        b1.setPositive(false);
        b1.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(6, negativeCounter.count);
        b2.setPositive(true);
        b2.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(6, negativeCounter.count);
        b1.setPositive(true);
        b1.trigger();
        Assert.assertEquals(3, positiveCounter.count);
        Assert.assertEquals(6, negativeCounter.count);

        Assert.assertFalse(b1.isClosed());
        Assert.assertFalse(b2.isClosed());
        Assert.assertFalse(b3.isClosed());
        parent.close();
        Assert.assertTrue(b1.isClosed());
        Assert.assertTrue(b2.isClosed());
        Assert.assertTrue(b3.isClosed());
    }

    @Test
    public void anyOfBarrier() {
        AnyOfBarrier any = new AnyOfBarrier(parent);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(1, negativeCounter.count);

        AtomicStubBarrier b1 = new AtomicStubBarrier(any, true);
        AtomicStubBarrier b2 = new AtomicStubBarrier(any, true);
        AtomicStubBarrier b3 = new AtomicStubBarrier(any, false);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(1, negativeCounter.count);

        b1.trigger();
        Assert.assertEquals(1, positiveCounter.count);
        Assert.assertEquals(1, negativeCounter.count);
        b2.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(1, negativeCounter.count);
        b3.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(2, negativeCounter.count);
        b1.setPositive(false);
        b1.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(3, negativeCounter.count);

        Assert.assertFalse(b1.isClosed());
        Assert.assertFalse(b2.isClosed());
        Assert.assertFalse(b3.isClosed());
        parent.close();
        Assert.assertTrue(b1.isClosed());
        Assert.assertTrue(b2.isClosed());
        Assert.assertTrue(b3.isClosed());
    }

    @Test
    public void allOfAny() {
        AllOfBarrier all = new AllOfBarrier(parent);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(1, negativeCounter.count);

        AnyOfBarrier sessionPair = new AnyOfBarrier(all);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(2, negativeCounter.count);

        AtomicStubBarrier login = new AtomicStubBarrier(sessionPair, true);
        AtomicStubBarrier logout = new AtomicStubBarrier(sessionPair, false);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(2, negativeCounter.count);

        AnyOfBarrier internetPair = new AnyOfBarrier(all);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(3, negativeCounter.count);

        AtomicStubBarrier online = new AtomicStubBarrier(internetPair, true);
        AtomicStubBarrier offline = new AtomicStubBarrier(internetPair, false);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(3, negativeCounter.count);

        AnyOfBarrier batteryPair = new AnyOfBarrier(all);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);

        AtomicStubBarrier chargerConnected = new AtomicStubBarrier(batteryPair, true);
        AtomicStubBarrier lowPower = new AtomicStubBarrier(batteryPair, false);
        AtomicStubBarrier highPower = new AtomicStubBarrier(batteryPair, true);
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);

        highPower.trigger();
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);

        online.trigger();
        Assert.assertEquals(0, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);

        login.trigger();
        Assert.assertEquals(1, positiveCounter.count);
        Assert.assertEquals(4, negativeCounter.count);

        lowPower.trigger();
        Assert.assertEquals(1, positiveCounter.count);
        Assert.assertEquals(5, negativeCounter.count);

        chargerConnected.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(5, negativeCounter.count);

        offline.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(6, negativeCounter.count);

        logout.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(7, negativeCounter.count);

        online.trigger();
        Assert.assertEquals(2, positiveCounter.count);
        Assert.assertEquals(7, negativeCounter.count);

        login.trigger();
        Assert.assertEquals(3, positiveCounter.count);
        Assert.assertEquals(7, negativeCounter.count);

        Assert.assertFalse(login.isClosed());
        Assert.assertFalse(logout.isClosed());
        Assert.assertFalse(online.isClosed());
        Assert.assertFalse(offline.isClosed());
        Assert.assertFalse(lowPower.isClosed());
        Assert.assertFalse(highPower.isClosed());
        Assert.assertFalse(chargerConnected.isClosed());
        parent.close();
        Assert.assertTrue(login.isClosed());
        Assert.assertTrue(logout.isClosed());
        Assert.assertTrue(online.isClosed());
        Assert.assertTrue(offline.isClosed());
        Assert.assertTrue(lowPower.isClosed());
        Assert.assertTrue(highPower.isClosed());
        Assert.assertTrue(chargerConnected.isClosed());
    }
}
