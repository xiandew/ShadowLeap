package stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import utilities.Movable;

public class World {
	
	/** tile width, in pixels */
	public static final int TILE_WIDTH = 48;
	
	public static final int NUM_HOLES = 5;
	
	/** level data */
	private static final String[] LEVEL_DATA =
			new String[] {"assets/levels/0.lvl", "assets/levels/1.lvl"};
	
	/** level data indexes */
	private static final int INDEX_OBJECT    = 0;
	private static final int INDEX_X         = 1;
	private static final int INDEX_Y         = 2;
	private static final int INDEX_DIRECTION = 3;
	
	private int currentLevel = 0;
	
	/** all sprites */
	private static ArrayList<Sprite> sprites;
	
	private static Player player;
	
	public World() {
		initialiseWorld();
	}
	
	public void initialiseWorld() {
		
		readLevelData();
		Hole.initialHoles();
		
		player = new Player();
		sprites.add(player);
		sprites.add(new ExtraLife());
	}
	
	public void update(Input input, int delta) {
		
		/** Update the movements of movable sprites */
		for(Sprite sprite : sprites) {
			if(sprite instanceof Movable) {
				((Movable) sprite).move(input, delta);
			}
		}
		
		/** check for collisions */
		for(Sprite sprite : sprites) {
			if(sprite != player && sprite.collides(player)) {
				player.onCollision(sprite);
			}
		}
		
		/** level up when all of the holes are filled */
		if(Hole.getNumFilledHoles() == NUM_HOLES) {
			levelUp();
		}
		
	}
	
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
	
	/**
	 * @return the sprites
	 */
	public static ArrayList<Sprite> getSprites() {
		return sprites;
	}
	
	/**
	 * @return the player
	 */
	public static Player getPlayer() {
		return player;
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
}
