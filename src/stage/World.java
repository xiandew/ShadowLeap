package stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import utilities.Movable;

/**
 * The World class for the game.
 * Handles the renders and the updates of all objects
 */
public class World {
	
	/** tile width, in pixels */
	public static final int TILE_WIDTH = 48;
	
	// level data
	private static final String[] LEVEL_DATA =
			new String[] {"assets/levels/0.lvl", "assets/levels/1.lvl"};
	// level data indexes
	private static final int INDEX_OBJECT    = 0;
	private static final int INDEX_X         = 1;
	private static final int INDEX_Y         = 2;
	private static final int INDEX_DIRECTION = 3;
	
	private int currentLevel = 0;
	
	// all sprites
	private ArrayList<Sprite> sprites;
	
	/**
	 * Creates the world.
	 */
	public World() {
		initialiseWorld();
	}
	
	/**
	 * Initialises all objects of the game.
	 */
	public void initialiseWorld() {
		
		readLevelData();
		sprites.add(new Player(sprites));
		sprites.add(new ExtraLife(sprites));
		initialiseHoles();
	}
	
	/**
	 * Update the movements of all movable sprites and detect collisions.
	 * @param input The input to control the movement.
	 * @param delta The delta makes sure the same speed with different FPS.
	 */
	public void update(Input input, int delta) {
		
		// Update the movements of movable sprites
		for(Sprite sprite : sprites) {
			if(sprite instanceof Movable) {
				((Movable) sprite).move(input, delta);
			}
		}
		
		// loop over all pairs of sprites and test for intersection
		for (Sprite sprite1: sprites) {
			for (Sprite sprite2: sprites) {
				if (sprite1 != sprite2 && sprite1.collides(sprite2)) {
					sprite1.onCollision(sprite2);
				}
			}
		}
		
		// level up when all of the holes are filled
		if(Hole.getNumFilledHoles() == Hole.NUM_HOLES) {
			levelUp();
		}
		
	}
	
	/**
	 * Render all sprites.
	 * @param g The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) {
		for(Sprite sprite: sprites) {
			sprite.render();
		}
	}
	
	// change the level data. Exit if the second level completed
	private void levelUp() {
		currentLevel ++;
		if(currentLevel > 1) {
			System.exit(0);
		}
		
		initialiseWorld();
	}
	
	private void readLevelData() {
		sprites = new ArrayList<>();
		try (BufferedReader br =
				new BufferedReader(new FileReader(LEVEL_DATA[currentLevel]))) {
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				
				int x = Integer.parseInt(data[INDEX_X]);
				int y = Integer.parseInt(data[INDEX_Y]);
				Integer direction = parseDirection(data);
				
				switch(data[INDEX_OBJECT]) {
					case("water"):
						sprites.add(new WaterTile(x, y));
						break;
					case("grass"):
						sprites.add(new GrassTile(x, y));
						break;
					case("tree"):
						sprites.add(new TreeTile(x, y));
						break;
					case("bus"):
						sprites.add(new Bus(x, y, direction));
						break;
					case("bulldozer"):
						sprites.add(new Bulldozer(x, y, direction));
						break;
					case("bike"):
						sprites.add(new Bike(x, y, direction));
						break;
					case("racecar"):
						sprites.add(new Racecar(x, y, direction));
						break;
					case("log"):
						sprites.add(new Log(x, y, direction));
						break;
					case("longLog"):
						sprites.add(new LongLog(x, y, direction));
						break;
					case("turtle"):
						sprites.add(new Turtles(x, y, direction));
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Integer parseDirection(String[] data) {
		try {
			return Boolean.parseBoolean(data[INDEX_DIRECTION]) ? 1 : -1;
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	//Initialise unfilled holes.
	private void initialiseHoles() {		
		for(int i=0; i<Hole.NUM_HOLES; i++) {
			
			int holeX = Hole.FIRST_HOLE_X + i * Hole.HOLES_SEPARATION;
			sprites.add(new Hole(holeX, Hole.HOLES_Y));
		}
		Hole.resetNumFilledHoles();
	}
	
}
