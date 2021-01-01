abstract class ImageParticleBehaviour {
	ImageParticle particle;
	boolean isEnabled = false;
	ImageParticleBehaviour(ImageParticle particle_) {
		particle = particle_;
	}

	void applyIfEnabled() {
		if (isEnabled) {
			apply();
		}
	}

	void enable() {
		enable_();
		isEnabled = true;
	}

	void disable() {
		disable_();
		isEnabled = false;
	}

	protected abstract void apply();
	protected abstract void enable_();
	protected abstract void disable_();
}

abstract class NavigationBehaviour extends ImageParticleBehaviour {
	NavigationBehaviour(ImageParticle particle_) {
		super(particle_);
	}
}