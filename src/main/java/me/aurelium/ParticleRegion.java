package me.aurelium;

import java.util.concurrent.locks.ReentrantLock;

public class ParticleRegion {
    public static final int REGION_SIZE = 16;

    // bits 0..7 : X velocity (millipixels per second)
    // bits 8..15: Y velocity (millipixels per second)
    // bits 16..23: X subpixel (millipixels)
    // bits 24..31: Y subpixel (millipixels)
    // bits 32..39: particle type
    // bits 40..52: temperature (degrees kelvin)
    // bits 53..63: special
    private final long[] data = new long[REGION_SIZE * REGION_SIZE];

    private final int simX, simY;

    private final ReentrantLock lock = new ReentrantLock();

    public ParticleRegion(int x, int y) {
        this.simX = x;
        this.simY = y;
    }

    public void simulate(ParticleRegion up, ParticleRegion down, ParticleRegion left, ParticleRegion right) {

    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
