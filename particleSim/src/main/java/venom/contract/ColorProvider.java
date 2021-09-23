package venom.contract;

import processing.core.PApplet;
import venom.ParticleSim;

public interface ColorProvider<T> {
    default float getColor(float value, float max) {
        return (int) PApplet.map(value, 0, max, 0, 255);
    }

    float col1();

	float col2();

	float col3();

	float col4();

	void setDataObject(T obj);

	default void setStroke() {
        ParticleSim.singleton.stroke(col1(), col2(), col3(), col4());
    }
}
