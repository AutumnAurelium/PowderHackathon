package me.aurelium;

public class GameThread extends Thread {
    private final RenderThread mainThread;

    public GameThread(RenderThread mainThread) {
        this.mainThread = mainThread;
    }

    private void physicsTick() {
        
    }

    public void run() {
        while (!mainThread.shouldExit()) {
            long start = System.nanoTime();

            physicsTick();

            long end = System.nanoTime();

            if((end - start) < 16670) { // if physics tick took less than 1/60th of a second
                try {
                    long sleepTime = (16670 - (end - start));
                    Thread.sleep((int) sleepTime / 1000, (int) sleepTime % 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
