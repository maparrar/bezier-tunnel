package remixlab.devices;

import processing.core.PVector;

/**
 * A BezierPart is a division of BezierCurve
 * */
public class BezierPart {
	PVector ini;		// Initial vector of the part
	PVector fin;		// Final vector of the part
	PVector normalIni;	// Normal vector to a plane between last part and this
	PVector normalFin;	// Normal vector to a plane between this part and next, this vector is defined with the next part
	float length;		// Length of part
	
	/**
	 * BezierPart constructor
	 * 	@param vIni: Initial vector of the part
	 *  @param vFin: Final vector of the part
	 *  @param vNormalIni: Normal vector to a plane between last part and this
	 * */
	public BezierPart(PVector vIni,PVector vFin,PVector vNormalIni){
		ini=vIni;
		fin=vFin;
		normalIni=vNormalIni;
		length=PVector.dist(ini, fin);
	}
}
