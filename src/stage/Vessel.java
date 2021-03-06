package stage;

import org.newdawn.slick.Input;

/**
 * Abstract Vessel class for the game. Extends Vehicle.
 */
public abstract class Vessel extends Vehicle {
	
	private int delta;

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
	
	@Override
	public void move(Input input, int delta) {
		super.move(input, delta);
		this.delta = delta;
		
	}
	
	/**
	 * Carry the player if the player is riding the vessel.
	 */
	@Override
	public void onCollision(Sprite other) {
		if(other instanceof Player) {
			
			Player player = (Player) other;
			player.setRidingVessel(this);
			player.setX(player.validX(player.getX() + getSpeed() * getDirection() * delta));
		}
	}
}
