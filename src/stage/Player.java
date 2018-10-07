package stage;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;
import utilities.Movable;

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
	
	public Player() {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y);
		try {
			this.livesImg = new Image(LIVES_SRC);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * draws the player as well as the lives
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
	 * controls the movement of the player.
	 * @param input Left, Right, Up, Down.
	 * @param delta Make sure the same rate.
	 */
	@Override
	public void move(Input input, int delta) {
		
		if(collidesSolid(input)) {
			return;
		}
		
		this.setX(getBounds().getX());
		this.setY(getBounds().getY());
		
		checkPlayerState();
	}
	
	
	/**
	 * checks the state of the player.
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
	 * validates x before moving.
	 */
	public float validX(float x) {
		return (x <= World.TILE_WIDTH/2 ||
				x >= App.SCREEN_WIDTH - World.TILE_WIDTH/2) ? getX() : x;
	}
	private float validY(float y) {
		return (y <= 0 || y >= App.SCREEN_HEIGHT) ? getY() : y;
	}
	
	/**
	 * prevents the player from solid tiles i.e. the bulldozers and the trees
	 * @param input Left, Right, Up, Down.
	 * @return whether or not it is going to crash a solid tile.
	 */
	private boolean collidesSolid(Input input) {
		
		BoundingBox bounds = getBounds();
		
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			bounds.setX(validX(getX() - World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_RIGHT)) {
			bounds.setX(validX(getX() + World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_UP)) {
			bounds.setY(validY(getY() - World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			bounds.setY(validY(getY() + World.TILE_WIDTH));
		}
		
		for(Sprite sprite : World.getSprites()) {
			if((sprite.hasTag(Sprite.SOLID)) && sprite.collides(bounds)) {
				bounds.setX(getX());
				bounds.setY(getY());
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * resets the player at the starting point.
	 */
	public void resetPosition() {
		this.setX(INITIAL_X);
		this.setY(INITIAL_Y);
	}
	
	/**
	 * deducts the player's lives by one and reset its position.
	 */
	public void dieOnce() {
		Player.lives--;
		this.resetPosition();
	}
	
	/**
	 * awards the player an extra life and reset the extra life.
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
	 * set the riding vessel to null.
	 */
	public void resetRidingVessel() {
		this.ridingVessel = null;
	}
	
	/**
	 * makes an action corresponding to the contacting sprite.
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
