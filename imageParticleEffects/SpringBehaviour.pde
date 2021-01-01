class SpringBehaviour extends NavigationBehaviour {
	SpringBehaviour(ImageParticle particle_) {
		super(particle_);
	}

	PVector getHingePoint() {
		return particle.pixelPos.copy();
	}

	PVector getSpringForce() {
		PVector pos = particle.pos.copy();
		PVector hingePoint = getHingePoint();
		float k = random(0.0025, 0.0075);
		float b = 0.025;
		PVector displacement = pos.sub(hingePoint);
		return displacement.copy().mult(-k).sub(particle.vel.copy().mult(b));
	}

	void applyDampening() {
		particle.vel.mult(0.9);
	}

	void apply() {
		particle.applyForce(getSpringForce());
	}

	void enable_() {

	}

	void disable_() {

	}
}