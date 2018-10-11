package stage;
import org.newdawn.slick.Input;

/**
 * Bulldozer class for the game. Extends Vehicle. Labeled as solid.
 * Pushes the player when making contacts with it.
 */
public class Bulldozer extends Vehicle {
	
	private static final String BULLDOZER_SRC = "assets/Bulldozer.png";
	private static final float SPEED = 0.05f;
	
	private int delta;
	
	/**
	 * Create a bulldozer with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Bulldozer(float x, float y, int direction) {
		super(BULLDOZER_SRC, x, y, SPEED, direction, new String[] { Sprite.SOLID });
	}
	
	@Override
	public void move(Input input, int delta) {
		super.move(input, delta);
		this.delta = delta;
	}
	
	/**
	 * Pushes the player when contacting it.
	 */
	@Override
	public void onCollision(Sprite other) {
		if(other instanceof Player) {
			other.setX(other.getX() + getSpeed() * getDirection() * delta);
		}
	}
}

