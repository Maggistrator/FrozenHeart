package core.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import core.MovingAreaCamera;
import core.TrueTypeFont;
import logic.entity.StarlightGlimmer;

public class GameUI {

	//параметры интерфейса в пикселях
	//TODO: сделать размеры интерфейса вычисляемыми
	public static final int INTERFACE_WIDTH = 460;
	public static final int INTERFACE_HEIGHT = 50;

	//------------------Изображения---------------------------//
	Image ult;
	Image heart_icon;
	Image lightning_icon;
	Image shield_icon;
	Image fireball_icon;
	Image impuls_icon;
	Image teleport_icon;
	Image hope_icon;
	Image power_icon;
	Image flower_icon;
	Image ultcharge_icon;
	Image stress_flower;

	//-------------------- Макет -----------------------------//
	//основные элементы
	Ellipse icon;//иконка в центре
	Rectangle hope;//жизнь aka. надежда
	Rectangle power;//мана aka. мощь
	Rectangle ult_charge_outline;//уровень заряда суперспособности
	
	//рамки основных элементов
	Rectangle ult_charge_frame;
	Rectangle hope_frame;
	Rectangle power_frame;

	//плейсхолдеры заклинаний, не рисуются
	Rectangle fireball;
	Rectangle impulse;
	Rectangle shield;
	Rectangle teleport;

	//украшательства
	Rectangle decore_top;
	Rectangle decore_bottom;
	Rectangle decore_hp;
	Rectangle decore_mg;
	
	//цветы стресс-поинтов
	Rectangle[] stress;	

	GameContainer container;
	MovingAreaCamera camera;
	StarlightGlimmer pony;

	public float x, y;
	private int ult_charge = 0;

	private Graphics g;
	private ParticleSystem powerfullSparklesEffect;
	private ParticleSystem powerfullFogEffect;

	public GameUI(GameContainer container, MovingAreaCamera observable, StarlightGlimmer pony) throws SlickException {
		this.container = container;
		camera = observable;
		this.pony = pony;
		x = (container.getWidth() - INTERFACE_WIDTH) / 2;
		y = INTERFACE_HEIGHT;
		constructOutline();
		loadImages();
		setupPowerParticleSystem();
	}

	TrueTypeFont kekFont;
	public void draw(Graphics g) {
		if (this.g == null) {
			this.g = g;
			try {
				Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(14f);
				kekFont = new TrueTypeFont(font, true);
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
		}
		g.setAntiAlias(true);
		//g.setFont(kekFont);
		
		g.setColor(Color.black);
		//g.draw(decore_hp);
		//g.draw(decore_mg);
		g.setColor(Color.black);
		g.draw(icon);
		g.setColor(Color.white);
		hope_icon.draw(hope.getX()-2, hope.getY(), hope.getX()+hope.getWidth(), hope.getY()+hope.getHeight(), 0, 0, hope.getWidth(), hope.getHeight());
		power_icon.draw(power.getX()-2, power.getY(), power.getX()+power.getWidth(), power.getY()+power.getHeight(), 0, 0, power.getWidth(), power.getHeight());
		g.setColor(Color.black);	
		g.draw(hope_frame);
		g.draw(power_frame);
		g.setColor(Color.white);
		
		powerfullSparklesEffect.render(power.getX(), power.getY());
		powerfullFogEffect.render(power.getX(), power.getY());
		g.texture(shield, shield_icon, true);	
		g.texture(fireball, fireball_icon, true);	
		g.texture(impulse, impuls_icon, true);	
		g.texture(teleport, teleport_icon, true);		
		g.texture(icon, ult, true);
		g.texture(ult_charge_outline, ultcharge_icon, true);	

		//g.draw(decore_top);
				
		g.setColor(Color.black);
		
		org.newdawn.slick.Font font = g.getFont();
		int powerLabelWidth = font.getWidth("Power");
		
		g.drawString("Hope", hope_frame.getX(), hope_frame.getY()- hope_frame.getHeight());
		g.drawString("Power", power_frame.getX()+(power_frame.getWidth()-powerLabelWidth), power_frame.getY()-power_frame.getHeight());

		g.draw(ult_charge_frame);
		
		// координаты строки, на основе размеров шрифта, и координат макета
		float ultcharge_y = ult_charge_outline.getY() + (20 - g.getFont().getLineHeight()) / 2;
		float ultcharge_x = ult_charge_outline.getX() + (40 - g.getFont().getWidth(ult_charge + "%")) / 2;

		g.drawString(ult_charge + "%", ultcharge_x, ultcharge_y);
		g.setColor(Color.white);

		g.texture(decore_hp, heart_icon, true);
		g.texture(decore_mg, lightning_icon, true);
		//g.texture(decore_bottom, flower_icon, true);
		for(Rectangle r: stress) g.texture(r, stress_flower, true);
		
	}

	public void update(int delta) {
			//updateOutline();//неправильная позиция!!!
			x = camera.x + (container.getWidth()-INTERFACE_WIDTH)/2;
			hope.setWidth(pony.hope*2);
			hope.setX(x+Math.abs(180 - pony.hope*2));
			updateOutline();   //правильная позиция!!! но она не работает!!!
			powerfullSparklesEffect.update(delta);
			powerfullFogEffect.update(delta);
	}

	private void setupPowerParticleSystem() {
		try {
			powerfullSparklesEffect = new ParticleSystem("textures/particles/power2.png", 500);
			File violet = new File("res/emitters/violet sparkles.xml");
			ParticleEmitter emitter1 = ParticleIO.loadEmitter(violet);
			powerfullSparklesEffect.addEmitter(emitter1);
			
			powerfullFogEffect = new ParticleSystem("textures/particles/smoke.png", 500);
			powerfullFogEffect.addEmitter(emitter1);
		} catch (IOException e) {
			System.out.println("ресурсы повреждены: отсутствует эмиттер частиц для эффекта магической силы");
		}
	}
	
	private void constructOutline() {
		final int baricon_width = 180;
		final int baricon_height = 20;
		final int circle_radius = 40;
		final int flag_width = 45;
		final int flag_height = 53;

		// жизнь, мана, иконка персонажа
		hope = new Rectangle(x, y - 10, baricon_width, baricon_height);
		icon = new Ellipse(x + baricon_width + circle_radius, y, circle_radius, circle_radius);
		power = new Rectangle(x + baricon_width + circle_radius * 2, y - baricon_height / 2, baricon_width,
				baricon_height);

		// ----------------------------Умения-------------------------------//
		// шаровая молния
		float fireball_x = x + baricon_width - 110;
		float fireball_y = y + baricon_height - baricon_height / 2;
		fireball = new Rectangle(fireball_x, fireball_y, flag_width, flag_height);

		// импульс
		float impulse_x = x + baricon_width - 110 + flag_width;
		float impulse_y = y + baricon_height - baricon_height / 2;
		impulse = new Rectangle(impulse_x, impulse_y, flag_width, flag_height);

		// телекинетическое поле
		float shield_x = x + baricon_width + circle_radius * 2 + flag_width;
		float shield_y = y + baricon_height - baricon_height / 2;
		shield = new Rectangle(shield_x, shield_y, flag_width, flag_height);

		// телепорт
		float teleport_x = x + baricon_width + circle_radius * 2;
		float teleport_y = y + baricon_height - baricon_height / 2;
		teleport = new Rectangle(teleport_x, teleport_y, flag_width, flag_height);

		// заряд "хаотического диссонанса"
		float ult_x = icon.getX() + circle_radius - 20;
		float ult_y = y + circle_radius - 5;
		ult_charge_outline = new Rectangle(ult_x, ult_y, 40, 20);
		ult_charge_frame = new Rectangle(ult_x, ult_y, 40, 20);
		
		//рамки "надежды" и "силы"
		hope_frame = new Rectangle(x+2, y-10, baricon_width, baricon_height);
		power_frame = new Rectangle(x + baricon_width + circle_radius * 2 + 2, y - baricon_height / 2,
				baricon_width, baricon_height);
		
		//макеты стресс-поинтов
		float flower_width = 25;	
		float flower_height = 25;		
		float flower_count = 4;
		float stressflowers_x = x + baricon_width - flower_width * flower_count;
		float stressflowers_y = y - flower_height - 10;
		stress = new Rectangle[4]; 
		for(int i = 0; i < flower_count; i++) stress[i] = new Rectangle(stressflowers_x+flower_width*i+2, stressflowers_y, flower_width, flower_height);
		
		
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

//		float decorebottom_x = icon.getX() + circle_radius * 3 / 2 - 10;
//		float decorebottom_y = y - circle_radius - 5 + circle_radius * 3 / 2;
		float decorebottom_x = ult_x + ult_charge_outline.getWidth()/3*2;
		float decorebottom_y = ult_y - ult_charge_outline.getHeight()/2;
		decore_bottom = new Rectangle(decorebottom_x, decorebottom_y, circle_radius * 2 / 3, circle_radius * 2 / 3);

	}

	private void updateOutline() {
		final int baricon_width = 180;
		final int circle_radius = 40;
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
		float impulse_x = fireball_x + flag_width + 2;
		impulse.setX(impulse_x);

		// телепорт
		float teleport_x = x + baricon_width + circle_radius * 2 + 20;
		teleport.setX(teleport_x);

		// телекинетическое поле
		float shield_x = teleport.getX() + flag_width + 2;
		shield.setX(shield_x);

		// заряд "хаотического диссонанса"
		float ult_x = icon.getX() + circle_radius - 20;
		ult_charge_outline.setX(ult_x);
		ult_charge_frame.setX(ult_x);

		// рамки "надежды" и "энергии/силы"
		hope_frame.setX(x);
		power_frame.setX(x + baricon_width + circle_radius * 2 - 2);
		
		// макеты стресс-поинтов
		float flower_width = 20;		
		float flower_count = 4;
		float stressflowers_x = x + baricon_width - flower_width * flower_count;
		for(int i = 0; i < flower_count; i++) stress[i].setX(stressflowers_x+flower_width*i+2);
		

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

//		float decorebottom_x = icon.getX() + circle_radius * 3 / 2 - 10;
		float decorebottom_x = ult_x + ult_charge_outline.getWidth()/2;
		decore_bottom.setX(decorebottom_x);
	}

	private void loadImages() throws SlickException {
		ult = new Image("textures/ui/ulticon.png");
		heart_icon = new Image("textures/ui/heart_icon.png");
		lightning_icon = new Image("textures/ui/lightning_icon.png");
		shield_icon = new Image("textures/ui/shield.png");
		fireball_icon = new Image("textures/ui/fireball flag .png");
		impuls_icon = new Image("textures/ui/impuls flag .png");
		teleport_icon = new Image("textures/ui/teleport.png");
		hope_icon = new Image("textures/ui/hope.png");
		power_icon = new Image("textures/ui/power.png");
		ultcharge_icon = new Image("textures/ui/ultcharge.png");
		flower_icon = new Image("textures/ui/flower for pony.png");
		stress_flower = new Image("textures/ui/stress flower.png");
	}

}
