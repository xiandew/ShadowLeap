package stage;
import java.util.ArrayList;

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
	
	private static final int INITIAL_NLIVES = 3;
	private static final int INITIAL_LIVES_X = 24;
	private static final int INITIAL_LIVES_Y = 744;
	private static final int LIVES_SEPARATION = 32;
	
	// make lives static to keep it in the next Stage
	private static int numLives = INITIAL_NLIVES;
	private Image livesImage;
	
	// the vessel that the player is riding
	private Sprite ridingVessel;
	// record the previous position
	private float prevX;
	private float prevY;
	private ArrayList<Sprite> sprites;
	
	/**
	 * Create the player at the initial position with three initial lives.
	 * @param sprites 
	 */
	public Player(ArrayList<Sprite> sprites) {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y);
		this.sprites = sprites;
		try {
			this.livesImage = new Image(LIVES_SRC);
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
		for(int i=0; i<numLives; i++) {
			livesImage.drawCentered(
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
		
		prevX = getX();
		prevY = getY();
		
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
		
		checkPlayerState();
	}
	
	/**
	 * Validate x before moving.
	 * @param x The x to validate.
	 * @return the validated x.
	 */
	public float validX(float x) {
		return (x < World.TILE_WIDTH/2 ||
				x > App.SCREEN_WIDTH - World.TILE_WIDTH/2) ? getX() : x;
	}
	
	// Validate y before moving
	private float validY(float y) {
		return (y < World.TILE_WIDTH/2 ||
				y > App.SCREEN_HEIGHT - World.TILE_WIDTH/2) ? getY() : y;
	}
	
	/**
	 * Check the state of the player.
	 */
	private void checkPlayerState() {
		
		// Prevent the player from solid tiles i.e. the bulldozers and the trees
		for(Sprite sprite : sprites) {
			if((sprite.hasTag(Sprite.SOLID)) && sprite.collides(this)) {
				setX(prevX);
				setY(prevY);
				break;
			}
		}
		
		// exit if the player has no lives
		if(Player.numLives < 0) {
			System.exit(0);
		}
		
		// die once if it is off screen
		if( getX() < World.TILE_WIDTH/2 ||
			getX() > App.SCREEN_WIDTH - World.TILE_WIDTH/2) {
			dieOnce();
		}
		
		// reset the riding vessel
		ridingVessel = null;
	}
	
	/**
	 * Reset the player at the starting point.
	 */
	public void resetPosition() {
		setX(INITIAL_X);
		setY(INITIAL_Y);
	}
	
	/**
	 * Deduct the player's lives by one and reset its position.
	 */
	public void dieOnce() {
		numLives--;
		resetPosition();
	}
	
	/**
	 * Award the player an extra life and reset the extra life.
	 */
	public void lifeUp() {
		numLives++;
	}
	
	@Override
	public void onCollision(Sprite other) {
		
		if(other.hasTag(Sprite.HAZARD) && ridingVessel == null) {
			dieOnce();
		}
		
		if(other instanceof ExtraLife && other.collides(this)) {
			lifeUp();
		}
	}

	/**
	 * @param ridingVessel the ridingVessel to set
	 */
	public void setRidingVessel(Vessel ridingVessel) {
		this.ridingVessel = ridingVessel;
	}
}
