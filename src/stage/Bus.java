package stage;
import org.newdawn.slick.SlickException;

public class Bus extends Vehicle {
	
	private static final String BUS_SRC = "assets/bus.png";
	
	/* The speed of buses, 0.15 pixels per ms */
	private static final float SPEED = 0.15f;
	private static final boolean HAZARD = true;
	
	/**
	 * Initialise a bus with moving direction at (x, y).
	 */
	public Bus(float x, float y, int direction) throws SlickException {
		super(BUS_SRC, x, y, SPEED, direction, HAZARD);
	}
}
