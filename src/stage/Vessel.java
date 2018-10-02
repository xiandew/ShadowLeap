package stage;

import org.newdawn.slick.Input;
import utilities.Rideable;

public abstract class Vessel extends Vehicle implements Rideable {

	private static final boolean HAZARD = false;
	
	public Vessel(String vehicleSrc, float x, float y,
										float speed, int direction) {
		super(vehicleSrc, x, y, speed, direction, HAZARD);
	}
	
	/** carry the player if the player is above of the vessel */
	public void move(Input input, int delta) {
		super.move(input, delta);
		if(super.ifContact()) {
			carry(input, delta);
		}else if(World.getPlayer().getRidingVessel() == this) {
			World.getPlayer().setRidingVessel(null);
		}
	}
	
	public void carry(Input input, int delta) {
		World.getPlayer().setX(
				World.getPlayer().validateX(
						World.getPlayer().getX() +
						getSpeed() * getDirection() * delta));
	}
	
}
