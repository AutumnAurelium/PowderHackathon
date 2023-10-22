package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperGas;

import java.awt.*;

public class Fire extends SuperGas {
    int weight;
    int life = 0;
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
        super.interactionCheck(p);
        life++;

        if(life > 30 && Math.random() > 0.2) {
            p[1][1] = new Air();
        }
    }
}
