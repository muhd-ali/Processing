class TreeBranch {
  TreePiece piece;
  int numBrancesPerSegment = 4;
  float growth = 0;
  
  List<TreeBranch> treeBranches = new ArrayList<TreeBranch>();
  
  TreeBranch(PVector p1, PVector p2, int weight, int minWeight) {
    piece = new TreePiece(p1, p2, weight, minWeight);
    int count = 0;
    for(TreePieceSegment segment : piece.segments) {
      for(int j = 0; j < numBrancesPerSegment + exp(-weight); j++) {
        if (weight > minWeight) {
          if (count > piece.numSegments / 2) {
              PVector innerP1 = segment.p1.copy().lerp(segment.p2, 0.5);
              PVector segmentVector = segment.p2.copy().sub(segment.p1);
              PVector innerP2 = segmentVector.copy().normalize().rotate(random(-HALF_PI, HALF_PI)).mult(random(0, 0.5) * p2.copy().sub(p1).mag()).add(innerP1);
              treeBranches.add(
                new TreeBranch(
                  innerP1,
                  innerP2,
                  segment.weight / 2,
                  minWeight
                )
              );
            }
        }
      }
      
      count++;
    }
  }
  
  void grow() {
    piece.grow();
    if (piece.growth == 1.0) {
      growth = 0.5;
      int innerpiecesGrowth = 0;
      for(TreeBranch innerBranch : treeBranches) {
        innerBranch.grow();
        innerpiecesGrowth += innerBranch.growth / treeBranches.size();
      }
      growth += 0.5 * innerpiecesGrowth;
    }
  }

  void draw() {
    piece.draw();
    for(TreeBranch innerPiece : treeBranches) {
      innerPiece.draw();
    }
  }
}
