package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Magma extends Particle {

    @Override
    public Color getColor() {
        if (temperature>1400)
            return new Color(255, 103, 102);
        else if(temperature>600){
            int r= (int)(temperature*.175);
            return new Color((int)(temperature*.175), 103, 102);
        }
        else
            return new Color(105, 103, 102);
    }
}
