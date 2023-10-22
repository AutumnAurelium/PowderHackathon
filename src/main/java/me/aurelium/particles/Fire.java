package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;

import java.awt.*;

public class Fire extends Particle {
    int weight;
    public Fire(){
        this.isCharged=false;
        this.isWet=false;
    }

    @Override
    public Color getColor() {
        return new Color(242,125,12);
    }

    @Override
    public boolean canCollide(Particle p) {
        return false;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //others will be caught on fire

    }
}
