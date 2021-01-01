import java.util.List;

int width = 1280;
int height = 720;
float res = 0.05;

void settings() {
    size(width, height);
}

List<Particle> ps = new ArrayList<Particle>();
SpringBehavior springBehavior = new SpringBehavior();
ChargedBehavior chargedBehavior = new ChargedBehavior();

Particle mouse = new Particle(new PVector(0, 0), 1000);
void setup() {
  frameRate(200);
  for(int x = 0; x < width; x += 1/res) {
    for(int y = 0; y < height; y += 1/res) {
      ps.add(
        new Particle(new PVector(x, y))
      );
    }
  }
}

void draw() {
  background(0);
  mouse.currPos.set(mouseX, mouseY);
  for(Particle p : ps) {
    chargedBehavior.updateExternal(mouse);
    chargedBehavior.applyTo(p);
    springBehavior.applyTo(p);
    p.draw();
    p.update();
  }
  mouse.draw();

  textSize(25);
  fill(0, 255, 0);
  text(String.valueOf((int)(frameRate)), 10, 25);
}
