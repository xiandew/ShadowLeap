package stage;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.Movable;

public class Player extends Sprite implements Movable {
	
	public static final String PLAYER_SRC = "assets/frog.png";
	
	/** starting point of the player */
	private static final int INITIAL_X = 512;
	private static final int INITIAL_Y = 720;
	private static final int INITIAL_LIVES = 3;
	
	private int lives;
	
	public Player() throws SlickException {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y);
		this.lives = INITIAL_LIVES;
	}
	
	public void render() {
		super.render();
	}
	
	/**
	 * Validate the x before update.
	 */
	public float validateX(float x) {
		if(x < 0 || x > App.SCREEN_WIDTH) {
			return getX();
		}
		return x;
	}
	
	/**
	 * Control the movement of the player
	 * @param input Left, Right, Up, Down
	 * @param delta
	 */
	@Override
	public void move(Input input, int delta) {
		// check the state of the player before moving
		checkState();
		
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			setX(validateX(getX() - World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_RIGHT)) {
			setX(validateX(getX() + World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_UP)) {			
			setX(getY() - World.TILE_WIDTH);
		}
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			setX(getY() + World.TILE_WIDTH);
		}
	}
	
	private void checkState() {
		if(this.lives == 0) {
			System.exit(0);
		}
		if(this.getX() < 0 || this.getX() > App.SCREEN_WIDTH) {
			dieOnce();
		}
	}
	
	public void resetPosition() {
		this.setX(INITIAL_X);
		this.setY(INITIAL_Y);
	}

	public void dieOnce() {
		this.lives--;
		this.resetPosition();
	}
	
}
