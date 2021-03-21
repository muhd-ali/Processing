import java.util.List;

float res = 0.02;
List<Particle> gridParticles;
SpringedBehavior springedBehavior;
ChargedBehavior chargedBehavior;
SteerableBehavior steerableBehavior;
RandomParticlesController rpc;
GridPointParticlesController gpc;


void settings() {
  fullScreen();
}


Particle mouse = new ParticleBuilder()
 .currPos(new PVector(0, 0))
 .charge(500)
 .build();

void setup() {
  frameRate(120);
  gridParticles = new ArrayList<Particle>();
  springedBehavior = new SpringedBehavior();
  chargedBehavior = new ChargedBehavior();
  steerableBehavior = new SteerableBehavior();
  rpc = new RandomParticlesController(0);
  gpc = new GridPointParticlesController();
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
