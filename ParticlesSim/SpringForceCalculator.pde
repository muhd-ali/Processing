class SpringForceCalculator {
  PVector calculateFor(Springed system) {
    float dist = system.getMassPosition().dist(system.getPivotPosition());
    double sForceMag = -dist * system.getSpringConstant();
    PVector sForce = system.getMassPosition().copy().sub(system.getPivotPosition());
    return sForce.setMag((float) sForceMag);
  }
}
