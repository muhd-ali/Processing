Ball[] balls;

void setup() {
  size(1280, 720);
  balls = new Ball[100];
  for (int i=0; i<balls.length; i++) {
    balls[i] = new Ball(i);
  }
  
}

void draw() {
  background(0);
  for (int i=0; i<balls.length; i++) {
    balls[i].draw();
    balls[i].move();
    balls[i].checkForSurrounding(balls);
  }
}
