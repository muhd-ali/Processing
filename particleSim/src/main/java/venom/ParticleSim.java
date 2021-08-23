package venom;

import com.google.common.collect.ImmutableList;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;
import venom.behavior.*;
import venom.colorProvider.WhiteColorProvider;
import venom.controller.GridPointParticlesController;
import venom.controller.RandomParticlesController;
import venom.drawer.*;

public class ParticleSim extends PApplet {
    public static ParticleSim singleton = new ParticleSim();
    public final float res = 0.03f;
    private SpringedBehavior springedBehavior;
    private ChargedBehavior chargedBehavior;
    private GravitationalBehavior gravitationalBehavior;
    private PerlinsNoiseBehavior perlinsNoiseBehavior;
    private MouseEventBehavior mouseEventBehavior;
    SteerableBehavior steerableBehavior;
    RandomParticlesController rpc;
    GridPointParticlesController gpc;

    int oldHeight, oldWidth;

    public static void main(String[] args) {
        String[] processingArgs = { "ParticleSim" };
        PApplet.runSketch(processingArgs, singleton);
    }

    public void settings() {
        size(1980, 1080, P3D);
    }

    public Particle mouse = Particle.builder().currPos(new PVector(0, 0)).mass(100).charge(0).build();

    public void mouseWheel(MouseEvent event) {
        mouse = mouse.toBuilder().charge(mouse.getCharge() - (float) event.getCount() * 5).build();
    }

    public void mousePressed(MouseEvent event) {
        if (mouseButton == CENTER) {
            mouse = mouse.toBuilder().charge(0).build();
        }
    }

    private void initObjects() {
        oldHeight = height;
        oldWidth = width;
        springedBehavior = new SpringedBehavior();
        chargedBehavior = new ChargedBehavior();
        gravitationalBehavior = new GravitationalBehavior();
        steerableBehavior = new SteerableBehavior();
        perlinsNoiseBehavior = new PerlinsNoiseBehavior(width, height);
        mouseEventBehavior = new MouseEventBehavior();
        rpc = new RandomParticlesController(0);
        gpc = new GridPointParticlesController(
            ImmutableList.of(chargedBehavior),
            ImmutableList.of(springedBehavior, perlinsNoiseBehavior, mouseEventBehavior));
    }

    public void setup() {
        frameRate(144);
        surface.setResizable(true);
        registerMethod("pre", this);
        initObjects();
    }

    public void pre() {
        if (oldHeight != height) {
            initObjects();
        }
    }

    public void draw() {
        background(0);
        mouse.setTargetPosition(new PVector(mouseX, mouseY));
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
