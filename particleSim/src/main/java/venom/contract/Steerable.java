package venom.contract;

import processing.core.PVector;

public interface Steerable extends Forced {
    PVector getTargetPosition();

    void updateTargetPosition(PVector targetPos);
}
