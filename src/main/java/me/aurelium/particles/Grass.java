package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Grass extends Particle {
    public Grass(){
        this.weight=70;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
