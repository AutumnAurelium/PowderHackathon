package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Gas extends Particle {

    public Gas(){
        this.weight=10;
    }
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
}
