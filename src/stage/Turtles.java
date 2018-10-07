package stage;

public class Turtles extends Vessel {

	private static final String TURTLES_SRC = "assets/turtles.png";
	private static final float SPEED = 0.085f;
	
	// Nanoseconds `divide` TO_SEC = seconds
	private static final long TO_SEC = (long) 1E9;
	// time in seconds
	private static final int BREATH_TIME = 7;
	private static final int DIVE_TIME = 2;
	
	private static long lastSurfaceTime;
	
	public Turtles(float x, float y, int direction) {
		super(TURTLES_SRC, x, y, SPEED, direction);
		lastSurfaceTime = System.nanoTime();
	}
	
	public void render() {
		long timeSinceAppear = (System.nanoTime() - lastSurfaceTime) / TO_SEC;
		
		/** shows above the water if the the breath time has not passed */
		if(timeSinceAppear < BREATH_TIME) {
			super.render();
			return;
		
		/** when the turtle is to resurface */
		}else if(timeSinceAppear >= BREATH_TIME + DIVE_TIME) {
			lastSurfaceTime = System.nanoTime();
		
		/** when diving and the player is above of it */
		}else if(World.getPlayer().getRidingVessel() == this){
			World.getPlayer().setRidingVessel(null);
			World.getPlayer().dieOnce();
		}
		
	}
	

}
