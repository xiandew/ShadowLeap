package stage;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;
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
	
	/** Keep the lives in the next Stage */
	private static int lives = INITIAL_LIVES;
	
	private Image livesImg;
	
	/** the vessel that the player is riding */
	private Sprite ridingVessel = null;
	
	public Player() {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y);
		try {
			this.livesImg = new Image(LIVES_SRC);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		super.render();
		
		/** draw the lives */
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
		
		if(collidesSolid(input)) {
			return;
		}
		
		this.setX(getBounds().getX());
		this.setY(getBounds().getY());
		
		checkPlayerState();
	}
	
	
	/** check the state of the player */
	private void checkPlayerState() {
		
		/** exit if the player has no lives */
		if(Player.lives < 0) {
			System.exit(0);
		}
		
		/** die once if it is off screen */
		if( this.getX() < World.TILE_WIDTH/2 ||
			this.getX() > App.SCREEN_WIDTH - World.TILE_WIDTH/2) {
			dieOnce();
		}
		
		/** check whether the player is riding */
		for(Sprite sprite : World.getSprites()) {
			if(sprite instanceof Vessel && collides(sprite)) {
				setRidingVessel(sprite);
			}
		}
	}
	
	/**
	 * Validate x before update.
	 */
	public float validX(float x) {
		return (x <= World.TILE_WIDTH/2 ||
				x >= App.SCREEN_WIDTH - World.TILE_WIDTH/2) ? getX() : x;
	}
	private float validY(float y) {
		return (y <= 0 || y >= App.SCREEN_HEIGHT) ? getY() : y;
	}
	
	// Prevent the player from solid tiles i.e. the bulldozers and the trees
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
	
	public void resetPosition() {
		this.setX(INITIAL_X);
		this.setY(INITIAL_Y);
	}

	public void dieOnce() {
		Player.lives--;
		this.resetPosition();
	}
	
	private void lifeUp() {
		Player.lives++;
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
	
	public void onCollision(Sprite other) { 
	
		if(other.hasTag(Sprite.HAZARD) && getRidingVessel() == null) {
			dieOnce();
		}
		
		/** check whether hitting the extra life */
		if(other instanceof ExtraLife) {
			ExtraLife.resetExtraLife();
			lifeUp();
		}
		
		if(other instanceof Hole) {
			((Hole)other).setfilled();
		}
	}
}
