import java.util.List;

class Vehicle {
	PVector pos;
	PVector vel;
	PVector acc;
	float vWidth = 20;
	float vHeight = 20;
	List <Home> homes;
	float maxSpeed = 5;
	float steeringStrength = 0.3;
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

	void applyForce(PVector force) {
		acc.add(force);
	}

	void mouseMovedTo(PVector mousePointerVector) {
		// if (pos.dist(mousePointerVector) < 100) {
		// 	applyForce(PVector.random2D().setMag(10));
		// }
	}

	void deccelerateIfReached(PVector destPos) {
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


	PVector getNearestDest() {
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


	void steer(PVector destPos) {
		if (destPos == null) {
			return;
		}
		PVector destHeading = destPos.copy().sub(pos).setMag(random(0, steeringStrength));
		vel.add(destHeading);
		vel.limit(maxSpeed);		
	}

	void updateSensors() {
	}

	void update() {
		pos.add(vel);
		vel.add(acc);
		acc.mult(0);
		PVector destPos = getNearestDest();
		steer(destPos);
		deccelerateIfReached(destPos);
		updateSensors();
	}

	void drawBody() {
		stroke(0);
		strokeWeight(1);
		fill(#bbe5f7);
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

	void drawFlame() {
		fill(#ffee00);		
		triangle(-vWidth, 0, vWidth, 0, 0, (-vel.mag()/maxSpeed) * vHeight);
		fill(#ee6611);		
		triangle(-2*vWidth/3, 0, 2*vWidth/3, 0, 0, (-vel.mag()/maxSpeed) * (2*vHeight/3));
		fill(#ee2211);		
		triangle(-vWidth/3, 0, vWidth/3, 0, 0, (-vel.mag()/maxSpeed) * (vHeight/3));
	}

	void drawStrobes() {
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

	void drawSensors() {
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

	void draw() {
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