class TreePiece {
  List<TreePieceSegment> segments = new ArrayList<TreePieceSegment>();
  int numSegments = 20;
  float growth = 0;
  
  TreePiece(PVector p1, PVector p2, int weight, int minWeight) {
    float segRatio = 1.0 / (float)numSegments;
    PVector delta = p1.copy().lerp(p2, segRatio).sub(p1).add(PVector.random2D());

    PVector tempP1 = p1.copy();
    for(int i = 0; i < numSegments; i++) {
      PVector currP1 = tempP1;
      //PVector currP2 = currP1.copy().add(delta.add(PVector.fromAngle(random(-HALF_PI, HALF_PI), new PVector(0,1)).mult(5)));
      PVector currP2 = currP1.copy().add(delta);
      int currDepth = (int) lerp(weight, weight / 2, (float)i / (float)numSegments);
      segments.add(
        new TreePieceSegment(currP1.copy(), currP2.copy(), currDepth)
      );
      tempP1 = currP2;
    }
  }
  
  void grow() {
    int count = 0;
    for(TreePieceSegment segment : segments) {
      segment.grow(0.75);
      count++;
      if (segment.growth < 1) {
        growth = (float)count / (float)segments.size();
        break;
      }
    }
  }

  void draw() {
    for(TreePieceSegment segment : segments) {
      segment.draw();
    }
  }
}
