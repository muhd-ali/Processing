package venom.behavior;

import processing.core.PVector;
import venom.contract.Charged;

public class ChargedBehavior extends ExternalInteractionBehavior<Charged> {

    public void applyTo(Charged particle) {
        float dist = particle.getCenterOfMassPosition().dist(external.getCenterOfMassPosition());
        double forceMag = (particle.ELECTRIC_CONSTANT * Math.abs(particle.getCharge())
                * Math.abs(external.getCharge())) / (dist * dist);
        PVector force = particle.getCenterOfMassPosition().sub(external.getCenterOfMassPosition());
        if (particle.getCharge() * external.getCharge() < 0) {
            force.mult(-1);
        }
        force.setMag((float) forceMag);
        particle.addForce(force);
    }
}
