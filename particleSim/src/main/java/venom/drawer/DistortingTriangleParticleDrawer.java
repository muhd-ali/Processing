package venom.drawer;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.contract.ColorProvider;

public class DistortingTriangleParticleDrawer extends ParticleDrawer {
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        colorProvider.setStroke();
        PVector vector = particle.getPosition().sub(particle.getAnchorPosition());
        ParticleSim.singleton.line(particle.getPosition().x, particle.getPosition().y, particle.getPosition().x + vector.x, particle.getPosition().y + vector.y);
        PVector vector1 = particle.getForce().mult(10).limit(50);
//        col2 = (int) ParticleSim.map(particle.getForce().mag(), 0, 1, 255, 0);
//        ParticleSim.singleton.stroke(col1, col2, col3, col4);
        ParticleSim.singleton.line(particle.getPosition().x, particle.getPosition().y, particle.getPosition().x + vector1.x, particle.getPosition().y + vector1.y);
//        col4 = (int) ParticleSim.map(vector.mag(), 0, 50, 75, 150);
//        ParticleSim.singleton.stroke(col1, col2, col3, col4);
        ParticleSim.singleton.line(particle.getPosition().x + vector.x, particle.getPosition().y + vector.y, particle.getPosition().x + vector1.x,
            particle.getPosition().y + vector1.y);
    }
}
