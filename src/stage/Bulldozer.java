package stage;
import org.newdawn.slick.Input;

public class Bulldozer extends Vehicle {
	
	private static final String BULLDOZER_SRC = "assets/Bulldozer.png";
	
	private static final float SPEED = 0.05f;
	
	public Bulldozer(float x, float y, int direction) {
		super(BULLDOZER_SRC, x, y, SPEED, direction, new String[] { Sprite.SOLID });
	}
	
	/** Push the player if making contact with it */
	public void move(Input input, int delta) {
		super.move(input, delta);
		
		Player player = World.getPlayer();
		if(collides(player)) {
			
			player.setX(player.getX() + getSpeed() * getDirection() * delta);
		}
	}
}

