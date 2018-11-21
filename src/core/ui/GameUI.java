package core.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import core.MovingAreaCamera;

public class GameUI {
	
	public static final int INTERFACE_WIDTH = 250;
	public static final int INTERFACE_HEIGHT = 50;

	Image ult;
	Circle ult_outline;

	public float x = 0, y = 30;
	
	MovingAreaCamera cam;
	
	public GameUI(MovingAreaCamera cam) throws SlickException {
		this.cam = cam;
		ult_outline = new Circle(x, y, 75);
		ult = new Image("textures/ui/ulticon.png");
		
	}
	
	public void draw(Graphics g) {
		g.texture(ult_outline, ult, true);
	}
	
	public void update(GameContainer container) {
		if(x != cam.x) x = cam.x + (container.getWidth() - INTERFACE_HEIGHT)/2;
	}
}
