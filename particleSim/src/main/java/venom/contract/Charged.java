package venom.contract;

import processing.core.PVector;

public interface Charged extends Forced {
    double getCharge();

    float getElectricConstant();

    PVector getPosition();
}
