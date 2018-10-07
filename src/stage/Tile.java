package stage;

/**
 * Abstract Tile class for the game. Extends Sprite.
 */
public abstract class Tile extends Sprite {
	
	/**
	 * Create a tile.
	 * @param imageSrc The image path of the tile.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 */
	public Tile(String imageSrc, float x, float y) {
		super(imageSrc, x, y);
	}
	/**
	 * Create a tile.
	 * @param imageSrc The image path of the tile.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 * @param tags Tags that the tile has.
	 */
	public Tile(String imageSrc, float x, float y, String[] tags) {
		super(imageSrc, x, y, tags);
	}
}
