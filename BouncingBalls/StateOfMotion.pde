class StateOfMotion {
  float pos;
  float vel;
  float acc;
  float min;
  float max;
  float elas = 0.9;

  StateOfMotion(float pos_, float min_, float max_, float acc_) {
    pos = pos_;
    vel = random(-10, 10);
    min = min_;
    max = max_;
    acc = acc_;
  }
  
  float nextPos() {
    float nPos;
    if ((pos + vel) < min) {
      nPos = min;
    } else if ((pos + vel) > max) {
      nPos = max;
    } else {
      nPos = pos + vel;
    }
    return nPos;
  }

  void move() {
    pos = nextPos();
    if (pos == min || pos == max) {
      vel = -elas * vel;
    }
    vel += acc;
    vel = 0.995 * vel;
  }
}
