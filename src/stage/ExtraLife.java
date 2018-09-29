package stage;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Input;

import utilities.Movable;

public class ExtraLife extends Sprite implements Movable{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	private static final boolean HAZARD = false;
	
	/** Number to divide when converting Nanosecond to second */
	private static final double TO_SEC = 1E9;
	
	/** time in seconds*/
	private static final int MIN_WAIT_TIME = 25;
	private static final int MAX_WAIT_TIME = 35;
	private static final int LIFETIME = 14;
	private static final int PAUSE = 2;
		
	private static Random random = new Random();
	
	/** the random chosen log to ride */
	private Vehicle ridingLog;
	private float relativeX = 0;
	
	private int waitTime;
	private long startTime;
	private long timeSinceAppear = 0;
	
	/** 1 for right, -1 for left */
	private int direction = 1;
	
	/**
	 * Create the next extra life and set the start time once the current
	 * extra life shows up
	 */
	private ExtraLife nextExtraLife;
	
	public ExtraLife() {
		this(randomLog());
	}
	
	public ExtraLife(Vehicle ridingLog) {
		super(EXTRALIFE_SRC, ridingLog.getX(), ridingLog.getY(), HAZARD);
		this.ridingLog = ridingLog;
		this.startTime = System.nanoTime();
		this.waitTime = MIN_WAIT_TIME +
							random.nextInt(MAX_WAIT_TIME - MIN_WAIT_TIME + 1);
	}
	
	/** choose the random log */
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
		int timeSinceStart = (int) ((System.nanoTime() - startTime) / TO_SEC);
		
		/** show up and create next extra life when the wait time passed. */
		if(timeSinceStart >= waitTime) {
			if(nextExtraLife == null || nextExtraLife == World.getExtraLife()) {
				nextExtraLife = new ExtraLife();
			}
			super.render();
		}
		
		/** destroy the current extra life when the lifetime passed */
		if(timeSinceStart >= waitTime + LIFETIME &&
				nextExtraLife != World.getExtraLife()) {
			World.setExtraLife(nextExtraLife);
		}
	}
	
	@Override
	public float validateX(float x) {
		int halfLogWidth = ridingLog.getImage().getWidth() / 2;
		
		if(x <= -halfLogWidth && direction == -1 ||
				x >= halfLogWidth && direction == 1) {
			direction *= (-1);
			return x + 2 * direction * World.TILE_WIDTH;
		}
		return x;
	}

	/** move along the log */
	@Override
	public void move(Input input, int delta) {
		int appearTime =
				(int) ((System.nanoTime() - startTime) / TO_SEC - waitTime);
		if(appearTime < 0) {
			return;
		}
		
		if(appearTime % PAUSE == 0 && appearTime != timeSinceAppear){
			relativeX = validateX(relativeX + direction * World.TILE_WIDTH);
			timeSinceAppear = appearTime;
		}
		
		setX(ridingLog.getX() + relativeX);
	}

	public ExtraLife getNextExtraLife() {
		return nextExtraLife;
	}

}