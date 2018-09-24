package stage;

import org.newdawn.slick.SlickException;

public class Racecar extends Vehicle {
	
	private static final String RACECAR_SRC = "assets/racecar.png";
	private static final float SPEED = 0.5f;
	private static final boolean HAZARD = true;

	public Racecar(float x, float y, int direction) throws SlickException {
		super(RACECAR_SRC, x, y, SPEED, direction, HAZARD);
	}

}
