package logic.spells;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import it.marteEngine.entity.Entity;
import logic.entity.Monster;

public class ImpactLightning extends Entity{
	
	private final float CONST_SPEED_FACTOR = 2f;
	public final int MAX_DISTANCE = 400;
	
	public int damage = 10;
	public float endx, endy;
	public int distance = 0;
	private Rectangle get_rect  = new Rectangle(x, y, 40, 40);
	ParticleSystem trail;
	
	private Image temp_image;
	
	public ImpactLightning(float x, float y, float newx, float newy) {
		super(x, y);
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
//		calculateSpeed();
		speed.x = CONST_SPEED_FACTOR; 
		speed.y = 0;
		setHitBox(0, 0, 40, 40);
		loadAnim();
		loadParticles();
	}
	
	private void calculateSpeed(int delta) {
		Vector2f ponyCoords = new Vector2f(endx, endy);
		Vector2f coords = new Vector2f(x, y);

		Vector2f distance = ponyCoords.sub(coords);
		distance.normalise();
		speed = distance.scale(CONST_SPEED_FACTOR);
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		trail.render(get_rect.getX(), get_rect.getY());
		g.texture(get_rect, temp_image, true);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		Monster monster;
		get_rect.setX(x);
		get_rect.setY(y);
		distance += speed.x;
		trail.update(delta);
		if((monster = (Monster) collide("MONSTER", x+2, y)) != null) {
			monster.getHitted(damage);
			destroy();
		}
		if(distance > MAX_DISTANCE) destroy();
	}
	
	private void loadParticles() {
		try {
			trail = new ParticleSystem("textures/particles/trail.png", 800);
			File emitterConfigFile = new File("res/emitters/ice_trail.xml");
			ParticleEmitter emitter = ParticleIO.loadEmitter(emitterConfigFile);
			trail.addEmitter(emitter);
		} catch (IOException e) {
			System.out.println("ресурсы повреждены: отсутствует эмиттер частиц для эффекта шлейфа шаровой молнии");
		}
	}
	
	private void loadAnim() {
		//TODO: нарисовать и подгрузить анимации
		try {
			temp_image = new Image("textures/spells/lightning.png");
		} catch (SlickException e) {
			System.out.println("вместо шаровой молнии поняша призвала Сотону, и он сожрал нужную пикчу");
		}	
	}
}

