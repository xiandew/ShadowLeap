package stage;
import utilities.Movable;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Vehicle extends Sprite implements Movable {
	
	private float speed;
	// -1 for left, 1 for right
	private int direction;
	private boolean isHazard;
	
	// whether contact with the player
	private boolean isContact;
	
	/**
	 * Initialise a vehicle with moving direction at (x, y).
	 * @throws SlickException 
	 */
	public Vehicle(String vehicleSrc, float x, float y,
			float speed, int direction, boolean isHazard) throws SlickException {
		super(vehicleSrc, x, y);
		this.speed = speed;
		this.direction = direction;
		this.setHazard(isHazard);
	}
	
	public float validateX(float x) {
		if(x < - this.getImage().getWidth() / 2 ||
				x > App.SCREEN_WIDTH + this.getImage().getWidth() / 2) {
			return (App.SCREEN_WIDTH - x);
		}
		return x;
	}
	
	/**
	 * Update the movement of a bus.
	 * @param delta Make sure the objects move at the same speed
	 */
	public void move(Input input, int delta) {
		super.setX((float) validateX((super.getX() + speed * direction * delta)));
	}
	
	/**
	 * reverse the direction
	 */
	public void reverseDirection() {
		this.direction *= (-1);
	}
	
	/**
	 * @return the isHazard
	 */
	public boolean ifHazard() {
		return isHazard;
	}

	/**
	 * @param isHazard the isHazard to set
	 */
	public void setHazard(boolean isHazard) {
		this.isHazard = isHazard;
	}
	
	/**
	 * @return the isContact
	 */
	public boolean ifContact() {
		return isContact;
	}

	/**
	 * @param isContact the isContact to set
	 */
	public void setContact(boolean isContact) {
		this.isContact = isContact;
	}
	
}
