package core.ui;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import core.MovingAreaCamera;
import core.event.CameraEvent;

public class GameUI implements Observer{
	
	public static final int INTERFACE_WIDTH = 250;
	public static final int INTERFACE_HEIGHT = 50;

	Image ult;
	Circle ult_outline;
	private GameContainer container;

	public float x = 0, y = 30;
	
	public GameUI(GameContainer container) throws SlickException {
		this.container = container;
		ult_outline = new Circle(x, y, 75);
		ult = new Image("textures/ui/ulticon.png");
		
	}
	
	public void draw(Graphics g) {
		g.texture(ult_outline, ult, true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CameraEvent) {
			CameraEvent cameraEvent = (CameraEvent)arg;
			if(x != cameraEvent.new_x) x = cameraEvent.new_x + (container.getWidth() - INTERFACE_HEIGHT)/2;
		}
	}
}
