package me.aurelium.particles.SuperParticle;

import me.aurelium.particles.Air;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Particle {
    public int xVelocity; // millipixels per sec
    public int yVelocity; // millipixels per sec
    public int xSubpixel; // millipixels
    public int ySubpixel; // millipixels
    public float temperature; // degrees kelvin
    public boolean isWet;
    public boolean isCharged;
    public static Color color;
    public double weight;
    public boolean isPhysicsTickFinished = false;

    public abstract Color getColor();
    public abstract boolean canCollide(Particle p);
    public void interactionCheck(Particle [][]p) {
        List<Particle> transfers = new ArrayList<>();

        transfers.add(this);
        float sum = thermalEnergy(this);
        if(!(p[1][2] instanceof Air)  && p[1][2] != null) {
            transfers.add(p[1][2]);
            sum += thermalEnergy(p[1][2]);
        }
        if(!(p[1][0] instanceof Air)  && p[1][0] != null) {
            transfers.add(p[1][0]);
            sum += thermalEnergy(p[1][0]);
        }

        if(!(p[2][1] instanceof Air) && p[2][1] != null) {
            transfers.add(p[2][1]);
            sum += thermalEnergy(p[2][1]);
        }
        if(!(p[0][1] instanceof Air)  && p[0][1] != null) {
            transfers.add(p[0][1]);
            sum += thermalEnergy(p[0][1]);
        }

        for(Particle transfer : transfers) {
            transfer.temperature = (float) ((sum / transfers.size()) / transfer.weight);
        }
    }
    private float thermalEnergy(Particle p) {
        return (float) (p.temperature * p.weight);
    }
}
