package venom.colorProvider;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import venom.ParticleSim;

public class VectorColorProvider1 extends VectorColorProvider {
    public float col1() {
        return (int) PApplet.map(vector.heading(), -PConstants.PI, PConstants.PI, 255, 0);
    }

    public float col2() {
        return (int) PApplet.map(vector.x, 0, ParticleSim.singleton.width, 255, 0);
    }

    public float col3() {
        return (int) PApplet.map(vector.y, 0, ParticleSim.singleton.height, 255, 0);
    }

    public float col4() {
        return (int) PApplet.map(vector.mag(), 0, 50, 255, 0);
    }
}
