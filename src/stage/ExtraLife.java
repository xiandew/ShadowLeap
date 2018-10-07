package stage;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Input;

import utilities.Movable;

/**
 * ExtraLife class for the game. Extends Sprite.
 * Repeatedly appears on a random log for 14 seconds after a random
 * chosen waiting time between 25 and 35 seconds. Moves one tile along
 * the log every 2 seconds. Destroys after 14-seconds appearing time
 * or if the player collides it and the player should get an extra life.
 */

public class ExtraLife extends Sprite implements Movable{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	
	// Nanoseconds `divide` TO_SEC = seconds
	public static final double TO_SEC = 1E9;
	
	// time in seconds
	private static final int MIN_WAIT_TIME = 25;
	private static final int MAX_WAIT_TIME = 35;
	private static final int LIFETIME = 14;
	private static final int PAUSE = 2;
		
	private static Random random = new Random();
	
	// the random chosen log to ride
	private Vehicle ridingLog;
	
	// x of the extra life relative to the center of the log
	private float relativeX = 0;
	
	private int  waitTime;
	private long createTime;
	
	// records the time that the extra life made the last move
	private long lastMoveTime = 0;
	
	// right: 1, left: -1
	private int direction = 1;
	
	private boolean isAppear = false;
	
	public ExtraLife() {
		this(randomLog());
	}
	
	public ExtraLife(Vehicle ridingLog) {
		super(EXTRALIFE_SRC, ridingLog.getX(), ridingLog.getY());
		this.ridingLog = ridingLog;
		this.createTime = System.nanoTime();
		this.waitTime = MIN_WAIT_TIME + random.nextInt(MAX_WAIT_TIME - MIN_WAIT_TIME + 1);
	}
	
	private static Vehicle randomLog() {
		ArrayList<Sprite> logs = new ArrayList<>();
		
		for(Sprite sprite : World.getSprites()) {
			if(sprite instanceof Log || sprite instanceof LongLog) {
				logs.add(sprite);
			}
		}
		return (Vehicle) logs.get(random.nextInt(logs.size()));
	}
	
	/**
	 * show up when the waiting time passed.
	 * reset the extra life when its lifetime passed.
	 */
	public void render() {
		int timeSinceCreate = (int) ((System.nanoTime() - createTime) / TO_SEC);
		
		if(timeSinceCreate >= waitTime) {
			isAppear = true;
			super.render();
		}
		
		if(timeSinceCreate >= waitTime + LIFETIME) {
			resetExtraLife();
		}
	}
	
	/**
	 * Make sure the extra life move around the log.
	 * @param x The x coordinate.
	 * @return the validated x.
	 */
	@Override
	public float validateX(float x) {
		float halfLogWidth = ridingLog.getImage().getWidth() / 2;
		
		if(x <= -halfLogWidth && direction == -1 || x >= halfLogWidth && direction == 1) {
			direction *= (-1);
			return x + 2 * direction * World.TILE_WIDTH;
		}
		return x;
	}

	/**
	 * move along the log every 2 seconds when appearing.
	 * @param input Place holder for override.
	 * @param delta Make sure the same speed with different FPS.
	 */
	@Override
	public void move(Input input, int delta) {		
		int appearTime = (int) ((System.nanoTime() - createTime) / TO_SEC - waitTime);
		
		if(isAppear && appearTime % PAUSE == 0 && appearTime != lastMoveTime){
			relativeX = validateX(relativeX + direction * World.TILE_WIDTH);
			lastMoveTime = appearTime;
		}
		setX(ridingLog.getX() + relativeX);
	}
	
	/**
	 * reset the extra life.
	 */
	public static void resetExtraLife() {
		ArrayList<Sprite> sprites = World.getSprites();
		
		for(Sprite sprite : sprites) {
			if(sprite instanceof ExtraLife) {
				sprites.set(sprites.indexOf(sprite), new ExtraLife());
				break;
			}
		}
	}
	
	/**
	 * Only collide-able when appearing.
	 * @param other The other sprite to detect collision with.
	 */
	public boolean collides(Sprite other) {
		if(isAppear) {
			return super.collides(other);
		}
		return false;
	}

}