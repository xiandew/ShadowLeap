package stage;

import org.newdawn.slick.SlickException;

public class ExtraLife extends Sprite{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	private static final boolean HAZARD = false;
	
	public ExtraLife(float x, float y) throws SlickException {
		super(EXTRALIFE_SRC, x, y, HAZARD);
	}



}
