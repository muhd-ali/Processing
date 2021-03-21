package venom;

import processing.core.PVector;

class SteerableBehavior extends Behavior<Steerable> {
    double distanceToTargetFor(Steerable body) {
        return body.getTargetPosition().copy().sub(body.getPosition()).mag();
    }

    void applyTo(Steerable body) {
        PVector currPos = body.getPosition();
        PVector forceToAdd = body.getTargetPosition().sub(currPos);
        body.addForce(forceToAdd.mult(0.1f));
    }
}
