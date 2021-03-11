class RandomParticlesController {
  int numParticles;
  List<Particle> particles = new ArrayList<Particle>();
  SteerableBehavior steerableBehavior = new SteerableBehavior();
  
  RandomParticlesController(int numParticles) {
    this.numParticles = numParticles;
    for (int i = 0; i < numParticles; i++) {
			Particle particle = new ParticleBuilder()
         .currPos(new PVector(0, 0))
         .charge(random(10, 5000))
         .build();
			particles.add(particle);
    }
  }
  
  void draw() {
    for (Particle p : particles) {
      if	(steerableBehavior.distanceToTargetFor(p) < 10) {
        p.updateTargetPosition(
          new PVector(
          random(width),
          random(height)
         )
         );
      }
      steerableBehavior.applyTo(p);
      p.draw();
      p.update();
    }
  }
}
