package me.aurelium.particles.SuperParticle;

import me.aurelium.particles.Air;

public abstract class SuperGas extends Particle {
    private boolean isAirOrBuoyant(Particle p) {
        if(p instanceof Air)
            return true;

        if(p instanceof GravityAffected) {
            return p.weight < this.weight;
        }

        return false;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        if(p[0][1] instanceof Air) {
            Particle old = p[0][1];
            p[0][1] = this;
            p[1][1] = old;
        }

        if(p[1][1] instanceof Air) {
            return;
        }

        boolean odd = System.currentTimeMillis() % 2 == 1;

        if(isAirOrBuoyant(p[0][0])) {
            Particle old = p[0][0];
            p[0][0] = this;
            p[1][1] = old;
        } else if(isAirOrBuoyant(p[0][2])) {
            Particle old = p[0][2];
            p[0][2] = this;
            p[1][1] = old;
        } else {
            if(odd) {
                if(isAirOrBuoyant(p[1][0])) {
                    Particle old = p[1][0];
                    p[1][0] = this;
                    p[1][1] = old;
                }
            } else {
                if(isAirOrBuoyant(p[1][2])) {
                    Particle old = p[1][2];
                    p[1][2] = this;
                    p[1][1] = old;
                }
            }
        }

        if(!(p[1][1] == this)) {
            return;
        }

        if(p[2][1] instanceof GravityAffected) {
            if(p[2][1].weight < this.weight) {
                Particle old = p[2][1];
                p[2][1] = this;
                p[1][1] = old;
            }
        }

        if(!(p[1][1] == this)) {
            return;
        }

        if(p[0][1] instanceof GravityAffected) {
            if(p[0][1].weight > this.weight) {
                Particle old = p[0][1];
                p[0][1] = this;
                p[1][1] = old;
            }
        }
    }
}
