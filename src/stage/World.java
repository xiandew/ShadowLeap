package stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
	/** sprite width, in pixels */
	public static final int TILE_WIDTH = 48;
	private static final String WATERTILE_SRC = "assets/water.png";
	private static final String GRASSTILE_SRC = "assets/grass.png";
	private static final String TREETILE_SRC = "assets/tree.png";
	
	// data indexes
	private static final int INDEX_OBJECT = 0;
	private static final int INDEX_X = 1;
	private static final int INDEX_Y = 2;
	private static final int INDEX_DIRECTION = 3;
	
	private static final int NUM_HOLES = 5;
	private static final int FIRST_HOLE_XOFFSET = 72;
	
	// the top row of the water tiles
	private static final int WATER_TOP = 96;
	// the bottom row of the water tiles
	private static final int WATER_BOTTOM = 336;
	
	private int currentLevel;
	private String currentLevelData;
	private int numFilledHoles;
	private boolean[] filledHoles;
	
	private static Player player;
	
	// for rendering
	private ArrayList<Sprite> sprites;
	
	private ArrayList<Tile> treeTiles;
	// All non-rideable vehicles
	private ArrayList<Vehicle> landVehicles;
	
	// All rideable vessels
	private ArrayList<Vessel> vessels;
	
	// for extra life
	private ArrayList<Vessel> logs;
	
	
	public World() throws SlickException {
		// Perform initialisation logic
		
		currentLevel = 0;
		currentLevelData = "assets/levels/0.lvl";
		numFilledHoles = 0;
		filledHoles = new boolean[NUM_HOLES];
		Arrays.fill(filledHoles, false);
		
		sprites = new ArrayList<>();
		treeTiles = new ArrayList<>();
		landVehicles = new ArrayList<>();
		vessels = new ArrayList<>();
		logs = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(currentLevelData))) {
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				int x = Integer.parseInt(data[INDEX_X]);
				int y = Integer.parseInt(data[INDEX_Y]);
				int direction = 0;
				
				try {
					direction = Boolean.parseBoolean(data[INDEX_DIRECTION]) ? 1 : -1;
				} catch(ArrayIndexOutOfBoundsException e) {}
				
				switch(data[INDEX_OBJECT]) {
					case("water"):
						Tile waterTile = new Tile(WATERTILE_SRC, x, y);
						sprites.add(waterTile);
						break;
					case("grass"):
						sprites.add(new Tile(GRASSTILE_SRC, x, y));
						break;
					case("tree"):
						Tile treeTile = new Tile(TREETILE_SRC, x, y);
						sprites.add(treeTile);
						treeTiles.add(treeTile);
						break;
					case("bus"):
						Bus bus = new Bus(x, y, direction);
						sprites.add(bus);
						landVehicles.add(bus);
						break;
					case("bulldozer"):
						Bulldozer bulldozer = new Bulldozer(x, y, direction);
						sprites.add(bulldozer);
						landVehicles.add(bulldozer);
						break;
					case("bike"):
						Bike bike = new Bike(x, y, direction);
						sprites.add(bike);
						landVehicles.add(bike);
						break;
					case("racecar"):
						Racecar racecar = new Racecar(x, y, direction);
						sprites.add(racecar);
						landVehicles.add(racecar);
						break;
					case("log"):
						Log log = new Log(x, y, direction);
						sprites.add(log);
						vessels.add(log);
						logs.add(log);
						break;
					case("longLog"):
						LongLog longLog = new LongLog(x, y, direction);
						sprites.add(longLog);
						vessels.add(longLog);
						logs.add(longLog);
						break;
					case("turtles"):
						Turtles turtles = new Turtles(x, y, direction);
						sprites.add(turtles);
						vessels.add(turtles);
						break;
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** create the player */
		player = new Player();
		sprites.add(player);
		
	}
	
	public void update(Input input, int delta) {
		// Update all of the sprites in the game
		
		// Update the movement of the player
		player.move(input, delta);
		
		for(Vehicle vehicle : this.landVehicles) {
			vehicle.move(input, delta);
		}
		for(Vessel vessel : this.vessels) {
			vessel.move(input, delta);
		}
		
		// Update the movement of vehicles and check whether any vehicle collides the player
		if(player.getY() > WATER_BOTTOM) {
			for(Vehicle vehicle : landVehicles) {
				if(player.collides(vehicle)) {
					if(vehicle.ifHazard()) {
						player.dieOnce();
					}else {
						vehicle.setContact(true);
					}
				}else {
					vehicle.setContact(false);
				}
			}
		}
		
		// when above the water
		if(player.getY() >= WATER_TOP && player.getY() <= WATER_BOTTOM) {
			for(Vessel vessel : vessels) {
				if(!vessel.ifContact()) {
					player.dieOnce();
				}
			}
		}
		
		if(player.getY() < WATER_TOP) {
			for(Tile treeTile : treeTiles) {
				if(player.collides(treeTile)) {
					player.dieOnce();
				}
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
		currentLevelData = "assets/levels/1.lvl";
	}
	
	/**
	 * @return the player
	 */
	public static Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		World.player = player;
	}
}
