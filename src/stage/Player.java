package stage;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.Movable;

public class Player extends Sprite implements Movable {
	
	private static final String PLAYER_SRC = "assets/frog.png";
	private static final String LIVES_SRC = "assets/lives.png";
	
	/** starting point of the player */
	private static final int INITIAL_X = 512;
	private static final int INITIAL_Y = 720;
	private static final int INITIAL_LIVES = 3;
	
	private static final int INITIAL_LIVES_X = 24;
	private static final int INITIAL_LIVES_Y = 744;
	private static final int LIVES_SEPARATION = 32;
	
	private static final boolean HAZARD = false;
	
	private int lives;
	private Image livesImg = new Image(LIVES_SRC);
	
	private Sprite ridingVessel;
	
	public Player() throws SlickException {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y, HAZARD);
		this.lives = INITIAL_LIVES;
		this.setRidingVessel(null);
	}
	
	public void render() {
		super.render();
		for(int i=0; i<lives; i++) {
			livesImg.drawCentered(INITIAL_LIVES_X + LIVES_SEPARATION * i,
															INITIAL_LIVES_Y);
		}
	}
	
	/**
	 * Validate the x before update.
	 */
	public float validateX(float x) {
		if(x <= 0 || x >= App.SCREEN_WIDTH) {
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
			setY(getY() - World.TILE_WIDTH);
		}
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			setY(getY() + World.TILE_WIDTH);
		}
	}
	
	private void checkState() {
		if(this.lives <= 0) {
			System.exit(0);
		}
		if(this.getX() < World.TILE_WIDTH/2 ||
				this.getX() > App.SCREEN_WIDTH - World.TILE_WIDTH/2) {
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

	/**
	 * @return the ridingVessel
	 */
	public Sprite getRidingVessel() {
		return ridingVessel;
	}

	/**
	 * @param ridingVessel the ridingVessel to set
	 */
	public void setRidingVessel(Sprite ridingVessel) {
		this.ridingVessel = ridingVessel;
	}
}
