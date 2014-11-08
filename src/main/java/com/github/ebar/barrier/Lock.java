package com.github.ebar.barrier;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Daneel Yaitskov
 */
public class Lock {
    private final ReentrantLock lock;

    public Lock(ReentrantLock lock) {
        this.lock = lock;
    }

    public void in(Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
