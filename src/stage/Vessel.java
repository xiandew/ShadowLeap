package stage;

import org.newdawn.slick.SlickException;
import utilities.Rideable;

public abstract class Vessel extends Vehicle implements Rideable {

	private static final boolean HAZARD = false;
	
	public Vessel(String vehicleSrc, float x, float y,
			float speed, int direction) throws SlickException {
		super(vehicleSrc, x, y, speed, direction, HAZARD);
	}
	
}
