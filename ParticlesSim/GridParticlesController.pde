class GridParticlesController {
	List<Particle> particles = new ArrayList<Particle>();
	
	GridParticlesController() {
		for (int x = 0; x < width; x += 1 / res) {
			for (int y = 0; y < height; y += 1 / res) {
				particles.add(
					new Particle(new PVector(x, y))
					);
			}
		}
	}
	
	void draw(RandomParticlesController rpc) {
		for (Particle gp : particles) {
			chargedBehavior.updateExternal(mouse);
			chargedBehavior.applyTo(gp);
			for (Particle rp : rpc.particles) {
				chargedBehavior.updateExternal(rp);
				chargedBehavior.applyTo(gp);
			}
			springedBehavior.applyTo(gp);
			gp.draw();
			gp.update();
		}
	}
}
