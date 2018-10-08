package stage;

/**
 * LongLog class for the game. Extends Vessel.
 */
public class LongLog extends Vessel {
	private static final String LONGLOG_SRC = "assets/longlog.png";
	private static final float SPEED = 0.07f;
	
	/**
	 * Create a long log with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public LongLog(float x, float y, int direction) {
		super(LONGLOG_SRC, x, y, SPEED, direction);
	}

}
