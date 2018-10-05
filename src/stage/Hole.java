package stage;

public class Hole extends Sprite {
	
	private static final String FROG_SRC = "assets/frog.png";
	
	private static final int HOLES_SEPARATION = 192;
	private static final int HOLES_Y = 48;
	private static final int FIRST_HOLE_X = 120;
	
	private static int numFilledHoles = 0;
	private boolean isfilled = false;
	
	public Hole(float x, float y) {
		super(FROG_SRC, x, y);
	}
	
	public void render() {
		if(isfilled) {
			super.render();
		}
	}
	
	public void setfilled() {
		Player player = World.getPlayer();
		if(isfilled) {
			player.dieOnce();
			return;
		}
		isfilled = true;
		player.resetPosition();
		numFilledHoles++;
	}
	
	public static void initialHoles() {
		
		for(int i=0; i<World.NUM_HOLES; i++) {
			
			int holeX = FIRST_HOLE_X + i * HOLES_SEPARATION;
			World.getSprites().add(new Hole(holeX, HOLES_Y));
		}
		numFilledHoles = 0;
	}
	
	/**
	 * @return the numFilledHoles
	 */
	public static int getNumFilledHoles() {
		return numFilledHoles;
	}
}
