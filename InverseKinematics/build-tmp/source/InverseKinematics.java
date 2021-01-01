import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.List; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class InverseKinematics extends PApplet {



List<KinematicsBody> bodies;
KinematicsBody body;

public void settings() {
	size(960, 1000);
}

PVector nextPosition = new PVector();
PVector lastPosition = new PVector();

public void setup() {
	surface.setLocation(0, 0);
	background(0, 0, 0);
	colorMode(HSB);
	bodies = GenerateNKinematicsWorms(50);
}

public void draw() {
	background(0, 0, 0);
	for (KinematicsBody b : bodies) {
		lastPosition.set(b.getHeadPosition().copy());
		nextPosition.set(lastPosition.x + random(-2, 2), lastPosition.y + random(-2, 2));
		b.draw();
		b.update(nextPosition);
	}
	text(String.valueOf((int)(frameRate)), 10, 25);
}
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

	public PVector getHeadPosition() {
		if (this.parts == null || this.parts.length == 0) {
			return null;
		}
		PVector pos = this.parts[this.parts.length - 1].endPoint;
		return pos.copy();
	}

	public void setWidthPropotionalToIndex() {
		for (int i = 0; i < parts.length; ++i) {
			parts[i].pWidth = i/5;
		}
	}

	public void setWidth(float w) {
		for (int i = 0; i < parts.length; ++i) {
			parts[i].pWidth = w;
		}
	}

	public void setup(float partLength) {
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

	public void setColor(int bColor_) {
		for (KinematicsPart p : parts) {
			p.pColor = bColor_;
		}
	}

	public void makeEndPointToApproach(PVector point_) {
		if (parts == null || parts.length == 0) { return; }
		int lastPartIndex = parts.length - 1;
		parts[lastPartIndex].makeEndPointToApproach(point_);
		for(int i = lastPartIndex-1; i>=0; i--) {
			parts[i].makeEndPointToApproach(parts[i+1].startPoint);
		}
	}

	public void makeStartPointToApproach(PVector point_) {
		if (parts == null || parts.length == 0) { return; }
		parts[0].makeStartPointToApproach(point_);
		for(int i = 1; i<parts.length; i++) {
			parts[i].makeStartPointToApproach(parts[i-1].endPoint);
		}
	}

	public void update(PVector position) {
		makeEndPointToApproach(position.copy());
		if (tiedToPoint != null) {
			makeStartPointToApproach(tiedToPoint);
		}
	}

	public void draw() {
		for (KinematicsPart p : parts) {
			p.draw();
		}
	}
}

public KinematicsBody GenerateKinematicsString(PVector tiedToPoint, float length) {
	return new KinematicsBody(
		tiedToPoint,
		length,
		5,
		false
	);
}

public KinematicsBody GenerateKinematicsPaintStroke(float length) {
	KinematicsBody body = new KinematicsBody(
		length,
		10,
		false
	);
	body.setWidthPropotionalToIndex();
	return body;
}

public KinematicsBody GenerateKinematicsPaintStroke(float length, int bColor_) {
	KinematicsBody body = GenerateKinematicsPaintStroke(length);
	body.setColor(bColor_);
	return body;
}

public KinematicsBody GenerateKinematicsWorm(float length) {
	KinematicsBody body = new KinematicsBody(
		length,
		5,
		false
	);
	body.setWidth(10);
	return body;
}

public KinematicsBody GenerateKinematicsWorm(float length, int bColor_) {
	KinematicsBody body = GenerateKinematicsWorm(length);
	body.setColor(bColor_);
	return body;
}

public List<KinematicsBody> GenerateKinematicsBodiesOnCircle(
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

public List<KinematicsBody> GenerateNKinematicsWorms(int n) {
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
class KinematicsPart {
	PVector startPoint;
	PVector endPoint;
	float pLength;
	float pAngle_deg;
	float pWidth;
	int pColor;
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

	public void setupColor() {
		pColor = color(255, 255, 255);
	}

	public PVector getEndPointFromPoint(PVector point_) {
		return new PVector(
			cos(radians(pAngle_deg)),
			sin(radians(pAngle_deg))
		).mult(pLength).add(point_);
	}

	public PVector getStartPointFromPoint(PVector point_) {
		return point_.copy().sub(
			new PVector(
				cos(radians(pAngle_deg)),
				sin(radians(pAngle_deg))
			).mult(pLength)
		);
	}

	public void setPointsFromStartPoint(PVector startPoint_) {
		startPoint = startPoint_;
		endPoint = getEndPointFromPoint(startPoint_);
	}

	public void setPointsFromEndPoint(PVector endPoint_) {
		endPoint = endPoint_;
		startPoint = getStartPointFromPoint(endPoint_);
	}

	public void setup() {
		setupColor();
		setPointsFromStartPoint(startPoint);
	}

	public float getAngleForEndPointPointingTo(PVector point_) {
		PVector dir = point_.copy().sub(startPoint);
		return degrees(dir.heading());
	}

	public void setAngleForEndPointPointingTo(PVector point_) {
		pAngle_deg = getAngleForEndPointPointingTo(point_);
	}

	public void setEndPointPointingToPoint(PVector point_) {
		setAngleForEndPointPointingTo(point_);
		setPointsFromStartPoint(startPoint);
	}

	public void makeEndPointToApproach(PVector point_) {
		setEndPointPointingToPoint(point_);
		setPointsFromEndPoint(point_);
	}

	public void swapEndPoints() {
		PVector temp = startPoint;
		startPoint = endPoint;
		endPoint = temp;
	}

	public void makeStartPointToApproach(PVector point_) {
		swapEndPoints();
		makeEndPointToApproach(point_);
		swapEndPoints();
	}

	public void update() {

	}

	public void draw() {
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "InverseKinematics" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
