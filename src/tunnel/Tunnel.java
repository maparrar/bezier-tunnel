package tunnel;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PGraphics3D;
import processing.core.PVector;

import remixlab.proscene.*;
import remixlab.devices.*;

@SuppressWarnings("serial")
public class Tunnel extends PApplet {

	Scene scene;
	public PGraphics3D pg3d;

	BezierTunnel tunnel;

	public void setup() {
		size(800, 600, P3D);
		// Scene instantiation
		scene = new Scene(this);

		int curvas = 5;
		float minA = -100;
		float maxA = 100;

		float minC = -200;
		float maxC = 200;

		// Array of BezierCurve(anchorPoint,controlPoint)
		BezierCurve points[];
		points = new BezierCurve[curvas];
		for (int i = 0; i < curvas; i++) {
			PVector anchor = new PVector(random(minA, maxA),
					random(minA, maxA), random(minA, maxA));
			PVector control = new PVector(random(minC, maxC),
					random(minC, maxC), random(minC, maxC));
			Color color = new Color((int) random(0, 255), (int) random(0, 255),
					(int) random(0, 255));
			points[i] = new BezierCurve(anchor, control, color);
		}

		PVector init = new PVector(0, 0, 0);
		tunnel = new BezierTunnel(this, scene, init, points, 20, 20);

		// TODO: Parametrize the Lights
		// TODO: Parametrize the Camera
		// TODO: Parametrize the cone detail
		// TODO: Parametrize the strokes of tunnel and the fill
		// TODO: Parametrize the strokes of Bezier curves
		// TODO: Parametrize the axis of Bezier Curves

	}

	public void draw() {
		background(0);
		lights();
		tunnel.draw();

		/**
		 * x: Position in X y: Position in y r1: radius of circle 1 r2: radius
		 * of circle 2 h1: height of highest side of cone h2: height of lowest
		 * side of cone highest: PVector aiming to highest side of cone, i.e.
		 * (3,4)
		 * */
		//drawCone(0, 0, 20, 25, 50, 60, new PVector(0, 4));

	}

	public void drawCone(float xa, float ya, float r1, float r2, float h1,float h2, PVector highest) {
		noFill();
		bezier(85, 20,50, 10, 10,10, 90, 90,90, 15, 80,30);
		stroke(255, 102, 0);
		int steps = 16;
		float r = 10;
		for (int i = 0; i <= steps; i++) {
			float t = i / (float) (steps);
			float x = bezierPoint(85, 10, 90, 15, t);
			float y = bezierPoint(20, 10, 90, 80, t);
			float z = bezierPoint(50, 10, 90, 30, t);
			float tx = bezierTangent(85, 10, 90, 15, t);
			float ty = bezierTangent(20, 10, 90, 80, t);
			float tz = bezierTangent(50, 10, 90, 30, t);
			float a = atan2(ty, tx);
			a -= HALF_PI;
			line(x, y,z, cos(a) * r + x, sin(a) * r + y,z);
		}

		// pushMatrix();
		// stroke(255,0,0);
		// line(0,0,0,20,0,h2);
		// stroke(0,0,255);
		// line(0,0,0,20,0,h1);
		//
		// stroke(0,255,0);
		// float midHeight=h2+((h1-h2)/2);
		// translate(0,0,midHeight);
		//
		// rotateX((float) (PI/3.0));
		// ellipse(x,y,r2,r2);
		// line(0,0,highest.x,highest.y);
		//
		// popMatrix();
		//
		//
		// beginShape(TRIANGLE_STRIP);
		// vertex(30, 75, 10);
		// vertex(40, 20, 0);
		// vertex(50, 75, 5);
		// vertex(60, 20, 7);
		// vertex(70, 75, 10);
		// vertex(80, 20, 45);
		// vertex(90, 75, 4);
		// endShape();
	}
}
