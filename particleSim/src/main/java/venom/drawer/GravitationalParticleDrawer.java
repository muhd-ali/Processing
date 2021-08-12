package venom.drawer;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.contract.ColorProvider;

public class GravitationalParticleDrawer extends ParticleDrawer {
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        colorProvider.setStroke();
        ParticleSim.singleton.strokeWeight(particle.getMass());
        PVector vector = particle.getPosition();
        ParticleSim.singleton.point(vector.x, vector.y);
    }
}
