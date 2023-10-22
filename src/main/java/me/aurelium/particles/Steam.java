package me.aurelium.particles;

import me.aurelium.Particle;

import java.awt.*;

public class Steam extends Particle {
        public Steam() {
            this.temperature = 390.15F;
            this.weight=10;
            this.isWet=true;
        }
    @Override
    public Color getColor() {
        return new Color(197, 235, 234);
    }

    @Override
    public boolean canCollide(Particle p) {
        return false;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //none?
    }
}
