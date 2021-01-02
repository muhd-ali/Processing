import java.util.List;

int width = 1280;
int height = 720;
float res = 0.05;

void settings() {
    size(width, height);
}

int numRandomParticles = 5;
List<Particle> randomParticles = new ArrayList<Particle>();
List<Particle> gridParticles = new ArrayList<Particle>();
SpringedBehavior springedBehavior = new SpringedBehavior();
ChargedBehavior chargedBehavior = new ChargedBehavior();
SteerableBehavior steerableBehavior = new SteerableBehavior();

Particle mouse = new Particle(new PVector(0, 0), 1000);
void setup() {
  frameRate(120);
  for(int i = 0; i < numRandomParticles; i++) {
    randomParticles.add(
      new Particle(
        new PVector(0,0),
        1000
      )
    );
  }

  for(int x = 0; x < width; x += 1/res) {
    for(int y = 0; y < height; y += 1/res) {
      gridParticles.add(
        new Particle(new PVector(x, y))
      );
    }
  }

}

void draw() {
  background(0);
  mouse.updateTargetPosition(new PVector(mouseX, mouseY));
  steerableBehavior.applyTo(mouse);
  mouse.update();
  mouse.draw();
  for(Particle p : randomParticles) {
    if (steerableBehavior.distanceToTargetFor(p) < 10) {
      p.updateTargetPosition(
        new PVector(
          random(width),
          random(height)
        )
      );
    }
    steerableBehavior.applyTo(p);
    p.update();
    p.draw();
  }
  for(Particle gp : gridParticles) {
    chargedBehavior.updateExternal(mouse);
    chargedBehavior.applyTo(gp);
    for(Particle rp: randomParticles) {
      chargedBehavior.updateExternal(rp);
      chargedBehavior.applyTo(gp);
    }
    springedBehavior.applyTo(gp);
    gp.update();
    gp.draw();
  }

  textSize(25);
  fill(0, 255, 0);
  text(String.valueOf((int)(frameRate)), 10, 25);
}
