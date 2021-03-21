package venom;

import processing.core.PVector;

interface Moving {
    void updatePosition();

    PVector getVelocity();
}
