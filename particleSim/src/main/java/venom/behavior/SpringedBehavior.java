package venom.behavior;

import processing.core.PVector;
import venom.contract.Springed;

public class SpringedBehavior extends Behavior<Springed> {
    public void applyTo(Springed system) {
        PVector force1 = system.getCenterOfMassPosition().sub(system.getAnchorPosition()).mult(-system.SPRING_CONSTANT);
        PVector force2 = system.getVelocity().mult(system.DAMPING_CONSTANT);
        PVector force = force1.sub(force2);
        system.addForce(force);
    }
}
