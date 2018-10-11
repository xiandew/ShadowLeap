package stage;

/**
 * Turtles class for the game. Extends Vessel.
 * Repeatedly appear on the surface for 7 seconds and then dive for 2 seconds.
 */
public class Turtles extends Vessel {
	private static final String TURTLES_SRC = "assets/turtles.png";
	private static final float SPEED = 0.085f;
	
	// Nanoseconds `divide` TO_SEC = seconds
	public static final long TO_SEC = (long) 1E9;
	// time in seconds
	private static final int BREATH_TIME = 7;
	private static final int DIVE_TIME = 2;
	
	private static long lastAppearTime;
	private boolean isDiving = false;
	
	/**
	 * Create a turtles object with a starting point and the moving direction.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param direction The moving direction.
	 */
	public Turtles(float x, float y, int direction) {
		super(TURTLES_SRC, x, y, SPEED, direction);
		lastAppearTime = System.nanoTime();
	}
	
	/**
	 * Occasionally render the turtles object.
	 */
	public void render() {
		long timeSinceAppear = (System.nanoTime() - lastAppearTime) / TO_SEC;
		
		// shows above the water if the the breath time has not passed
		if(timeSinceAppear < BREATH_TIME) {
			super.render();
			isDiving = false;
			return;
		}else if(timeSinceAppear >= BREATH_TIME + DIVE_TIME) {
			lastAppearTime = System.nanoTime();
		}else{
			isDiving = true;
		}
		
	}
	
	/**
	 * when diving and the player is above of it, player loses a life.
	 */
	@Override
	public void onCollision(Sprite other) {
		
		if(other instanceof Player) {
			Player player = (Player) other;
			if(isDiving) {
				player.dieOnce();
			}else {
				super.onCollision(player);
			}
		}
	}
}
