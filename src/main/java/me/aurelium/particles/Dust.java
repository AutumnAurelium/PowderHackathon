package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Dust extends Particle {

    public Dust(){
        this.isCharged=false;
        this.isWet=false;
        this.weight=50;
    }

    @Override
    public Color getColor() {
        return new Color(250, 186, 112);
    }
}
