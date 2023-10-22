package me.aurelium;

import java.awt.*;

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

}
