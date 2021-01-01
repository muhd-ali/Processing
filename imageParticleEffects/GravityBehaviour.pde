class GravityBehaviour extends ImageParticleBehaviour {
	ForceFromMousePointerBehaviour forceFromMousePointerBehaviour;
	GravityBehaviour(ImageParticle particle_) {
		super(particle_);
		forceFromMousePointerBehaviour = new ForceFromMousePointerBehaviour(particle_);
	}

	void manageEdgeCollisions() {
		float elasticityFactor = 0.75;
		if (particle.pos.y > (height - particle.pSize)) {
			particle.pos.y = (height - particle.pSize);
			particle.vel.y = - elasticityFactor * particle.vel.y;
		}
		if (particle.pos.x > (width - particle.pSize)) {
			particle.pos.x = (width - particle.pSize);
			particle.vel.x = - elasticityFactor * particle.vel.x;
		}
		if (particle.pos.x < (0 + particle.pSize)) {
			particle.pos.x = (0 + particle.pSize);
			particle.vel.x = - elasticityFactor * particle.vel.x;
		}
	}

	void apply() {
		particle.applyForce(new PVector(0, 0.2));
		manageEdgeCollisions();
		particle.vel.mult(0.995);
	}

	void enable_() {
		forceFromMousePointerBehaviour.enable();
		forceFromMousePointerBehaviour.apply();
	}
	void disable_() {
		forceFromMousePointerBehaviour.disable();
	}
}