package stage;

import org.newdawn.slick.Input;

import utilities.Movable;
import utilities.Rideable;

public abstract class Vessel extends Vehicle implements Rideable {
	
	public Vessel(String vehicleSrc, float x, float y, float speed, int direction) {
		super(vehicleSrc, x, y, speed, direction);
	}
	
	/**
	 * carry the player if the player is above of the vessel
	 * @param input Pass it to super constructor as a place holder
	 * @param delta Make sure the same rate with different FPS
	 */
	public void move(Input input, int delta) {
		super.move(input, delta);
		
		Player player = World.getPlayer();
		if(collides(player)) {
			carry(player, delta);
			
		}else if(player.getRidingVessel() == this) {
			player.setRidingVessel(null);
		}
	}
	
	public void carry(Sprite sprite, int delta) {
		sprite.setX(((Movable)sprite).validateX(sprite.getX() + getSpeed() * getDirection() * delta));
	}
	
}
