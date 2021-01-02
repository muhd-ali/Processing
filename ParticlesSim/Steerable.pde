interface Steerable extends Forced {
  PVector getPosition();
  PVector getTargetPosition();
  void updateTargetPosition(PVector targetPos);
}
