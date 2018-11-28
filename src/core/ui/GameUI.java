package core.ui;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

import logic.event.CameraEvent;

public class GameUI implements Observer{
	
	public static final int INTERFACE_WIDTH = 500;
	public static final int INTERFACE_HEIGHT = 50;

	Image ult;
	
	Ellipse icon;
	Rectangle hope;
	Rectangle power;
	Rectangle ult_charge;

	Rectangle fireball;
	Rectangle impulse;
	Rectangle shield;
	Rectangle teleport;
	
	GameContainer container;
	Rectangle debug;

	public float x, y;
	
	public GameUI(GameContainer container) throws SlickException {
		this.container = container;
		x = (container.getWidth() - INTERFACE_WIDTH)/2;
		y = INTERFACE_HEIGHT;
		constructOutline();
		loadImages();
		debug = new Rectangle(x, y, INTERFACE_WIDTH, INTERFACE_HEIGHT);
	}
	
	public void draw(Graphics g) {
		g.texture(icon, ult, true);
		g.draw(icon);
		g.draw(hope);
		g.draw(power);
		g.draw(ult_charge);
		
		g.draw(fireball);
		g.draw(impulse);
		g.draw(shield);
		g.draw(teleport);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CameraEvent) {
			CameraEvent cameraEvent = (CameraEvent)arg;
			if(x != cameraEvent.new_x) x = cameraEvent.new_x + (container.getWidth() - INTERFACE_HEIGHT)/2;
		}
	}
	
	private void constructOutline() {
		final int baricon_width = 200;
		final int baricon_height = 20;
		final int circle_radius = 45;
		final int flag_width = 45;
		final int flag_height = 65;	
		
		
		hope = new Rectangle(x, y-10, baricon_width, baricon_height);
		icon = new Ellipse(x + baricon_width + circle_radius, y, 45, 50);
		power = new Rectangle(x+baricon_width+circle_radius*2, y-baricon_height/2, baricon_width, baricon_height);
		
		float fireball_x = x + baricon_width-110;
		float fireball_y = y+baricon_height-baricon_height/2;
		fireball = new Rectangle(fireball_x, fireball_y, flag_width, flag_height);
		
		float impulse_x = x + baricon_width-110 + flag_width+10;
		float impulse_y = y+baricon_height-baricon_height/2;
		impulse = new Rectangle(impulse_x, impulse_y, flag_width, flag_height);
		
		float shield_x = x + baricon_width + circle_radius*2 + flag_width + 20;
		float shield_y = y+baricon_height-baricon_height/2;
		shield = new Rectangle(shield_x, shield_y, flag_width, flag_height);
		
		float teleport_x = x + baricon_width + circle_radius*2 + 10;
		float teleport_y = y+baricon_height-baricon_height/2;
		teleport = new Rectangle(teleport_x, teleport_y, flag_width, flag_height);
		
		float ult_x = icon.getX()+circle_radius-20;
		float ult_y = y+circle_radius-5;
		ult_charge = new Rectangle(ult_x, ult_y, 40, 20);
		
		
	}
	
	private void loadImages() throws SlickException {
		ult = new Image("textures/ui/ulticon.png");
	}
	
	
	
	
	
	
	
	
	
	
}
