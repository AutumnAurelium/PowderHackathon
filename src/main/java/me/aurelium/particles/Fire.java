package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Fire extends Particle {
    int weight;
    public Fire(){
        this.isCharged=false;
        this.isWet=false;
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
}
