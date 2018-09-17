import org.newdawn.slick.SlickException;

public class Bus extends Sprite {
	
	public static final String BUS_SRC = "assets/bus.png";
	
	/** The speed of buses, 0.15 pixels per ms */
	public static final float SPEED = 0.15f;
	/** -1 for left, 1 for right */
	private int direction;
	
	/**
	 * Initialise a bus with moving direction at (x, y).
	 */
	public Bus(float x, float y, int direction) throws SlickException {
		super(BUS_SRC, x, y);
		this.direction = direction;
	}
	
	/**
	 * Roll the row of buses around if the other side is off screen.
	 */
	public void setX(float x) {
		if(x <= - World.SPRITE_WIDTH / 2 && direction == -1 ||
				x >= App.SCREEN_WIDTH + World.SPRITE_WIDTH / 2 && direction == 1) {
			super.setX(App.SCREEN_WIDTH - x);
			return;
		}
		super.setX(x);		
	}
	
	/**
	 * Update the movement of a bus.
	 * @param delta Make sure the objects move at the same speed
	 */
	public void update(int delta) {
		this.setX((float) (this.getX() + SPEED * this.direction * delta));
	}
	
}
