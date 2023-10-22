package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Air extends Particle {

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


}
