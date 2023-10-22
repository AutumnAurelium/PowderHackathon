package me.aurelium;

import me.aurelium.particles.Air;

import java.util.ArrayList;
import java.util.List;
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
            particles[i] = new Air();
        }
    }

    public static List<Integer[]> bresenham(int x0, int y0, int x1, int y1) {
        List<Integer[]> results = new ArrayList<>();

        int dx = x1 - x0;
        int dy = y1 - y0;
        int D = 2*dy - dx;
        int y = y0;

        for(int x=x0; x < x1; x++) {
            if(x == x0)
                continue;
            results.add(new Integer[]{x, y});
            if(D > 0) {
                y += 1;
                D -= 2*dx;
            }
            D += 2*dy;
        }
        return results;
    }

    public void simulate(boolean first, ParticleRegion up, ParticleRegion down, ParticleRegion left, ParticleRegion right) {
        this.lock();
        for(int y=REGION_SIZE-1; y >= 0; y--) {
            for(int x=0; x < REGION_SIZE; x++) {
                Particle particle = particleAt(x, y);

                if(particle.isPhysicsTickFinished) {
                    if(first) {
                        particle.isPhysicsTickFinished = false;
                    }
                    continue;
                }

                if(!(particle instanceof Air)) {
                    particle.yVelocity += 1;
                }

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

                int xOverflow=0, yOverflow=0;
                boolean hasXOverflow=false, hasYOverflow=false;
                if(destX >= REGION_SIZE) {
                    xOverflow = destX - REGION_SIZE;
                    hasXOverflow = true;
                }
                if(destY >= REGION_SIZE) {
                    yOverflow = destY - REGION_SIZE;
                    hasYOverflow = true;
                }
                if(destX < 0) {
                    xOverflow = destX;
                    hasXOverflow = true;
                }
                if(destY < 0) {
                    yOverflow = destY;
                    hasYOverflow = true;
                }

                if(hasXOverflow && hasYOverflow) { // ugly hack. nobody care
                    if(yOverflow >= 0) {
                        destY--;
                    } else {
                        destY++;
                    }
                    hasYOverflow = false;
                }

                if(hasXOverflow) {
                    List<Integer[]> positions = bresenham(x, y, destX, destY);
                    positions.add(new Integer[]{destX, destY});
                    int intX=0;
                    int intY=0;
                    for(int i=0; i < positions.size(); i++) {
                        Integer[] pos = positions.get(i);
                        if(pos[0] == REGION_SIZE) { // y-intercept found. this is surely not a bad way to do this.
                            intX = 0;
                            intY = pos[1];

                            if(right == null) {
                                continue;
                            }

                            Particle particleAt = right.particleAt(intX, intY);
                            if(particleAt.canCollide(particle)) {
                                int stopX;
                                int stopY;
                                if(i == 0) {
                                    stopX = x;
                                    stopY = y;
                                } else {
                                    Integer[] stopPos = positions.get(i - 1);
                                    stopX = stopPos[0];
                                    stopY = stopPos[1];
                                }

                                particle.xVelocity = 0;
                                particle.yVelocity = 0;
                                intX = stopX;
                                intY = stopY;
                            }

                            Particle old;
                            if(intX == destX-1 && intY == destY) {
                                old = replaceParticle(intX, intY, particle);
                            } else {
                                old = right.replaceParticle(intX, intY, particle);
                            }
                            replaceParticle(x, y, old);
                        } else if(pos[0] == 0) {
                            intX = REGION_SIZE-1;
                            intY = pos[1];

                            if(left == null)
                                continue;

                            Particle particleAt = left.particleAt(intX, intY);
                            if(particleAt.canCollide(particle)) {
                                int stopX;
                                int stopY;
                                if(i == 0) {
                                    stopX = x;
                                    stopY = y;
                                } else {
                                    Integer[] stopPos = positions.get(i - 1);
                                    stopX = stopPos[0];
                                    stopY = stopPos[1];
                                }

                                particle.xVelocity = 0;
                                particle.yVelocity = 0;
                                intX = stopX;
                                intY = stopY;
                            }

                            Particle old;
                            if(intX == destX && intY == destY) {
                                old = replaceParticle(intX, intY, particle);
                            } else {
                                old = left.replaceParticle(intX, intY, particle);
                            }
                            replaceParticle(x, y, old);
                        }
                    }
                } else if(hasYOverflow) {
                    List<Integer[]> positions = bresenham(x, y, destX, destY);
                    positions.add(new Integer[]{destX, destY});
                    int intX=0;
                    int intY=0;
                    for(int i=0; i < positions.size(); i++) {
                        Integer[] pos = positions.get(i);
                        if(pos[1] == REGION_SIZE) { // y-intercept found. this is surely not a bad way to do this.
                            intX = pos[0];
                            intY = 0;

                            if(down == null)
                                continue;

                            Particle particleAt = down.particleAt(intX, intY);
                            if(particleAt.canCollide(particle)) {
                                int stopX;
                                int stopY;
                                if(i == 0) {
                                    stopX = x;
                                    stopY = y;
                                } else {
                                    Integer[] stopPos = positions.get(i - 1);
                                    stopX = stopPos[0];
                                    stopY = stopPos[1];
                                }

                                particle.xVelocity = 0;
                                particle.yVelocity = 0;
                                intX = stopX;
                                intY = stopY;
                            }

                            Particle old;
                            if(intX == destX && intY == destY) {
                                old = replaceParticle(intX, intY, particle);
                            } else {
                                old = down.replaceParticle(intX, intY, particle);
                            }
                            replaceParticle(x, y, old);
                        } else if(pos[1] == 0) {
                            intX = pos[0];
                            intY = REGION_SIZE-1;

                            if(up == null)
                                continue;

                            Particle particleAt = up.particleAt(intX, intY);
                            if(particleAt.canCollide(particle)) {
                                int stopX;
                                int stopY;
                                if(i == 0) {
                                    stopX = x;
                                    stopY = y;
                                } else {
                                    Integer[] stopPos = positions.get(i - 1);
                                    stopX = stopPos[0];
                                    stopY = stopPos[1];
                                }

                                particle.xVelocity = 0;
                                particle.yVelocity = 0;
                                intX = stopX;
                                intY = stopY;
                            }

                            Particle old;
                            if(intX == destX && intY == destY) {
                                old = replaceParticle(intX, intY, particle);
                            } else {
                                old = up.replaceParticle(intX, intY, particle);
                            }
                            replaceParticle(x, y, old);
                        }
                    }
                } else if((destX != x || destY != y)) {
                    List<Integer[]> intersections = bresenham(x, y, destX, destY);

                    for(int i=0; i < intersections.size(); i++) {
                        Integer[] arr = intersections.get(i);
                        int inX = arr[0];
                        int inY = arr[1];

                        Particle particleAt = particleAt(inX, inY);
                        if(particleAt.canCollide(particle)) {
                            Integer[] stopPos = intersections.get(i - 1);
                            int stopX = stopPos[0];
                            int stopY = stopPos[1];
                            particle.xVelocity = 0;
                            particle.yVelocity = 0;
                            destX = stopX;
                            destY = stopY;
                        }
                    }

                    if(particleAt(destX, destY).canCollide(particle)) {
                        destX = x;
                        destY = y;
                        particle.xVelocity = 0;
                        particle.yVelocity = 0;
                    }

                    Particle old = replaceParticle(destX, destY, particle);
                    replaceParticle(x, y, old);
                }

                particle.isPhysicsTickFinished = true;
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

    public int getSimX() {
        return simX;
    }

    public int getSimY() {
        return simY;
    }
}
