package stage;

import org.newdawn.slick.Input;

import utilities.Movable;

public class ExtraLife extends Sprite implements Movable{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	private static final boolean HAZARD = false;
	
	public ExtraLife(float x, float y) {
		super(EXTRALIFE_SRC, x, y, HAZARD);
	}

	@Override
	public float validateX(float x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void move(Input input, int delta) {
		// TODO Auto-generated method stub
		
	}



}
