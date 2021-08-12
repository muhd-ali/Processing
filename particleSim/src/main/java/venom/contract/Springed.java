package venom.contract;

import processing.core.PVector;

public interface Springed extends Forced {
    double SPRING_CONSTANT = 0.1;

    PVector getPivotPosition();
}
