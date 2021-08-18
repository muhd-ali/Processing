package venom.drawer;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.contract.ColorProvider;

public class HairParticleDrawer extends LineDrawer {
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        colorProvider.setStroke();
        ParticleSim.singleton.pushMatrix();
        ParticleSim.singleton.translate(particle.getAnchorPosition().x, particle.getAnchorPosition().y);
        PVector pos = particle.getPosition().sub(particle.getAnchorPosition()).setMag(50);
        ParticleSim.singleton.line(0, 0, pos.x, pos.y);
        ParticleSim.singleton.popMatrix();
    }
}
