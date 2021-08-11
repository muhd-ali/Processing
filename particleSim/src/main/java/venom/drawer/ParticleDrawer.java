package venom.drawer;

import venom.Particle;
import venom.contract.ColorProvider;
import venom.contract.Drawer;

abstract class ParticleDrawer implements Drawer<Particle> {
    public abstract void draw(Particle particle, ColorProvider<Particle> colorProvider);
}
