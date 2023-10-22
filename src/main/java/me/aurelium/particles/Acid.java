package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;
public class Acid extends Particle {
    Acid(){
        this.isWet=true;
        this.isCharged=false;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
