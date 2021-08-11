package venom.contract;

import processing.core.PVector;

public interface Springed extends Forced {
    double getSpringConstant();

    PVector getPivotPosition();

    PVector getMassPosition();
}
