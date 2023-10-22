package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;

import java.awt.*;

public class PoisonFumes extends Particle {

    public PoisonFumes(){
        this.weight=10;
    }
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public boolean canCollide(Particle p) {
        return false;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //catches fire when adjacent

    }
}
