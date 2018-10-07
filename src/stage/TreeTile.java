package stage;

/**
 * TreeTile class for the game. Extends Tile.
 */
public class TreeTile extends Tile {
	private static final String TREETILE_SRC = "assets/tree.png";
	
	/**
	 * Create a solid tree tile at the given position.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 */
	public TreeTile(float x, float y) {
		super(TREETILE_SRC, x, y, new String[] {Sprite.SOLID});
	}

}
