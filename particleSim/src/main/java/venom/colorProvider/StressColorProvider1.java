package venom.colorProvider;

import processing.core.PApplet;
import processing.core.PVector;
import venom.ParticleSim;

public class StressColorProvider1 extends ParticleColorProvider {
    public float col1() {
        return 255;
    }
    public float col2() {
        return getColor();
    }

    private float getColor() {
        PVector vector = particle.getPosition().sub(particle.getAnchorPosition());
        return (int) PApplet.map(vector.mag(), 0, 200, 255, 0);
    }


    public float col3() {
        return getColor();
    }

    public float col4() {
        return 255;
    }
}
