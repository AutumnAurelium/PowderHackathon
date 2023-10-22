package me.aurelium;

import me.aurelium.particles.Dust;

import javax.swing.plaf.synth.Region;

public class GameThread extends Thread {
    private static final int regionsPerRow = RenderThread.SIM_SIZE / ParticleRegion.REGION_SIZE;
    private final RenderThread mainThread;

    private final ParticleRegion[] regions;

    public GameThread(RenderThread mainThread) {
        this.mainThread = mainThread;

        this.regions = new ParticleRegion[regionsPerRow * regionsPerRow];
        for(int y=0; y < regionsPerRow; y++) {
            for(int x=0; x < regionsPerRow; x++) {
                this.regions[y * regionsPerRow + x] = new ParticleRegion(x, y);
            }
        }
    }

    private void simulateRegion(boolean first, int i) {
        ParticleRegion up=null, down=null, left=null, right=null;

        if(i - regionsPerRow >= 0) {
            up = this.regions[i - regionsPerRow];
        }
        if(i + regionsPerRow < this.regions.length) {
            down = this.regions[i + regionsPerRow];
        }
        if(i - 1 >= 0) {
            left = this.regions[i-1];
        }
        if(i + 1 < this.regions.length) {
            right = this.regions[i+1];
        }

        this.regions[i].simulate(first, up, down, left, right);
    }

    private void physicsTick() {
        for(int i=0; i < regionsPerRow*regionsPerRow; i += 2) { // Alternating pattern
            simulateRegion(true, i);
        }

        for(int i=1; i < regionsPerRow*regionsPerRow; i += 2) { // Alternating pattern 2
            simulateRegion(true, i);
        }

        for(int i=0; i < regionsPerRow*regionsPerRow; i += 2) { // Alternating pattern 3
            simulateRegion(false, i);
        }

        for(int i=1; i < regionsPerRow*regionsPerRow; i += 2) { // Alternating pattern 4
            simulateRegion(false, i);
        }
    }

    public void run() {

        // make test particle
        Particle[] particles2 = regions[0].lockAndGetParticles();

        Particle p = new Dust();
        p.xVelocity = 120;
        p.yVelocity = 25;
        p.type = 1;
        particles2[4 * ParticleRegion.REGION_SIZE + 3] = p;
        regions[0].unlock();

        while (!mainThread.shouldExit()) {
            long start = System.nanoTime();

            physicsTick();

            int[] pixels = mainThread.lockPixelArray();
            for(ParticleRegion region : regions) {

                Particle[] particles = region.lockAndGetParticles();

                for (int y = ParticleRegion.REGION_SIZE - 1; y >= 0; y--) {
                    for (int x = 0; x < ParticleRegion.REGION_SIZE; x++) {
                        Particle particle = particles[y * ParticleRegion.REGION_SIZE + x];

                        int pixY = y + ParticleRegion.REGION_SIZE*region.getSimY();
                        int pixX = x + ParticleRegion.REGION_SIZE*region.getSimX();

                        if (particle.type == 1) {
                            pixels[pixY * RenderThread.SIM_SIZE + pixX] = 0xFFFFFF;
                        } else {
                            if((region.getSimX() + region.getSimY()) % 2 == 0) {
                                pixels[pixY * RenderThread.SIM_SIZE + pixX] = 0x222222;
                            } else {
                                pixels[pixY * RenderThread.SIM_SIZE + pixX] = 0x444444;
                            }
                        }
                    }
                }

                region.unlock();
            }

            mainThread.unlockPixelArray();

            long end = System.nanoTime();

            if((end - start) < 16666666) { // if physics tick took less than 1/60th of a second
                try {
                    long sleepTime = (16666666 - (end - start));
                    int a = (int) sleepTime / 1000000;
                    int b = (int) sleepTime % 1000000;
                    Thread.sleep(a, b);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
