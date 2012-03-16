package remixlab.devices;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import remixlab.proscene.*;

/**
 * A BezierTunnel is a tunnel build with one or more BezierCurves
 * */
public class BezierTunnel {
	PApplet parent;
	Scene scene;
	PVector ini;	
	PVector fin;
	float radius;
	ArrayList<BezierCurve> curves = new ArrayList<BezierCurve>();
	
	/**
	 * BezierTunnel Constructor
	 * @param p: PApplet
	 * @param sc: Scene
	 * */
	public BezierTunnel(PApplet p, Scene sc) {
		parent = p;
		scene = sc;
		radius = 10;
		ini = new PVector(radius,radius,radius);
		fin = new PVector(-(2*radius),-(2*radius),-(2*radius));
	}
	/**
	 * BezierTunnel Constructor
	 * @param p: PApplet
	 * @param sc: Scene
	 * @param tRadius: radius of tunnel
	 * */
	public BezierTunnel(PApplet p, Scene sc, float tRadius) {
		parent = p;
		scene = sc;
		radius = tRadius;
		ini = new PVector(radius,radius,radius);
		fin = new PVector(-(2*radius),-(2*radius),-(2*radius));
	}
	/**
	 * BezierTunnel Constructor
	 * @param p: PApplet
	 * @param sc: Scene
	 * @param vIni: Normal vector to first plane of tunnel
	 * @param tRadius: radius of tunnel
	 * */
	public BezierTunnel(PApplet p, Scene sc, PVector vIni, float tRadius) {
		parent = p;
		scene = sc;
		ini = vIni;
		radius = tRadius;
		fin = new PVector(-(2*radius),-(2*radius),-(2*radius));
	}
	/**
	 * BezierTunnel Constructor
	 * @param p: PApplet
	 * @param sc: Scene
	 * @param vIni: Normal vector to first plane of tunnel
	 * @param vFin: Normal vector to last plane of tunnel
	 * @param tRadius: radius of tunnel
	 * */
	public BezierTunnel(PApplet p, Scene sc, PVector vIni, PVector vFin, float tRadius) {
		parent = p;
		scene = sc;
		ini = vIni;
		fin = vFin;
		radius = tRadius;
	}

	/**
	 * Adds a BezierCurve to the tunnel
	 * @param curve: BezierCurve to add to the tunnel
	 * */
	public void addCurve(BezierCurve curve){
		if(curves.size()>0){
			BezierCurve lastCurve=curves.get(curves.size()-1);
			
			//Reassign the first point of this curve
			curve.ini=lastCurve.fin;
			
			//Reassign the second control point of the last curve
			PVector ctrl = new PVector(2 * (lastCurve.fin.x) - curve.ctrl1.x, 2 * (lastCurve.fin.y)- curve.ctrl1.y, 2 * (lastCurve.fin.z) - curve.ctrl1.z);
			lastCurve.ctrl2 = ctrl;
			
			
			
			
			
			
			
			
			
			
			//Sums of two vectors returns the medium vector
			PVector normal=PVector.add(lastCurve.normalFin,curve.normalIni);
			//Reassign the same normal
			lastCurve.normalFin=normal;
			curve.normalIni=normal;
		}
		//Adds the curve to the tunnel
		curves.add(curve);
		
		//Recalculate curves
		for(int i=0;i<curves.size();i++){
			curves.get(i).calculate();
		}
	}
	
	/**
	 * Draws the tunnel around the Bezier Path, defined by BezierCurve array
	 * */
	public void draw() {
		drawBezierPoints();
		drawBezierPath();
		drawBezierTunnel();
	}
	
	/**
	 * Draws each part of each BezierCurve
	 * */
	public void drawBezierTunnel() {
		for(int i=0;i<curves.size();i++){
			BezierCurve curve=curves.get(i);
			
			for(int j=0;j<curve.parts.size();j++){
				BezierPart part=curve.parts.get(j);
				
							
				parent.pushMatrix();
					parent.pushStyle();
						//Create an InteractiveFrame to draw the cylinder
						InteractiveFrame iFrame = new InteractiveFrame(scene);
						iFrame.setPosition(part.ini);
						PVector to = PVector.sub(part.fin, iFrame.position());
						iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
						iFrame.applyTransformation();
						//scene.drawAxis(10*1.3f);
						
						
						
						
						
						
						
						
						
						
						
						
						
						//Variables to calculate normals
						PVector endNextVector;
						//Transform the z-vector of next part to this iFrame
						if(j==curve.parts.size()-1){
							endNextVector=curve.fin;
						}else{
							endNextVector = curve.parts.get(j+1).fin;
						}
						InteractiveFrame nextFrame = new InteractiveFrame(scene);
						nextFrame.setPosition(part.fin);
						PVector toNext = PVector.sub(endNextVector, nextFrame.position());
						nextFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), toNext));
						PVector zNext=iFrame.transformOfFrom(new PVector(0,0,10), nextFrame);
						//z-vector of this iFrame
						PVector zThis=new PVector(0,0,10);
						
						//Sum of both vectors: medium vector in the plane of both vectors
						PVector normal=PVector.add(zThis,zNext);
						
						part.normalFin=normal;
						
						//If last part
						if(j==curve.parts.size()-1){
							//If last curve
							if(i<curves.size()-1){
								PVector endNextVectorCurve=curves.get(i+1).parts.get(0).fin;
								InteractiveFrame nextFrameCurve = new InteractiveFrame(scene);
								nextFrameCurve.setPosition(part.fin);
								PVector toNextCurve = PVector.sub(endNextVectorCurve, nextFrameCurve.position());
								nextFrameCurve.setOrientation(new Quaternion(new PVector(0, 0, 1), toNextCurve));
								PVector zNextCurve=iFrame.transformOfFrom(new PVector(0,0,10), nextFrameCurve);
								PVector normalCurve=PVector.add(zThis,zNextCurve);
								curves.get(i+1).parts.get(0).normalIni=nextFrameCurve.transformOfFrom(normalCurve, iFrame);
								part.normalFin=normalCurve;
							}
						}else{
							curve.parts.get(j+1).normalIni=nextFrame.transformOfFrom(normal, iFrame);
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
//						parent.strokeWeight(2);
//						parent.stroke(255,0,0);
//						parent.line(0,0,0,part.normalIni.x,part.normalIni.y,part.normalIni.z);
//						parent.stroke(0,255,0);
//						parent.line(0,0,0,part.normalFin.x,part.normalFin.y,part.normalFin.z+part.length);
//						parent.strokeWeight(1);
						
						//Draws the cylinder
						float dist = PVector.dist(part.ini,part.fin);
						parent.fill(curve.color.getRGB());
						weirdCylinder(60,5,dist,part.normalIni,part.normalFin);
						
					parent.popStyle();
				parent.popMatrix();
				
				
				
				
//				if(j==curve.parts.size()-1){
//					PApplet.println("Curve["+i+"] part["+j+"]: \n"+curve.toString());
//				}
				
//				InteractiveFrame iFrame = new InteractiveFrame(scene);
//				iFrame.setPosition(part.ini);
//				//Draw the frame
//				parent.pushMatrix();
//					parent.pushStyle();
//						//Set frame to draw
//						PVector to = PVector.sub(part.fin, iFrame.position());
//						iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
//						iFrame.applyTransformation();
//						//scene.drawAxis(10*1.3f);
//						
//						//Variables to calculate normals
//						PVector endNextVector;
//						
//						//The first part of the Bezier curve
//						if(j==0){
//							part.normalIni=part.normalFin;
//						}
//						
//						if(j+1<curve.parts.size()){
//							//Transform the z-vector of next part to this iFrame
//							endNextVector = curve.parts.get(j+1).fin;
//							InteractiveFrame nextFrame = new InteractiveFrame(scene);
//							nextFrame.setPosition(part.fin);
//							PVector toNext = PVector.sub(endNextVector, nextFrame.position());
//							nextFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), toNext));
//							PVector zNext=iFrame.transformOfFrom(new PVector(0,0,10), nextFrame);
//							//z-vector of this iFrame
//							PVector zThis=new PVector(0,0,10);
//							
//							//Sum of both vectors: medium vector in the plane of both vectors
//							PVector normal=PVector.add(zThis,zNext);
//							
//							part.normalFin=normal;
//							curve.parts.get(j+1).normalIni=nextFrame.transformOfFrom(normal, iFrame);
//							
//							//Draws the cylinder
//							float dist = PVector.dist(part.ini,part.fin);
//							parent.fill(curve.color.getRGB());
//							weirdCylinder(60,5,dist,part.normalIni,part.normalFin);
//						}
//					parent.popStyle();
//				parent.popMatrix();
			}
		}
	}
	
	
	/**
	 * Draws a Bezier curve
	 * @param BezierCurve b
	 * */
	public void drawCurve(BezierCurve curve) {
		parent.stroke(curve.color.getRGB());
		parent.bezier(
				curve.ini.x, curve.ini.y, curve.ini.z, 
				curve.ctrl1.x, curve.ctrl1.y, curve.ctrl1.z,
				curve.ctrl2.x, curve.ctrl2.y, curve.ctrl2.z,
				curve.fin.x, curve.fin.y, curve.fin.z);
	}
	
	/**
	 * Draws a path with the BezierCurves in 'curves' array
	 * */
	public void drawBezierPath() {
		parent.noFill();
		// Draw the curves.length-1 Bezier Lines
		for (int i = 0; i < curves.size(); i++) {
			drawCurve(curves.get(i));
		}
	}
	
	/**
	 * Draws points of each curve, getting of Bezier formula
	 * */
	public void drawBezierPoints() {
		for (int i = 0; i < curves.size(); i++) {
			BezierCurve curve=curves.get(i);
			float t = (1 / (float) curve.nParts);
			for (int j = 0; j < curve.nParts; j++) {
				PVector v = curve.getPoint(j * t);
				parent.strokeWeight(3);
				parent.stroke(curve.color.getRGB());
				parent.point(v.x, v.y, v.z);
				parent.strokeWeight(1);
			}
		}
	}
	
	/**
	 * Draw a cylinder cut by the planes given by the vector normal to them.
	 * @author Jean Pierre Charalambos:
	 * @param w is the radius of the cylinder and
	 * @param h is its height.
	 * @param n is the normal of the plane that intersects the cylinder at z=0
	 * @param m is the normal of the plane that intersects the cylinder at z=h
	 * @info Took from: http://en.wikipedia.org/wiki/Line-plane_intersection
	 * */
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
}
