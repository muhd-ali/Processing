class RandomParticlesController {
	int numParticles;
	List<Particle> particles = new ArrayList<Particle>();
	SteerableBehavior steerableBehavior = new SteerableBehavior();
	
	RandomParticlesController(int numParticles) {
		this.numParticles = numParticles;
		for (int i = 0; i < numParticles; i++) {
			particles.add(
				new Particle(
				new PVector(0,0),
				1000
				)
				);
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
