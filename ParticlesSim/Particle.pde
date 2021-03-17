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
  
  void setLine(PVector pivotPos, PVector currPos) {
    this.currPos = currPos.copy();
    this.pivotPos = pivotPos.copy();
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
    currVel.mult(0.1); // damping effect
  }
  
  void update() {
    updatePosition();
  }
  
  void draw() {
    strokeWeight(10);
    stroke(255, 255, 255);
    int mode = 8;
    float col1 = 255, col2 = 255, col3 = 255, col4 = 255;
    col2 = (int)map(getPivotPosition().x, 0, width, 255, 0);
    col3 = (int)map(getPivotPosition().y, 0, height, 255, 0);
    PVector vector, vector1;
    switch(mode) {
      case 1:
      col1 = (int)map(force.mag(), 0, 1, 255, 0);
      stroke(col1, col2, col3);
      vector = force.copy().mult(10).limit(50);
      line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
      break;
      case 2:
      point(currPos.x, currPos.y);
      break;
      case 3:
      vector = currPos.copy().sub(getPivotPosition());
      col1 = (int)map(vector.heading(), 0, 2 * PI, 255, 0);
      col4 = (int)map(vector.mag(), 0, 50, 75, 150);
      stroke(col1, col2, col3, col4);
      line(getPivotPosition().x, getPivotPosition().y, currPos.x, currPos.y);
      break;
      case 4:
      vector = currPos.copy().sub(getPivotPosition());
      col1 = (int)map(vector.heading(), 0, 2 * PI, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
      vector1 = force.copy().mult(10).limit(50);
      col2 = (int)map(force.mag(), 0, 1, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
      col4 = (int)map(vector.mag(), 0, 50, 75, 150);
      stroke(col1, col2, col3, col4);
      line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x, currPos.y + vector1.y);      
      break;
      case 5:
      vector = currPos.copy().sub(getPivotPosition());
      col1 = (int)map(vector.heading(), 0, 2 * PI, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
      vector1 = vector.copy().rotate(PI / 3);
      col2 = (int)map(force.mag(), 0, 1, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
      col4 = (int)map(vector.mag(), 0, 50, 75, 150);
      stroke(col1, col2, col3, col4);
      line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x, currPos.y + vector1.y);      
      break;
      case 6:
      vector = force.copy().mult(10).limit(50);
      col1 = (int)map(vector.heading(), 0, 2 * PI, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
      vector1 = vector.copy().rotate(PI / 3);
      col2 = (int)map(force.mag(), 0, 1, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
      col4 = (int)map(vector.mag(), 0, 50, 75, 150);
      stroke(col1, col2, col3, col4);
      line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x, currPos.y + vector1.y);      
      break;
      case 7:
      vector = currPos.copy().sub(getPivotPosition()).setMag(30);
      col1 = (int)map(vector.heading(), 0, 2 * PI, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
      vector1 = vector.copy().rotate(PI / 3);
      col2 = (int)map(force.mag(), 0, 1, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
      col4 = (int)map(vector.mag(), 0, 50, 75, 150);
      stroke(col1, col2, col3, col4);
      line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x, currPos.y + vector1.y);      
      break;
      case 8:
      vector = force.copy().setMag(30);
      col1 = (int)map(vector.heading(), 0, 2 * PI, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector.x, currPos.y + vector.y);
      vector1 = vector.copy().rotate(PI / 3);
      col2 = (int)map(force.mag(), 0, 1, 255, 0);
      stroke(col1, col2, col3, col4);
      line(currPos.x, currPos.y, currPos.x + vector1.x, currPos.y + vector1.y);
      col4 = (int)map(vector.mag(), 0, 50, 75, 150);
      stroke(col1, col2, col3, col4);
      line(currPos.x + vector.x, currPos.y + vector.y, currPos.x + vector1.x, currPos.y + vector1.y);      
      break;
    }
  }
}
