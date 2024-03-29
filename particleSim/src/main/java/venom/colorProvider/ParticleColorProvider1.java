package venom.colorProvider;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import venom.ParticleSim;

public class ParticleColorProvider1 extends ParticleColorProvider {
    public float col1() {
        return 255;
    }

    public float col2() {
        return (int) PApplet.map(particle.getAnchorPosition().x, 0, ParticleSim.singleton.width, 255, 0);
    }

    public float col3() {
        return (int) PApplet.map(particle.getAnchorPosition().y, 0, ParticleSim.singleton.height, 255, 0);
    }

    public float col4() {
        PVector vector = particle.getPosition().sub(particle.getAnchorPosition());
        return (int) PApplet.map(vector.mag(), 0, 50, 75, 150);
    }
}
