package core.ui;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

import logic.event.CameraEvent;
import logic.event.PlayerEvent;

public class GameUI implements Observer {

	public static final int INTERFACE_WIDTH = 500;
	public static final int INTERFACE_HEIGHT = 50;

	Image ult;
	Image heart_icon;
	Image lightning_icon;
	Image shield_icon;
	Image hope_icon;
	Image power_icon;
	Image ultcharge_icon;

	Ellipse icon;
	Rectangle hope;
	Rectangle power;
	Rectangle ult_charge_outline;
	Rectangle ult_charge_frame;

	Rectangle hope_frame;
	Rectangle power_frame;

	Rectangle fireball;
	Rectangle impulse;
	Rectangle shield;
	Rectangle teleport;

	Rectangle decore_top;
	Rectangle decore_bottom;
	Rectangle decore_hp;
	Rectangle decore_mg;

	GameContainer container;

	public float x, y;
	private int ult_charge = 0;

	private Graphics g;

	public GameUI(GameContainer container) throws SlickException {
		this.container = container;
		x = (container.getWidth() - INTERFACE_WIDTH) / 2;
		y = INTERFACE_HEIGHT;
		constructOutline();
		loadImages();
	}

	
	Color golden = new Color(235, 169, 69);
	public void draw(Graphics g) {
		if (this.g == null)
			this.g = g;
		
		g.setColor(Color.black);
		g.draw(icon);
		g.setColor(Color.white);
		g.texture(hope, hope_icon, true);
		g.texture(power, power_icon, true);
		g.setColor(Color.black);	
		g.draw(hope_frame);
		g.draw(power_frame);




		g.setColor(Color.white);
		
		g.texture(icon, ult, true);
		g.texture(shield, shield_icon, true);	
		g.texture(fireball, shield_icon, true);	
		g.texture(impulse, shield_icon, true);	
		g.texture(teleport, shield_icon, true);		
		g.texture(ult_charge_outline, ultcharge_icon, true);	
		
//		g.setColor(Color.black);
//		g.draw(decore_hp);
//		g.draw(decore_mg);
		//g.draw(fireball);
		//g.draw(impulse);
		//g.draw(shield);
		//g.draw(teleport);


		//g.draw(decore_top);
		//g.draw(decore_bottom);		
		g.setColor(Color.black);	

		g.draw(ult_charge_frame);
		
		// координаты строки, на основе размеров шрифта, и координат макета
		float ultcharge_y = ult_charge_outline.getY() + (20 - g.getFont().getLineHeight()) / 2;
		float ultcharge_x = ult_charge_outline.getX() + (40 - g.getFont().getWidth(ult_charge + "%")) / 2;

		g.drawString(ult_charge + "%", ultcharge_x, ultcharge_y);
		g.setColor(Color.white);

		g.texture(decore_hp, heart_icon, true);
		g.texture(decore_mg, lightning_icon, true);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof CameraEvent) {
			CameraEvent cameraEvent = (CameraEvent) arg;
			updateOutline();//неправильная позиция!!!
			x = cameraEvent.new_x + (container.getWidth()-INTERFACE_WIDTH)/2;

//			updateOutline();   //правильная позиция!!! но она не работает!!!
		}
		
		if(arg instanceof PlayerEvent) {
			PlayerEvent eve = (PlayerEvent)arg;
			switch (eve.type) {
				case PlayerEvent.POWER_USED:
					int power_used = (int)eve.obj;
					power.setWidth(power.getWidth() - power_used*2);
				break;
				
				case PlayerEvent.POWER_RECHARGED:
					float power_recharged = (float)eve.obj;
					power.setWidth(power_recharged*2);
				break;

				case PlayerEvent.ULT_CHARGED:
					float ult_status = (float)eve.obj;
					ult_charge = (int) ult_status;
					break;

			}
		}
	}

	private void constructOutline() {
		final int baricon_width = 200;
		final int baricon_height = 20;
		final int circle_radius = 45;
		final int flag_width = 50;
		final int flag_height = 60;

		// жизнь, мана, иконка персонажа
		hope = new Rectangle(x, y - 10, baricon_width, baricon_height);
		icon = new Ellipse(x + baricon_width + circle_radius, y, 45, 50);
		power = new Rectangle(x + baricon_width + circle_radius * 2, y - baricon_height / 2, baricon_width,
				baricon_height);

		// ----------------------------Умения-------------------------------//
		// шаровая молния
		float fireball_x = x + baricon_width - 110;
		float fireball_y = y + baricon_height - baricon_height / 2;
		fireball = new Rectangle(fireball_x, fireball_y, flag_width, flag_height);

		// импульс
		float impulse_x = x + baricon_width - 110 + flag_width + 10;
		float impulse_y = y + baricon_height - baricon_height / 2;
		impulse = new Rectangle(impulse_x, impulse_y, flag_width, flag_height);

		// телекинетическое поле
		float shield_x = x + baricon_width + circle_radius * 2 + flag_width + 20;
		float shield_y = y + baricon_height - baricon_height / 2;
		shield = new Rectangle(shield_x, shield_y, flag_width, flag_height);

		// телепорт
		float teleport_x = x + baricon_width + circle_radius * 2 + 10;
		float teleport_y = y + baricon_height - baricon_height / 2;
		teleport = new Rectangle(teleport_x, teleport_y, flag_width, flag_height);

		// заряд "хаотического диссонанса"
		float ult_x = icon.getX() + circle_radius - 20;
		float ult_y = y + circle_radius - 5;
		ult_charge_outline = new Rectangle(ult_x, ult_y, 40, 20);
		ult_charge_frame = new Rectangle(ult_x, ult_y, 40, 20);

		// -------------------декорирующие элементы------------------//
		// ..сердечко для хп...
		float decorehp_x = icon.getX() - 15;
		float decorehp_y = y - 15;
		decore_hp = new Rectangle(decorehp_x, decorehp_y, 30, 30);

		// ...молния для маны..
		float decoremg_x = icon.getX() + circle_radius * 2 - 15;
		float decoremg_y = y - 15;
		decore_mg = new Rectangle(decoremg_x, decoremg_y, 30, 30);

		// ...ветки и новогодние украшения в правом верхнем углу
		float decoretop_x = icon.getX();
		float decoretop_y = y - circle_radius - 5;
		decore_top = new Rectangle(decoretop_x, decoretop_y, circle_radius, circle_radius);

		float decorebottom_x = icon.getX() + circle_radius * 3 / 2 - 10;
		float decorebottom_y = y - circle_radius - 5 + circle_radius * 3 / 2;
		decore_bottom = new Rectangle(decorebottom_x, decorebottom_y, circle_radius * 2 / 3, circle_radius * 2 / 3);

		hope_frame = new Rectangle(x - 2, y - 12, baricon_width + 4, baricon_height + 4);
		power_frame = new Rectangle(x + baricon_width + circle_radius * 2 - 2, y - baricon_height / 2 - 2,
				baricon_width + 4, baricon_height + 4);
	}

	private void updateOutline() {
		final int baricon_width = 200;
		final int circle_radius = 45;
		final int flag_width = 45;

		// жизнь, мана, иконка персонажа
		hope.setX(x + (baricon_width - hope.getWidth()));
		icon.setX(x + baricon_width);
		power.setX(x + baricon_width + circle_radius * 2);

		// ----------------------------Умения-------------------------------//
		// шаровая молния
		float fireball_x = x + baricon_width - 110;
		fireball.setX(fireball_x);

		// импульс
		float impulse_x = x + baricon_width - 110 + flag_width + 10;
		impulse.setX(impulse_x);

		// телекинетическое поле
		float shield_x = x + baricon_width + circle_radius * 2 + flag_width + 20;
		shield.setX(shield_x);

		// телепорт
		float teleport_x = x + baricon_width + circle_radius * 2 + 10;
		teleport.setX(teleport_x);

		// заряд "хаотического диссонанса"
		float ult_x = icon.getX() + circle_radius - 20;
		ult_charge_outline.setX(ult_x);
		ult_charge_frame.setX(ult_x);

		// -------------------декорирующие элементы------------------//
		// ..сердечко для хп...
		float decorehp_x = icon.getX() - 15;
		decore_hp.setX(decorehp_x);

		// ...молния для маны..
		float decoremg_x = icon.getX() + circle_radius * 2 - 15;
		decore_mg.setX(decoremg_x);

		// ...ветки и новогодние украшения в правом верхнем углу
		float decoretop_x = icon.getX();
		decore_top.setX(decoretop_x);

		float decorebottom_x = icon.getX() + circle_radius * 3 / 2 - 10;
		decore_bottom.setX(decorebottom_x);

		hope_frame.setX(x - 2);
		power_frame.setX(x + baricon_width + circle_radius * 2 - 2);
	}

	private void loadImages() throws SlickException {
		ult = new Image("textures/sprites/starlight/index.jpg");
		heart_icon = new Image("textures/ui/heart_icon.png");
		lightning_icon = new Image("textures/ui/lightning_icon.png");
		shield_icon = new Image("textures/ui/shield.png");
		hope_icon = new Image("textures/ui/health.png");
		power_icon = new Image("textures/ui/mana.png");
		ultcharge_icon = new Image("textures/ui/ultcharge.png");
	}

}
