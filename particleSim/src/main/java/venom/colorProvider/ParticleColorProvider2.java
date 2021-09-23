package venom.colorProvider;

import processing.core.PApplet;
import processing.core.PVector;
import venom.ParticleSim;

public class ParticleColorProvider2 extends ParticleColorProvider {

    private float getHeadingColor() {
        return getColor(
            particle.getHeadingVector().heading(),
            2 * ParticleSim.singleton.PI
        );
    }

    public float col1() {
        return (PApplet.map(particle.getAnchorPosition().x, 0, ParticleSim.singleton.width, 255, 0) + getHeadingColor()) / 2;
    }


    public float col2() {
        return (PApplet.map(particle.getAnchorPosition().y, 0, ParticleSim.singleton.height, 255, 0) + getHeadingColor()) / 2;
    }

    public float col3() {
        return getHeadingColor();
    }

    public float col4() {
        PVector vector = particle.getPosition().sub(particle.getAnchorPosition());
        return (int) PApplet.map(vector.mag(), 0, 50, 75, 150);
    }
}
