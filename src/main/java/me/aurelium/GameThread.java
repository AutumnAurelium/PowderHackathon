package me.aurelium;

public class GameThread extends Thread {
    private final RenderThread mainThread;

    private final ParticleRegion region = new ParticleRegion(0, 0);

    public GameThread(RenderThread mainThread) {
        this.mainThread = mainThread;
    }

    private void physicsTick() {
        region.simulate(null, null, null, null);
    }

    public void run() {

        // make test particle
        Particle[] particles2 = region.lockAndGetParticles();

        Particle p = new Particle();
        p.xVelocity = 20;
        p.yVelocity = 10;
        p.type = 1;
        particles2[4 * ParticleRegion.REGION_SIZE + 3] = p;

        while (!mainThread.shouldExit()) {
            long start = System.nanoTime();

            physicsTick();

            Particle[] particles = region.lockAndGetParticles();
            int[] pixels = mainThread.lockPixelArray();

            for(int y=ParticleRegion.REGION_SIZE-1; y >= 0; y--) {
                for (int x = 0; x < ParticleRegion.REGION_SIZE; x++) {
                    Particle particle = particles[y*ParticleRegion.REGION_SIZE + x];

                    if(particle.type == 1) {
                        pixels[y*RenderThread.SIM_WIDTH + x] = 0xFFFFFF;
                    } else {
                        pixels[y*RenderThread.SIM_WIDTH + x] = 0;
                    }
                }
            }

            region.unlock();
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
