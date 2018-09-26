package stage;

import org.newdawn.slick.SlickException;

public class Hole extends Sprite {
	
	private static final String FROG_SRC = "assets/frog.png";
	private static final boolean HAZARD = false;
	
	private static final int HOLES_WIDTH = 96;
	private static final int HOLES_HEIGHT = 48;
	private static final int HOLES_SEPARATION = 192;
	private static final int FIRST_HOLE_BOTTOM = 72;
	private static final int FIRST_HOLE_XOFFSET = 72;
	
	public Hole(float x, float y) throws SlickException {
		super(FROG_SRC, x, y, HAZARD);
	}
	
	public void initialHoles() {
		
		
	}
	
}
