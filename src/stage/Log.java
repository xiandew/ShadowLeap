package stage;

/**
 * Log class for the game. Extends Vessel.
 */
public class Log extends Vessel {

	private static final String LOG_SRC = "assets/log.png";
	private static final float SPEED = 0.1f;
	
	/**
	 * Create a log with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Log(float x, float y, int direction) {
		super(LOG_SRC, x, y, SPEED, direction);
	}

}
