package venom;

abstract class ParticleDrawer implements Drawer<Particle> {
    public abstract void draw(Particle particle, ColorProvider<Particle> colorProvider);
}
