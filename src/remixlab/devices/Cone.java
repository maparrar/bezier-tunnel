package remixlab.devices;
/**
 * cone taken from http://wiki.processing.org/index.php/Cone
 * @author Tom Carden
 * Modifications: remixlab
 */
public class Cone {

	private static final float TWO_PI = (float) (Math.PI*2);
	

	static float unitConeX[];
	static float unitConeY[];
	static int coneDetail;

	static {
		coneDetail(24);
	}
	// places a cone with it's base centred at (x,y),
	// beight h in positive z, radius r.
	public Cone(float x, float y, float r, float h) {
//		pushMatrix();
//		translate(x, y);
//		scale(r, r);
//		beginShape(TRIANGLES);
//		for (int i = 0; i < coneDetail; i++) {
//			vertex(unitConeX[i], unitConeY[i], 0.0);
//			vertex(unitConeX[i + 1], unitConeY[i + 1], 0.0);
//			vertex(0, 0, h);
//		}
//		endShape();
//		popMatrix();
	}
		
	// just inits the points of a circle,
	// if you're doing lots of cones the same size
	// then you'll want to cache height and radius too
	static void coneDetail(int det) {
		coneDetail = det;
		unitConeX = new float[det + 1];
		unitConeY = new float[det + 1];
		for (int i = 0; i <= det; i++) {
			float a1 = TWO_PI * i / det;
			unitConeX[i] = (float) Math.cos(a1);
			unitConeY[i] = (float) Math.sin(a1);
		}
	}
}
