package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;
public class Acid extends Particle {
    public Acid(){
        this.isWet=true;
        this.isCharged=false;
        this.weight=100;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
