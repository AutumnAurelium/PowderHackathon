package me.aurelium.particles.SuperParticle;

import me.aurelium.particles.Air;

public abstract class GravityAffected extends Particle {

    @Override
    public void interactionCheck(Particle[][] p) {
        super.interactionCheck(p);
        if(p[2][1] instanceof Air) {
            Particle old = p[2][1];
            p[1][1] = old;
            p[2][1] = this;
        }
    }
}
