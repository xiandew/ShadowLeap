import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Sprite {
	
	public static final String PLAYER_SRC = "assets/frog.png";
	
	/** the starting point of the player */
	public static final int START_X = 512;
	public static final int START_Y = 720;
	
	public Player() throws SlickException {
		super(PLAYER_SRC, START_X, START_Y);
	}
	
	/**
	 * Validate the x before update.
	 */
	public void setX(float x) {
		if(x < 0 || x > App.SCREEN_WIDTH) {
			return;
		}
		super.setX(x);
	}
	
	
	/**
	 * Control the movement of the player
	 * @param input Left, Right, Up, Down
	 * @param delta
	 */
	public void update(Input input, int delta) {
		
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			this.setX(this.getX() - World.SPRITE_WIDTH);
		}
		if(input.isKeyPressed(Input.KEY_RIGHT)) {
			this.setX(this.getX() + World.SPRITE_WIDTH);
		}
		if(input.isKeyPressed(Input.KEY_UP)) {
			
			/** Exit the game if the player reaches beyond
			 * the lower bound of pool.
			 */
			if((this.getY() - World.SPRITE_WIDTH) == World.WATER_LOWERBOUND) {
				System.exit(0);
			}
			
			this.setY(this.getY() - World.SPRITE_WIDTH);
		}
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			this.setY(this.getY() + World.SPRITE_WIDTH);
		}
		
	}
}
