import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;

public abstract class Sprite {
	
	private Image image;
	private float x, y;
	private BoundingBox bb;
	/**
	 * Constructor for the player and the buses. Coordinates are
	 * needed for initial position.
	 */
	public Sprite(String imageSrc, float x, float y) throws SlickException {
		// Why would the constructor need a path to an image, and a coordinate?
		this.setImage(new Image(imageSrc));
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Constructor for tiles. No need of coordinates since they are still.
	 */
	public Sprite(String imageSrc) throws SlickException {
		this.setImage(new Image(imageSrc));
	}
	
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
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
		if(y < 0 || y >= App.SCREEN_HEIGHT) {
			return;
		}
		this.y = y;
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
	public void contactSprite(Sprite other) {
		// Should be called when one sprite makes contact with another. 
		
		this.bb = new BoundingBox(this.getImage(), this.getX(), this.getY());
		
		if(this.bb.intersects(
				new BoundingBox(other.getImage(), other.getX(), other.getY()))) {
			System.exit(0);
		}
		
	}
	
}
