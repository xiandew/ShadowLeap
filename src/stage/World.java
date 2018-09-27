package stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {
	// sprite width, in pixels
	public static final int TILE_WIDTH = 48;
	
	private static final int NUM_HOLES = 5;	
	
	//level data
	private static final String FIRST_LEVEL_DATA = "assets/levels/0.lvl";
	private static final String SECOND_LEVEL_DATA = "assets/levels/1.lvl";
	
	// level data indexes
	private static final int INDEX_OBJECT = 0;
	private static final int INDEX_X = 1;
	private static final int INDEX_Y = 2;
	private static final int INDEX_DIRECTION = 3;
	
	private int currentLevel;
	private String currentLevelData;
	
	private int numFilledHoles;
	
	private static Player player;
	private static ArrayList<Sprite> sprites;	
	
	public World() {
		// Perform initialisation logic
		
		currentLevel = 0;
		currentLevelData = FIRST_LEVEL_DATA;
		
		numFilledHoles = 0;
		
		readLevelData();
		
		/** create the player */
		player = new Player();
		sprites.add(player);
	}
	
	public void update(Input input, int delta) {	
		
		// check whether the player is riding
		for(Sprite sprite : sprites) {
			if(sprite instanceof Vessel && player.collides(sprite)) {
				player.setRidingVessel(sprite);
			}
		}
		
		// check for hazard and non-hazard collisions
		for(Sprite sprite : sprites) {
			if(sprite != player && sprite.collides(player)) {
				if(sprite.ifHazard() && player.getRidingVessel() == null) {
					player.dieOnce();
					break;
				}
				
				if(sprite instanceof Bulldozer || sprite instanceof Vessel) {
					((Vehicle)sprite).setContact(true);
				}
				
			}else if(sprite instanceof Bulldozer || sprite instanceof Vessel) {
				((Vehicle)sprite).setContact(false);
			}
		}
		
		// Update the movement of the player
		player.move(input, delta);
		
		//ExtraLife.move(input, delta);
		
		for(Sprite sprite : sprites) {
			if(sprite instanceof Vehicle) {
				((Vehicle)sprite).move(input, delta);
			}
		}
		
		if(numFilledHoles == NUM_HOLES) {
			levelUp();
		}
		
	}
	
	public void render(Graphics g) {
		// Draw all of the sprites in the game
		for(Sprite sprite: sprites) {
			sprite.render();
		}
	}

	private void levelUp() {
		currentLevel++;
		if(currentLevel > 1) {
			System.exit(0);
		}
		currentLevelData = SECOND_LEVEL_DATA;
	}
	
	/**
	 * @return the player
	 */
	public static Player getPlayer() {
		return player;
	}
	
	/**
	 * @return the sprites
	 */
	public static ArrayList<Sprite> getSprites() {
		return sprites;
	}
	
	private void readLevelData() {
		sprites = new ArrayList<>();
		try (BufferedReader br =
				new BufferedReader(new FileReader(currentLevelData))) {
			
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
