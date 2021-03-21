package venom;

import processing.core.PApplet;
import processing.core.PVector;

class ParticleColorProvider1 extends ParticleColorProvider {
    public float col1() {
        return (int) PApplet.map(particle.force.mag(), 0, 1, 255, 0);
    }

    public float col2() {
        return (int) PApplet.map(particle.getPivotPosition().x, 0, ParticleSim.singleton.width, 255, 0);
    }

    public float col3() {
        return (int) PApplet.map(particle.getPivotPosition().y, 0, ParticleSim.singleton.height, 255, 0);
    }

    public float col4() {
        PVector vector = particle.currPos.copy().sub(particle.getPivotPosition());
        return (int) PApplet.map(vector.mag(), 0, 50, 75, 150);
    }
}
