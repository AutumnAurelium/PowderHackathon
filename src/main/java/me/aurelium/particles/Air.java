package me.aurelium.particles;

import me.aurelium.SuperParticle.Particle;
import me.aurelium.SuperParticle.SuperGas;

import java.awt.*;

public class Air extends SuperGas {

    public Air(){
        this.isCharged=false;
        this.isWet=false;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean canCollide(Particle p) {
        return false;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //straight chillin

    }


}
