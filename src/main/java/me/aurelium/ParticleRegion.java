package me.aurelium;

import java.util.concurrent.locks.ReentrantLock;

public class ParticleRegion {
    public static final int REGION_SIZE = 16;
    private final Particle[] particles = new Particle[REGION_SIZE * REGION_SIZE];
    private final int simX, simY;

    private final ReentrantLock lock = new ReentrantLock();

    public ParticleRegion(int x, int y) {
        this.simX = x;
        this.simY = y;

        for(int i=0; i < particles.length; i++) {
            particles[i] = new Particle();
        }
    }

    public void set(int x, int y, long particleData) {

    }

    public void simulate(ParticleRegion up, ParticleRegion down, ParticleRegion left, ParticleRegion right) {
        this.lock();
        for(int y=REGION_SIZE-1; y >= 0; y--) {
            for(int x=0; x < REGION_SIZE; x++) {
                Particle particle = particleAt(x, y);

                particle.xSubpixel += particle.xVelocity;
                particle.ySubpixel += particle.yVelocity;

                int destX = x;
                int destY = y;

                if(particle.xSubpixel >= 500) {
                    destX += particle.xSubpixel / 500;
                    particle.xSubpixel = particle.xSubpixel % 500;
                }
                if(particle.ySubpixel >= 500) {
                    destY += particle.ySubpixel / 500;
                    particle.ySubpixel = particle.ySubpixel % 500;
                }
                if(particle.xSubpixel <= -500) {
                    destX -= Math.abs(particle.xSubpixel) / 500;
                    particle.xSubpixel = Math.abs(particle.xSubpixel) % 500;
                }
                if(particle.ySubpixel <= -500) {
                    destY -= Math.abs(particle.ySubpixel) / 500;
                    particle.ySubpixel = Math.abs(particle.ySubpixel) % 500;
                }

                if(destX >= REGION_SIZE) {
                    destX = x;
                }
                if(destY >= REGION_SIZE) {
                    destY = y;
                }

                if((destX != x || destY != y) && particleAt(destX, destY).type != 1) {
                    Particle old = replaceParticle(destX, destY, particle);
                    replaceParticle(x, y, old);
                }
            }
        }
    }

    private Particle particleAt(int x, int y) {
        return particles[y*REGION_SIZE + x];
    }

    private Particle replaceParticle(int x, int y, Particle p) {
        Particle replaced = particles[y*REGION_SIZE + x];
        particles[y*REGION_SIZE + x] = p;
        return replaced;
    }

    public Particle[] lockAndGetParticles() {
        this.lock();
        return particles;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
