package venom.contract;

import processing.core.PVector;

public interface Forced {
	void addForce(PVector force);

    PVector getCenterOfMassPosition();
}
