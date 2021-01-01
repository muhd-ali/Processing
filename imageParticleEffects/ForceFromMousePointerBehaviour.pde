class ForceFromMousePointerBehaviour extends ImageParticleBehaviour {
	ForceFromMousePointerBehaviour(ImageParticle particle_) {
		super(particle_);
	}

	void applyForceFromMousePointer() {
		PVector dir = particle.pos.copy().sub(mousePointVector);
		particle.applyForce(dir.setMag(5));
	}

	void apply() {
		applyForceFromMousePointer();
	}

	void enable_() {}
	void disable_() {}
}
