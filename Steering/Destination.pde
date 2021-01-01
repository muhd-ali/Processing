abstract class Destination {
	PVector pos;
	boolean followMouse = false;


	Destination() {
		pos = new PVector(
			random((deccelerRange / 2), width - (deccelerRange / 2)),
			random((deccelerRange / 2), height - (deccelerRange / 2))
			);
	}

	abstract void draw();

	void mousePressedAt(PVector mousePointerVector) {
		if (pos.dist(mousePointerVector) < deccelerRange/2) {
			followMouse = !followMouse;
			pos.set(mousePointerVector);
			if (pos.x > width - (deccelerRange / 2)) {
				pos.x = width - (deccelerRange / 2);
			}
			if (pos.y > height - (deccelerRange / 2)) {
				pos.y = height - (deccelerRange / 2);
			}
		}
	}

	void mouseMovedTo(PVector mousePointerVector) {
		if (followMouse) {
			if (mousePointerVector.x < width - (deccelerRange / 2)) {
				pos.x = mousePointerVector.x;
			}

			if (mousePointerVector.y < 1000 - (deccelerRange / 2)) {
				pos.y = mousePointerVector.y;
			}
		}
	}
}