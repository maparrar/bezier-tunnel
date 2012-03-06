package remixlab.devices;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * A BezierTunnel is a tunnel build around a Bezier path.
 * */
public class BezierTunnel{
	PApplet parent;
	PVector start;
	BezierCurve curves[];
	float radius;
	int parts;		//Parts to divide each curve
	/**
	 * BezierTunnel Constructor
	 * */
	public BezierTunnel(PApplet p){
		parent=p;
		start=new PVector(0, 0, 0);
		radius=10;
		parts=5;
	}
	/**
	 * BezierTunnel Constructor
	 * 	@param point: is the first point of BezierTunnel
	 * 	@param curve: is a BezierCurve with three PVector inside (two control points and an anchor point)
	 * */
	public BezierTunnel(PApplet p,PVector point,BezierCurve curve){
		parent=p;
		start=point;
		curves=new BezierCurve[1];
		curves[0]=curve;
		radius=10;
		parts=5;
	}
	/**
	 * BezierTunnel Constructor
	 * 	@param point: is the first point of BezierTunnel
	 * 	@param bCurves: is an array of BezierCurves
	 * 
	 * */
	public BezierTunnel(PApplet p,PVector point,BezierCurve[] bCurves){
		parent=p;
		start=point;
		curves=bCurves;
		radius=10;
		parts=5;
	}
	/**
	 * BezierTunnel Constructor
	 * 	@param point: is the first point of BezierTunnel
	 * 	@param bCurves: is an array of BezierCurves
	 * 	@param radius: radius of tunnel
	 * 	@param parts: Parts to divide each curve
	 * */
	public BezierTunnel(PApplet p,PVector point,BezierCurve[] bCurves,float tRadius,int tParts){
		parent=p;
		start=point;
		curves=bCurves;
		radius=tRadius;
		parts=tParts;
	}
	
	
	/**
	 * Draws the tunnel around the Bezier Path, defined by BezierCurve array
	 * */
	public void draw(){
		calculateBezierCurves();
		drawBezierPath();
		
		
		//Draws the curves with points
		for(int i=0;i<curves.length;i++){
			//drawCurvePoints(curves[i]);
		}
		
		//Draws the tunnel
		for(int i=0;i<curves.length;i++){
			drawTunnel(curves[i]);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	/**
	 * Copy the first point of last point of previous BezierCurve and 
	 * calculate second control points for each BezierCurve in 'curves' array
	 * */
	public void calculateBezierCurves(){
		//Temp control points
		PVector ctrl;
		//Calculate the curves.length-1 Bezier Curves
		for(int i=0;i<curves.length-1;i++){
			BezierCurve bA=curves[i];
			BezierCurve bB=curves[i+1];
			ctrl=new PVector(2*(bA.end.x)-bB.ctrl1.x,2*(bA.end.y)-bB.ctrl1.y,2*(bA.end.z)-bB.ctrl1.z);
			bA.ctrl2=ctrl;
			if(i==0){
				bA.start=start;
			}else{
				bA.start=curves[i-1].end;
			}
		}
		//Calculate the last Bezier Curve
		BezierCurve bLast=curves[curves.length-1];
		bLast.ctrl2=new PVector(bLast.end.x,bLast.end.y,bLast.end.z);
		bLast.start=curves[curves.length-2].end;
	}
	
	
	
	
	/**
	 * Draws a path with the BezierCurves in 'curves' array
	 * */
	public void drawBezierPath(){
		parent.noFill();
		parent.stroke(255, 102, 0);
		//Draw the curves.length-1 Bezier Lines
		for(int i=0;i<curves.length;i++){
			drawCurve(curves[i]);
		}
	}
	
	/**
	 * Draws a Bezier curve
	 * @param PVector init: The first point of Bezier curve
	 * @param BezierCurve bPoint: Object BezierPoint with anchor point and two control points 
	 * */
	public void drawCurve(BezierCurve b){
		parent.bezier(b.start.x,b.start.y,b.start.z, b.ctrl1.x,b.ctrl1.y,b.ctrl1.z,  b.ctrl2.x,b.ctrl2.y,b.ctrl2.z,  b.end.x,b.end.y,b.end.z);
	}
	
	
	/**
	 * Draws points of a curve, getting of Bezier formula, with the resolution defined for 'this'
	 * @param BezierCurve bPoint: Object BezierPoint with anchor point and two control points 
	 * */
	public void drawCurvePoints(BezierCurve b){
		float t=(1/(float)parts);
		for(int i=0;i<parts;i++){
			PVector v=b.getPoint(i*t);
			parent.stroke(255, 102, 0);
			parent.point(v.x,v.y,v.z);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void drawTunnel(BezierCurve b){
		PVector temp=new PVector(0,0,0);
		float t=(1/(float)parts);
		for(int i=0;i<parts-1;i++){
			PVector vec=b.getPoint(i*t);
			parent.stroke(255, 102, 0);
			
			cone(temp,vec);
			
			temp=vec;
		}
	}
	
	public void cone(PVector center,PVector dir){
		// just inits the points of a circle,
		// if you're doing lots of cones the same size
		// then you'll want to cache height and radius too
		int det=24;
		float unitConeX[] = new float[det + 1];
		float unitConeY[] = new float[det + 1];
		for (int i = 0; i <= det; i++) {
			float a1 = (float) ((Math.PI*2) * i / det);
			unitConeX[i] = (float) Math.cos(a1);
			unitConeY[i] = (float) Math.sin(a1);
		}
		
		int coneDetail=det;
		float r=12,h=10;
		
		parent.pushMatrix();
		parent.translate(center.x,center.y,center.z);
		parent.rotateX(0);
		parent.rotateY(0);
		parent.rotateZ(0);
		parent.scale(r,r);
		parent.beginShape(parent.TRIANGLES);
		for (int i = 0; i < coneDetail; i++) {
			parent.vertex(unitConeX[i], unitConeY[i], (float) 0.0);
			parent.vertex(unitConeX[i + 1], unitConeY[i + 1], (float) 0.0);
			parent.vertex(0, 0, h);
		}
		parent.endShape();
		parent.popMatrix();
	}
	
	
}
