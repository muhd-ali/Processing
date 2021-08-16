package venom.drawer;

import venom.Particle;
import venom.contract.ColorProvider;
import venom.contract.Drawer;

abstract class ParticleDrawer implements Drawer<Particle> {
    Particle particle;
    ColorProvider<Particle> colorProvider;
    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        this.particle = particle;
        this.colorProvider = colorProvider;
        draw();
    }

    public abstract void draw();
}
