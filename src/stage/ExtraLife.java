package stage;

public class ExtraLife extends Sprite{
	
	private static final String EXTRALIFE_SRC = "assets/extralife.png";
	private static final boolean HAZARD = false;
	
	public ExtraLife(float x, float y) {
		super(EXTRALIFE_SRC, x, y, HAZARD);
	}



}
