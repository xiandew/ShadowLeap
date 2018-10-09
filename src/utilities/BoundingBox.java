/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 */
package utilities;

import org.newdawn.slick.Image;

/**
 * The BoundingBox class for the game. Used to detect collisions.
 */
public class BoundingBox {
	private static final float FUZZ = 0.95f;
	
	private float left;
	private float top;
	private float width;
	private float height;
	
	/**
	 * Create a bounding box.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 * @param width The width.
	 * @param height The height.
	 */
	public BoundingBox(float x, float y, float width, float height) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}
	/**
	 * Create a bounding box.
	 * @param img The image of the object inside the bounding box.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 */
	public BoundingBox(Image img, float x, float y) {
		setWidth(img.getWidth());
		setHeight(img.getHeight());
		setX(x);
		setY(y);
	}
	/**
	 * Create a bounding box.
	 * @param bb The bounding box to copy.
	 */
	public BoundingBox(BoundingBox bb) {
		width = bb.width;
		height = bb.height;
		left = bb.left;
		top = bb.top;
	}

	/**
	 * Sets the x position at the center of the bounding box.
	 */
	public void setX(float x) {
		left = x - width / 2;
	}
	/**
	 * Sets the y position at the center of the bounding box.
	 */
	public void setY(float y) {
		top = y - height / 2;
	}
	
	/**
	 * Sets the width.
	 */
	public void setWidth(float w) {
		width = w * FUZZ;
	}
	/**
	 * Sets the height.
	 */
	public void setHeight(float h) {
		height = h * FUZZ;
	}
	
	/**
	 * @return the left border position.
	 */
	public float getLeft() {
		return left;
	}
	/**
	 * @return the top border position.
	 */
	public float getTop() {
		return top;
	}
	/**
	 * @return the right border position.
	 */
	public float getRight() {
		return left + width;
	}
	/**
	 * @return the bottom border position.
	 */
	public float getBottom() {
		return top + height;
	}
	
	/**
	 * @return the width.
	 */
	public float getWidth() {
		return width;
	}
	/**
	 * @return the height.
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Tell whether two bounding boxes collide.
	 * @param other The other bounding box to check for collision.
	 * @return whether this bounding box collides the other.
	 */
	public boolean intersects(BoundingBox other) {
		return !(other.left > getRight()
			  || other.getRight() < left
			  || other.top > getBottom()
			  || other.getBottom() < top);
	}
}
