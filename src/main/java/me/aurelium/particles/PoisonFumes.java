package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperGas;

import java.awt.*;

public class PoisonFumes extends SuperGas {

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
        if (p[1][0].temperature>390){
            p[1][1]=new Fire();
        } else if (p[0][1].temperature>390){
            p[1][1]=new Fire();
        } else if (p[1][2].temperature>390){
            p[1][1]=new Fire();
        } else if (p[2][1].temperature>390){
            p[1][1]=new Fire();
        }
    }
}
