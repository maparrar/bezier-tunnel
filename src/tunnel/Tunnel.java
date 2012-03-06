package tunnel;

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
		size(800,600, P3D);
		// Scene instantiation
		scene = new Scene(this);

		
		int curvas=5;
		float min=-100;
		float max=100;
		
		//Array of BezierCurve(anchorPoint,controlPoint)-->
		BezierCurve points[];
		points=new BezierCurve[curvas];
		for(int i=0;i<curvas;i++){
			PVector anchor=new PVector(random(min,max),random(min,max),random(min,max));
			PVector control=new PVector(random(min,max),random(min,max),random(min,max));
			points[i]=new BezierCurve(anchor,control);
		}

		PVector init=new PVector(0,0,0);
		tunnel=new BezierTunnel(this,init,points,20,50);

	}

	public void draw() {
		background(0);
		fill(204, 102, 0);
//		box(20, 30, 50);
//		drawCone(0, 3, 15, 15, 30);
		
//		scene.cylinder(10, 10);
		
		
		
		tunnel.draw();
		
		
	}

	public void drawCone(float zMin, float zMax, float r1, float r2, int nbSub) {
		translate(0.0f, 0.0f, zMin);
		scene.cone(nbSub, 0, 0, r1, r2, zMax - zMin);
		translate(0.0f, 0.0f, -zMin);
	}
}
