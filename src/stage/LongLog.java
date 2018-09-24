package stage;

import org.newdawn.slick.SlickException;

public class LongLog extends Vessel {
	private static final String LONGLOG_SRC = "assets/longlog.png";
	private static final float SPEED = 0.07f;
	
	public LongLog(float x, float y, int direction) throws SlickException {
		super(LONGLOG_SRC, x, y, SPEED, direction);
	}

}
