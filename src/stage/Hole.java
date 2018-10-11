package stage;

/**
 * The Hole class for the game. Extends Sprite.
 * Game over when all of the holes are filled.
 */
public class Hole extends Sprite {
	/** number of holes */
	public static final int NUM_HOLES = 5;
	
	/** the separation between holes */
	public static final int HOLES_SEPARATION = 192;
	/** the y coordinates of all holes */
	public static final int HOLES_Y = 48;
	/** the x offset of the first hole */
	public static final int FIRST_HOLE_X = 120;
	
	private static final String FROG_SRC = "assets/frog.png";
	
	private static int numFilledHoles = 0;
	private boolean isFilled = false;
	
	/**
	 * Create a hole at the given point.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public Hole(float x, float y) {
		super(FROG_SRC, x, y);
	}
	
	/**
	 * render the frog in the hole when it has been filled.
	 */
	@Override
	public void render() {
		if(isFilled()) {
			super.render();
		}
	}
	
	/**
	 * @return the isFilled.
	 */
	public boolean isFilled() { return isFilled; }
	
	/**
	 * Fill a hole if it has not been filled.
	 */
	public void setFilled() {
		if(isFilled) {
			return;
		}
		isFilled = true;		
		numFilledHoles ++;
	}
	
	/**
	 * @return the number of filled holes.
	 */
	public static int getNumFilledHoles() { return numFilledHoles; }
	
	/**
	 * reset the number of filled holes to 0.
	 */
	public static void resetNumFilledHoles() { numFilledHoles = 0; }
}
