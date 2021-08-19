package venom.controller;

import java.util.List;

import java.util.ArrayList;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.behavior.Behavior;
import venom.behavior.ExternalInteractionBehavior;
import venom.colorProvider.WhiteColorProvider;
import venom.drawer.LineDrawer;

class GridLineParticlesController {
	List<List<Particle>> edgeParticles = new ArrayList<>();
	List<Particle> lines = new ArrayList<>();

    private final ExternalInteractionBehavior externalInteractionBehavior;
    private final List<Behavior> defaultBehaviors;
    List<Particle> particles = new ArrayList<>();

	GridLineParticlesController(ExternalInteractionBehavior externalInteractionBehavior, List<Behavior> defaultBehaviors) {
		for (int x = 0; x < ParticleSim.singleton.width; x += 1 / ParticleSim.singleton.res) {
			for (int y = 0; y < ParticleSim.singleton.height; y += 1 / ParticleSim.singleton.res) {
				List<Particle> pair = new ArrayList<>();
				Particle particle = Particle.builder().currPos(new PVector(x, y)).anchorPos(new PVector(x, y)).charge(1)
						.build();
				pair.add(particle);
				particle = Particle.builder().currPos(new PVector(x, y)).anchorPos(new PVector(x, y)).charge(10).build();
				pair.add(particle);
				edgeParticles.add(pair);
				particle = Particle.builder().currPos(new PVector(x, y)).charge(0).build();
				lines.add(particle);
			}
		}

        this.externalInteractionBehavior = externalInteractionBehavior;
        this.defaultBehaviors = defaultBehaviors;
	}

	void update(RandomParticlesController rpc) {
		for (List<Particle> pair : edgeParticles) {
			for (Particle gp : pair) {
				externalInteractionBehavior.updateExternal(ParticleSim.singleton.mouse);
				externalInteractionBehavior.applyTo(gp);
				for (Particle rp : rpc.particles) {
					externalInteractionBehavior.updateExternal(rp);
					externalInteractionBehavior.applyTo(gp);
				}
                for (Behavior defaultBehavior : defaultBehaviors) {
                    defaultBehavior.applyTo(gp);
                }
			}
		}
	}

	void draw() {
		for (int i = 0; i < lines.size(); i++) {
			Particle line = lines.get(i);
			List<Particle> pair = edgeParticles.get(i);
			line.setLine(pair.get(0).getPosition(), pair.get(1).getPosition());
			line.draw(new LineDrawer(), new WhiteColorProvider<>());
		}
		for (List<Particle> pair : edgeParticles) {
			for (Particle gp : pair) {
				gp.update();
			}
		}
	}
}
