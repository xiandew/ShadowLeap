package stage;

public class Turtles extends Vessel {

	private static final String TURTLES_SRC = "assets/turtles.png";
	private static final float SPEED = 0.085f;
	
	public Turtles(float x, float y, int direction) {
		super(TURTLES_SRC, x, y, SPEED, direction);
	}

}
