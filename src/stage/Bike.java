package stage;

public class Bike extends Vehicle {
	
	private static final String BIKE_SRC = "assets/bike.png";
	
	private static final float SPEED = 0.2f;
	private static final boolean HAZARD = true;
	
	private static final int LEFT_END = 24;
	private static final int RIGHT_END = 1000;
	
	public Bike(float x, float y, int direction) {
		super(BIKE_SRC, x, y, SPEED, direction, HAZARD);
	}
	
	@Override
	public float validateX(float x) {
		if(x <= LEFT_END && getDirection() == -1 ||
				x >= RIGHT_END && getDirection() == 1) {
			reverseDirection();
		}
		return x;
	}
	
}
