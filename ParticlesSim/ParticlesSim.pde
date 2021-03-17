import java.util.List;

int width = 1280;
int height = 720;
float res = 0.01;

void settings() {
  size(width, height);
}

List<Particle> gridParticles = new ArrayList<Particle>();
SpringedBehavior springedBehavior = new SpringedBehavior();
ChargedBehavior chargedBehavior = new ChargedBehavior();
SteerableBehavior steerableBehavior = new SteerableBehavior();
RandomParticlesController rpc = new RandomParticlesController(0);
GridPointParticlesController gpc = new GridPointParticlesController();

Particle mouse = new ParticleBuilder()
 .currPos(new PVector(0, 0))
 .charge(10)
 .build();

void setup() {
  frameRate(120);
}

void draw() {
  background(0);
  mouse.updateTargetPosition(new PVector(mouseX, mouseY));
  steerableBehavior.applyTo(mouse);
  mouse.update();
  mouse.draw();
  rpc.draw();	
  gpc.draw();	
  gpc.update(rpc);	
  textSize(25);
  fill(0, 255, 0);
  text(String.valueOf((int)(frameRate)), 10, 25);
}
