package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Water extends Particle {
    public Water() {
        this.temperature = 293.15F;
        this.weight=350;
        this.isWet=true;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //nothing?

    }
}