package venom.behavior;

import venom.Particle;
import venom.ParticleSim;
import venom.contract.Forced;

public class MouseEventBehavior extends Behavior<Forced> {
    public void applyTo(Forced system) {
        final Particle mouse = ParticleSim.singleton.mouse;
        if (ParticleSim.singleton.mousePressed) {
            if (ParticleSim.singleton.mouseButton == ParticleSim.singleton.LEFT) {
                SpinBehavior spinBehavior = new SpinBehavior();
                spinBehavior.updateExternal(mouse);
                spinBehavior.applyTo(system);
            }
        }
    }
}
