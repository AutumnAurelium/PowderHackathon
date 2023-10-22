package me.aurelium.particles;

import me.aurelium.SuperParticle.Particle;

import java.awt.*;

public class Grass extends Particle {
    public Grass(){
        this.weight=70;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //multiply with water

    }
}
