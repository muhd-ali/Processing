class KinematicsPart {
	PVector startPoint;
	PVector endPoint;
	float pLength;
	float pAngle_deg;
	float pWidth;
	color pColor;
	boolean shouldDrawEndPoints;

	KinematicsPart(
		PVector startPoint_,
		boolean shouldDrawEndPoints_,
		float pLength_,
		float pWidth_
	) {
		startPoint = startPoint_.copy();
		pAngle_deg = random(0, 360);
		shouldDrawEndPoints = shouldDrawEndPoints_;
		pLength = pLength_;
		pWidth = pWidth_;
		setup();
	}

	void setupColor() {
		pColor = color(255, 255, 255);
	}

	PVector getEndPointFromPoint(PVector point_) {
		return new PVector(
			cos(radians(pAngle_deg)),
			sin(radians(pAngle_deg))
		).mult(pLength).add(point_);
	}

	PVector getStartPointFromPoint(PVector point_) {
		return point_.copy().sub(
			new PVector(
				cos(radians(pAngle_deg)),
				sin(radians(pAngle_deg))
			).mult(pLength)
		);
	}

	void setPointsFromStartPoint(PVector startPoint_) {
		startPoint = startPoint_;
		endPoint = getEndPointFromPoint(startPoint_);
	}

	void setPointsFromEndPoint(PVector endPoint_) {
		endPoint = endPoint_;
		startPoint = getStartPointFromPoint(endPoint_);
	}

	void setup() {
		setupColor();
		setPointsFromStartPoint(startPoint);
	}

	float getAngleForEndPointPointingTo(PVector point_) {
		PVector dir = point_.copy().sub(startPoint);
		return degrees(dir.heading());
	}

	void setAngleForEndPointPointingTo(PVector point_) {
		pAngle_deg = getAngleForEndPointPointingTo(point_);
	}

	void setEndPointPointingToPoint(PVector point_) {
		setAngleForEndPointPointingTo(point_);
		setPointsFromStartPoint(startPoint);
	}

	void makeEndPointToApproach(PVector point_) {
		setEndPointPointingToPoint(point_);
		setPointsFromEndPoint(point_);
	}

	void swapEndPoints() {
		PVector temp = startPoint;
		startPoint = endPoint;
		endPoint = temp;
	}

	void makeStartPointToApproach(PVector point_) {
		swapEndPoints();
		makeEndPointToApproach(point_);
		swapEndPoints();
	}

	void update() {

	}

	void draw() {
		stroke(pColor);
		strokeWeight(pWidth);
		if (shouldDrawEndPoints) {
			fill(0, 255, 255);
			ellipse(startPoint.x, startPoint.y, 10, 10);
			fill(150, 255, 255);
			ellipse(endPoint.x, endPoint.y, 10, 10);
		}
		line(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}
}