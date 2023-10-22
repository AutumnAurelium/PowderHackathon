package me.aurelium.particles.SuperParticle;

import me.aurelium.particles.Air;

public abstract class SuperSolid extends GravityAffected {
    @Override
    public void interactionCheck(Particle[][] p) {
        super.interactionCheck(p);

        if(p[1][1] != this)
            return;

        if(p[2][1] != null && yVelocity == 0) {
            if ((p[2][1].canCollide(this))) {
                if (p[2][0] instanceof Air) {
                    Particle old = p[2][0];
                    p[2][0] = this;
                    p[1][1] = old;
                } else if (p[2][2] instanceof Air) {
                    Particle old = p[2][2];
                    p[2][2] = this;
                    p[1][1] = old;
                }
            }
        }
    }
}
