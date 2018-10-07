package stage;

/**
 * Bus class for the game. Extends Vehicle. Labeled as hazard.
 */
public class Bus extends Vehicle {
	
	private static final String BUS_SRC = "assets/bus.png";
	private static final float SPEED = 0.15f;
	
	/**
	 * Create a bus with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Bus(float x, float y, int direction) {
		super(BUS_SRC, x, y, SPEED, direction, new String[] { Sprite.HAZARD });
	}
}
