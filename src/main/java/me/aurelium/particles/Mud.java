package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Mud extends Particle {

    public Mud(){
        this.weight=400;
        this.isWet=true;
    }
    @Override
    public Color getColor() {
        return new Color(92, 62, 39);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //can become dry

    }
}
