package venom;

import processing.core.PVector;

interface Charged extends Forced {
	double getCharge();

	float getElectricConstant();

	PVector getPosition();
}
