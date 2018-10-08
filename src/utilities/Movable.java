package utilities;

import org.newdawn.slick.Input;

/**
 * Movable interface for the game.
 */
public interface Movable {
	/**
	 * Validate x before updating.
	 * @param x The x to validate.
	 * @return the validated x.
	 */
	public float validX(float x);
	
	/**
	 * Make a movement on the screen.
	 * @param input The input to control the movement.
	 * @param delta The delta makes sure the same speed with different FPS.
	 */
	public void move(Input input, int delta);
}
