package venom.drawer;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.contract.ColorProvider;

public class DistortingPointParticleDrawer extends ParticleDrawer {
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        colorProvider.setStroke();
        PVector vector = particle.getForce().mult(10).limit(50);
        ParticleSim.singleton.line(particle.getPosition().x, particle.getPosition().y, particle.getPosition().x + vector.x, particle.getPosition().y + vector.y);
    }
}
