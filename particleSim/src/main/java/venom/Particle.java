package venom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;
import venom.colorProvider.ParticleColorProvider;
import venom.colorProvider.ParticleColorProvider1;
import venom.colorProvider.WhiteColorProvider;
import venom.contract.*;
import venom.drawer.*;

@Builder
public
class Particle implements LiveDrawable, Moving, Charged, Springed, Steerable, Gravitational {
    private PVector anchorPos, targetPos;
    @Setter
    PVector currPos;
    @Builder.Default
    PVector currVel = new PVector(0, 0);
    @Builder.Default
    @Getter
    float mass = 1, charge = 1;
    @Builder.Default
    private PVector force = new PVector(0, 0);

    public void setLine(PVector anchorPos, PVector currPos) {
        this.currPos = currPos.copy();
        this.anchorPos = anchorPos.copy();
    }

    public PVector getAnchorPosition() {
        if (anchorPos == null) {
            return currPos.copy();
        } else {
            return anchorPos.copy();
        }
    }

    public PVector getCenterOfMassPosition() {
        return getPosition();
    }

    public PVector getPosition() {
        return currPos.copy();
    }

    public PVector getForce() {
        return force.copy();
    }

    public PVector getVelocity() {
        return currVel.copy();
    }

    public PVector getTargetPosition() {
        if (targetPos == null) {
            return currPos.copy();
        } else {
            return targetPos.copy();
        }
    }

    public void updateTargetPosition(PVector targetPos) {
        this.targetPos = targetPos;
    }

    public void addForce(PVector f) {
        force.add(f);
    }

    public void updatePosition() {
        PVector accel = force.div(mass);
        if (Math.abs(currVel.mag() - currVel.copy().add(accel).mag()) > 10) {
            System.out.println("particlePos: " + currPos);
            System.out.println("particleAccel: " + accel.mag());
            System.out.println("mousePos: " + ParticleSim.singleton.mouse.currPos);
            System.out.println("");
        }
        currVel.add(accel);
        currPos.add(currVel);
        force = new PVector(0, 0);
    }

    public void update() {
        updatePosition();
    }

    public void draw() {
        ParticleSim.singleton.strokeWeight(10);
        ParticleSim.singleton.stroke(255, 255, 255);
        String mode = "rocket";
        float col1, col2, col3, col4 = 255;
        col2 = (int) ParticleSim.map(getAnchorPosition().x, 0, ParticleSim.singleton.width, 255, 0);
        col3 = (int) ParticleSim.map(getAnchorPosition().y, 0, ParticleSim.singleton.height, 255, 0);
        PVector vector, vector1;
        ParticleColorProvider colorProvider = new ParticleColorProvider1();
        colorProvider.setDataObject(this);
        switch (mode) {
        case "points":
            new PointDrawer().draw(getPosition(), new WhiteColorProvider<>());
            break;
        case "gravitational":
            new GravitationalParticleDrawer().draw(this, new WhiteColorProvider<>());
            break;
        case "rocket":
            new RocketDrawer().draw(this, new WhiteColorProvider<>());
            break;
        case "2":
            new DistortingPointParticleDrawer().draw(this, colorProvider);
            break;
        case "3":
            new HairParticleDrawer().draw(this, colorProvider);
            break;
        case "4":
            new DistortingTriangleParticleDrawer().draw(this, colorProvider);
            break;
        case "5":
            vector = currPos.copy().sub(getAnchorPosition());
            col1 = (int) ParticleSim.map(vector.heading(), 0, 2 * ParticleSim.PI, 255, 0);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
            vector1 = vector.copy().rotate(ParticleSim.PI / 3);
            col2 = (int) ParticleSim.map(force.mag(), 0, 1, 255, 0);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
            col4 = (int) ParticleSim.map(vector.mag(), 0, 50, 75, 150);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x,
                    currPos.y + vector1.y);
            break;
        case "6":
            vector = currPos.copy().sub(getAnchorPosition()).mult(10).limit(100);
            col1 = (int) ParticleSim.map(vector.heading(), 0, 2 * ParticleSim.PI, 255, 0);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
            vector1 = vector.copy().rotate(ParticleSim.PI / 3);
            col2 = (int) ParticleSim.map(force.mag(), 0, 1, 255, 0);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
            col4 = (int) ParticleSim.map(vector.mag(), 0, 50, 75, 150);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x,
                    currPos.y + vector1.y);
            break;
        case "7":
            vector = force.copy().setMag(150);
            col1 = (int) ParticleSim.map(vector.heading(), 0, 2 * ParticleSim.PI, 255, 0);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
            vector1 = vector.copy().rotate(ParticleSim.PI / 3);
            col2 = (int) ParticleSim.map(force.mag(), 0, 1, 255, 0);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
            col4 = (int) ParticleSim.map(vector.mag(), 0, 50, 75, 150);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x,
                    currPos.y + vector1.y);
            break;
        case "8":
            vector = force.copy().setMag(150);
            col1 = (int) ParticleSim.map(vector.heading(), 0, 2 * ParticleSim.PI, 255, 0);
            col2 = (int) ParticleSim.map(force.mag(), 0, 1, 255, 0);
            col4 = (int) ParticleSim.map(vector.mag(), 0, 50, 75, 150);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.circle(currPos.x, currPos.y, (int) ParticleSim.map(force.mag(), 0, 1, 0, 10));
            break;
        case "9":
            vector = currPos.copy().sub(getAnchorPosition()).limit(125);
            col1 = (int) ParticleSim.map(vector.heading(), 0, 2 * ParticleSim.PI, 255, 0);
            col4 = (int) ParticleSim.map(vector.mag(), 0, 50, 75, 150);
            ParticleSim.singleton.stroke(col1, col2, col3, col4);
            ParticleSim.singleton.circle(getAnchorPosition().x, getAnchorPosition().y,
                    (int) ParticleSim.map(vector.mag(), 0, 100, 0, 75));
            break;
        }
    }
}
