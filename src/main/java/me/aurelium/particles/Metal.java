package me.aurelium.particles;

import me.aurelium.SuperParticle.Particle;

import java.awt.*;

public class Metal extends Particle {

    public Metal(){
        this.weight=1000;
    }
    @Override
    public Color getColor() {
        if (temperature>1400)
            return new Color(255, 103, 102);
        else if(temperature>600){
            int r= (int)(temperature*.175);
            return new Color((int)(temperature*.175), 103, 102);
        }
        else
            return new Color(105, 103, 102);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //heats up, thats about all

    }
}
