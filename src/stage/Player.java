package stage;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.Movable;

/**
 * Player class for the game. Extends Sprite. Implements Movable.
 */
public class Player extends Sprite implements Movable {
	
	private static final String PLAYER_SRC = "assets/frog.png";
	private static final String LIVES_SRC = "assets/lives.png";
	
	// starting point of the player
	private static final int INITIAL_X = 512;
	private static final int INITIAL_Y = 720;
	
	private static final int INITIAL_LIVES = 3;
	private static final int INITIAL_LIVES_X = 24;
	private static final int INITIAL_LIVES_Y = 744;
	private static final int LIVES_SEPARATION = 32;
	
	// make lives static to keep it in the next Stage
	private static int lives = INITIAL_LIVES;
	private Image livesImg;
	
	// the vessel that the player is riding
	private Sprite ridingVessel = null;
	
	/**
	 * Create the player at the initial position with three initial lives.
	 */
	public Player() {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y);
		try {
			this.livesImg = new Image(LIVES_SRC);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw the player as well as the lives.
	 */
	public void render() {
		super.render();
		
		// draw the lives
		for(int i=0; i<lives; i++) {
			livesImg.drawCentered(
					INITIAL_LIVES_X + LIVES_SEPARATION * i, INITIAL_LIVES_Y);
		}
	}
	
	/**
	 * Control the movement of the player.
	 * @param input Left, Right, Up, Down.
	 * @param delta Make sure the same rate.
	 */
	@Override
	public void move(Input input, int delta) {
		
		float prevX = getX();
		float prevY = getY();
		
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			setX(validX(getX() - World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_RIGHT)) {
			setX(validX(getX() + World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_UP)) {
			setY(validY(getY() - World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			setY(validY(getY() + World.TILE_WIDTH));
		}
		
		// Prevent the player from solid tiles i.e. the bulldozers and the trees
		for(Sprite sprite : World.getSprites()) {
			if((sprite.hasTag(Sprite.SOLID)) && sprite.collides(this)) {
				setX(prevX);
				setY(prevY);
				break;
			}
		}
		
		checkPlayerState();
	}
	
	
	/**
	 * Check the state of the player.
	 */
	private void checkPlayerState() {
		
		// exit if the player has no lives
		if(Player.lives < 0) {
			System.exit(0);
		}
		
		// die once if it is off screen
		if( this.getX() < World.TILE_WIDTH/2 ||
			this.getX() > App.SCREEN_WIDTH - World.TILE_WIDTH/2) {
			dieOnce();
		}
		
		// check whether the player is riding
		for(Sprite sprite : World.getSprites()) {
			if(sprite instanceof Vessel && collides(sprite)) {
				ridingVessel = sprite;
			}
		}
	}
	
	/**
	 * Validate x before moving.
	 * @param x The x to validate.
	 * @return the validated x.
	 */
	public float validX(float x) {
		return (x <= World.TILE_WIDTH/2 ||
				x >= App.SCREEN_WIDTH - World.TILE_WIDTH/2) ? getX() : x;
	}
	
	// Validate y before moving
	private float validY(float y) {
		return (y <= 0 || y >= App.SCREEN_HEIGHT) ? getY() : y;
	}
	
	/**
	 * Reset the player at the starting point.
	 */
	public void resetPosition() {
		this.setX(INITIAL_X);
		this.setY(INITIAL_Y);
	}
	
	/**
	 * Deduct the player's lives by one and reset its position.
	 */
	public void dieOnce() {
		Player.lives--;
		this.resetPosition();
	}
	
	/**
	 * Award the player an extra life and reset the extra life.
	 */
	public void lifeUp() {
		Player.lives++;
		ExtraLife.resetExtraLife();
	}
	
	/**
	 * @return the riding vessel.
	 */
	public Sprite getRidingVessel() {
		return ridingVessel;
	}
	
	/**
	 * Set the riding vessel to null.
	 */
	public void resetRidingVessel() {
		this.ridingVessel = null;
	}
	
	/**
	 * Take an action corresponding to the contacting sprite.
	 * @param other The sprite that collides the player.
	 */
	public void onCollision(Sprite other) { 
	
		if(other.hasTag(Sprite.HAZARD) && getRidingVessel() == null) {
			dieOnce();
		}
		
		if(other instanceof ExtraLife) {
			lifeUp();
		}
		
		if(other instanceof Hole) {
			((Hole)other).setfilled();
		}
	}
}
