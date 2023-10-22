package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Ice extends Particle {

    public Ice(){
        this.weight=150;
    }
    @Override
    public Color getColor() {
        return new Color(66, 233, 245);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //melt with heat

    }
}
