class ParticleColorProvider1 extends ParticleColorProvider {
  float col1() {
    return(int)map(particle.force.mag(), 0, 1, 255, 0);
  }
  
  float col2() {
    return(int)map(particle.getPivotPosition().x, 0, width, 255, 0);
  }
  
  float col3() {
    return(int)map(particle.getPivotPosition().y, 0, height, 255, 0);
  }
  
  float col4() {
    PVector vector = particle.currPos.copy().sub(particle.getPivotPosition());
    return(int)map(vector.mag(), 0, 50, 75, 150);
  }
}