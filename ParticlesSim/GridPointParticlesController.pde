class GridPointParticlesController {
  List<Particle> particles = new ArrayList<Particle>();
  
  GridPointParticlesController() {
    for (int x = 0; x < width; x += 1 / res) {
      for (int y = 0; y < height; y += 1 / res) {
        Particle particle = new ParticleBuilder()
         .currPos(new PVector(x, y))
         .pivotPos(new PVector(x, y))
         .charge(50)
         .build();
        particles.add(particle);
      }
    }
  }
  
  void update(RandomParticlesController rpc) {
    for (Particle gp : particles) {
      chargedBehavior.updateExternal(mouse);
      chargedBehavior.applyTo(gp);
      for (Particle rp : rpc.particles) {
        chargedBehavior.updateExternal(rp);
        chargedBehavior.applyTo(gp);
      }
      springedBehavior.applyTo(gp);
    }
  }
  
  void draw() {
    for (Particle gp : particles) {
      gp.draw();
      gp.update();
    }
  }
}
