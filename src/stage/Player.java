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
	
	/** Keep the lives in the next Stage */
	private static int lives = INITIAL_LIVES;
	
	private Image livesImg;
	
	/** the vessel that the player is riding */
	private Sprite ridingVessel = null;
	
	/** Prevent the player from solid tiles i.e. the bulldozers and the trees*/
	private Player guard;
	
	public Player() {
		super(PLAYER_SRC, INITIAL_X, INITIAL_Y, HAZARD);
		try {
			this.livesImg = new Image(LIVES_SRC);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Player(Player player) {
		super(PLAYER_SRC, player.getX(), player.getY(), HAZARD);
	}
	
	public void render() {
		super.render();
		for(int i=0; i<lives; i++) {
			livesImg.drawCentered(
					INITIAL_LIVES_X + LIVES_SEPARATION * i, INITIAL_LIVES_Y);
		}
	}
	
	/**
	 * Control the movement of the player
	 * @param input Left, Right, Up, Down
	 * @param delta
	 */
	@Override
	public void move(Input input, int delta) {
		
		checkPlayerState();
		
		if(!canGuardMove(input)) {
			return;
		}
		
		this.setX(guard.getX());
		this.setY(guard.getY());
	}
	
	
	/** check the state of the player before moving */
	private void checkPlayerState() {
		if(Player.lives < 0) {
			System.exit(0);
		}
		if(this.getX() < World.TILE_WIDTH/2 ||
				this.getX() > App.SCREEN_WIDTH - World.TILE_WIDTH/2) {
			dieOnce();
		}
	}
	
	/**
	 * Validate the x before update.
	 */
	public float validateX(float x) {
		if(x < World.TILE_WIDTH/2 ||
				x > App.SCREEN_WIDTH - World.TILE_WIDTH/2) {
			return getX();
		}
		return x;
	}
	
	/** prevent the player from crashing into the bulldozer */
	private boolean canGuardMove(Input input) {
		guard = new Player(this);
		
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			guard.setX(validateX(getX() - World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_RIGHT)) {
			guard.setX(validateX(getX() + World.TILE_WIDTH));
		}
		if(input.isKeyPressed(Input.KEY_UP)) {			
			guard.setY(getY() - World.TILE_WIDTH);
		}
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			guard.setY(getY() + World.TILE_WIDTH);
		}
		
		for(Sprite sprite : World.getSprites()) {
			if((sprite instanceof Bulldozer || sprite instanceof TreeTile) &&
													sprite.collides(guard)) {
				return false;
			}
		}
		return true;
		
	}
	
	public void resetPosition() {
		this.setX(INITIAL_X);
		this.setY(INITIAL_Y);
	}

	public void dieOnce() {
		Player.lives--;
		this.resetPosition();
	}
	
	public void lifeUp() {
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
	
}
