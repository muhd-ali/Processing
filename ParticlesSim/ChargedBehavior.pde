class ChargedBehavior extends Behavior<Charged> {
  Charged external;

  public void updateExternal(Charged external) {
    this.external = external;
  }

  void applyTo(Charged particle) {
    float dist = particle.getPosition().dist(external.getPosition());
    double forceMag = (particle.getElectricConstant() * Math.abs(particle.getCharge()) * Math.abs(external.getCharge())) / (dist * dist);
    PVector force = particle.getPosition().copy().sub(external.getPosition());
    if (particle.getCharge() * external.getCharge() < 0) {
      force.mult(-1);
    }
    force = force.setMag((float) forceMag);
    particle.addForce(force);
  }
}
