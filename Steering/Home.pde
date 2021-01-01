class Home extends Destination {

	public Home () {
		super();
	}

	void draw() {
		noStroke();
		fill(#f46b42, 125);
		ellipse(pos.x, pos.y, deccelerRange, deccelerRange);
		fill(0);
		ellipse(pos.x, pos.y, deccelerRange/2, deccelerRange/2);
	}

}