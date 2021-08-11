package venom.contract;

import processing.core.PVector;

public interface Moving {
    void updatePosition();

    PVector getVelocity();
}
