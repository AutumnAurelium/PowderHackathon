package me.aurelium.particles;



import me.aurelium.particles.SuperParticle.GravityAffected;
import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperLiquid;

import java.awt.*;

public class Water extends SuperLiquid {
    public Water() {
        this.temperature = 293.15F;
        this.weight=350;
        this.isWet=true;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        super.interactionCheck(p);

        if(p[1][1] instanceof Air)
            return;

        if(p[2][1] instanceof Fire) {
            p[2][1] = new Air();
        }
    }
}