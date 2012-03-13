package remixlab.devices;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import remixlab.proscene.*;

/**
 * A BezierTunnel is a tunnel build around a Bezier path.
 * */
public class BezierTunnel {
	PApplet parent;
	PVector start;
	BezierCurve curves[];
	float radius;
	int parts; // Parts to divide each curve
	Scene scene;

	/**
	 * BezierTunnel Constructor
	 * */
	public BezierTunnel(PApplet p, Scene sc) {
		parent = p;
		start = new PVector(0, 0, 0);
		radius = 10;
		parts = 5;
		scene = sc;
	}

	/**
	 * BezierTunnel Constructor
	 * 
	 * @param point
	 *            : is the first point of BezierTunnel
	 * @param curve
	 *            : is a BezierCurve with three PVector inside (two control
	 *            points and an anchor point)
	 * */
	public BezierTunnel(PApplet p, Scene sc, PVector point, BezierCurve curve) {
		parent = p;
		start = point;
		curves = new BezierCurve[1];
		curves[0] = curve;
		radius = 10;
		parts = 5;
		scene = sc;
	}

	/**
	 * BezierTunnel Constructor
	 * 
	 * @param point
	 *            : is the first point of BezierTunnel
	 * @param bCurves
	 *            : is an array of BezierCurves
	 * 
	 * */
	public BezierTunnel(PApplet p, Scene sc, PVector point,
			BezierCurve[] bCurves) {
		parent = p;
		start = point;
		curves = bCurves;
		radius = 10;
		parts = 5;
		scene = sc;
	}

	/**
	 * BezierTunnel Constructor
	 * 
	 * @param point
	 *            : is the first point of BezierTunnel
	 * @param bCurves
	 *            : is an array of BezierCurves
	 * @param radius
	 *            : radius of tunnel
	 * @param parts
	 *            : Parts to divide each curve
	 * */
	public BezierTunnel(PApplet p, Scene sc, PVector point,
			BezierCurve[] bCurves, float tRadius, int tParts) {
		parent = p;
		start = point;
		curves = bCurves;
		radius = tRadius;
		parts = tParts;
		scene = sc;
	}

	/**
	 * Draws the tunnel around the Bezier Path, defined by BezierCurve array
	 * */
	public void draw() {
		calculateBezierCurves();
		drawBezierPath();

		// Draws the curves with points
		for (int i = 0; i < curves.length; i++) {
			// drawCurvePoints(curves[i]);
		}

		// Draws the tunnel
		for (int i = 0; i < curves.length; i++) {
			drawTunnel(curves[i]);

		}

	}

	/**
	 * Copy the first point of last point of previous BezierCurve and calculate
	 * second control points for each BezierCurve in 'curves' array
	 * */
	public void calculateBezierCurves() {
		// Temp control points
		PVector ctrl;
		// Calculate the curves.length-1 Bezier Curves
		for (int i = 0; i < curves.length - 1; i++) {
			BezierCurve bA = curves[i];
			BezierCurve bB = curves[i + 1];
			ctrl = new PVector(2 * (bA.end.x) - bB.ctrl1.x, 2 * (bA.end.y)
					- bB.ctrl1.y, 2 * (bA.end.z) - bB.ctrl1.z);
			bA.ctrl2 = ctrl;
			if (i == 0) {
				bA.start = start;
			} else {
				bA.start = curves[i - 1].end;
			}
		}
		// Calculate the last Bezier Curve
		BezierCurve bLast = curves[curves.length - 1];
		bLast.ctrl2 = new PVector(bLast.end.x, bLast.end.y, bLast.end.z);
		bLast.start = curves[curves.length - 2].end;
	}

	/**
	 * Draws a path with the BezierCurves in 'curves' array
	 * */
	public void drawBezierPath() {
		parent.noFill();
		parent.stroke(255, 102, 0);
		// Draw the curves.length-1 Bezier Lines
		for (int i = 0; i < curves.length; i++) {
			drawCurve(curves[i]);
		}
	}

	/**
	 * Draws a Bezier curve
	 * 
	 * @param PVector
	 *            init: The first point of Bezier curve
	 * @param BezierCurve
	 *            bPoint: Object BezierPoint with anchor point and two control
	 *            points
	 * */
	public void drawCurve(BezierCurve b) {
		parent.stroke(b.color.getRed(), b.color.getGreen(), b.color.getBlue());
		parent.bezier(b.start.x, b.start.y, b.start.z, b.ctrl1.x, b.ctrl1.y,
				b.ctrl1.z, b.ctrl2.x, b.ctrl2.y, b.ctrl2.z, b.end.x, b.end.y,
				b.end.z);

	}

	/**
	 * Draws points of a curve, getting of Bezier formula, with the resolution
	 * defined for 'this'
	 * 
	 * @param BezierCurve
	 *            bPoint: Object BezierPoint with anchor point and two control
	 *            points
	 * */
	public void drawCurvePoints(BezierCurve b) {
		float t = (1 / (float) parts);
		for (int i = 0; i < parts; i++) {
			PVector v = b.getPoint(i * t);
			parent.stroke(255, 102, 0);
			parent.point(v.x, v.y, v.z);
		}
	}

	public void drawTunnel(BezierCurve b) {
		PVector init = b.start;
		parent.stroke(b.color.getRed(), b.color.getGreen(), b.color.getBlue());
		parent.fill(b.color.getRed(), b.color.getGreen(), b.color.getBlue());

		parent.noStroke();

		float t = (1 / (float) parts);
		// Iterate for each part of the curve
		for (int i = 0; i < parts; i++) {
			PVector end = b.getPoint(i * t);

			float dist = PApplet.dist(init.x, init.y, init.z, end.x, end.y,
					end.z);

			// InteractiveFrame iFrame = new InteractiveFrame(scene);
			InteractiveFrame iFrame = new InteractiveFrame(scene);
			iFrame.setPosition(init);
			parent.pushMatrix();
			parent.pushStyle();
			PVector to = PVector.sub(end, iFrame.position());
			// new Quaternion(Unitary_vector_of_axis, vector_to_aim)
			iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
			iFrame.applyTransformation(); // optimum

			// scene.drawAxis(10*1.3f);
			//scene.cone(20, 0, 0, 5, 5, dist);
			// parent.ellipse(0,0,5,5);
			
			weirdCylinder(20,5,dist,init,end);

			parent.popStyle();
			parent.popMatrix();

			init = end;
		}

		float dist = PApplet.dist(init.x, init.y, init.z, b.end.x, b.end.y,
				b.end.z);

		InteractiveFrame iFrame = new InteractiveFrame(scene);
		iFrame.setPosition(init);
		parent.pushMatrix();
		parent.pushStyle();
		PVector to = PVector.sub(b.end, iFrame.position());
		// new Quaternion(Unitary_vector_of_axis, vector_to_aim)
		iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
		iFrame.applyTransformation(); // optimum

		// scene.drawAxis(10*1.3f);
		//scene.cone(20, 0, 0, 5, 5, dist);
		// parent.ellipse(0,0,5,5);
		
		
		
		//weirdCylinder(10,20,dist,init,b.end);

		parent.popStyle();
		parent.popMatrix();

	}

	// By: Jean Pierre Charalambos:
	// w is the radius of the cylinder and h is its height.
	// n is the normal of the plane that intersects the cylinder at z=0
	// m is the normal of the plane that intersects the cylinder at z=h
	// eqs took from: http://en.wikipedia.org/wiki/Line-plane_intersection
	public void weirdCylinder(int detail, float w, float h, PVector n, PVector m) {
		PVector Pn0 = new PVector(0, 0, 0);
		PVector l0 = new PVector();
		PVector Pm0 = new PVector(0, 0, h);
		PVector l = new PVector(0, 0, 1);
		PVector pn = new PVector();
		PVector pm = new PVector();
		float x, y, d;

		parent.pushStyle();
		parent.noStroke();
		parent.beginShape(PConstants.QUAD_STRIP);
		for (float t = 0; t <= detail; t++) {
			x = (float) (w * Math.cos(t * (Math.PI*Math.PI) / detail));
			y = (float) (w * Math.sin(t * (Math.PI*Math.PI) / detail));
			l0.set(x, y, 0);

			d = (n.dot(PVector.sub(Pn0, l0))) / (l.dot(n));
			pn = PVector.add(PVector.mult(l, d), l0);
			parent.vertex(pn.x, pn.y, pn.z);

			l0.z = h;
			d = (m.dot(PVector.sub(Pm0, l0))) / (l.dot(m));
			pm = PVector.add(PVector.mult(l, d), l0);
			parent.vertex(pm.x, pm.y, pm.z);
		}
		parent.endShape();
		parent.popStyle();
	}

	/**
	 * Build from cone function of Proscene Draws a truncated cone along the
	 * {@link #renderer()} positive {@code z} axis, with its base centered at
	 * {@code (x,y)}, height {@code h}, and radii {@code r1} and {@code r2}
	 * (basis and height respectively).
	 * 
	 * @see #cone(int, float, float, float, float)
	 */
	public void cone(int detail, float x, float y, float r1, float r2, float h) {
		float firstCircleX[] = new float[detail + 1];
		float firstCircleY[] = new float[detail + 1];
		float secondCircleX[] = new float[detail + 1];
		float secondCircleY[] = new float[detail + 1];

		for (int i = 0; i <= detail; i++) {
			float a1 = (float) ((Math.PI * 2) * i / detail);
			firstCircleX[i] = r1 * (float) Math.cos(a1);
			firstCircleY[i] = r1 * (float) Math.sin(a1);
			secondCircleX[i] = r2 * (float) Math.cos(a1);
			secondCircleY[i] = r2 * (float) Math.sin(a1);
		}

		parent.pushMatrix();
		parent.translate(x, y);
		parent.beginShape(PConstants.QUAD_STRIP);
		for (int i = 0; i <= detail; i++) {
			parent.vertex(firstCircleX[i], firstCircleY[i], 0);
			parent.vertex(secondCircleX[i], secondCircleY[i], h);
		}
		parent.endShape();
		parent.popMatrix();
	}

}
