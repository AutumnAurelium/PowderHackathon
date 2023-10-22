package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public abstract class GravityAffected extends Particle {

    @Override
    public void interactionCheck(Particle[][] p) {
        if(p[2][1] instanceof Air) {
            Particle old = p[2][1];
            p[1][1] = old;
            p[2][1] = this;
        }
    }
}
