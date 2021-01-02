class Particle implements LiveDrawable, Moving, Charged, Springed, Steerable {
	PVector currPos, currVel = new PVector(0, 0);
	PVector pivotPos, targetPos;
	float charge = 1, mass = 1, electricConstant = 100;
	PVector force = new PVector(0, 0);
	int i = 0;
	
	
	Particle(PVector pos) {
		pivotPos = pos.copy();
		targetPos = pos.copy();
		currPos = pos;
	}
	
	Particle(PVector pos, float charge) {
		this(pos);
		this.charge = charge;
	}
	
	double getSpringConstant() {
		return 0.1;
	}
	
	PVector getPivotPosition() {
		return pivotPos;
	}
	
	PVector getMassPosition() {
		return currPos;
	}
	
	double getCharge() {
		return charge;
	}
	
	PVector getPosition() {
		return currPos.copy();
	}
	
	PVector getVelocity() {
		return currVel.copy();
	}
	
	float getElectricConstant() {
		return electricConstant;
	}
	
	PVector getTargetPosition() {
		return targetPos.copy();
	}
	
	void updateTargetPosition(PVector targetPos) {
		this.targetPos = targetPos;
	}
	
	void addForce(PVector f) {
		force.add(f);
	}
	
	void addVelocity(PVector vel) {
		currVel.add(vel);
	}
	
	void updatePosition() {
		PVector accel = force.div(mass);
		currVel.add(accel);
		currPos.add(currVel);
		force = new PVector(0,0);
		currVel.mult(0.05); // damping effect
	}
	
	void update() {
		updatePosition();
	}
	
	void draw() {
		strokeWeight(5);
		float num = (int)map(force.mag(), 0, 1, 255, 0);
		stroke(255, num, num);
		int mode = 1;
		switch(mode) {
			case 1:
			PVector forceCopy = force.copy().mult(10).limit(50);
			line(currPos.x, currPos.y, currPos.x + forceCopy.x, currPos.y + forceCopy.y);
			case 2:
			point(currPos.x, currPos.y);
		}
	}
}
