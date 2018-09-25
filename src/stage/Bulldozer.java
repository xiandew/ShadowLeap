package stage;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Bulldozer extends Vehicle {
	
	private static final String BULLDOZER_SRC = "assets/Bulldozer.png";
	
	private static final float SPEED = 0.05f;
	private static final boolean HAZARD = false;
	
	public Bulldozer(float x, float y, int direction) throws SlickException {
		super(BULLDOZER_SRC, x, y, SPEED, direction, HAZARD);
	}
	
	// 
	public void move(Input input, int delta) {
		super.move(input, delta);
		if(super.ifContact()) {
			if(World.getPlayer().getY() == this.getY()) {
				World.getPlayer().setX(this.getX() + World.TILE_WIDTH);
			}else {
				World.getPlayer().resetPosition();
			}
		}
	}
}
