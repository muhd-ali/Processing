class Particle implements LiveDrawable, Moving, Charged, Springed, Steerable {
  PVector currPos, currVel = new PVector(0, 0);
  private PVector pivotPos, targetPos;
  float charge = 1, mass = 1, electricConstant = 100;
  PVector force = new PVector(0, 0);
  int i = 0;
  
  Particle(PVector currPos, PVector pivotPos, float charge) {
    this.currPos = currPos;
    this.charge = charge;
    this.pivotPos = pivotPos;
    if (pivotPos != null) {
      this.targetPos = pivotPos.copy();
    }
  }
  
  double getSpringConstant() {
    return 0.1;
  }
  
  PVector getPivotPosition() {
    if (pivotPos == null) {
      return currPos.copy();
    } else {
      return pivotPos.copy();
    }
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
    if (targetPos == null) {
      return currPos.copy();
    } else {
      return targetPos.copy();
    }
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
    stroke(255, 255, 255);
    int mode = 3;
    float col = (int)map(force.mag(), 0, 1, 255, 0);
    PVector normalized;
    switch(mode) {
      case 1:
      stroke(255, col, col);
      normalized = force.copy().mult(10).limit(50);
      line(currPos.x, currPos.y, currPos.x + normalized.x, currPos.y + normalized.y);
      break;
      case 2:
      point(currPos.x, currPos.y);
      break;
      case 3:
      stroke(255, col, col);
      line(getPivotPosition().x, getPivotPosition().y, currPos.x, currPos.y);
      break;
    }
  }
}
