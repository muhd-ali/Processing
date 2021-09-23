package venom.controller;

import java.util.List;

import processing.core.PVector;
import venom.Particle;
import venom.ParticleSim;
import venom.behavior.Behavior;
import venom.behavior.ExternalInteractionBehavior;
import venom.colorProvider.WhiteColorProvider;
import venom.drawer.RocketDrawer;

public class GridPointParticlesController extends ParticlesController {
    public GridPointParticlesController(List<ExternalInteractionBehavior> externalInteractionBehaviors, List<Behavior> defaultBehaviors) {
        super(externalInteractionBehaviors, defaultBehaviors);
    }

    @Override
    protected void initParticles() {
        for (int x = 0; x < ParticleSim.singleton.width; x += 1 / ParticleSim.singleton.res) {
            for (int y = 0; y < ParticleSim.singleton.height; y += 1 / ParticleSim.singleton.res) {
                Particle particle = Particle.builder().currPos(new PVector(x, y)).anchorPos(new PVector(x, y)).mass(1).charge(50)
                        .build();
                particles.add(particle);
            }
        }
    }

    @Override
    public void update(RandomParticlesController rpc) {
        for (Particle gp : particles) {
            for(ExternalInteractionBehavior externalInteractionBehavior : externalInteractionBehaviors) {
                externalInteractionBehavior.updateExternal(ParticleSim.singleton.mouse);
                externalInteractionBehavior.applyTo(gp);
                for (Particle rp : rpc.particles) {
                    externalInteractionBehavior.updateExternal(rp);
                    externalInteractionBehavior.applyTo(gp);
                }
            }
            for (Behavior defaultBehavior : defaultBehaviors) {
                defaultBehavior.applyTo(gp);
            }
        }
    }

    public void draw() {
        for (Particle gp : particles) {
            gp.draw(new RocketDrawer(), new WhiteColorProvider<>());
            gp.update();
        }
    }
}
