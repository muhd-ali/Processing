import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Ball {

  StateOfMotion[] som;
  float radius = 20;
  float[] fillColor = {
    random(0,255),
    random(0,255),
    random(0,255)
  };
  int id;

  Ball(float[] pos) {
    som = new StateOfMotion[] {
      new StateOfMotion(pos[0], radius, width, 0),
      new StateOfMotion(pos[1], radius, height, 1)
    };
  }
  
  Ball(int id_) {
    this(new float[] {random(0,width), random(0,height)});
    id = id_;
  }

  void draw() {
    stroke(255);
    strokeWeight(5);
    fill(fillColor[0], fillColor[1], fillColor[2]);
    ellipse(som[0].pos - radius/2, som[1].pos - radius/2, radius, radius);
  }
  
  float[] nextPos() {
    float[] nPos = new float[som.length];
    for (int i=0; i < nPos.length; i++) {
      nPos[i] = som[i].nextPos();
    }
    return nPos;
  }
  
  float distanceFrom(float[] p) {
    float distance = 0;
    for (int i=0; i<som.length; i++) {
      distance += Math.pow((som[i].pos - p[i]), 2);
    }
    
    return (float)(Math.sqrt(distance));
  }
  
  void checkForSurrounding(Ball[] balls) {
    List<Ball> surroundingBalls = Arrays.asList(balls);
    surroundingBalls.sort(new Comparator<Ball>(){
      public int compare(Ball b1, Ball b2) {
        int cmp;
        float param1 = b1.radius;
        float param2 = b2.radius;
        if (param1 > param2)
           cmp = +1;
        else if (param1 < param2)
           cmp = -1;
        else
           cmp = 0;
        return cmp;
      }
    });
    
    for (int i=0; i<surroundingBalls.size(); i++) {
      Ball other = surroundingBalls.get(i);
      if (other.id != this.id) {
        float[] otherNextPos = other.nextPos();
        if (distanceFrom(otherNextPos) < radius) {
          //adjustMotionForCollisionWith(other);
          line(som[0].pos - radius/2,som[1].pos - radius/2,otherNextPos[0]-other.radius/2,otherNextPos[1]-other.radius/2);
        }
      }
    }
  }
  
  void adjustMotionForCollisionWith(Ball ball) {
    for (int i=0; i<som.length; i++) {
      som[i].vel = -som[i].vel;
    }
  }

  void move() {
    for (StateOfMotion s : som) {
      s.move();
    }
  }
}
