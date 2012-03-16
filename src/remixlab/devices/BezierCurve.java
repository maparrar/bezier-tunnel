package remixlab.devices;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import remixlab.proscene.InteractiveFrame;
import remixlab.proscene.Quaternion;
import remixlab.proscene.Scene;
/**
 * A BezierCurve is a curve like a painted by Processing function bezier().
 * */
public class BezierCurve {
	PApplet parent;
	Scene scene;
	PVector ini;		// Initial vector of the curve
	PVector fin;		// Final vector of the curve
	PVector ctrl1;		// First control point
	PVector ctrl2;		// Last control point
	PVector normalIni;	// Normal vector to a plane between last curve and this
	PVector normalFin;	// Normal vector to a plane between this curve and next
	Color color;		// Color to this curve 
	int nParts;			// Parts to divide each curve
	ArrayList<BezierPart> parts = new ArrayList<BezierPart>();
	
	/**
	 * BezierCurve Constructor
	 * 	@param point: is an anchor Point
	 * 	@param ctrl1: is the first control point
	 * */
	public BezierCurve(PApplet p,Scene sc,PVector vFin,PVector c1){
		parent = p;
		scene = sc;
		ini=new PVector(0, 0, 0);
		fin=vFin;
		ctrl1=c1;
		ctrl2=new PVector(0, 0, 0);
		color=new Color(0,0,255);
		nParts=30;
		normalIni=fin;
		normalFin=fin;
	}
	/**
	 * BezierCurve Constructor
	 * 	@param point: is an anchor Point
	 * 	@param ctrl1: is the first control point
	 *	@param col: is the Bezier Curve color
	 * */
	public BezierCurve(PApplet p,Scene sc,PVector vFin,PVector c1,Color col,int cantParts){
		parent = p;
		scene = sc;
		ini=new PVector(0, 0, 0);
		fin=vFin;
		ctrl1=c1;
		ctrl2=new PVector(0, 0, 0);
		color=col;
		nParts=cantParts;
		normalIni=fin;
		normalFin=fin;
	}
	
	
	/**
	 * Calculates the parts of the curve, with normal vectors
	 * */
	public void calculate(){
		parts.clear();
		BezierPart part;
		float t = (1 / (float) nParts);
		PVector iniPoint=ini;
		PVector finPoint;
		
		PVector normal;
		for (int i = 0; i < nParts; i++) {
			iniPoint = getPoint(i * t);
			finPoint = getPoint((i+1) * t);
			
			if(i==0){
				//part=new BezierPart(iniPoint,finPoint,new PVector(0,0,0));
				//finPoint = getPoint((i+1) * t);
				normal=normalIni;
			}else{
				BezierPart prevPart=parts.get(i-1);
				//Calculates the normal between parts
				normal=PVector.add(prevPart.ini,iniPoint);
				//part=new BezierPart(iniPoint,finPoint,new PVector(0,0,0));
				prevPart.normalFin=normal;
				
				
			}
			
			
			
			
			part=new BezierPart(iniPoint,finPoint,normal);
			
			
			
			
			
			//part.ini=new PVector(0,0,0);
			
			parent.pushMatrix();
				parent.pushStyle();
					//Create an InteractiveFrame to draw the cylinder
					InteractiveFrame iFrame = new InteractiveFrame(scene);
					iFrame.setPosition(part.ini);
					PVector to = PVector.sub(part.fin, iFrame.position());
					iFrame.setOrientation(new Quaternion(new PVector(0, 0, 1), to));
					iFrame.applyTransformation();
					scene.drawAxis(10*1.3f);
					
					
					
					//part.ini=iFrame.transformOf(part.ini);
					//part.fin=iFrame.transformOf(part.fin);
					
				parent.popStyle();
			parent.popMatrix();
		
			
			
			
			
			
			
			parts.add(part);
		}
	}
	
	/**
	 * Get a PVector with a point defined by t parameter in the cubic Bezier curve
	 * 	@param t: Parameter of parametric curve
	 * 	@return PVector: Point where the curve takes the t-value 
	 * */
	public PVector getPoint(float t){
		PVector p0,p1,p2,p3;
		p0=ini;
		p1=ctrl1;
		p2=ctrl2;
		p3=fin;
		//Using Cubic Bezier curves Theory
		float x=(float) ((p0.x*Math.pow(1-t,3))+(3*p1.x*t*Math.pow(1-t,2))+(3*p2.x*Math.pow(t,2)*(1-t))+(p3.x*Math.pow(t,3)));
		float y=(float) ((p0.y*Math.pow(1-t,3))+(3*p1.y*t*Math.pow(1-t,2))+(3*p2.y*Math.pow(t,2)*(1-t))+(p3.y*Math.pow(t,3)));
		float z=(float) ((p0.z*Math.pow(1-t,3))+(3*p1.z*t*Math.pow(1-t,2))+(3*p2.z*Math.pow(t,2)*(1-t))+(p3.z*Math.pow(t,3)));
		return new PVector(x,y,z);
	}
	
	/**
	 * Return a string with data of the curve
	 * */
	public String toString(){
		String pa="parts: "+nParts+"\n";
		String in="ini:("+ini.x+","+ini.y+","+ini.z+")\n";
		String fi="fin:("+fin.x+","+fin.y+","+fin.z+")\n";
		String c1="ctrl1:("+ctrl1.x+","+ctrl1.y+","+ctrl1.z+")\n";
		String c2="ctrl2:("+ctrl2.x+","+ctrl2.y+","+ctrl2.z+")\n";
		String ni="normalIni:("+normalIni.x+","+normalIni.y+","+normalIni.z+")\n";
		String nf="normalFin:("+normalFin.x+","+normalFin.y+","+normalFin.z+")\n";
		return "*****\n"+pa+in+fi+c1+c2+ni+nf+"*****";
	}
}
