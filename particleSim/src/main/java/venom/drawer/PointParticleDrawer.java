package venom.drawer;

import venom.Particle;
import venom.ParticleSim;
import venom.contract.ColorProvider;
import venom.contract.Drawer;

public class PointParticleDrawer implements Drawer<Particle> {
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        colorProvider.setStroke();
        ParticleSim.singleton.strokeWeight(10);
        ParticleSim.singleton.point(particle.getPosition().x, particle.getPosition().y);
    }
}
