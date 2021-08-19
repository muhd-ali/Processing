package venom.behavior;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.contract.Forced;

public class SpinBehavior extends ExternalInteractionBehavior<Forced> {
    public void applyTo(Forced system) {
        PVector force = external.getCenterOfMassPosition().sub(system.getCenterOfMassPosition()).setMag(10);
        force.rotate(ParticleSim.singleton.PI / 2);
        system.addForce(force);
    }
}
