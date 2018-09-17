import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
	
	public static final String WATERTILE_SRC = "assets/water.png";
	public static final String GRASSTILE_SRC = "assets/grass.png";
	
	/** sprite width, in pixels */
	public static final int SPRITE_WIDTH = 48;
	
	/** the y location of upper bound of the pool */
	public static final int WATER_UPPERBOUND = 48;
	/** the y location of lower bound of the pool */
	public static final int WATER_LOWERBOUND = 336;
	
	/** the y location of the upper grass tiles */
	public static final int GRASS_UPPER = 384;
	/** the y location of the lower grass tiles */
	public static final int GRASS_LOWER = 672;
	
	/** number of rows of buses */
	public static final int BUS_ROWS = 5;	
	/** the y locations of buses */
	public static final int[] BUS_YLOCATIONS =
			new int[] {432, 480, 528, 576, 624};
	/** the x offsets of buses, in pixels */
	public static final int[] BUS_XOFFSETS =
			new int[] {48, 0, 64, 128, 250};
	/** separation distances between buses in a row, in tiles */
	public static final float[] BUS_SEPARATIONS =
			new float[] {6.5f, 5, 12, 5, 6.5f};
	/** the directions of each row of buses, 1 for right, -1 for left */
	public static final int[] BUS_DIRECTIONS =
			new int[] {-1, 1, -1, 1, -1};
	
	private Tile waterTile;
	private Tile grassTile;
	private Player player;
	private ArrayList<Bus> buses;
	
	public World() throws SlickException {
		// Perform initialisation logic
		
		/** create tiles */
		this.waterTile = new Tile(WATERTILE_SRC);
		this.grassTile = new Tile(GRASSTILE_SRC);
		
		/** create the player */
		this.player = new Player();
		
		/** create buses row by row */
		this.buses = new ArrayList<Bus>();
		for(int row = 0; row < BUS_ROWS; row++) {
			for(int x = BUS_XOFFSETS[row];
					x < App.SCREEN_WIDTH; x += BUS_SEPARATIONS[row] * SPRITE_WIDTH) {
				this.buses.add(new Bus(x, BUS_YLOCATIONS[row], BUS_DIRECTIONS[row]));
			}
		}
		
	}
	
	public void update(Input input, int delta) {
		// Update all of the sprites in the game
		
		// Update the movement of the player
		this.player.update(input, delta);
		
		// Update the movement of buses and decide whether the player collides a bus
		for(int i = 0; i < this.buses.size(); i++) {
			this.buses.get(i).update(delta);
			this.player.contactSprite(this.buses.get(i));
		}
		
	}
	
	public void render(Graphics g) {
		// Draw all of the sprites in the game
		
		// Render the water tiles
		for(int depth = WATER_LOWERBOUND; depth > WATER_UPPERBOUND; depth -= SPRITE_WIDTH) {
			for(int width = 0; width < App.SCREEN_WIDTH; width += SPRITE_WIDTH) {
				this.waterTile.render(width, depth);
			}
		}
		
		// Render the grass titles
		for(int width = 0; width < App.SCREEN_WIDTH; width += SPRITE_WIDTH) {
			this.grassTile.render(width, GRASS_UPPER);
		}
		for(int width = 0; width < App.SCREEN_WIDTH; width += SPRITE_WIDTH) {
			this.grassTile.render(width, GRASS_LOWER);
		}
		
		// Render the player
		this.player.render();
		
		// Render buses
		for(Bus bus : this.buses) {
			bus.render();
		}
		
	}
}
