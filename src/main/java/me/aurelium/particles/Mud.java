package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Mud extends Particle {


    public Mud(){
        this.weight=400;
    }
    @Override
    public Color getColor() {
        return new Color(92, 62, 39);
    }
}
