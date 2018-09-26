package stage;

public class TreeTile extends Tile {
	private static final String TREETILE_SRC = "assets/tree.png";
	private static final boolean HAZARD = true;
	
	public TreeTile(float x, float y) {
		super(TREETILE_SRC, x, y, HAZARD);
	}

}
