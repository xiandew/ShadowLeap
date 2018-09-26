package stage;

import org.newdawn.slick.SlickException;

public class TreeTile extends Tile {
	private static final String TREETILE_SRC = "assets/tree.png";
	private static final boolean HAZARD = true;
	
	public TreeTile(float x, float y) throws SlickException {
		super(TREETILE_SRC, x, y, HAZARD);
	}

}
