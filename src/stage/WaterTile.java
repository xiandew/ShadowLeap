package stage;

import org.newdawn.slick.SlickException;

public class WaterTile extends Tile {
	private static final String WATERTILE_SRC = "assets/water.png";
	private static final boolean HAZARD = true;
	
	public WaterTile(float x, float y) throws SlickException {
		super(WATERTILE_SRC, x, y, HAZARD);
	}

}
