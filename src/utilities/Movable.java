package utilities;

import org.newdawn.slick.Input;

public interface Movable {
	
	public float validateX(float x);
	public void move(Input input, int delta);
}
