package me.aurelium.particles;

import me.aurelium.SuperParticle.Particle;
import me.aurelium.SuperParticle.SuperLiquid;

import java.awt.*;

public class Water extends SuperLiquid {
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