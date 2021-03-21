package venom;

import java.util.List;

import java.util.ArrayList;

import processing.core.PVector;

class GridLineParticlesController {
	List<List<Particle>> edgeParticles = new ArrayList<List<Particle>>();
	List<Particle> lines = new ArrayList<Particle>();

	GridLineParticlesController() {
		for (int x = 0; x < ParticleSim.singleton.width; x += 1 / ParticleSim.singleton.res) {
			for (int y = 0; y < ParticleSim.singleton.height; y += 1 / ParticleSim.singleton.res) {
				List<Particle> pair = new ArrayList<Particle>();
				Particle particle = new ParticleBuilder().currPos(new PVector(x, y)).pivotPos(new PVector(x, y))
						.charge(1).build();
				pair.add(particle);
				particle = new ParticleBuilder().currPos(new PVector(x, y)).pivotPos(new PVector(x, y)).charge(10)
						.build();
				pair.add(particle);
				edgeParticles.add(pair);
				particle = new ParticleBuilder().currPos(new PVector(x, y)).charge(0).build();
				lines.add(particle);
			}
		}
	}

	void update(RandomParticlesController rpc) {
		for (List<Particle> pair : edgeParticles) {
			for (Particle gp : pair) {
				ParticleSim.singleton.chargedBehavior.updateExternal(ParticleSim.singleton.mouse);
				ParticleSim.singleton.chargedBehavior.applyTo(gp);
				for (Particle rp : rpc.particles) {
					ParticleSim.singleton.chargedBehavior.updateExternal(rp);
					ParticleSim.singleton.chargedBehavior.applyTo(gp);
				}
				ParticleSim.singleton.springedBehavior.applyTo(gp);
			}
		}
	}

	void draw() {
		for (int i = 0; i < lines.size(); i++) {
			Particle line = lines.get(i);
			List<Particle> pair = edgeParticles.get(i);
			line.setLine(pair.get(0).currPos, pair.get(1).currPos);
			line.draw();
		}
		for (List<Particle> pair : edgeParticles) {
			for (Particle gp : pair) {
				gp.update();
			}
		}
	}
}
