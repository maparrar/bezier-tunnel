package remixlab.devices;

import processing.core.PVector;
/**
 * A BezierCurve is a Point like a painted by Processing function bezierVertex().......
 * Needs 2 points PVector:
 * 	start point is defined of previous BezierPoint
 * 	@param point: is an anchor Point
 * 	@param ctrl1: is the first control point
 * 	The second control point is calculated using the next BezierPoint. This is 'point' if not exist. 
 * */
public class BezierCurve {
	PVector start,end,ctrl1,ctrl2;
	/**
	 * BezierCurve Constructor
	 * */
	public BezierCurve(){
		start=new PVector(0, 0, 0);
		end=new PVector(0, 0, 0);
		ctrl1=new PVector(0, 0, 0);
		ctrl2=new PVector(0, 0, 0);
	}
	/**
	 * BezierCurve Constructor
	 * 	@param point: is an anchor Point
	 * 	@param ctrl1: is the first control point
	 * 	@param ctrl2: is the second control point
	 * */
	public BezierCurve(PVector p,PVector c1){
		start=new PVector(0, 0, 0);
		end=p;
		ctrl1=c1;
		ctrl2=new PVector(0, 0, 0);
	}
	/**
	 * BezierCurve Constructor
	 * 	@param point: is an anchor Point
	 * 	@param control1: is the first control point
	 * 	@param control2: is the second control point
	 * */
	public BezierCurve(PVector p,PVector c1,PVector c2){
		start=new PVector(0, 0, 0);
		end=p;
		ctrl1=c1;
		ctrl2=c2;
	}
	/**
	 * BezierCurve Constructor
	 * 	Point @param px,@parameter py,@parameter pz : is an anchor Point
	 * 	ctrl1 @param c1x,@parameter c1y,@parameter c1z : is the first control point
	 * 	ctrl2 @param c2x,@parameter c2y,@parameter c2z : is the second control point
	 * */
	public BezierCurve(float px,float py,float pz,float c1x,float c1y,float c1z,float c2x,float c2y,float c2z){
		start=new PVector(0, 0, 0);
		end=new PVector(px,py,py);
		ctrl1=new PVector(c1x,c1y,c1y);
		ctrl2=new PVector(c2x,c2y,c2y);
	}
	
	
	/**
	 * Get a PVector with a point defined by t parameter in the cubic Bezier curve
	 * 	@param t: Parameter of parametric curve
	 * 	@return PVector: Point where the curve takes the t-value 
	 * */
	public PVector getPoint(float t){
		PVector p0,p1,p2,p3;
		p0=start;
		p1=ctrl1;
		p2=ctrl2;
		p3=end;
		//Using Cubic Bézier curves Theory
		float x=(float) ((p0.x*Math.pow(1-t,3))+(3*p1.x*t*Math.pow(1-t,2))+(3*p2.x*Math.pow(t,2)*(1-t))+(p3.x*Math.pow(t,3)));
		float y=(float) ((p0.y*Math.pow(1-t,3))+(3*p1.y*t*Math.pow(1-t,2))+(3*p2.y*Math.pow(t,2)*(1-t))+(p3.y*Math.pow(t,3)));
		float z=(float) ((p0.z*Math.pow(1-t,3))+(3*p1.z*t*Math.pow(1-t,2))+(3*p2.z*Math.pow(t,2)*(1-t))+(p3.z*Math.pow(t,3)));
		
//		System.out.println("t: "+t);
//		System.out.println("p1.x: "+p1.x);
//		System.out.println("p2.x: "+p2.x);
//		System.out.println("p3.x: "+p3.x);
//		System.out.println("1-t: "+(1-t));
		
		return new PVector(x,y,z);
	}
}
