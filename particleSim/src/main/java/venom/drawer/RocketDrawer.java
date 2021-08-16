package venom.drawer;

import venom.ParticleSim;

public class RocketDrawer extends ParticleDrawer {
    float vWidth = 20;
    float vHeight = 20;
    float maxSpeed = 5;

    void drawBody() {
        ParticleSim.singleton.stroke(0);
        ParticleSim.singleton.strokeWeight(1);
        ParticleSim.singleton.fill(255);
        ParticleSim.singleton.triangle(-vWidth, 0, vWidth, 0, 0, vHeight);
        ParticleSim.singleton.pushMatrix();
        ParticleSim.singleton.translate(0, vHeight/2);
        ParticleSim.singleton.rect(-vWidth/2, 0, vWidth, vHeight);
        ParticleSim.singleton.pushMatrix();
        ParticleSim.singleton.translate(0, vHeight);
        ParticleSim.singleton.triangle(-vWidth/2, 0, vWidth/2, 0, 0, vHeight/2);
        ParticleSim.singleton.popMatrix();
        ParticleSim.singleton.popMatrix();
    }

    void drawFlame() {
        ParticleSim.singleton.fill(255);
        ParticleSim.singleton.triangle(-vWidth, 0, vWidth, 0, 0, (-particle.getVelocity().mag()/maxSpeed) * vHeight);
        ParticleSim.singleton.fill(255);
        ParticleSim.singleton.triangle(-2*vWidth/3, 0, 2*vWidth/3, 0, 0, (-particle.getVelocity().mag()/maxSpeed) * (2*vHeight/3));
        ParticleSim.singleton.fill(255);
        ParticleSim.singleton.triangle(-vWidth/3, 0, vWidth/3, 0, 0, (-particle.getVelocity().mag()/maxSpeed) * (vHeight/3));
    }

    void drawStrobes() {
        ParticleSim.singleton.strokeWeight(5);
        if (ParticleSim.singleton.frameCount%100 == 0 || ParticleSim.singleton.frameCount%(100+5) == 0 && ParticleSim.singleton.random(1) > 0) {
            ParticleSim.singleton.stroke(255);
        } else {
            ParticleSim.singleton.stroke(255, 0, 0);
        }
        ParticleSim.singleton.point(-vWidth, 0);
        if (ParticleSim.singleton.frameCount%100 == 0 || ParticleSim.singleton.frameCount%(100+5) == 0 && ParticleSim.singleton.random(1) > 0) {
            ParticleSim.singleton.stroke(255);
        } else {
            ParticleSim.singleton.stroke(0, 255, 0);
        }
        ParticleSim.singleton.point(vWidth, 0);
    }

    public void draw() {
        ParticleSim.singleton.pushMatrix();
        ParticleSim.singleton.translate(particle.getPosition().x, particle.getPosition().y);
        ParticleSim.singleton.rotate(particle.getVelocity().copy().rotate(ParticleSim.singleton.HALF_PI).rotate(ParticleSim.singleton.PI).heading());
        drawBody();
        drawFlame();
        drawStrobes();
        ParticleSim.singleton.popMatrix();
    }
}
