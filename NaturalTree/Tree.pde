class Tree {
  TreeBranch stem;
  
  Tree(PVector pos, int treeHeight) {
    int startWeight = 40;
    int minWeight = 10;
    stem = new TreeBranch(
      pos.copy(),
      pos.copy().sub(0,treeHeight),
      startWeight,
      minWeight
    );
  }
  
  void grow() {
    stem.grow();
  }
  
  void draw() {
    stem.draw();
  }
}
