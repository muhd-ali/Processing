package venom.controller;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.behavior.ExternalInteractionBehavior;

public class GridPointParticlesController {
    private final ExternalInteractionBehavior<venom.contract.Gravitational> externalInteractionBehavior;
    List<Particle> particles = new ArrayList<>();

    public GridPointParticlesController(ExternalInteractionBehavior<venom.contract.Gravitational> externalInteractionBehavior) {
        for (int x = 0; x < ParticleSim.singleton.width; x += 1 / ParticleSim.singleton.res) {
            for (int y = 0; y < ParticleSim.singleton.height; y += 1 / ParticleSim.singleton.res) {
                Particle particle = Particle.builder().currPos(new PVector(x, y)).pivotPos(new PVector(x, y)).charge(50)
                        .build();
                particles.add(particle);
            }
        }
        this.externalInteractionBehavior = externalInteractionBehavior;
    }

    public void update(RandomParticlesController rpc) {
        for (Particle gp : particles) {
            externalInteractionBehavior.updateExternal(ParticleSim.singleton.mouse);
            externalInteractionBehavior.applyTo(gp);
            for (Particle rp : rpc.particles) {
                externalInteractionBehavior.updateExternal(rp);
                externalInteractionBehavior.applyTo(gp);
            }
            ParticleSim.singleton.springedBehavior.applyTo(gp);
        }
    }

    public void draw() {
        for (Particle gp : particles) {
            gp.draw();
            gp.update();
        }
    }
}
