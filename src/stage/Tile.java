package stage;

public abstract class Tile extends Sprite {
	
	public Tile(String imageSrc, float x, float y) {
		super(imageSrc, x, y);
	}
	
	public Tile(String imageSrc, float x, float y, String[] tags) {
		super(imageSrc, x, y, tags);
	}
}
