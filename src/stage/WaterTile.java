package stage;

public class WaterTile extends Tile {
	private static final String WATERTILE_SRC = "assets/water.png";
	
	public WaterTile(float x, float y) {
		super(WATERTILE_SRC, x, y, new String[] {Sprite.HAZARD});
	}

}
