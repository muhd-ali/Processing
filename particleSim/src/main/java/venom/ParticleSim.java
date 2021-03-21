package venom;

import java.util.List;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

class ParticleSim extends PApplet {
    public static ParticleSim singleton = new ParticleSim();
    float res = 0.02f;
    List<Particle> gridParticles;
    SpringedBehavior springedBehavior;
    ChargedBehavior chargedBehavior;
    SteerableBehavior steerableBehavior;
    RandomParticlesController rpc;
    GridPointParticlesController gpc;

    public static void main(String[] args) {
        String[] processingArgs = {"ParticleSim"};
        PApplet.runSketch(processingArgs, singleton);
    }

    public void settings() {
        fullScreen();
    }

    Particle mouse = new ParticleBuilder().currPos(new PVector(0, 0)).charge(500).build();

    public void setup() {
        frameRate(120);
        gridParticles = new ArrayList<Particle>();
        springedBehavior = new SpringedBehavior();
        chargedBehavior = new ChargedBehavior();
        steerableBehavior = new SteerableBehavior();
        rpc = new RandomParticlesController(0);
        gpc = new GridPointParticlesController();
    }

    public void draw() {
        background(0);
        mouse.updateTargetPosition(new PVector(mouseX, mouseY));
        steerableBehavior.applyTo(mouse);
        mouse.update();
        mouse.draw();
        rpc.draw();
        gpc.draw();
        gpc.update(rpc);
        textSize(25);
        fill(0, 255, 0);
        text(String.valueOf((int) (frameRate)), 10, 25);
    }
}
