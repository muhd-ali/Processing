package venom.colorProvider;

import processing.core.PVector;
import venom.Particle;
import venom.contract.ColorProvider;

public abstract class VectorColorProvider implements ColorProvider<PVector> {
    PVector vector;

    public abstract float col1();

    public abstract float col2();

    public abstract float col3();

    public abstract float col4();

    public void setDataObject(PVector vector) {
        this.vector = vector;
    }
}
