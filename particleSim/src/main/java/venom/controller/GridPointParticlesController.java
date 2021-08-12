package venom.controller;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.behavior.Behavior;
import venom.behavior.ExternalInteractionBehavior;

public class GridPointParticlesController {
    private final ExternalInteractionBehavior externalInteractionBehavior;
    private final Behavior defaultBehavior;
    List<Particle> particles = new ArrayList<>();

    public GridPointParticlesController(ExternalInteractionBehavior externalInteractionBehavior, Behavior defaultBehavior) {
        for (int x = 0; x < ParticleSim.singleton.width; x += 1 / ParticleSim.singleton.res) {
            for (int y = 0; y < ParticleSim.singleton.height; y += 1 / ParticleSim.singleton.res) {
                Particle particle = Particle.builder().currPos(new PVector(x, y)).anchorPos(new PVector(x, y)).mass(5).charge(50)
                        .build();
                particles.add(particle);
            }
        }
        this.externalInteractionBehavior = externalInteractionBehavior;
        this.defaultBehavior = defaultBehavior;
    }

    public void update(RandomParticlesController rpc) {
        for (Particle gp : particles) {
            externalInteractionBehavior.updateExternal(ParticleSim.singleton.mouse);
            externalInteractionBehavior.applyTo(gp);
            for (Particle rp : rpc.particles) {
                externalInteractionBehavior.updateExternal(rp);
                externalInteractionBehavior.applyTo(gp);
            }
            if (defaultBehavior != null)
            defaultBehavior.applyTo(gp);
        }
    }

    public void draw() {
        for (Particle gp : particles) {
            gp.draw();
            gp.update();
        }
    }
}
