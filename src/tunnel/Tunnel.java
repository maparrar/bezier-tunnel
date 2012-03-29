package tunnel;

import java.awt.Color;
import processing.core.PApplet;
import processing.core.PVector;
import remixlab.proscene.*;
import remixlab.devices.*;

// TODO: Parameterize the Lights
// TODO: Parameterize the Camera
// TODO: Avoid the intersections
// TODO: Lock the iFrames

@SuppressWarnings("serial")
public class Tunnel extends PApplet {
	Scene scene;
	BezierTunnel tunnel;

	public void setup() {
		size(800, 600, P3D);
		scene = new Scene(this);
		
		//PARAMETERS OF BEZIER TUNNEL
		//Number of BezierCurves to create
		int curves = 7;
		//In many parts divide each BezierCurve
		int parts= 60;
		//Detail of the Tunnel
		int detail= 60;
		//Radius
		float radius=5;
		
		//Tunnel declaration
		tunnel = new BezierTunnel(this,scene,parts,detail,radius);
		
		//Maximum and Minimum to random bezier curves
		float minA = -100;
		float maxA = 100;
		float minC = -200;
		float maxC = 200;
		//Randomize Bezier Curves
		for (int i = 0; i < curves; i++) {
			PVector anchor = new PVector(random(minA, maxA),random(minA, maxA),random(minA, maxA));
			PVector control = new PVector(random(minC, maxC),random(minC, maxC), random(minC, maxC));
			Color color = new Color((int) random(0, 255), (int) random(0, 255),(int) random(0, 255));
			//Add the curve to the Tunnel
			tunnel.addCurve(anchor,control,color);
		}
	}
	public void draw() {
		background(0);
		lights();
		tunnel.draw();
	}
}
