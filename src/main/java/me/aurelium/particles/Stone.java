package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.GravityAffected;
import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperSolid;

import java.awt.*;

public class Stone extends GravityAffected {

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
        super.interactionCheck(p);

        if(p[1][1] != this)
            return;

        if(p[1][1].temperature > 300) {
            p[1][1] = new Magma();
            p[1][1].temperature = this.temperature;
        }
    }
}
