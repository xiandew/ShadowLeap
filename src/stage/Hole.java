package stage;

/**
 * The Hole class for the game. Extends Sprite.
 * Game over when all of the holes are filled.
 * Each hole can only be filled once.
 * @param x The x coordinate.
 * @param y The y coordinate.
 */
public class Hole extends Sprite {
	
	private static final String FROG_SRC = "assets/frog.png";
	
	private static final int HOLES_SEPARATION = 192;
	private static final int HOLES_Y = 48;
	private static final int FIRST_HOLE_X = 120;
	
	private static int numFilledHoles = 0;
	private boolean filled = false;
	
	public Hole(float x, float y) {
		super(FROG_SRC, x, y);
	}
	
	
	public void render() {
		if(filled) {
			super.render();
		}
	}
	
	public void setfilled() {
		filled = true;
		numFilledHoles++;
	}
	
	public static void initialHoles() {
		numFilledHoles = 0;
		
		for(int i=0; i<World.NUM_HOLES; i++) {
			
			int holeX = FIRST_HOLE_X + i * HOLES_SEPARATION;
			World.getSprites().add(new Hole(holeX, HOLES_Y));
		}
		
	}
	
	/**
	 * @return the number of filled holes
	 */
	public static int getNumFilledHoles() { return numFilledHoles; }
	
	/**
	 * @return the filled
	 */
	public boolean isFilled() { return filled; }
}
