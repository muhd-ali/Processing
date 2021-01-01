import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.List; 
import java.util.ArrayList; 
import java.util.concurrent.Executors; 
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.TimeUnit; 
import java.lang.reflect.Constructor; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class imageParticleEffects extends PApplet {

int mousePressCount = 0;
ImageParticlesGenerator gen;
PVector mousePointVector;
public void settings() {
	size(ImageParticlesGenerator.windowWidth, ImageParticlesGenerator.windowHeight);
}

public void setup() {
	surface.setLocation(0, 0);
	gen = new ImageParticlesGenerator("desktop1.jpg");
	background(0);
	gen.draw();
}

public void mousePressed() {
	mousePressCount++;
	mousePointVector = new PVector(mouseX, mouseY);
	gen.mousePressed();
}

public void draw() {
	background(0);
	gen.update();
	gen.draw();
	textSize(25);
	fill(0, 255, 0);
	text(String.valueOf((int)(frameRate)), 10, 25);
}
class ForceFromMousePointerBehaviour extends ImageParticleBehaviour {
	ForceFromMousePointerBehaviour(ImageParticle particle_) {
		super(particle_);
	}

	public void applyForceFromMousePointer() {
		PVector dir = particle.pos.copy().sub(mousePointVector);
		particle.applyForce(dir.setMag(5));
	}

	public void apply() {
		applyForceFromMousePointer();
	}

	public void enable_() {}
	public void disable_() {}
}
class GravityBehaviour extends ImageParticleBehaviour {
	ForceFromMousePointerBehaviour forceFromMousePointerBehaviour;
	GravityBehaviour(ImageParticle particle_) {
		super(particle_);
		forceFromMousePointerBehaviour = new ForceFromMousePointerBehaviour(particle_);
	}

	public void manageEdgeCollisions() {
		float elasticityFactor = 0.75f;
		if (particle.pos.y > (height - particle.pSize)) {
			particle.pos.y = (height - particle.pSize);
			particle.vel.y = - elasticityFactor * particle.vel.y;
		}
		if (particle.pos.x > (width - particle.pSize)) {
			particle.pos.x = (width - particle.pSize);
			particle.vel.x = - elasticityFactor * particle.vel.x;
		}
		if (particle.pos.x < (0 + particle.pSize)) {
			particle.pos.x = (0 + particle.pSize);
			particle.vel.x = - elasticityFactor * particle.vel.x;
		}
	}

	public void apply() {
		particle.applyForce(new PVector(0, 0.2f));
		manageEdgeCollisions();
		particle.vel.mult(0.995f);
	}

	public void enable_() {
		forceFromMousePointerBehaviour.enable();
		forceFromMousePointerBehaviour.apply();
	}
	public void disable_() {
		forceFromMousePointerBehaviour.disable();
	}
}



class ImageParticle {
	PVector pixelPos;
	PVector destPos;
	PVector pos;
	PVector vel = new PVector();
	PVector acc = new PVector();
	int pColor;
	int pSize;
	ImageParticleBehaviour navBehaviour;
	ImageParticleBehaviour alternateBehaviour;
	ImageParticleBehaviour mousePressBehaviour;

	ImageParticle(PVector pixelPos_, int color_) {
		pixelPos = pixelPos_;
		destPos = pixelPos.copy();
		pos = new PVector(random(0, width), random(0, height));
		pColor = color_;
		addNavigationBehaviour();
		addAlternateBehaviour();
		addMousePressBehaviour();
	}

	public void addNavigationBehaviour() {
		navBehaviour = new SteeringBehaviour(this);
		// navBehaviour = new SpringBehaviour(this);
		navBehaviour.enable();
	}

	public void addAlternateBehaviour() {
		alternateBehaviour = new GravityBehaviour(this);
		// alternateBehaviour = new RendezvousBehaviour(this);
	}

	public void addMousePressBehaviour() {
	}

	public void applyForce(PVector force) {
		acc.add(force);
	}

	public void toggleBehaviour() {
		ImageParticleBehaviour[] behaviours = {
			navBehaviour,
			alternateBehaviour
		};

		for (ImageParticleBehaviour behaviour : behaviours) {
			if (behaviour != null) {
				if (behaviour.isEnabled) {
					behaviour.disable();
				} else {
					behaviour.enable();
				}
			}
		}
	}

	public void mousePressed(ImageParticlesGenerator generator) {
		toggleBehaviour();
	}

	public void appyBehaviours() {
		ImageParticleBehaviour[] behaviours = {
			navBehaviour,
			alternateBehaviour
		};

		for (ImageParticleBehaviour behaviour : behaviours) {
			if (behaviour != null) {
				behaviour.applyIfEnabled();
			}
		}
	}

	public void update(ImageParticlesGenerator generator) {
		pos.add(vel);
		vel.add(acc);
		acc.mult(0);
		appyBehaviours();
	}

	public void draw(ImageParticlesGenerator generator) {
		pSize = generator.stepSize;
		noStroke();
		// stroke(255);
		// fill(pColor, random(100, 255));
		fill(pColor);
		ellipse(pos.x + pSize/2, pos.y + pSize/2, pSize, pSize);
	}
}
abstract class ImageParticleBehaviour {
	ImageParticle particle;
	boolean isEnabled = false;
	ImageParticleBehaviour(ImageParticle particle_) {
		particle = particle_;
	}

	public void applyIfEnabled() {
		if (isEnabled) {
			apply();
		}
	}

	public void enable() {
		enable_();
		isEnabled = true;
	}

	public void disable() {
		disable_();
		isEnabled = false;
	}

	protected abstract void apply();
	protected abstract void enable_();
	protected abstract void disable_();
}

abstract class NavigationBehaviour extends ImageParticleBehaviour {
	NavigationBehaviour(ImageParticle particle_) {
		super(particle_);
	}
}





class ImageParticlesGenerator {
	final static int windowWidth = (int)(1280);
	final static int windowHeight = (int)(720);
	int resolutionPercentage = 2;
	int stepSize;

	String filename;
	PImage img;
	ImageParticle[] particles;
	ExecutorService executor;
	ImageParticlesGenerator(String filename_) {
		filename = filename_;
		loadGeneratorWith(filename);
		resolutionPercentage = constrain(resolutionPercentage, 0, 100);
		stepSize = 100 / resolutionPercentage;
	}

	public void loadGeneratorWith(String filename_){
		img = loadImage(filename_);
		img.resize(windowWidth, windowHeight);
		img.loadPixels();
		particles = new ImageParticle[img.pixels.length];
		for (int y = 0; y < windowHeight; ++y) {
			for (int x = 0; x < windowWidth; ++x) {
				int index = y*windowWidth + x;
				PVector pos = new PVector(x, y);
				particles[index] = new ImageParticle(pos, img.pixels[index]);
			}
		}
	}

	public void executeTask(ParticleTask task) {
		task.setState(0, 1, particles, this);
		task.run();
	}

	public void draw() {
		// loadPixels();
		executeTask(new DrawTask());
		// updatePixels();
	}

	public void update() {
		executeTask(new UpdateTask());
	}

	public void mousePressed() {
		executeTask(new MousePressed());
	}

	abstract class ParticleTask implements Runnable {
		int start, numberOfThreads;
		ImageParticle[] particles;
		ImageParticlesGenerator generator;

		public void setState(int start_, int numberOfThreads_, ImageParticle[] particles_, ImageParticlesGenerator generator_) {
			particles = particles_;
			start = start_;
			numberOfThreads = numberOfThreads_;
			generator = generator_;
		}

		public void run() {
			for (int x = 0; x < width; x+=stepSize) {
				for (int y = 0; y < height; y+=stepSize) {
					int index = y * width + x;
					if (index >= 0 && index < particles.length) {
						task(particles[index], generator);
					}
				}
			}
		}

		public abstract void task(ImageParticle particle, ImageParticlesGenerator generator);
	}

	class DrawTask extends ParticleTask {
		public void task(ImageParticle particle, ImageParticlesGenerator generator) {
			particle.draw(generator);
		}
	}
	class UpdateTask extends ParticleTask {
		public void task(ImageParticle particle, ImageParticlesGenerator generator) {
			particle.update(generator);
		}
	}
	class MousePressed extends ParticleTask {
		public void task(ImageParticle particle, ImageParticlesGenerator generator) {
			particle.mousePressed(generator);
		}
	}
}
class RendezvousBehaviour extends ImageParticleBehaviour {
	SteeringBehaviour steeringBehaviour;
	
	RendezvousBehaviour(ImageParticle particle_) {
		super(particle_);
		steeringBehaviour = new SteeringBehaviour(particle_);
	}

	public void apply() {
		steeringBehaviour.applyIfEnabled();
	}

	public void enable_() {
		particle.destPos = mousePointVector.copy();
		steeringBehaviour.enable();
	}

	public void disable_() {
		steeringBehaviour.disable();
		particle.destPos = particle.pixelPos.copy();
	}
}
class SpringBehaviour extends NavigationBehaviour {
	SpringBehaviour(ImageParticle particle_) {
		super(particle_);
	}

	public PVector getHingePoint() {
		return particle.pixelPos.copy();
	}

	public PVector getSpringForce() {
		PVector pos = particle.pos.copy();
		PVector hingePoint = getHingePoint();
		float k = random(0.0025f, 0.0075f);
		float b = 0.025f;
		PVector displacement = pos.sub(hingePoint);
		return displacement.copy().mult(-k).sub(particle.vel.copy().mult(b));
	}

	public void applyDampening() {
		particle.vel.mult(0.9f);
	}

	public void apply() {
		particle.applyForce(getSpringForce());
	}

	public void enable_() {

	}

	public void disable_() {

	}
}
class SteeringBehaviour extends NavigationBehaviour {
	float steeringStrength = 0.3f;
	float maxSpeed;
	float deccelerRange;

	SteeringBehaviour(ImageParticle particle_) {
		super(particle_);
	}

	public void steer(PVector destPos) {
		if (destPos == null) {
			return;
		}
		PVector destHeading = destPos.copy().sub(particle.pos).setMag(steeringStrength);
		particle.vel.add(destHeading);
		particle.vel.limit(maxSpeed);		
	}

	public void deccelerateIfReached(PVector destPos) {
		if (destPos == null) {
			return;
		}

		float distanceFromDest = particle.pos.dist(destPos);
		float stopRange = 0;
		if (distanceFromDest < deccelerRange) {
			float speed = map(distanceFromDest, stopRange, deccelerRange, 0, maxSpeed);
			particle.vel.setMag(speed);
		}
	}

	public void apply() {
		float deccelerRangeMin = min(width, height) / 2;
		float deccelerRangeMax = sqrt(sq(width)+sq(height)) / 2;
		deccelerRange = random(deccelerRangeMin, deccelerRangeMax);
		maxSpeed = random(5, 10);
		if (particle == null) {
			println("what???");
		}
		steer(particle.destPos);
		deccelerateIfReached(particle.destPos);
	}

	public void enable_() {}
	public void disable_() {}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "imageParticleEffects" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
