package venom.controller;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.behavior.SteerableBehavior;
import venom.colorProvider.WhiteColorProvider;
import venom.drawer.RocketDrawer;

public class RandomParticlesController {
    int numParticles;
    List<Particle> particles = new ArrayList<>();
    SteerableBehavior steerableBehavior = new SteerableBehavior();

    public RandomParticlesController(int numParticles) {
        this.numParticles = numParticles;
        for (int i = 0; i < numParticles; i++) {
            Particle particle = Particle.builder().mass(100).currPos(new PVector(0, 0))
                    .charge(ParticleSim.singleton.random(1, 100)).build();
            particles.add(particle);
        }
    }

    public void draw() {
        for (Particle p : particles) {
            if (steerableBehavior.distanceToTargetFor(p) < 10) {
                p.setTargetPosition(new PVector(ParticleSim.singleton.random(ParticleSim.singleton.width),
                        ParticleSim.singleton.random(ParticleSim.singleton.height)));
            }
            steerableBehavior.applyTo(p);
            p.draw(new RocketDrawer(), new WhiteColorProvider<>());
            p.update();
        }
    }
}
