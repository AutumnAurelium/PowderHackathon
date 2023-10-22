package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;

import java.awt.*;

public class Grass extends Particle {
    public Grass(){
        this.weight=70;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //multiply with water, burn with fire(?)
        if (p[1][0] instanceof Water){
            p[1][0]=new Grass();
        } else if (p[0][1] instanceof Water){
            p[0][1]=new Grass();
        } else if (p[1][2] instanceof Water){
            p[1][2]=new Grass();
        } else if (p[2][1] instanceof Water){
            p[2][1]=new Grass();
        }

        if (p[1][0] instanceof Fire){
            p[1][1]=new Fire();
        } else if (p[0][1] instanceof Fire){
            p[1][1]=new Fire();
        } else if (p[1][2] instanceof Fire){
            p[1][1]=new Fire();
        } else if (p[2][1] instanceof Fire){
            p[1][1]=new Fire();
        }
    }
}
