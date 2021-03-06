package stage;

/**
 * Racecar class for the game. Extends Vehicle. Labeled as hazard.
 */
public class Racecar extends Vehicle {
	
	private static final String RACECAR_SRC = "assets/racecar.png";
	private static final float SPEED = 0.5f;

	/**
	 * Create a race car with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Racecar(float x, float y, int direction) {
		super(RACECAR_SRC, x, y, SPEED, direction, new String[] { Sprite.HAZARD });
	}

}
