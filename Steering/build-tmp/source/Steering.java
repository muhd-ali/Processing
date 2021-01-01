import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.List; 
import java.util.ArrayList; 
import java.util.List; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Steering extends PApplet {




Vehicle[] vehicles = new Vehicle[100];
List <Home> homes = new ArrayList<Home>();
PVector mousePointerVector = new PVector();
float deccelerRange = 50;

public void setup() {
	
	surface.setLocation(0, 0);
	for (int i = 0; i < 5; ++i) {
		homes.add(new Home());
	}
	for (int i = 0; i < vehicles.length; ++i) {
		vehicles[i] = new Vehicle();
		vehicles[i].homes = homes;
	}
}

public void draw() {
	background(0, 0.01f);
	for (int i = 0; i < homes.size(); ++i) {
		homes.get(i).draw();
	}
	for (int i = 0; i < vehicles.length; ++i) {
		vehicles[i].draw();
		vehicles[i].update();
		// vehicles[i].destPos = homes.pos;
	}
}

public void mousePressed() {
	for (int i = 0; i < homes.size(); ++i) {
		homes.get(i).mousePressedAt(mousePointerVector);
	}
}

public void mouseMoved() {
	mousePointerVector.set(mouseX, mouseY);
	for (int i = 0; i < homes.size(); ++i) {
		homes.get(i).mouseMovedTo(mousePointerVector);
	}
	for (int i = 0; i < vehicles.length; ++i) {
		vehicles[i].mouseMovedTo(mousePointerVector);
	}
}
abstract class Destination {
	PVector pos;
	boolean followMouse = false;


	Destination() {
		pos = new PVector(
			random((deccelerRange / 2), width - (deccelerRange / 2)),
			random((deccelerRange / 2), height - (deccelerRange / 2))
			);
	}

	public abstract void draw();

	public void mousePressedAt(PVector mousePointerVector) {
		if (pos.dist(mousePointerVector) < deccelerRange/2) {
			followMouse = !followMouse;
			pos.set(mousePointerVector);
			if (pos.x > width - (deccelerRange / 2)) {
				pos.x = width - (deccelerRange / 2);
			}
			if (pos.y > height - (deccelerRange / 2)) {
				pos.y = height - (deccelerRange / 2);
			}
		}
	}

	public void mouseMovedTo(PVector mousePointerVector) {
		if (followMouse) {
			if (mousePointerVector.x < width - (deccelerRange / 2)) {
				pos.x = mousePointerVector.x;
			}

			if (mousePointerVector.y < 1000 - (deccelerRange / 2)) {
				pos.y = mousePointerVector.y;
			}
		}
	}
}
public class Food extends Destination {

	public Food () {
		super();
	}

	public void draw() {
		noStroke();
		fill(0xffffff00, 125);
		ellipse(pos.x, pos.y, deccelerRange, deccelerRange);
		fill(0);
		ellipse(pos.x, pos.y, deccelerRange/2, deccelerRange/2);
	}

}
class Home extends Destination {

	public Home () {
		super();
	}

	public void draw() {
		noStroke();
		fill(0xfff46b42, 125);
		ellipse(pos.x, pos.y, deccelerRange, deccelerRange);
		fill(0);
		ellipse(pos.x, pos.y, deccelerRange/2, deccelerRange/2);
	}

}


class Vehicle {
	PVector pos;
	PVector vel;
	PVector acc;
	float vWidth = 20;
	float vHeight = 20;
	List <Home> homes;
	float maxSpeed = 5;
	float steeringStrength = 0.3f;
	PVector[] sensors = new PVector[3];

	Vehicle () {
		// pos = new PVector(width/2, height/2);
		pos = new PVector(random(vWidth, width - vWidth), random(vHeight, height - vHeight));
		vel = PVector.random2D().setMag(maxSpeed);
		acc = new PVector();
		for (int i = 0; i < sensors.length; ++i) {
			sensors[i] = new PVector();
		}
	}

	public void applyForce(PVector force) {
		acc.add(force);
	}

	public void mouseMovedTo(PVector mousePointerVector) {
		// if (pos.dist(mousePointerVector) < 100) {
		// 	applyForce(PVector.random2D().setMag(10));
		// }
	}

	public void deccelerateIfReached(PVector destPos) {
		if (destPos == null) {
			return;
		}

		float distanceFromDest = pos.dist(destPos);
		float stopRange = min(vHeight, vWidth);
		if (distanceFromDest < deccelerRange) {
			float speed = map(distanceFromDest, stopRange, deccelerRange, 0, maxSpeed);
			vel.setMag(speed);
		}
	}


	public PVector getNearestDest() {
		if (homes.size() == 0) {
			return null;
		}
		Destination nearest = homes.get(0);
		for (Destination d : homes) {
			if (pos.dist(d.pos) < pos.dist(nearest.pos)) {
				nearest = d;
			}
		}
		return nearest.pos.copy();
	}


	public void steer(PVector destPos) {
		if (destPos == null) {
			return;
		}
		PVector destHeading = destPos.copy().sub(pos).setMag(random(0, steeringStrength));
		vel.add(destHeading);
		vel.limit(maxSpeed);		
	}

	public void updateSensors() {
	}

	public void update() {
		pos.add(vel);
		vel.add(acc);
		acc.mult(0);
		PVector destPos = getNearestDest();
		steer(destPos);
		deccelerateIfReached(destPos);
		updateSensors();
	}

	public void drawBody() {
		stroke(0);
		strokeWeight(1);
		fill(0xffbbe5f7);
		triangle(-vWidth, 0, vWidth, 0, 0, vHeight);
		pushMatrix();
		translate(0, vHeight/2);
		rect(-vWidth/2, 0, vWidth, vHeight);
		pushMatrix();
		translate(0, vHeight);
		triangle(-vWidth/2, 0, vWidth/2, 0, 0, vHeight/2);
		popMatrix();
		popMatrix();
	}

	public void drawFlame() {
		fill(0xffffee00);		
		triangle(-vWidth, 0, vWidth, 0, 0, (-vel.mag()/maxSpeed) * vHeight);
		fill(0xffee6611);		
		triangle(-2*vWidth/3, 0, 2*vWidth/3, 0, 0, (-vel.mag()/maxSpeed) * (2*vHeight/3));
		fill(0xffee2211);		
		triangle(-vWidth/3, 0, vWidth/3, 0, 0, (-vel.mag()/maxSpeed) * (vHeight/3));
	}

	public void drawStrobes() {
		strokeWeight(5);
		if (frameCount%100 == 0 || frameCount%(100+5) == 0 && random(1) > 0) {
			stroke(255);
		} else {
			stroke(255, 0, 0);			
		}
		point(-vWidth, 0);
		if (frameCount%100 == 0 || frameCount%(100+5) == 0 && random(1) > 0) {
			stroke(255);
		} else {
			stroke(0, 255, 0);			
		}
		point(vWidth, 0);
	}

	public void drawSensors() {
		pushMatrix();
		translate(0, 2*vHeight);
		strokeWeight(1);
		stroke(255);

		float angleStep = HALF_PI/2;
		float angle = -angleStep;
		for (int i = 0; i < sensors.length; ++i) {
			pushMatrix();
			rotate(angle);
			line(0, 0, 0, vHeight);
			popMatrix();
			angle += angleStep;
		}
		
		stroke(0, 0, 255);
		line(0, 0, 0, vel.mag());
		popMatrix();
	}

	public void draw() {
		pushMatrix();				
		translate(pos.x, pos.y);
		rotate(vel.copy().rotate(HALF_PI).rotate(PI).heading());
		drawBody();
		drawFlame();
		drawStrobes();
		// drawSensors();
		popMatrix();
	}

}
  public void settings() { 	size(960, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Steering" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
