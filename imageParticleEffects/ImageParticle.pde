import java.util.List;
import java.util.ArrayList;

class ImageParticle {
	PVector pixelPos;
	PVector destPos;
	PVector pos;
	PVector vel = new PVector();
	PVector acc = new PVector();
	color pColor;
	int pSize;
	ImageParticleBehaviour navBehaviour;
	ImageParticleBehaviour alternateBehaviour;

	ImageParticle(PVector pixelPos_, color color_, BehaviourGenerator navBehaviour, BehaviourGenerator alternateBehaviour) {
		pixelPos = pixelPos_;
		destPos = pixelPos.copy();
		pos = new PVector(random(0, width), random(0, height));
		pColor = color_;
		this.navBehaviour = navBehaviour.get(this);
		this.navBehaviour.enable();
		this.alternateBehaviour = alternateBehaviour.get(this);
	}

	// void addNavigationBehaviour() {
	// 	navBehaviour = new SteeringBehaviour(this);
	// 	 //navBehaviour = new SpringBehaviour(this);
	// 	navBehaviour.enable();
	// }

	// void addAlternateBehaviour() {
	// 	//alternateBehaviour = new GravityBehaviour(this);
	// 	 alternateBehaviour = new RendezvousBehaviour(this);
	// }

	void applyForce(PVector force) {
		acc.add(force);
	}

	void toggleBehaviour() {
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

	void mousePressed(ImageParticlesGenerator generator) {
		toggleBehaviour();
	}

	void appyBehaviours() {
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

	void update(ImageParticlesGenerator generator) {
		pos.add(vel);
		vel.add(acc);
		acc.mult(0);
		appyBehaviours();
	}

	void draw(ImageParticlesGenerator generator) {
		pSize = generator.stepSize;
		noStroke();
		fill(pColor);
		ellipse(pos.x + pSize/2, pos.y + pSize/2, pSize, pSize);
	}
}
