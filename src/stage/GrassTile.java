package stage;

import org.newdawn.slick.SlickException;

public class GrassTile extends Tile {
	private static final String GRASSTILE_SRC = "assets/grass.png";
	private static final boolean HAZARD = false;
	
	public GrassTile(float x, float y) throws SlickException {
		super(GRASSTILE_SRC, x, y, HAZARD);
	}

}
