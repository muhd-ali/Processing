package venom.contract;

import venom.ParticleSim;

public interface ColorProvider<T> {
	float col1();

	float col2();

	float col3();

	float col4();

	void setDataObject(T obj);

	default void setStroke() {
        ParticleSim.singleton.stroke(col1(), col2(), col3(), col4());
    }
}
