package stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {
	/** sprite width, in pixels */
	public static final int TILE_WIDTH = 48;
	
	public static final int NUM_HOLES = 5;
	
	/** level data */
	private static final String FIRST_LEVEL_DATA = "assets/levels/0.lvl";
	private static final String SECOND_LEVEL_DATA = "assets/levels/1.lvl";
	
	/**level data indexes*/
	private static final int INDEX_OBJECT = 0;
	private static final int INDEX_X = 1;
	private static final int INDEX_Y = 2;
	private static final int INDEX_DIRECTION = 3;
	
	private int currentLevel = 0;
	private String currentLevelData = FIRST_LEVEL_DATA;
	
	/** containing all except the player and extra life */
	private static ArrayList<Sprite> sprites;
	
	private static Player player;
	private static ExtraLife extraLife;
	private static ExtraLife nextExtraLife;
	
	public World() {
		readLevelData();
		Hole.initialHoles();
		player = new Player();
		extraLife = new ExtraLife();
	}
	
	public void update(Input input, int delta) {
		
		/** Update the movement of the player */
		player.move(input, delta);
		
		/** Update the movement of the extra life */
		extraLife.move(input, delta);
		
		/** check whether the player is riding */
		for(Sprite sprite : sprites) {
			if(sprite instanceof Vessel && player.collides(sprite)) {
				player.setRidingVessel(sprite);
			}
		}
		
		/** check for hazard and non-hazard collisions */
		for(Sprite sprite : sprites) {
			if(sprite != player && sprite.collides(player)) {
				if(sprite.ifHazard() && player.getRidingVessel() == null) {
					player.dieOnce();
					break;
				}
				
				if(sprite instanceof Bulldozer || sprite instanceof Vessel) {
					((Vehicle)sprite).setContact(true);
				}
				
				if(sprite instanceof Hole) {
					((Hole)sprite).setfilled();
				}
				
			}else if(sprite instanceof Bulldozer || sprite instanceof Vessel) {
				((Vehicle)sprite).setContact(false);
			}
		}
		
		// check whether hitting the extra life
		if(player.collides(extraLife) && extraLife != nextExtraLife) {
			extraLife = nextExtraLife;
			player.getExtraLife();
		}
		
		/** Update the movements of sprites except the player and extra life */
		for(Sprite sprite : sprites) {
			if(sprite instanceof Vehicle) {
				((Vehicle)sprite).move(input, delta);
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
		player.render();
		extraLife.render();
	}
	
	/** change the level data. Exit if the second level completed */
	private void levelUp() {
		currentLevel++;
		if(currentLevel > 1) {
			System.exit(0);
		}
		currentLevelData = SECOND_LEVEL_DATA;
		
		readLevelData();
		Hole.resetNumFilledHoles();
		Hole.initialHoles();
		player = new Player();
		extraLife = new ExtraLife();
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
	
	public static void setExtraLife(ExtraLife extraLife) {
		World.extraLife = extraLife;
	}
	
	/**
	 * @return the nextExtraLife
	 */
	public static ExtraLife getNextExtraLife() {
		return nextExtraLife;
	}

	/**
	 * @param nextExtraLife the nextExtraLife to set
	 */
	public static void setNextExtraLife(ExtraLife nextExtraLife) {
		World.nextExtraLife = nextExtraLife;
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
