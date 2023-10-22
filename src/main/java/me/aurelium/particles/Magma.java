package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperLiquid;

import java.awt.*;

public class Magma extends SuperLiquid {

    public Magma(){
        this.weight=500;
        this.temperature = 4000;
    }
    @Override
    public Color getColor() {
        if (temperature<500)
            return new Color(0,0,0);
        else if(temperature<1400){
            int r= (int)(temperature*.175);
            return new Color(255-(int)(temperature*.175), 0,0);
        }
        else
            return new Color(255,0,0);
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

        if(p[1][1].temperature < 500) {
            p[1][1] = new Stone();
            p[1][1].temperature = this.temperature;
        }
    }
}
