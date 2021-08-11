package venom;

import java.util.List;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import venom.behavior.ChargedBehavior;
import venom.behavior.SpringedBehavior;
import venom.behavior.SteerableBehavior;
import venom.controller.GridPointParticlesController;
import venom.controller.RandomParticlesController;

public class ParticleSim extends PApplet {
    public static ParticleSim singleton = new ParticleSim();
    public float res = 0.01f;
    List<Particle> gridParticles;
    public SpringedBehavior springedBehavior;
    public ChargedBehavior chargedBehavior;
    SteerableBehavior steerableBehavior;
    RandomParticlesController rpc;
    GridPointParticlesController gpc;

    public static void main(String[] args) {
        String[] processingArgs = { "ParticleSim" };
        PApplet.runSketch(processingArgs, singleton);
    }

    public void settings() {
        fullScreen();
    }

    public Particle mouse = Particle.builder().currPos(new PVector(0, 0)).charge(500).build();

    public void setup() {
        frameRate(120);
        gridParticles = new ArrayList<>();
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
