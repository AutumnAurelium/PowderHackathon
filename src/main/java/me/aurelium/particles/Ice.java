package me.aurelium.particles;

import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperSolid;

import java.awt.*;

public class Ice extends SuperSolid {

    public Ice(){
        this.weight=150;
        this.temperature=270;
    }
    @Override
    public Color getColor() {
        return new Color(66, 233, 245);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //melt with heat
        super.interactionCheck(p);

        if(p[1][1] != this)
            return;

        if(p[1][1].temperature > 273) {
            p[1][1] = new Water();
            p[1][1].temperature = this.temperature;
        }
    }
}
