package venom;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

class GridPointParticlesController {
    List<Particle> particles = new ArrayList<Particle>();

    GridPointParticlesController() {
        for (int x = 0; x < ParticleSim.singleton.width; x += 1 / ParticleSim.singleton.res) {
            for (int y = 0; y < ParticleSim.singleton.height; y += 1 / ParticleSim.singleton.res) {
                Particle particle = new ParticleBuilder().currPos(new PVector(x, y)).pivotPos(new PVector(x, y))
                        .charge(50).build();
                particles.add(particle);
            }
        }
    }

    void update(RandomParticlesController rpc) {
        for (Particle gp : particles) {
            ParticleSim.singleton.chargedBehavior.updateExternal(ParticleSim.singleton.mouse);
            ParticleSim.singleton.chargedBehavior.applyTo(gp);
            for (Particle rp : rpc.particles) {
                ParticleSim.singleton.chargedBehavior.updateExternal(rp);
                ParticleSim.singleton.chargedBehavior.applyTo(gp);
            }
            ParticleSim.singleton.springedBehavior.applyTo(gp);
        }
    }

    void draw() {
        for (Particle gp : particles) {
            gp.draw();
            gp.update();
        }
    }
}
