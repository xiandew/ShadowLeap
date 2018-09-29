package stage;

public class Bus extends Vehicle {
	
	private static final String BUS_SRC = "assets/bus.png";
	
	private static final float SPEED = 0.15f;
	private static final boolean HAZARD = true;
	
	public Bus(float x, float y, int direction) {
		super(BUS_SRC, x, y, SPEED, direction, HAZARD);
	}
}
