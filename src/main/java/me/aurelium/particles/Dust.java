package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Dust extends GravityAffected {

    public Dust(){
        this.isCharged=false;
        this.isWet=false;
        this.weight=50;
    }

    @Override
    public Color getColor() {
        return new Color(250, 186, 112);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        super.interactionCheck(p);
        //catches fire, becomes wet

        if(p[1][1] instanceof Air)
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
