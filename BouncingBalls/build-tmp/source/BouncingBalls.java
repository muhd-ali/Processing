import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Arrays; 
import java.util.Comparator; 
import java.util.List; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BouncingBalls extends PApplet {

Ball[] balls;

public void setup() {
  
  balls = new Ball[100];
  for (int i=0; i<balls.length; i++) {
    balls[i] = new Ball(i);
  }
  
}

public void draw() {
  background(0);
  for (int i=0; i<balls.length; i++) {
    balls[i].draw();
    balls[i].move();
    balls[i].checkForSurrounding(balls);
  }
}




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

  public void draw() {
    stroke(255);
    strokeWeight(5);
    fill(fillColor[0], fillColor[1], fillColor[2]);
    ellipse(som[0].pos - radius/2, som[1].pos - radius/2, radius, radius);
  }
  
  public float[] nextPos() {
    float[] nPos = new float[som.length];
    for (int i=0; i < nPos.length; i++) {
      nPos[i] = som[i].nextPos();
    }
    return nPos;
  }
  
  public float distanceFrom(float[] p) {
    float distance = 0;
    for (int i=0; i<som.length; i++) {
      distance += Math.pow((som[i].pos - p[i]), 2);
    }
    
    return (float)(Math.sqrt(distance));
  }
  
  public void checkForSurrounding(Ball[] balls) {
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
  
  public void adjustMotionForCollisionWith(Ball ball) {
    for (int i=0; i<som.length; i++) {
      som[i].vel = -som[i].vel;
    }
  }

  public void move() {
    for (StateOfMotion s : som) {
      s.move();
    }
  }
}
class StateOfMotion {
  float pos;
  float vel;
  float acc;
  float min;
  float max;
  float elas = 0.9f;

  StateOfMotion(float pos_, float min_, float max_, float acc_) {
    pos = pos_;
    vel = random(-10, 10);
    min = min_;
    max = max_;
    acc = acc_;
  }
  
  public float nextPos() {
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

  public void move() {
    pos = nextPos();
    if (pos == min || pos == max) {
      vel = -elas * vel;
    }
    vel += acc;
    vel = 0.995f * vel;
  }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BouncingBalls" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
