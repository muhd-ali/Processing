package venom;

abstract class ParticleColorProvider implements ColorProvider<Particle> {
    Particle particle;

    public abstract float col1();

    public abstract float col2();

    public abstract float col3();

    public abstract float col4();

    public void setDataObject(Particle particle) {
        this.particle = particle;
    }
}
