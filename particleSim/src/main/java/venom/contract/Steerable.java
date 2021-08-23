package venom.contract;

import processing.core.PVector;

public interface Steerable extends Positioned {
    PVector getTargetPosition();
    void setVelocity(PVector vel);

    void setTargetPosition(PVector targetPos);
}
