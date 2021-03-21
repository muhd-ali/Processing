package venom;

import processing.core.PVector;

interface Springed extends Forced {
    double getSpringConstant();

    PVector getPivotPosition();

    PVector getMassPosition();
}
