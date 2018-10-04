package stage;

public class Racecar extends Vehicle {
	
	private static final String RACECAR_SRC = "assets/racecar.png";
	private static final float SPEED = 0.5f;

	public Racecar(float x, float y, int direction) {
		super(RACECAR_SRC, x, y, SPEED, direction, new String[] { Sprite.HAZARD });
	}

}
