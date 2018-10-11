package stage;

import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Input;
import utilities.Movable;

/**
 * ExtraLife class for the game. Extends Sprite. Implements Movable.
 * The player gets an extra life if colliding it. It repeatedly appears
 * on a random log after a random chosen waiting time and move along the log
 * when appearing.
 */
public class ExtraLife extends Sprite implements Movable{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	
	// Nanoseconds `divide` TO_SEC = seconds
	public static final long TO_SEC = (long) 1E9;
	
	// time in seconds
	private static final int MIN_WAIT_TIME = 25;
	private static final int MAX_WAIT_TIME = 35;
	private static final int LIFETIME = 14;
	private static final int PAUSE = 2;
	
	private static Random random = new Random();
	
	// the create time
	private long createTime;
	// the random wait time
	private long waitTime;
	// the random chosen log to ride
	private Vehicle ridingLog;
	// records the time that the extra life made the last move
	private long lastMoveTime = 0;
	// x of the extra life relative to the center of the log
	private float relativeX = 0;
	// right: 1, left: -1
	private int direction = 1;
	// appear after the wait time
	private boolean isAppear = false;
	// all sprites
	private ArrayList<Sprite> sprites;
	
	/**
	 * Create an extra life at a random chosen log and set the random
	 * waiting time for appearing on the log later on.
	 * @param sprites All sprites
	 */
	public ExtraLife(ArrayList<Sprite> sprites) {
		this(randomLog(sprites));
		this.sprites = sprites;
	}
	private ExtraLife(Vehicle ridingLog) {
		super(EXTRALIFE_SRC, ridingLog.getX(), ridingLog.getY());
		this.ridingLog = ridingLog;
		this.createTime = System.nanoTime();
		this.waitTime = MIN_WAIT_TIME + random.nextInt(MAX_WAIT_TIME - MIN_WAIT_TIME + 1);
	}
	
	private static Vehicle randomLog(ArrayList<Sprite> sprites) {
		ArrayList<Sprite> logs = new ArrayList<>();
		for(Sprite sprite : sprites) {
			if(sprite instanceof Log || sprite instanceof LongLog) {
				logs.add(sprite);
			}
		}
		return (Vehicle) logs.get(random.nextInt(logs.size()));
	}
	
	/**
	 * Show up when the waiting time passed.
	 */
	@Override
	public void render() {
		
		if((System.nanoTime() - createTime) / TO_SEC >= waitTime) {
			isAppear = true;
			super.render();
		}
	}
	
	/**
	 * Make sure the extra life move around the log.
	 */
	@Override
	public float validX(float x) {
		setX(ridingLog.getX() + x);
		
		if(ridingLog.collides(this)) {
			return x;
		}
		
		direction *= (-1);
		return x + 2 * direction * World.TILE_WIDTH;
	}

	/**
	 * Move along the log every 2 seconds when appearing.
	 */
	@Override
	public void move(Input input, int delta) {
		long appearTime = (System.nanoTime() - createTime) / TO_SEC - waitTime;
		
		// Reset the extra life when its lifetime passed.
		if(appearTime >= LIFETIME) {
			resetExtraLife();
		}
		
		if(isAppear && appearTime % PAUSE == 0 && appearTime != lastMoveTime){
			relativeX = validX(relativeX + direction * World.TILE_WIDTH);
			lastMoveTime = appearTime;
		}
		
		setX(ridingLog.getX() + relativeX);
	}
	
	/**
	 * Only collide-able when appearing.
	 */
	@Override
	public boolean collides(Sprite other) {
		return isAppear && super.collides(other);
	}
	
	/**
	 * Reset when colliding the player.
	 */
	@Override
	public void onCollision(Sprite other) {
		resetExtraLife();
	}
	
	/**
	 * Reset the extra life.
	 */
	public void resetExtraLife() {
		
		for(Sprite sprite : sprites) {
			if(sprite instanceof ExtraLife) {
				sprites.set(sprites.indexOf(sprite), new ExtraLife(sprites));
				break;
			}
		}
	}

}