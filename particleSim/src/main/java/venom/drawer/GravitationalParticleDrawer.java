package venom.drawer;

import processing.core.PVector;
import venom.ParticleSim;

public class GravitationalParticleDrawer extends ParticleDrawer {
    public void draw() {
        colorProvider.setStroke();
        ParticleSim.singleton.strokeWeight(particle.getMass());
        PVector vector = particle.getPosition();
        ParticleSim.singleton.point(vector.x, vector.y);
    }
}
