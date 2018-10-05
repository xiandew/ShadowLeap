package stage;

public class TreeTile extends Tile {
	private static final String TREETILE_SRC = "assets/tree.png";
	
	public TreeTile(float x, float y) {
		super(TREETILE_SRC, x, y, new String[] {Sprite.SOLID});
	}

}
