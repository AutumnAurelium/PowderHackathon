package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Magma extends Particle {

    public Magma(){
        this.weight=500;
    }
    @Override
    public Color getColor() {
        if (temperature<300)
            return new Color(0,0,0);
        else if(temperature<1400){
            int r= (int)(temperature*.175);
            return new Color(255-(int)(temperature*.175), 0,0);
        }
        else
            return new Color(255,0,0);
    }
}
