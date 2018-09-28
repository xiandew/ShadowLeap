package stage;

public class Turtles extends Vessel {

	private static final String TURTLES_SRC = "assets/turtles.png";
	private static final float SPEED = 0.085f;
	
	private static final double TO_SEC = 1E9;
	private static final int BREATH_TIME = 7;
	private static final int DIVE_TIME = 2;
	
	private static long timeAppear;
	
	public Turtles(float x, float y, int direction) {
		super(TURTLES_SRC, x, y, SPEED, direction);
		timeAppear = System.nanoTime();
	}
	
	public void render() {
		int timeSinceAppear = (int) ((System.nanoTime() - timeAppear) / TO_SEC);
		if(timeSinceAppear < BREATH_TIME) {
			super.render();
			return;
		}else if(timeSinceAppear >= BREATH_TIME + DIVE_TIME){
			timeAppear = System.nanoTime();
		}
		
	}
	

}
