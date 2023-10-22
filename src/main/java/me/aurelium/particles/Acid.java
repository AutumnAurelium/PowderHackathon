package me.aurelium.particles;

import me.aurelium.SuperParticle.Particle;
import me.aurelium.SuperParticle.SuperLiquid;

import java.awt.*;
public class Acid extends SuperLiquid {
    public Acid(){
        this.isWet=true;
        this.isCharged=false;
        this.weight=100;
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
        //dissolves adjacent particle, destroyed in the process.
    }


}
