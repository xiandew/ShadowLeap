package stage;
import utilities.Movable;

import org.newdawn.slick.Input;

public abstract class Vehicle extends Sprite implements Movable {
	/** The speed of vehicles, in pixels per millisecond */
	private float speed;
	
	/** -1 for left, 1 for right */
	private int direction;
	
	/**
	 * Initialise a vehicle with moving direction at (x, y).
	 */
	public Vehicle(String vehicleSrc, float x, float y,
							float speed, int direction, boolean isHazard) {
		super(vehicleSrc, x, y, isHazard);
		this.speed = speed;
		this.direction = direction;
	}
	
	
	public float validateX(float x) {
		if(x < - this.getImage().getWidth() / 2 && getDirection() == -1 ||
				x > App.SCREEN_WIDTH +
						this.getImage().getWidth() / 2 && getDirection() == 1) {
			return (App.SCREEN_WIDTH - x);
		}
		return x;
	}
	
	/**
	 * Update the movement of a vehicle.
	 * @param delta Make sure the objects move at the same speed
	 */
	public void move(Input input, int delta) {
		super.setX((float) validateX((super.getX() +
									getSpeed() * getDirection() * delta)));
	}
	
	/**
	 * reverse the direction
	 */
	public void reverseDirection() {
		this.direction *= (-1);
	}
	
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}
	
}
