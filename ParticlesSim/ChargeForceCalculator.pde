class ChargeForceCalculator {
  PVector calculateFor(Charged particle, Charged from) {
    float dist = particle.getPosition().dist(from.getPosition());
    double eForceMag = (particle.getElectricConstant() * Math.abs(particle.getCharge()) * Math.abs(from.getCharge())) / (dist * dist);
    PVector eForce = particle.getPosition().copy().sub(from.getPosition());
    if (particle.getCharge() * from.getCharge() < 0) {
      eForce.mult(-1);
    }
    return eForce.setMag((float) eForceMag);
  }
}
