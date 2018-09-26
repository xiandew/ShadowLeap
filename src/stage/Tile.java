package stage;
import org.newdawn.slick.SlickException;

public abstract class Tile extends Sprite {
	
	public Tile(String imageSrc, float x, float y, boolean isHazard) throws SlickException {
		super(imageSrc, x, y, isHazard);
	}
}
