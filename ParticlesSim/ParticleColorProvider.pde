abstract class ParticleColorProvider implements ColorProvider<Particle> {
  Particle particle;
  
  abstract float col1();
  
  abstract float col2();
  
  abstract float col3();
  
  abstract float col4();
  
  void setDataObject(Particle particle) {
    this.particle = particle;
  }
}