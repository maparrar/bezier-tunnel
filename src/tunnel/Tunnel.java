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
		size(800,600, P3D);
		// Scene instantiation
		scene = new Scene(this);

		
		int curvas=5;
		float minA=-100;
		float maxA=100;
		
		float minC=-200;
		float maxC=200;
		
		//Array of BezierCurve(anchorPoint,controlPoint)
		BezierCurve points[];
		points=new BezierCurve[curvas];
		for(int i=0;i<curvas;i++){
			PVector anchor=new PVector(random(minA,maxA),random(minA,maxA),random(minA,maxA));
			PVector control=new PVector(random(minC,maxC),random(minC,maxC),random(minC,maxC));
			Color color=new Color((int)random(0,255),(int)random(0,255),(int)random(0,255));
			points[i]=new BezierCurve(anchor,control,color);
		}

		PVector init=new PVector(0,0,0);
		tunnel=new BezierTunnel(this,scene,init,points,20,20);
		
		//TODO: Parametrize the Lights
		//TODO: Parametrize the Camera
		//TODO: Parametrize the cone detail
		//TODO: Parametrize the strokes of tunnel and the fill
		//TODO: Parametrize the strokes of Bezier curves
		//TODO: Parametrize the axis of Bezier Curves
		

	}

	public void draw() {
		background(0);
		lights();
		tunnel.draw();
		
		
	}

	public void drawCone(float zMin, float zMax, float r1, float r2, int nbSub) {
		translate(0.0f, 0.0f, zMin);
		scene.cone(nbSub, 0, 0, r1, r2, zMax - zMin);
		translate(0.0f, 0.0f, -zMin);
	}
}
