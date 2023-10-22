package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Air extends Particle {

    Air(){
        this.isCharged=false;
        this.isWet=false;
    }

    @Override
    public Color getColor() {
        return null;
    }
}
