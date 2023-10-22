package me.aurelium.particles;

import me.aurelium.SuperParticle.Particle;
import me.aurelium.SuperParticle.SuperSolid;

import java.awt.*;

public class Stone extends SuperSolid {

    public Stone(){
        this.weight=800;
    }
    @Override
    public Color getColor() {
        if (temperature>1400)
            return new Color(255, 52, 50);
        else if(temperature>600){
            int r= (int)(temperature*.175);
            return new Color((int)(temperature*.175), 52, 50);
        }
        else
            return new Color(61, 52, 50);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //none?

    }
}
