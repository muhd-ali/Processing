package venom.behavior;

import processing.core.PVector;
import venom.contract.Steerable;

public class SteerableBehavior extends Behavior<Steerable> {
    public double distanceToTargetFor(Steerable body) {
        return body.getTargetPosition().sub(body.getCenterOfMassPosition()).mag();
    }

    public void applyTo(Steerable body) {
        PVector currPos = body.getCenterOfMassPosition();
        PVector targetVelocity = body.getTargetPosition().sub(currPos);
        body.setVelocity(targetVelocity.mult(1f));
    }
}
