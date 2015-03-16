package com.tor.sun.misc;

import sun.misc.Cleaner;
import sun.misc.Unsafe;

/**
 * User: tor
 * Date: 16.03.15
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public class NeedsCleanup {
    private final Long resource;
    static Unsafe unsafe = UnsafeUtil.unsafe;

    public NeedsCleanup() {
        this.resource = unsafe.allocateMemory(16 * 1024 * 1024);
        Cleaner.create(this, new Destructor(resource));
    }


    private static class Destructor implements Runnable {
        final long resource;

        Destructor(long resource) {
            this.resource = resource;
        }

        public void run() {
            unsafe.freeMemory(resource);
        }

    }
}
