class GridLineParticlesController {
  List<List<Particle>> edgeParticles = new ArrayList<List<Particle>>();
  List<Particle> lines = new ArrayList<Particle>();
  
  GridLineParticlesController() {
    for (int x = 0; x < width; x += 1 / res) {
      for (int y = 0; y < height; y += 1 / res) {
        List<Particle> pair = new ArrayList<Particle>();
        Particle particle = new ParticleBuilder()
         .currPos(new PVector(x, y))
         .pivotPos(new PVector(x, y))
         .charge(1)
         .build();
        pair.add(particle);
        particle = new ParticleBuilder()
         .currPos(new PVector(x, y))
         .pivotPos(new PVector(x, y))
         .charge(10)
         .build();
        pair.add(particle);
        edgeParticles.add(pair);
        particle = new ParticleBuilder()
         .currPos(new PVector(x, y))
         .charge(0)
         .build();
        lines.add(particle);
      }
    }
  }
  
  void update(RandomParticlesController rpc) {
    for (List < Particle > pair : edgeParticles) {
      for (Particle gp : pair) {
        chargedBehavior.updateExternal(mouse);
        chargedBehavior.applyTo(gp);
        for (Particle rp : rpc.particles) {
          chargedBehavior.updateExternal(rp);
          chargedBehavior.applyTo(gp);
        }
        springedBehavior.applyTo(gp);
      }
    }
  }
  
  void draw() {
    for (int i = 0; i < lines.size(); i++) {
      Particle line = lines.get(i);
      List<Particle> pair = edgeParticles.get(i);
      line.setLine(pair.get(0).currPos, pair.get(1).currPos);
      line.draw();
    }
    for (List < Particle > pair : edgeParticles) {
      for (Particle gp : pair) {
        gp.update();
      }
    }
  }
}
