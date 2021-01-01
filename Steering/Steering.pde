import java.util.List;
import java.util.ArrayList;

Vehicle[] vehicles = new Vehicle[100];
List <Home> homes = new ArrayList<Home>();
PVector mousePointerVector = new PVector();
float deccelerRange = 50;

void setup() {
	fullScreen();
	surface.setLocation(0, 0);
	for (int i = 0; i < 5; ++i) {
		homes.add(new Home());
	}
	for (int i = 0; i < vehicles.length; ++i) {
		vehicles[i] = new Vehicle();
		vehicles[i].homes = homes;
	}
}

void draw() {
	background(0, 0.01);
	for (int i = 0; i < homes.size(); ++i) {
		homes.get(i).draw();
	}
	for (int i = 0; i < vehicles.length; ++i) {
		vehicles[i].draw();
		vehicles[i].update();
		// vehicles[i].destPos = homes.pos;
	}
}

void mousePressed() {
	for (int i = 0; i < homes.size(); ++i) {
		homes.get(i).mousePressedAt(mousePointerVector);
	}
}

void mouseMoved() {
	mousePointerVector.set(mouseX, mouseY);
	for (int i = 0; i < homes.size(); ++i) {
		homes.get(i).mouseMovedTo(mousePointerVector);
	}
	for (int i = 0; i < vehicles.length; ++i) {
		vehicles[i].mouseMovedTo(mousePointerVector);
	}
}
