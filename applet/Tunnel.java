package tunnel;

import processing.core.PApplet;
import remixlab.devices.*;
//import SimpleOpenNI.*;

@SuppressWarnings("serial")
public class Tunnel extends PApplet {

	public void setup() {
		Test t=new Test();
		t.seta(3);
		t.setb(8);
		
		println(t.suma());
		
	}

	public void draw() {
	}
}
