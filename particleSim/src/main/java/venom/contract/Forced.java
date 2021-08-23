package venom.contract;


import processing.core.PVector;

public interface Forced extends Positioned {
	void addForce(PVector force);
}
