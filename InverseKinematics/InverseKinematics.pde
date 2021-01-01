import java.util.List;

List<KinematicsBody> bodies;
KinematicsBody body;

void settings() {
	fullScreen();
}

PVector nextPosition = new PVector();
PVector lastPosition = new PVector();

void setup() {
	surface.setLocation(0, 0);
	background(0, 0, 0);
	colorMode(HSB);
	bodies = GenerateNKinematicsWorms(50);
}

void draw() {
	background(0, 0, 0);
	for (KinematicsBody b : bodies) {
		lastPosition.set(b.getHeadPosition().copy());
		nextPosition.set(lastPosition.x + random(-2, 2), lastPosition.y + random(-2, 2));
		b.draw();
		b.update(nextPosition);
	}
	text(String.valueOf((int)(frameRate)), 10, 25);
}
