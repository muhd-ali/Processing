package venom;

import java.util.List;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import venom.behavior.ChargedBehavior;
import venom.behavior.GravitationalBehavior;
import venom.behavior.SpringedBehavior;
import venom.behavior.SteerableBehavior;
import venom.colorProvider.WhiteColorProvider;
import venom.controller.GridPointParticlesController;
import venom.controller.RandomParticlesController;
import venom.drawer.*;

public class ParticleSim extends PApplet {
    public static ParticleSim singleton = new ParticleSim();
    public float res = 0.01f;
    List<Particle> gridParticles;
    public SpringedBehavior springedBehavior;
    public ChargedBehavior chargedBehavior;
    public GravitationalBehavior gravitationalBehavior;
    SteerableBehavior steerableBehavior;
    RandomParticlesController rpc;
    GridPointParticlesController gpc;

    public static void main(String[] args) {
        String[] processingArgs = { "ParticleSim" };
        PApplet.runSketch(processingArgs, singleton);
    }

    public void settings() {
        size(1920, 1080, P3D);
    }

    public Particle mouse = Particle.builder().currPos(new PVector(0, 0)).mass(25).charge(200).build();

    public void setup() {
        frameRate(144);
        gridParticles = new ArrayList<>();
        springedBehavior = new SpringedBehavior();
        chargedBehavior = new ChargedBehavior();
        gravitationalBehavior = new GravitationalBehavior();
        steerableBehavior = new SteerableBehavior();
        rpc = new RandomParticlesController(0);
        gpc = new GridPointParticlesController(gravitationalBehavior, null);
    }

//    private static void getDrawer() {
//        switch (mode) {
//            case "points":
//                new PointParticleDrawer().draw(getPosition(), new WhiteColorProvider<>());
//                break;
//            case "gravitational":
//                new GravitationalParticleDrawer().draw(this, new WhiteColorProvider<>());
//                break;
//            case "rocket":
//                new RocketDrawer().draw(this, new WhiteColorProvider<>());
//                break;
//            case "2":
//                new DistortingPointParticleDrawer().draw(this, colorProvider);
//                break;
//            case "3":
//                new HairParticleDrawer().draw(this, colorProvider);
//                break;
//            case "4":
//                new DistortingTriangleParticleDrawer().draw(this, colorProvider);
//                break;
//        }
//    }

    public void draw() {
        background(0);
        mouse.setCurrPos(new PVector(mouseX, mouseY));
        steerableBehavior.applyTo(mouse);
        mouse.update();
        mouse.draw(new PointParticleDrawer(), new WhiteColorProvider<>());
        rpc.draw();
        gpc.draw();
        gpc.update(rpc);
        textSize(25);
        fill(0, 255, 0);
        text(String.valueOf((int) (frameRate)), 10, 25);
    }
}
