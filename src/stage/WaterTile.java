package stage;

/**
 * WaterTile class for the game. Extends Tile.
 */
public class WaterTile extends Tile {
	private static final String WATERTILE_SRC = "assets/water.png";
	
	/**
	 * Create a hazard water tile at the given position.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 */
	public WaterTile(float x, float y) {
		super(WATERTILE_SRC, x, y, new String[] {Sprite.HAZARD});
	}

}
