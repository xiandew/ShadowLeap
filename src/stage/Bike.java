package stage;

public class Bike extends Vehicle {
	
	private static final String BIKE_SRC = "assets/bike.png";
	private static final float SPEED = 0.2f;
	
	private static final int LEFT_END = 24;
	private static final int RIGHT_END = 1000;
	
	public Bike(float x, float y, int direction) {
		super(BIKE_SRC, x, y, SPEED, direction, new String[] { Sprite.HAZARD });
	}
	
	/**
	 * Reverse the direction if x reaches the end.
	 * @param x Tell if it reaches the end.
	 * @return the same x.
	 */
	@Override
	public float validateX(float x) {
		if(getDirection() == -1 && x <= LEFT_END ||
			getDirection() == 1 && x >= RIGHT_END) {
			
			reverseDirection();
		}
		return x;
	}
	
}
