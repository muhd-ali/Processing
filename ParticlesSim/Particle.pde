class Particle implements LiveDrawable, Moving, Charged, Springed {
  PVector currPos, currVel = new PVector(0, 0);
  PVector pivotPos;
  float charge = 1, mass = 1, electric_constant = 1;
  PVector force = new PVector(0, 0);
  int i = 0;

  
  Particle(PVector pos) {
    pivotPos = pos.copy();
    currPos = pos;
  }

  Particle(PVector pos, float charge) {
    this(pos);
    this.charge = charge;
  }
  
  double getSpringConstant() {
    return 0.1;
  }
  
  void updateSpringForce() {
    float dist = currPos.dist(pivotPos);
    double sForceMag = -dist * getSpringConstant();
    PVector sForce = currPos.copy().sub(pivotPos);
    sForce = sForce.setMag((float) sForceMag);
    force.add(sForce);
  }

  double getCharge() {
    return charge;
  }

  PVector getPosition() {
    return currPos.copy();
  }

  void updateChargedForce(Charged c) {
    float dist = currPos.dist(c.getPosition());
    double eForceMag = (electric_constant * Math.abs(charge) * Math.abs(c.getCharge())) / (dist * dist);
    PVector eForce = currPos.copy().sub(c.getPosition());
    if (charge * c.getCharge() < 0) {
      eForce.mult(-1);
    }
    eForce = eForce.setMag((float) eForceMag);
    force.add(eForce);
  }
  
  void updatePosition() {
    PVector accel = force.div(mass);
    currVel.add(accel);
    currPos.add(currVel);
    force = new PVector(0,0);
    currVel.mult(0.05);
  }
  
  void update() {
    updatePosition();
    updateSpringForce();
  }
  
  void draw() {
    stroke(255,255,255,255);
    strokeWeight(10);
    point(currPos.x, currPos.y);
  }
}
