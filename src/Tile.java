import org.newdawn.slick.SlickException;

public class Tile extends Sprite {
	
	public Tile(String imageSrc) throws SlickException {
		super(imageSrc);
	}
	
	/**
	 * This render is for drawing tiles in different places.
	 */
	public void render(float x, float y) {
		this.getImage().drawCentered(x, y);
	}
	
}
