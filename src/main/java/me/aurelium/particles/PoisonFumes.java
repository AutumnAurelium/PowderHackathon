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
        super.interactionCheck(p);
        if (p[1][1]!=this) return;

        //catches fire when adjacent
        if (p[1][0]!=null && p[1][0].temperature>310){
            p[1][1]=new Fire();
        } else if (p[0][1]!=null && p[0][1].temperature>310){
            p[1][1]=new Fire();
        } else if (p[1][2]!=null && p[1][2].temperature>310){
            p[1][1]=new Fire();
        } else if (p[2][1]!=null && p[2][1].temperature>310){
            p[1][1]=new Fire();
        }

        if (p[1][0] instanceof Steam){
            p[1][0]=new PoisonFumes();
        } else if (p[0][1] instanceof Steam){
            p[0][1]=new PoisonFumes();
        } else if (p[1][2] instanceof Steam){
            p[1][2]=new PoisonFumes();
        } else if (p[2][1] instanceof Steam){
            p[2][1]=new PoisonFumes();
        }
    }
}
