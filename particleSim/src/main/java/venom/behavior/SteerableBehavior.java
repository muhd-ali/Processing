package venom.behavior;

import processing.core.PVector;
import venom.contract.Steerable;

public class SteerableBehavior extends Behavior<Steerable> {
    public double distanceToTargetFor(Steerable body) {
        return body.getTargetPosition().sub(body.getCenterOfMassPosition()).mag();
    }

    public void applyTo(Steerable body) {
        PVector currPos = body.getCenterOfMassPosition();
        PVector forceToAdd = body.getTargetPosition().sub(currPos);
        body.addForce(forceToAdd.mult(0.1f));
    }
}
