class SteeringBehaviour extends NavigationBehaviour {
	float steeringStrength = 0.3;
	float maxSpeed;
	float deccelerRange;

	SteeringBehaviour(ImageParticle particle_) {
		super(particle_);
	}

	void steer(PVector destPos) {
		if (destPos == null) {
			return;
		}
		PVector destHeading = destPos.copy().sub(particle.pos).setMag(steeringStrength);
		particle.vel.add(destHeading);
		particle.vel.limit(maxSpeed);		
	}

	void deccelerateIfReached(PVector destPos) {
		if (destPos == null) {
			return;
		}

		float distanceFromDest = particle.pos.dist(destPos);
		float stopRange = 0;
		if (distanceFromDest < deccelerRange) {
			float speed = map(distanceFromDest, stopRange, deccelerRange, 0, maxSpeed);
			particle.vel.setMag(speed);
		}
	}

	void apply() {
		float deccelerRangeMin = min(width, height) / 2;
		float deccelerRangeMax = sqrt(sq(width)+sq(height)) / 2;
		deccelerRange = random(deccelerRangeMin, deccelerRangeMax);
		maxSpeed = random(5, 10);
		if (particle == null) {
			println("what???");
		}
		steer(particle.destPos);
		deccelerateIfReached(particle.destPos);
	}

	void enable_() {}
	void disable_() {}
}