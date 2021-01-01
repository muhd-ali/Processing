class Particle implements LiveDrawable, Moving, Charged, Springed, Steerable {
  PVector currPos, currVel = new PVector(0, 0);
  PVector pivotPos;
  float charge = 1, mass = 1, electricConstant = 100;
  PVector force = new PVector(0, 0);
  int i = 0;
  SpringForceCalculator sCalc;
  ChargeForceCalculator cCalc;

  
  Particle(PVector pos) {
    pivotPos = pos.copy();
    currPos = pos;
    this.sCalc = new SpringForceCalculator();
    this.cCalc = new ChargeForceCalculator();
  }

  Particle(PVector pos, float charge) {
    this(pos);
    this.charge = charge;
  }

  void moveTowards(PVector point) {

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

  float getElectricConstant() {
    return electricConstant;
  }

  void addForce(PVector f) {
    force.add(f);
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
  }
  
  void draw() {
    stroke(255,255,255,255);
    strokeWeight(5);
    int mode = 2;
    switch(mode) {
      case 1:
        PVector forceCopy = force.copy().mult(10);
        line(currPos.x, currPos.y, currPos.x + forceCopy.x, currPos.y + forceCopy.y);
      case 2:
        point(currPos.x, currPos.y);
    }
  }
}
