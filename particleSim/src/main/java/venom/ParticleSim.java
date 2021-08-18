package venom;

import java.util.List;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;
import jcuda.Pointer;
import jcuda.runtime.JCuda;
import processing.core.PApplet;
import processing.core.PVector;
import venom.behavior.*;
import venom.colorProvider.WhiteColorProvider;
import venom.controller.GridPointParticlesController;
import venom.controller.RandomParticlesController;
import venom.drawer.*;

public class ParticleSim extends PApplet {
    public static ParticleSim singleton = new ParticleSim();
    public float res = 0.025f;
    List<Particle> gridParticles;
    public SpringedBehavior springedBehavior;
    public ChargedBehavior chargedBehavior;
    public GravitationalBehavior gravitationalBehavior;
    public PerlinsNoiseBehavior perlinsNoiseBehavior;
    SteerableBehavior steerableBehavior;
    RandomParticlesController rpc;
    GridPointParticlesController gpc;

    public static void main(String[] args) {
        String[] processingArgs = { "ParticleSim" };
        PApplet.runSketch(processingArgs, singleton);
        Pointer pointer = new Pointer();
        JCuda.cudaMalloc(pointer, 4);
        System.out.println("Pointer: "+pointer);
        JCuda.cudaFree(pointer);
    }

    public void settings() {
        size(1980, 1080, P3D);
    }

    public Particle mouse = Particle.builder().currPos(new PVector(0, 0)).mass(1).charge(50).build();

    public void setup() {
        frameRate(144);
        gridParticles = new ArrayList<>();
        springedBehavior = new SpringedBehavior();
        chargedBehavior = new ChargedBehavior();
        gravitationalBehavior = new GravitationalBehavior();
        steerableBehavior = new SteerableBehavior();
        perlinsNoiseBehavior = new PerlinsNoiseBehavior(width, height);
        rpc = new RandomParticlesController(0);
        gpc = new GridPointParticlesController(chargedBehavior, ImmutableList.of(springedBehavior, perlinsNoiseBehavior));
    }

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
