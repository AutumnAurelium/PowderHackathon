package me.aurelium.particles;


import me.aurelium.particles.SuperParticle.GravityAffected;
import me.aurelium.particles.SuperParticle.Particle;
import me.aurelium.particles.SuperParticle.SuperLiquid;
import me.aurelium.particles.SuperParticle.SuperSolid;

import java.awt.*;
public class Acid extends SuperLiquid {
    public Acid(){
        this.isWet=true;
        this.isCharged=false;
        this.weight=100;
        this.temperature=295;
    }

    @Override
    public Color getColor() {
        return new Color(237, 253, 63);
    }

    @Override
    public boolean canCollide(Particle p) {
        return true;
    }

    @Override
    public void interactionCheck(Particle[][] p) {
        //dissolves adjacent particle, destroying acid in the process (turns both into air).
        if (p[1][0] instanceof SuperSolid || (p[1][0] instanceof SuperLiquid && notAcidLiquid(p[1][0])) || p[1][0] instanceof Metal || p[1][0] instanceof Grass){
            p[1][1]=new Air();
            p[1][0]=new Air();
        } else if (p[0][1] instanceof SuperSolid || (p[0][1] instanceof SuperLiquid && notAcidLiquid(p[0][1])) || p[0][1] instanceof Metal || p[0][1] instanceof Grass){
            p[1][1]=new Air();
            p[0][1]=new Air();
        } else if (p[1][2] instanceof SuperSolid || (p[1][2] instanceof SuperLiquid && notAcidLiquid(p[1][2])) || p[1][2] instanceof Metal || p[1][2] instanceof Grass){
            p[1][1]=new Air();
            p[1][2]=new Air();
        } else if (p[2][1] instanceof SuperSolid || (p[2][1] instanceof SuperLiquid && notAcidLiquid(p[2][1])) || p[2][1] instanceof Metal || p[2][1] instanceof Grass){
            p[1][1]=new Air();
            p[2][1]=new Air();
        }
        super.interactionCheck(p);
        if(p[1][1] !=this) return;
    }

    public boolean notAcidLiquid(Particle p){
        //stops acid from melting acid
        return !(p instanceof Acid);
    }
}
