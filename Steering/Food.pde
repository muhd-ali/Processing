public class Food extends Destination {

	public Food () {
		super();
	}

	void draw() {
		noStroke();
		fill(#ffff00, 125);
		ellipse(pos.x, pos.y, deccelerRange, deccelerRange);
		fill(0);
		ellipse(pos.x, pos.y, deccelerRange/2, deccelerRange/2);
	}

}