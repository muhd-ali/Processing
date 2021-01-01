import java.util.List;

int width = 1280*2;
int height = 720*2;
float res = 0.01;

void settings() {
    size(width, height);
}

List<Particle> ps = new ArrayList<Particle>();
Particle mouse = new Particle(new PVector(0, 0), 100000);
void setup() {
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
    p.updateChargedForce(mouse);
  }
  for(Particle p : ps) {
    p.draw();
    p.update();
  }
  mouse.draw();
}
