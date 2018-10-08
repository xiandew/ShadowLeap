package stage;
import org.newdawn.slick.Input;

/**
 * Bulldozer class for the game. Extends Vehicle. Labeled as solid.
 * Pushes the player when making contacts with it.
 */
public class Bulldozer extends Vehicle {
	
	private static final String BULLDOZER_SRC = "assets/Bulldozer.png";
	private static final float SPEED = 0.05f;
	
	/**
	 * Create a bulldozer with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Bulldozer(float x, float y, int direction) {
		super(BULLDOZER_SRC, x, y, SPEED, direction, new String[] { Sprite.SOLID });
	}
	
	/**
	 * Pushes the player if making contact with it
	 * @param input Place holder for override.
	 * @param delta Make sure the same speed with different FPS
	 */
	public void move(Input input, int delta) {
		super.move(input, delta);
		
		Player player = World.getPlayer();
		if(collides(player)) {
			
			player.setX(player.getX() + getSpeed() * getDirection() * delta);
		}
	}
}

