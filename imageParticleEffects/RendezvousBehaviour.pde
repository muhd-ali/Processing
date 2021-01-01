class RendezvousBehaviour extends ImageParticleBehaviour {
	SteeringBehaviour steeringBehaviour;
	
	RendezvousBehaviour(ImageParticle particle_) {
		super(particle_);
		steeringBehaviour = new SteeringBehaviour(particle_);
	}

	void apply() {
		steeringBehaviour.applyIfEnabled();
	}

	void enable_() {
		particle.destPos = mousePointVector.copy();
		steeringBehaviour.enable();
	}

	void disable_() {
		steeringBehaviour.disable();
		particle.destPos = particle.pixelPos.copy();
	}
}