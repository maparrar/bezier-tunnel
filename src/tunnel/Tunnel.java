package tunnel;

import java.awt.Color;



import processing.core.PApplet;
import processing.core.PGraphics3D;
import processing.core.PVector;
//import processing.opengl.*;

//import codeanticode.glgraphics.*;

import remixlab.proscene.*;
import remixlab.devices.*;

//@SuppressWarnings("serial")
public class Tunnel extends PApplet {

	Scene scene;
	//GLGraphicsOffScreen canvas;
	
	//public PGraphics3D pg3d;

	BezierTunnel tunnel;

	public void setup() {
		size(800, 600, P3D);
		//size(800, 600, GLConstants.GLGRAPHICS); 
		
//		canvas = new GLGraphicsOffScreen(this, width, height);
//		scene = new Scene(this, canvas);  
		
		
		// Scene instantiation
		scene = new Scene(this);

		
		//Parameters
		int curves = 5;
		
		
		
		
		//Parts of each BezierCurve
		int parts=60;
		
		
		//Tunnel declaration
		tunnel = new BezierTunnel(this, scene);
		
		
		//Maxs and Mins to random bezier curves
		float minA = -100;
		float maxA = 100;
		float minC = -200;
		float maxC = 200;
		//Randomize Bezier Curves
		for (int i = 0; i < curves; i++) {
			PVector anchor = new PVector(random(minA, maxA),
					random(minA, maxA), random(minA, maxA));
			PVector control = new PVector(random(minC, maxC),
					random(minC, maxC), random(minC, maxC));
			Color color = new Color((int) random(0, 255), (int) random(0, 255),
					(int) random(0, 255));
			tunnel.addCurve(new BezierCurve(anchor, control, color,parts));
		}

		

		// TODO: Parametrize the Lights
		// TODO: Parametrize the Camera
		// TODO: Parametrize the cone detail
		// TODO: Parametrize the strokes of tunnel and the fill
		// TODO: Parametrize the strokes of Bezier curves
		// TODO: Parametrize the axis of Bezier Curves

	}

	public void draw() {
		background(0);
		lights();
		tunnel.draw();

		/**
		 * x: Position in X y: Position in y r1: radius of circle 1 r2: radius
		 * of circle 2 h1: height of highest side of cone h2: height of lowest
		 * side of cone highest: PVector aiming to highest side of cone, i.e.
		 * (3,4)
		 * */
		//drawCone(0, 0, 20, 25, 50, 60, new PVector(0, 4));
		
		//Testing Weird Cylinder
		//tunnel.weirdCylinder(detail, radius,height, n, m)
		
		
		//tunnel.weirdCylinder(50,30,50,new PVector(0,0,1),new PVector(1,550,200));
		
		
		
		
/**		
		
		
		//Variables frame 1
		InteractiveFrame frame1 = new InteractiveFrame(scene);
		PVector frame1posIni=new PVector(-20,30,20);
		frame1.setPosition(frame1posIni);
		stroke(0,0,255);
//		line(0,0,0,frame1posIni.x,frame1posIni.y,frame1posIni.z);
		
		//Variables frame 2
		InteractiveFrame frame2 = new InteractiveFrame(scene);
		PVector frame2posIni=new PVector(50,50,50);
		frame2.setPosition(frame2posIni);
		stroke(0,255,0);
//		line(0,0,0,frame2posIni.x,frame2posIni.y,frame2posIni.z);
		
		//Variables frame 3
		InteractiveFrame frame3 = new InteractiveFrame(scene);
		PVector frame3posIni=new PVector(100,50,100);
		frame3.setPosition(frame3posIni);
		stroke(255,0,0);
//		line(0,0,0,frame3posIni.x,frame3posIni.y,frame3posIni.z);
		
		//Variables frame 4
		PVector frame4posIni=new PVector(80,-50,50);
		
		
		
		//AUX: Pinta los vectores entre los vetores de los Frames
		strokeWeight(2);
		stroke(112,134,45);
		line(frame1posIni.x,frame1posIni.y,frame1posIni.z,frame2posIni.x,frame2posIni.y,frame2posIni.z);
		line(frame2posIni.x,frame2posIni.y,frame2posIni.z,frame3posIni.x,frame3posIni.y,frame3posIni.z);
		line(frame3posIni.x,frame3posIni.y,frame3posIni.z,frame4posIni.x,frame4posIni.y,frame4posIni.z);
		
		//AUX: Vectores en cada Frame
		PVector test1=new PVector(0,0,20);
		PVector test2=new PVector(0,20,20);
		PVector test3=new PVector(0,20,20);
		
		
		
		
		
		
		
		
		//Dibuja el FRAME 1
		pushMatrix();
			pushStyle();
				PVector to2 = PVector.sub(frame2posIni, frame1.position());
				frame1.setOrientation(new Quaternion(new PVector(0, 0, 1), to2));
				frame1.applyTransformation();
				scene.drawAxis(10*1.3f);
				//Pinta un vector de prueba
				stroke(255,0,0);
				line(0,0,0,test1.x,test1.y,test1.z);
				
				
				
				
			popStyle();
		popMatrix();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Transformaciones
		
		//Transformación al mundo
//		PVector trans=frame1.inverseTransformOf(test1);
//		stroke(255,0,255);
//		line(0,0,0,trans.x,trans.y,trans.z);

		
		
		
		
		
		
		
		
		
		
		
		
//		PVector trans=frame2.transformOf(new PVector(0,0,20));
//		stroke(255,0,0);
//		line(0,0,0,trans.x,trans.y,trans.z);
//		stroke(255,0,255);
//		line(test2.x,test2.y,test2.z,trans.x,trans.y,trans.z);
		
				
		//Dibuja el FRAME 2
		pushMatrix();
			pushStyle();
				PVector to3 = PVector.sub(frame3posIni, frame2.position());
				frame2.setOrientation(new Quaternion(new PVector(0, 0, 1), to3));
				frame2.applyTransformation();
				scene.drawAxis(10*1.3f);
				
				
				
				//Pinta un vector de prueba
				stroke(0,255,255);
				line(0,0,0,test2.x,test2.y,test2.z);
			popStyle();
		popMatrix();
		
		
		
		
		
		
		//Pintando de nuevo en el frame1
		pushMatrix();
			//Aplica la transformación del frame1 a la escena
			frame1.applyTransformation();		
			
			//Transformación del frame2 al 1
			PVector trans1=frame1.transformOfFrom(new PVector(0,0,10), frame2);
			stroke(0,255,255);
			line(0,0,0,trans1.x,trans1.y,trans1.z);
			
			//ángulo en radianes
			PVector vecZ=new PVector(0,0,10);
			float angulo=PVector.angleBetween(vecZ, trans1);
			
			
			//Vector medio
			PVector medio=PVector.add(vecZ,trans1);
			stroke(134,15,200);
			line(0,0,0,medio.x,medio.y,medio.z);
			
			//Dibuja el cilindro raro
			//tunnel.weirdCylinder(detail, radius,height, n, m)
			float dist=PVector.dist(frame1posIni,frame2posIni);
			tunnel.weirdCylinder(100,5,dist,new PVector(0,0,10),medio);
			
		popMatrix();
		
		
		
		
		
		
		
		
		
		
		
		
		//Dibuja el FRAME 3
		pushMatrix();
			pushStyle();
				PVector to4 = PVector.sub(frame4posIni, frame3.position());
				frame3.setOrientation(new Quaternion(new PVector(0, 0, 1), to4));
				frame3.applyTransformation();
				scene.drawAxis(10*1.3f);
				//Pinta un vector de prueba
				stroke(255);
//				line(0,0,0,test3.x,test3.y,test3.z);
			popStyle();
		popMatrix();
		
		
		
		//Pintando de nuevo en el frame2
		pushMatrix();
			//Aplica la transformación del frame2 a la escena
			frame2.applyTransformation();		
			
			//Transformación del frame3 al 2
			PVector trans2=frame2.transformOfFrom(new PVector(0,0,10), frame3);
			stroke(0,255,255);
			line(0,0,0,trans2.x,trans2.y,trans2.z);
			
			//ángulo en radianes
			PVector vecZ2=new PVector(0,0,10);
			
			
			//Vector medio
			PVector medio2=PVector.add(vecZ2,trans2);
			stroke(134,15,200);
			line(0,0,0,medio2.x,medio2.y,medio2.z);
			
			//Dibuja el cilindro raro
			//tunnel.weirdCylinder(detail, radius,height, n, m)
			float dist2=PVector.dist(frame2posIni,frame3posIni);
			
			PVector base=frame2.transformOfFrom(medio,frame1);
			
			tunnel.weirdCylinder(100,5,dist2,base,medio2);
			
		popMatrix();
*/				

	}
}
