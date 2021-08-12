package venom.behavior;

import processing.core.PVector;
import venom.contract.Springed;

public class SpringedBehavior extends Behavior<Springed> {
    public void applyTo(Springed system) {
        float dist = system.getCenterOfMassPosition().dist(system.getPivotPosition());
        double forceMag = -dist * system.SPRING_CONSTANT;
        PVector force = system.getCenterOfMassPosition().copy().sub(system.getPivotPosition());
        force = force.setMag((float) forceMag);
        system.addForce(force);
    }
}
