package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;
public class Acid extends Particle {
    public Acid(){
        this.isWet=true;
        this.color= Color.GREEN;
        this.isCharged=false;

    }

    @Override
    public Color getColor() {
        return null;
    }
}
