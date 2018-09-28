package stage;

import utilities.BoundingBox;

public class Hole extends Sprite {
	
	private static final String FROG_SRC = "assets/frog.png";
	private static final boolean HAZARD = false;
	
	private static final int HOLES_WIDTH = 96;
	private static final int HOLES_HEIGHT = 48;
	private static final int HOLES_SEPARATION = 192;
	private static final int HOLES_Y = 48;
	private static final int FIRST_HOLE_X = 120;
	
	private static int numFilledHoles = 0;
	private boolean isfilled = false;
	private BoundingBox bounds;
	
	public Hole(float x, float y) {
		super(FROG_SRC, x, y, HAZARD);
	}
	
	public void render() {
		if(isfilled) {
			super.render();
		}
	}
	
	public void setfilled() {
		if(isfilled) {
			World.getPlayer().dieOnce();
			return;
		}
		isfilled = true;
		World.getPlayer().resetPosition();
		numFilledHoles++;
	}
	
	public static void initialHoles() {
		for(int i=0; i<World.NUM_HOLES; i++) {
			World.getSprites().add(
					new Hole(FIRST_HOLE_X + i * HOLES_SEPARATION, HOLES_Y));
		}
		numFilledHoles = 0;
	}
	
	public boolean collides(Sprite player) {
		bounds = new BoundingBox(
				this.getX(), this.getY(), HOLES_WIDTH, HOLES_HEIGHT);
		BoundingBox playerBounds = new BoundingBox(
				player.getImage(), player.getX(), player.getY());
		return bounds.intersects(playerBounds);
	}

	/**
	 * @return the numFilledHoles
	 */
	public static int getNumFilledHoles() {
		return numFilledHoles;
	}
}
