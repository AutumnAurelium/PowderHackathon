package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperSolid;

import java.awt.*;

public class Dust extends SuperSolid {

    public Dust(){
        this.isCharged=false;
        this.isWet=false;
        this.weight=50;
        this.temperature=295;
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

        if (p[1][0] instanceof Water){
            p[1][1]=new Mud();
            p[1][0]=new Air();
            p[1][1].temperature=this.temperature;
        } else if (p[0][1] instanceof Water){
            p[1][1]=new Mud();
            p[0][1]=new Air();
            p[1][1].temperature=this.temperature;
        } else if (p[1][2] instanceof Water){
            p[1][1]=new Mud();
            p[1][2]=new Air();
            p[1][1].temperature=this.temperature;
        } else if (p[2][1] instanceof Water){
            p[1][1]=new Mud();
            p[2][1]=new Air();
            p[1][1].temperature=this.temperature;
        }
    }
}
