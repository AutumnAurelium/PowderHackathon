package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperSolid;

import java.awt.*;

public class Mud extends SuperSolid {

    public Mud(){
        this.weight=400;
        this.isWet=true;
    }
    @Override
    public Color getColor() {
        return new Color(92, 62, 39);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //can become dry
        super.interactionCheck(p);
        if(p[1][1] !=this) return;

        if(p[1][1].temperature>373){
            p[1][1]=new Dust();
        }
    }
}
