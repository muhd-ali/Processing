class PointParticleDrawer extends ParticleDrawer {
  void draw(Particle particle, ColorProvider colorProvider) {
    strokeWeight(10);
    stroke(colorProvider.col1(), colorProvider.col2(), colorProvider.col3(), colorProvider.col4());
    point(particle.currPos.x, particle.currPos.y);
  }
}
