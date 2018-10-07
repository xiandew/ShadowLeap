package stage;

/**
 * The GrassTile class for the game. Extends Tile. Serves as background.
 */
public class GrassTile extends Tile {
	private static final String GRASSTILE_SRC = "assets/grass.png";
	
	/**
	 * Create a grass tile at the given point.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public GrassTile(float x, float y) {
		super(GRASSTILE_SRC, x, y);
	}

}
