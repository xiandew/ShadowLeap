package stage;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;

public abstract class Sprite {
	// this is a defined constant to avoid typos
	public final static String HAZARD = "hazard";
	public final static String SOLID = "solid";
	
	private BoundingBox bounds;
	private Image image;
	private float x;
	private float y;
	
	private String[] tags;
	
	public Sprite(String imageSrc, float x, float y) {
		setupSprite(imageSrc, x, y);
	}
	public Sprite(String imageSrc, float x, float y, String[] tags) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
	}
	
	private void setupSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		
		bounds = new BoundingBox(image, (int)x, (int)y);
		tags = new String[0];		
	}
	
	/**
	 * @return the image
	 */
	public Image getImage() { return image; }
	
	/**
	 * @return the x
	 */
	public float getX() { return x; }

	/**
	 * @param x the x to set
	 */
	public void setX(float x) { this.x = x; this.bounds.setX(x); }
	
	/**
	 * @return the y
	 */
	public float getY() { return y; }

	/**
	 * @param y the y to set
	 */
	public void setY(float y) { this.y = y; this.bounds.setY(y); }
	
	/**
	 * Draw the sprite
	 */
	public void render() { image.drawCentered(x, y); }
	
	/**
	 * Tell whether two sprites collide.
	 * @param other The other sprite
	 */
	public boolean collides(Sprite other) {
		return bounds.intersects(other.bounds);
	}
	
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
}
