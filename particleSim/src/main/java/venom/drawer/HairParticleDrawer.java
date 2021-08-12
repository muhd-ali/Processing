package venom.drawer;

import venom.Particle;
import venom.ParticleSim;
import venom.contract.ColorProvider;

public class HairParticleDrawer extends LineDrawer {
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        colorProvider.setStroke();
        ParticleSim.singleton.line(particle.getAnchorPosition().x, particle.getAnchorPosition().y, particle.getPosition().x, particle.getPosition().y);
    }
}
