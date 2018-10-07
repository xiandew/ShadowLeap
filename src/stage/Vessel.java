package stage;

import org.newdawn.slick.Input;

/**
 * Abstract Vessel class for the game. Extends Vehicle.
 */
public abstract class Vessel extends Vehicle {
	
	/**
	 * Create a vessel.
	 * @param vesselSrc The image path of the vessel.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param speed The speed of the vessel.
	 * @param direction The moving direction.
	 */
	public Vessel(String vesselSrc, float x, float y, float speed, int direction) {
		super(vesselSrc, x, y, speed, direction);
	}
	
	/**
	 * Carry the player if the player is riding the vessel.
	 * @param input Place holder to invoke super constructor.
	 * @param delta Make sure the same speed with different FPS.
	 */
	public void move(Input input, int delta) {
		super.move(input, delta);
		
		Player player = World.getPlayer();
		if(collides(player)) {
			player.setX(player.validX(player.getX() + getSpeed() * getDirection() * delta));
			
		}else if(player.getRidingVessel() == this) {
			player.resetRidingVessel();
		}
	}
	
}
