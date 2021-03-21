package venom;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

class RandomParticlesController {
    int numParticles;
    List<Particle> particles = new ArrayList<Particle>();
    SteerableBehavior steerableBehavior = new SteerableBehavior();

    RandomParticlesController(int numParticles) {
        this.numParticles = numParticles;
        for (int i = 0; i < numParticles; i++) {
            Particle particle = new ParticleBuilder().currPos(new PVector(0, 0))
                    .charge(ParticleSim.singleton.random(1, 100)).build();
            particles.add(particle);
        }
    }

    void draw() {
        for (Particle p : particles) {
            if (steerableBehavior.distanceToTargetFor(p) < 10) {
                p.updateTargetPosition(new PVector(ParticleSim.singleton.random(ParticleSim.singleton.width),
                        ParticleSim.singleton.random(ParticleSim.singleton.height)));
            }
            steerableBehavior.applyTo(p);
            p.draw();
            p.update();
        }
    }
}
