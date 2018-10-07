package stage;
import utilities.Movable;

import org.newdawn.slick.Input;

public abstract class Vehicle extends Sprite implements Movable {
	/** The speed of vehicles, in pixels per millisecond */
	private float speed;
	
	/** left: -1, right: 1 */
	private int direction;
	
	/**
	 * Initialise a vehicle with moving direction at (x, y).
	 */
	public Vehicle(String vehicleSrc, float x, float y,
									float speed, int direction) {
		super(vehicleSrc, x, y);
		this.speed = speed;
		this.direction = direction;
	}
	
	public Vehicle(String vehicleSrc, float x, float y,
									float speed, int direction, String[] tags) {
		super(vehicleSrc, x, y, tags);
		this.speed = speed;
		this.direction = direction;
	}
	
	public float validX(float x) {
		if(getDirection() == -1 && x < -getImage().getWidth() / 2 ||
			getDirection() == 1 && x > getImage().getWidth() / 2 + App.SCREEN_WIDTH) {
			
			return (App.SCREEN_WIDTH - x);
		}
		return x;
	}
	
	/**
	 * Update the movement of a vehicle.
	 * @param input Place holder
	 * @param delta Make sure the objects move at the same speed
	 */
	public void move(Input input, int delta) {
		setX((float) validX((getX() + getSpeed() * getDirection() * delta)));
	}
	
	/**
	 * reverse the direction
	 */
	public void reverseDirection() { this.direction *= (-1); }
	
	/**
	 * @return the speed
	 */
	public float getSpeed() { return speed; }

	/**
	 * @return the direction
	 */
	public int getDirection() { return direction; }
	
}
