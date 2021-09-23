package venom.controller;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.behavior.Behavior;
import venom.behavior.ExternalInteractionBehavior;
import venom.colorProvider.WhiteColorProvider;
import venom.drawer.LineDrawer;

import java.util.ArrayList;
import java.util.List;

abstract class ParticlesController {
    protected final List<ExternalInteractionBehavior> externalInteractionBehaviors;
    protected final List<Behavior> defaultBehaviors;
    protected List<Particle> particles = new ArrayList<>();

	ParticlesController(List<ExternalInteractionBehavior> externalInteractionBehaviors, List<Behavior> defaultBehaviors) {
		initParticles();
        this.externalInteractionBehaviors = externalInteractionBehaviors;
        this.defaultBehaviors = defaultBehaviors;
	}

	abstract protected void initParticles();
	abstract public void update(RandomParticlesController rpc);
	abstract public void draw();
}
