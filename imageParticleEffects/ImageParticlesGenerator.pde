import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Constructor;

class ImageParticlesGenerator {
	final static int windowWidth = (int)(1280);
	final static int windowHeight = (int)(720);
	int stepSize;
	BehaviourGenerator navBehaviour;
	BehaviourGenerator alternateBehaviour;

	String filename;
	PImage img;
	ImageParticle[] particles;
	ExecutorService executor;
	ImageParticlesGenerator(String filename_, int resolutionPercentage, BehaviourGenerator navBehaviour, BehaviourGenerator alternateBehaviour) {
		filename = filename_;
		this.navBehaviour = navBehaviour;
		this.alternateBehaviour = alternateBehaviour;
		loadGeneratorWith(filename);
		resolutionPercentage = constrain(resolutionPercentage, 0, 100);
		stepSize = 100 / resolutionPercentage;
	}

	void loadGeneratorWith(String filename_){
		img = loadImage(filename_);
		img.resize(windowWidth, windowHeight);
		img.loadPixels();
		particles = new ImageParticle[img.pixels.length];
		for (int y = 0; y < windowHeight; ++y) {
			for (int x = 0; x < windowWidth; ++x) {
				int index = y*windowWidth + x;
				PVector pos = new PVector(x, y);
				particles[index] = new ImageParticle(
					pos,
					img.pixels[index],
					navBehaviour,
					alternateBehaviour
				);
			}
		}
	}

	void executeTask(ParticleTask task) {
		task.setState(this);
		task.run();
	}

	void draw() {
		executeTask(new DrawTask());
	}

	void update() {
		executeTask(new UpdateTask());
	}

	void mousePressed() {
		executeTask(new MousePressed());
	}

	abstract class ParticleTask implements Runnable {
		ImageParticlesGenerator generator;

		void setState(ImageParticlesGenerator generator_) {
			generator = generator_;
		}

		void run() {
			for (int x = 0; x < width; x+=stepSize) {
				for (int y = 0; y < height; y+=stepSize) {
					int index = y * width + x;
					if (index >= 0 && index < particles.length) {
						task(particles[index], generator);
					}
				}
			}
		}

		abstract void task(ImageParticle particle, ImageParticlesGenerator generator);
	}

	class DrawTask extends ParticleTask {
		void task(ImageParticle particle, ImageParticlesGenerator generator) {
			particle.draw(generator);
		}
	}
	class UpdateTask extends ParticleTask {
		void task(ImageParticle particle, ImageParticlesGenerator generator) {
			particle.update(generator);
		}
	}
	class MousePressed extends ParticleTask {
		void task(ImageParticle particle, ImageParticlesGenerator generator) {
			particle.mousePressed(generator);
		}
	}
}
