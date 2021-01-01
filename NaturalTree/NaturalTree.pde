import java.util.*;

List<Tree> trees = new ArrayList<Tree>();
int NUMBER_OF_TREES = 1;

void setup() {
  //size(720, 480);
  fullScreen();
  for(int i = 0; i < NUMBER_OF_TREES; i++) {
      trees.add(
        new Tree(
          new PVector(width / 2, height),
          //new PVector(random(0, width), height),
          (int)(0.75 * height)
          //(int)random(0, height)
        )
      );
  }
  //noLoop();  // draw() will not loop
}

void draw() {
  background(204);
  for(Tree tree : trees) {
    tree.grow();
    tree.draw();
  }
}

//void mousePressed() {
//  loop();  // Holding down the mouse activates looping
//}

//void mouseReleased() {
//  noLoop();  // Releasing the mouse stops looping draw()
//}
