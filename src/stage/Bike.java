package stage;

/**
 * Bike class for the game. Extends Vehicle. Labeled as hazard.
 * Capable to bounce around when reaching the screen end.
 */
public class Bike extends Vehicle {
	
	private static final String BIKE_SRC = "assets/bike.png";
	private static final float SPEED = 0.2f;
	
	private static final int LEFT_END = 24;
	private static final int RIGHT_END = 1000;
	
	/**
	 * Creates a bike with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Bike(float x, float y, int direction) {
		super(BIKE_SRC, x, y, SPEED, direction, new String[] { Sprite.HAZARD });
	}
	
	/**
	 * Reverses the direction if x reaches the end.
	 * @param x Tell if the bike reaches the end.
	 * @return the same x. Place holder for override.
	 */
	@Override
	public float validX(float x) {
		if(getDirection() == -1 && x <= LEFT_END ||
			getDirection() == 1 && x >= RIGHT_END) {
			
			reverseDirection();
		}
		return x;
	}
	
}
