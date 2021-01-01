class KinematicsBody {
	KinematicsPart[] parts;
	PVector tiedToPoint = null;
	boolean shouldDrawJoints;

	KinematicsBody(float bodyLength, float partLength, boolean shouldDrawJoints_) {
		int length = (int)(bodyLength / partLength);
		parts = new KinematicsPart[length];
		shouldDrawJoints = shouldDrawJoints_;
		setup(partLength);
	}

	KinematicsBody(PVector tiedToPoint_, float bodyLength, float partLength, boolean shouldDrawJoints_) {
		this(bodyLength, partLength, shouldDrawJoints_);
		if (tiedToPoint_ != null) {
			tiedToPoint = tiedToPoint_.copy();
		}
	}

	PVector getHeadPosition() {
		if (this.parts == null || this.parts.length == 0) {
			return null;
		}
		PVector pos = this.parts[this.parts.length - 1].endPoint;
		return pos.copy();
	}

	void setWidthPropotionalToIndex() {
		for (int i = 0; i < parts.length; ++i) {
			parts[i].pWidth = i/5;
		}
	}

	void setWidth(float w) {
		for (int i = 0; i < parts.length; ++i) {
			parts[i].pWidth = w;
		}
	}

	void setup(float partLength) {
		PVector point_ = new PVector();
		for (int i = 0; i < parts.length; ++i) {
			parts[i] = new KinematicsPart(
				point_,
				shouldDrawJoints,
				partLength,
				1
			);
			parts[i].pColor = color((float)i%255, 255 , 255);
			point_ = parts[i].endPoint;
		}
	}

	void setColor(color bColor_) {
		for (KinematicsPart p : parts) {
			p.pColor = bColor_;
		}
	}

	void makeEndPointToApproach(PVector point_) {
		if (parts == null || parts.length == 0) { return; }
		int lastPartIndex = parts.length - 1;
		parts[lastPartIndex].makeEndPointToApproach(point_);
		for(int i = lastPartIndex-1; i>=0; i--) {
			parts[i].makeEndPointToApproach(parts[i+1].startPoint);
		}
	}

	void makeStartPointToApproach(PVector point_) {
		if (parts == null || parts.length == 0) { return; }
		parts[0].makeStartPointToApproach(point_);
		for(int i = 1; i<parts.length; i++) {
			parts[i].makeStartPointToApproach(parts[i-1].endPoint);
		}
	}

	void update(PVector position) {
		makeEndPointToApproach(position.copy());
		if (tiedToPoint != null) {
			makeStartPointToApproach(tiedToPoint);
		}
	}

	void draw() {
		for (KinematicsPart p : parts) {
			p.draw();
		}
	}
}

KinematicsBody GenerateKinematicsString(PVector tiedToPoint, float length) {
	return new KinematicsBody(
		tiedToPoint,
		length,
		5,
		false
	);
}

KinematicsBody GenerateKinematicsPaintStroke(float length) {
	KinematicsBody body = new KinematicsBody(
		length,
		10,
		false
	);
	body.setWidthPropotionalToIndex();
	return body;
}

KinematicsBody GenerateKinematicsPaintStroke(float length, color bColor_) {
	KinematicsBody body = GenerateKinematicsPaintStroke(length);
	body.setColor(bColor_);
	return body;
}

KinematicsBody GenerateKinematicsWorm(float length) {
	KinematicsBody body = new KinematicsBody(
		length,
		5,
		false
	);
	body.setWidth(10);
	return body;
}

KinematicsBody GenerateKinematicsWorm(float length, color bColor_) {
	KinematicsBody body = GenerateKinematicsWorm(length);
	body.setColor(bColor_);
	return body;
}

List<KinematicsBody> GenerateKinematicsBodiesOnCircle(
	PVector center,
	float radius,
	float numberOfBodies,
	int lengthOfBody,
	boolean shouldDrawJoints
) {
	List<KinematicsBody> bodies = new ArrayList();
	float d_angle = 360 / numberOfBodies;
	for (float angle_deg = 0; angle_deg <= 360; angle_deg+=d_angle) {
		PVector point = new PVector(
			cos(radians(angle_deg)),
			sin(radians(angle_deg))
		).mult(radius).add(center);
		bodies.add(
			GenerateKinematicsString(point, 200)
		);
	}
	return bodies;
}

List<KinematicsBody> GenerateNKinematicsWorms(int n) {
	List<KinematicsBody> bodies = new ArrayList();
	KinematicsBody body;
	PVector pos = new PVector();
	for (int i = 0; i < n; ++i) {
		pos.set(random(0, width), random(0, height));
		body = GenerateKinematicsWorm(random(50, 150));
		body.update(pos);
		bodies.add(body);
	}
	return bodies;
}