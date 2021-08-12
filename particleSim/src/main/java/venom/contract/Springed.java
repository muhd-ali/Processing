package venom.contract;

import processing.core.PVector;

public interface Springed extends Forced {
    float SPRING_CONSTANT = 0.1f;

    float DAMPING_CONSTANT = 1;

    PVector getAnchorPosition();

    PVector getVelocity();
}
