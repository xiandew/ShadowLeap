package stage;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;

public abstract class Sprite {
	
	private Image image;
	private float x, y;
	private BoundingBox bounds;
	private boolean isHazard;
	
	public Sprite(String imageSrc, float x, float y, boolean isHazard)
			throws SlickException {
		this.image = new Image(imageSrc);
		this.x = x;
		this.y = y;
		this.isHazard = isHazard;
	}
	
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		if(y >= App.SCREEN_HEIGHT) {
			return;
		}
		this.y = y;
	}
	
	/**
	 * @return the isHazard
	 */
	public boolean ifHazard() {
		return isHazard;
	}
	
	/**
	 * This render is for showing the movement of the player
	 * and buses
	 */
	public void render() {
		// This should be pretty simple.
		this.getImage().drawCentered(this.getX(), this.getY());
	}
	
	/**
	 * Decide whether two sprites collide. Exit the game if collide happens.
	 * @param other The other sprite
	 */
	public boolean collides(Sprite other) {
		bounds = new BoundingBox(this.image, this.x, this.y);
		BoundingBox otherBounds =
				new BoundingBox(other.getImage(), other.getX(), other.getY());
		return bounds.intersects(otherBounds);
	}
}
