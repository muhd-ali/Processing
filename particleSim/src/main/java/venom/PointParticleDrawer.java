package venom;

import processing.core.PApplet;

class PointParticleDrawer extends ParticleDrawer {
    private PApplet processing;

    PointParticleDrawer(PApplet processing) {
        this.processing = processing;
    }

    public void draw(Particle particle, ColorProvider<Particle> colorProvider) {
        processing.strokeWeight(10);
        processing.stroke(colorProvider.col1(), colorProvider.col2(), colorProvider.col3(), colorProvider.col4());
        processing.point(particle.currPos.x, particle.currPos.y);
    }
}
