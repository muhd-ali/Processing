class TreePieceSegment {
  PVector p1, p2;
  int weight;
  float growth = 0;
  
  TreePieceSegment(PVector p1, PVector p2, int weight) {
    this.p1 = p1;
    this.p2 = p2;
    this.weight = weight;
  }
  
  void grow(float delta) {
    growth = Math.min(1, growth + delta);
  }

  void draw() {
    strokeWeight(Math.max(0, weight * growth));
    PVector endPoint = p1.copy().lerp(p2, growth);
    line(p1.x, p1.y, endPoint.x, endPoint.y);
  }
  
  public String toString() {
    return String.format("%s, %s, %d", p1, p2, weight);
  }
}
