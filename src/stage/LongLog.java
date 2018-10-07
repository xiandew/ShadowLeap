package stage;

/**
 * LongLog class for the game. Extends Vessel.
 * @param x The initial x coordinate.
 * @param y The initial y coordinate.
 * @param direction The moving direction.
 */
public class LongLog extends Vessel {
	private static final String LONGLOG_SRC = "assets/longlog.png";
	private static final float SPEED = 0.07f;
	
	public LongLog(float x, float y, int direction) {
		super(LONGLOG_SRC, x, y, SPEED, direction);
	}

}
