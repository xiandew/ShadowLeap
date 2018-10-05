package stage;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Input;

import utilities.Movable;

public class ExtraLife extends Sprite implements Movable{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	
	/** Nanoseconds `divide` TO_SEC = seconds */
	public static final double TO_SEC = 1E9;
	
	/** time in seconds*/
	private static final int MIN_WAIT_TIME = 25;
	private static final int MAX_WAIT_TIME = 35;
	private static final int LIFETIME = 14;
	private static final int PAUSE = 2;
		
	private static Random random = new Random();
	
	/** the random chosen log to ride */
	private Vehicle ridingLog;
	private float relativeX = 0;
	
	private int  waitTime;
	private long createTime;
	private long timeSinceAppear = 0;
	
	/** 1 for right, -1 for left */
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
	
	/** @return the random log */
	public static Vehicle randomLog() {
		ArrayList<Sprite> logs = new ArrayList<>();
		
		for(Sprite sprite : World.getSprites()) {
			if(sprite instanceof Log || sprite instanceof LongLog) {
				logs.add(sprite);
			}
		}
		return (Vehicle) logs.get(random.nextInt(logs.size()));
	}
	
	public void render() {
		int timeSinceCreate = (int) ((System.nanoTime() - createTime) / TO_SEC);
		
		/** show up and create next extra life when the wait time passed. */
		if(timeSinceCreate >= waitTime) {
			isAppear = true;
			super.render();
		}
		
		/** update the extra life when its lifetime passed */
		if(timeSinceCreate >= waitTime + LIFETIME) {
			resetExtraLife();
		}
	}
	
	@Override
	public float validateX(float x) {
		int halfLogWidth = ridingLog.getImage().getWidth() / 2;
		
		if(x <= -halfLogWidth && direction == -1 || x >= halfLogWidth && direction == 1) {
			direction *= (-1);
			return x + 2 * direction * World.TILE_WIDTH;
		}
		return x;
	}

	/**
	 * move along the log
	 * @param input Place holder
	 * @param delta Make sure the same rate with different FPS
	 */
	@Override
	public void move(Input input, int delta) {
		if(!isAppear) {
			((Vessel)ridingLog).carry(this, delta);
			return;
		}
		
		int appearTime = (int) ((System.nanoTime() - createTime) / TO_SEC - waitTime);
		
		if(appearTime % PAUSE == 0 && appearTime != timeSinceAppear){
			relativeX = validateX(relativeX + direction * World.TILE_WIDTH);
			timeSinceAppear = appearTime;
		}
		
		setX(ridingLog.getX() + relativeX);
	}
	
	public static void resetExtraLife() {
		ArrayList<Sprite> sprites = World.getSprites();
		
		for(Sprite sprite : sprites) {
			if(sprite instanceof ExtraLife) {
				sprites.set(sprites.indexOf(sprite), new ExtraLife());
				break;
			}
		}
	}
	
	public boolean collides(Sprite other) {
		if(isAppear) {
			return super.collides(other);
		}
		return false;
	}

}