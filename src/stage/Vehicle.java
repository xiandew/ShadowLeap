package stage;
import utilities.Movable;

import org.newdawn.slick.Input;

/**
 * Abstract Vehicle class for the game. Extends Sprite. Implements Movable.
 */
public abstract class Vehicle extends Sprite implements Movable {
	// The speed of vehicles, in pixels per millisecond
	private float speed;
	// left: -1, right: 1
	private int direction;
	
	/**
	 * Create a vehicle.
	 * @param vehicleSrc The image path of the vehicle.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param speed The speed of the vehicle.
	 * @param direction The moving direction.
	 */
	public Vehicle(String vehicleSrc, float x, float y,
									float speed, int direction) {
		super(vehicleSrc, x, y);
		this.speed = speed;
		this.direction = direction;
	}
	
	/**
	 * Create a vehicle with moving direction at (x, y).
	 * @param vehicleSrc The image path of the vehicle.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param speed The speed of the vehicle.
	 * @param direction The moving direction.
	 * @param tags The tags that the vehicle has.
	 */
	public Vehicle(String vehicleSrc, float x, float y,
									float speed, int direction, String[] tags) {
		super(vehicleSrc, x, y, tags);
		this.speed = speed;
		this.direction = direction;
	}
	
	/**
	 * Make sure the vehicle wrap around the screen.
	 */
	@Override
	public float validX(float x) {
		if(getDirection() == -1 && x < -getImage().getWidth() / 2 ||
			getDirection() == 1 && x > getImage().getWidth() / 2 + App.SCREEN_WIDTH) {
			
			return (App.SCREEN_WIDTH - x);
		}
		return x;
	}
	
	/**
	 * Update the movement of a vehicle.
	 */
	@Override
	public void move(Input input, int delta) {
		setX((float) validX((getX() + getSpeed() * getDirection() * delta)));
	}
	
	/**
	 * reverse the direction of the vehicle.
	 */
	public void reverseDirection() { this.direction *= (-1); }
	
	/**
	 * @return the speed.
	 */
	public float getSpeed() { return speed; }

	/**
	 * @return the direction.
	 */
	public int getDirection() { return direction; }
	
}
