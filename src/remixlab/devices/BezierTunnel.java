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
		/*
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
		*/
		
		//drawBezierPath();
		drawTunnel();
//		
//		float dist=PVector.dist(b.ini,b.fin);
//		weirdCylinder(40,20,dist,b.normalIni,b.normalFin);

	}
	
	
	public void drawTunnel() {
		
		
		
		
		for(int i=0;i<curves.size();i++){
			BezierCurve b=curves.get(i);
			
			drawCurvePoints(b);
			
			//PApplet.println("curva "+i+": parts: "+b.parts.size());
			
			for(int j=0;j<b.parts.size();j++){
				BezierPart part=b.parts.get(j);
				
//				parent.stroke(164,102,0);
//				parent.line(0,0,0,part.ini.x,part.ini.y,part.ini.z);
				
				//PApplet.println("    parte "+j+": "+part.ini.x+","+part.ini.y+","+part.ini.z);
				
				parent.strokeWeight(2);
				//Shows the init vector in the world
				parent.stroke(200,0,200);
				//parent.line(0,0,0,part.ini.x,part.ini.y,part.ini.z);
				
				//Shows the final vector in the world
				parent.stroke(0,255,0);
				//parent.line(0,0,0,part.fin.x,part.fin.y,part.fin.z);
				parent.strokeWeight(1);
				
				
				InteractiveFrame iFrame = new InteractiveFrame(scene);
				iFrame.setPosition(part.ini);
				//Dibuja el FRAME
				parent.pushMatrix();
					parent.pushStyle();
						//Set frame to draw
						PVector to = PVector.sub(part.fin, iFrame.position());
						iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
						iFrame.applyTransformation();
						//scene.drawAxis(10*1.3f);
						
						
						
						
						//Variables to calculate normals
						PVector endNextVector;
						
						
						
						
						//The first part of the Bezier curve
						if(j==0){
							part.normalIni=part.normalFin;
						}
						
						
						
						//The last part of the Bezier curve
						if(j==b.parts.size()-1){
//							PApplet.println("Curve "+i+": "+b.toString());
//							BezierPart nextCurve = b.parts.get(j+1);
						}
						
						//
						if(j+1<b.parts.size()){
							//Transform the z-vector of next part to this iFrame
							endNextVector = b.parts.get(j+1).fin;
							InteractiveFrame nextFrame = new InteractiveFrame(scene);
							nextFrame.setPosition(part.fin);
							PVector toNext = PVector.sub(endNextVector, nextFrame.position());
							nextFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), toNext));
							PVector zNext=iFrame.transformOfFrom(new PVector(0,0,10), nextFrame);
							//z-vector of this iFrame
							PVector zThis=new PVector(0,0,10);
							
							
							
							//Shows the next z-vector in iFrame coordinates
//							parent.stroke(255,0,0);
//							parent.line(0,0,0,zNext.x,zNext.y,zNext.z);
							
							
							//Sum of both vectors: medium vector in the plane of both vectors
							PVector normal=PVector.add(zThis,zNext);
							
							
							
							
							
							//Draws the cylinder
							float dist = PVector.dist(part.ini,part.fin);
							
							part.normalFin=normal;
							b.parts.get(j+1).normalIni=nextFrame.transformOfFrom(normal, iFrame);
							
							
							
							
							
							
							
//							
//							//Shows the init normal vector
//							parent.stroke(100,200,0);
//							parent.line(0,0,0,part.normalIni.x,part.normalIni.y,part.normalIni.z);
//							
//							//Shows the final normal vector at height
//							parent.stroke(0,0,255);
//							parent.line(0,0,0,part.normalFin.x,part.normalFin.y,part.normalFin.z+dist);
							
							parent.strokeWeight(1);
							
							parent.fill(b.color.getRGB());
							weirdCylinder(60,5,dist,part.normalIni,part.normalFin);
							
						}
					parent.popStyle();
				parent.popMatrix();
				
				
			}
		}
		
		
/*
		PVector init = b.start;
		parent.stroke(b.color.getRed(), b.color.getGreen(), b.color.getBlue());
		parent.fill(b.color.getRed(), b.color.getGreen(), b.color.getBlue());

		parent.noStroke();

		float t = (1 / (float) parts);
		// Iterate for each part of the curve
		for (int i = 0; i < parts; i++) {
			PVector end = b.getPoint(i * t);

			float dist = PVector.dist(init,end);
			
			
			
			InteractiveFrame iFrame = new InteractiveFrame(scene);
			iFrame.setPosition(init);
			
			//Dibuja el FRAME
			parent.pushMatrix();
				parent.pushStyle();
					PVector to = PVector.sub(end, iFrame.position());
					iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
					//Set frame to draw
					iFrame.applyTransformation();
					scene.drawAxis(10*1.3f);
					
					
					//Transform the z-vector of next part to this iFrame
					PVector endNextVector = b.getPoint((i+1) * t);
					InteractiveFrame nextFrame = new InteractiveFrame(scene);
					nextFrame.setPosition(end);
					PVector toNext = PVector.sub(endNextVector, nextFrame.position());
					nextFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), toNext));
					
					
					PVector zNext=iFrame.transformOfFrom(new PVector(0,0,10), nextFrame);
					
					
					
					
					
					//z-vector of this iFrame
					PVector zThis=new PVector(0,0,10);
					
					
					parent.stroke(255,0,0);
					parent.line(0,0,0,zNext.x,zNext.y,zNext.z);
					
					
					//Sum of both vectors: medium vector in the plane of both vectors
					PVector medium=PVector.add(zThis,zNext);
					
					parent.stroke(0,0,255);
					parent.line(0,0,0,medium.x,medium.y,medium.z);
					
					
					
					
					//Draws the cylinder
					//tunnel.weirdCylinder(detail, radius,height, n, m)
					//PVector base=iFrame.transformOfFrom(medium,zThis);
					weirdCylinder(100,5,dist,zThis,medium);
				parent.popStyle();
			parent.popMatrix();
			
			

//			// InteractiveFrame iFrame = new InteractiveFrame(scene);
//			InteractiveFrame iFrame = new InteractiveFrame(scene);
//			iFrame.setPosition(init);
//			parent.pushMatrix();
//			parent.pushStyle();
//			PVector to = PVector.sub(end, iFrame.position());
//			// new Quaternion(Unitary_vector_of_axis, vector_to_aim)
//			iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
//			iFrame.applyTransformation(); // optimum
//
//			// scene.drawAxis(10*1.3f);
//			//scene.cone(20, 0, 0, 5, 5, dist);
//			// parent.ellipse(0,0,5,5);
//			
//			//weirdCylinder(20,5,dist,init,end);
//
//			parent.popStyle();
//			parent.popMatrix();

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

		scene.drawAxis(10*1.3f);
		//scene.cone(20, 0, 0, 5, 5, dist);
		// parent.ellipse(0,0,5,5);
		
		
		
		//weirdCylinder(20,5,dist,init,b.end);

		parent.popStyle();
		parent.popMatrix();
		
*/

	}
	
	
	/**
	 * Draws a Bezier curve
	 * @param BezierCurve b
	 * */
	public void drawCurve(BezierCurve b) {
		parent.stroke(b.color.getRed(), b.color.getGreen(), b.color.getBlue());
		parent.bezier(
				b.ini.x, b.ini.y, b.ini.z, 
				b.ctrl1.x, b.ctrl1.y, b.ctrl1.z,
				b.ctrl2.x, b.ctrl2.y, b.ctrl2.z,
				b.fin.x, b.fin.y, b.fin.z);
	}
	
	/**
	 * Draws a path with the BezierCurves in 'curves' array
	 * */
	public void drawBezierPath() {
		parent.noFill();
		parent.stroke(255, 102, 0);
		// Draw the curves.length-1 Bezier Lines
		for (int i = 0; i < curves.size(); i++) {
			drawCurve(curves.get(i));
		}
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
		int parts=10;
		float t = (1 / (float) parts);
		for (int i = 0; i < parts; i++) {
			PVector v = b.getPoint(i * t);
			parent.strokeWeight(3);
			parent.stroke(255, 102, 0);
			parent.point(v.x, v.y, v.z);
			parent.strokeWeight(1);
		}
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
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

/*
	
	/**
	 * Copy the first point of last point of previous BezierCurve and calculate
	 * second control points for each BezierCurve in 'curves' array.
	 * Also calculates the normal vectors of medium planes between each pair of parts 
	 * *
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
	 * Draws a Bezier curve
	 * @param PVector
	 *            init: The first point of Bezier curve
	 * @param BezierCurve
	 *            bPoint: Object BezierPoint with anchor point and two control
	 *            points
	 * *
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
	 * *
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

			float dist = PVector.dist(init,end);
			
			
			
			InteractiveFrame iFrame = new InteractiveFrame(scene);
			iFrame.setPosition(init);
			
			//Dibuja el FRAME
			parent.pushMatrix();
				parent.pushStyle();
					PVector to = PVector.sub(end, iFrame.position());
					iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
					//Set frame to draw
					iFrame.applyTransformation();
					scene.drawAxis(10*1.3f);
					
					
					//Transform the z-vector of next part to this iFrame
					PVector endNextVector = b.getPoint((i+1) * t);
					InteractiveFrame nextFrame = new InteractiveFrame(scene);
					nextFrame.setPosition(end);
					PVector toNext = PVector.sub(endNextVector, nextFrame.position());
					nextFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), toNext));
					
					
					PVector zNext=iFrame.transformOfFrom(new PVector(0,0,10), nextFrame);
					
					
					
					
					
					//z-vector of this iFrame
					PVector zThis=new PVector(0,0,10);
					
					
					parent.stroke(255,0,0);
					parent.line(0,0,0,zNext.x,zNext.y,zNext.z);
					
					
					//Sum of both vectors: medium vector in the plane of both vectors
					PVector medium=PVector.add(zThis,zNext);
					
					parent.stroke(0,0,255);
					parent.line(0,0,0,medium.x,medium.y,medium.z);
					
					
					
					
					//Draws the cylinder
					//tunnel.weirdCylinder(detail, radius,height, n, m)
					//PVector base=iFrame.transformOfFrom(medium,zThis);
					weirdCylinder(100,5,dist,zThis,medium);
				parent.popStyle();
			parent.popMatrix();
			
			

//			// InteractiveFrame iFrame = new InteractiveFrame(scene);
//			InteractiveFrame iFrame = new InteractiveFrame(scene);
//			iFrame.setPosition(init);
//			parent.pushMatrix();
//			parent.pushStyle();
//			PVector to = PVector.sub(end, iFrame.position());
//			// new Quaternion(Unitary_vector_of_axis, vector_to_aim)
//			iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
//			iFrame.applyTransformation(); // optimum
//
//			// scene.drawAxis(10*1.3f);
//			//scene.cone(20, 0, 0, 5, 5, dist);
//			// parent.ellipse(0,0,5,5);
//			
//			//weirdCylinder(20,5,dist,init,end);
//
//			parent.popStyle();
//			parent.popMatrix();

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

		scene.drawAxis(10*1.3f);
		//scene.cone(20, 0, 0, 5, 5, dist);
		// parent.ellipse(0,0,5,5);
		
		
		
		//weirdCylinder(20,5,dist,init,b.end);

		parent.popStyle();
		parent.popMatrix();

	}

	
*/
	
	
}
