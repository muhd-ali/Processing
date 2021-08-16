package venom.behavior;

import processing.core.PVector;
import venom.contract.Charged;
import venom.contract.Gravitational;

public class GravitationalBehavior extends ExternalInteractionBehavior<Gravitational> {

    public void applyTo(Gravitational particle) {
        float dist = particle.getCenterOfMassPosition().dist(external.getCenterOfMassPosition());
        double forceMag = (particle.GRAVITATIONAL_CONSTANT * Math.abs(particle.getMass())
                * Math.abs(external.getMass())) / (dist * dist);
        PVector force = particle.getCenterOfMassPosition().sub(external.getCenterOfMassPosition()).mult(-1);
        if (particle.getMass() * external.getMass() < 0) {
            force.mult(-1);
        }
        force.setMag((float) forceMag).limit(10);
        particle.addForce(force);
    }
}
