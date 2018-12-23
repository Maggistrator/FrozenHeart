package logic.spells;

import java.io.File;
import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import it.marteEngine.entity.Entity;
import logic.entity.Monster;
import logic.entity.StarlightGlimmer;

public class ImpactLightning extends Entity{
	
	private final float SEARCHING_SPEED = 2F;
	private final float CHAISING_SPEED = 8F;
	private final float ORIENTED_MOTION_SPEED = 6f;
	public final int MAX_DISTANCE = 400;
	public float finding_range = 100;
	
	public int damage = 10;
	public float endx, endy;
	public float distance = 0;
	
	private Monster enemy;
	private StarlightGlimmer sender;
	private Sound sound;
	
	private Rectangle get_rect  = new Rectangle(x, y, 40, 40);
	ParticleSystem trail;
	private Image temp_image;
	
	public ImpactLightning(float x, float y, float newx, float newy, StarlightGlimmer sender) {
		super(x, y);
		this.sender = sender;
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
		calculateSpeed(ORIENTED_MOTION_SPEED, endx, endy);
		setHitBox(0, 0, 40, 40);
		loadAnim();
//		loadParticles();
		loadSound();

        sound.play(0.8f, 1f);
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		g.texture(get_rect, temp_image, true);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		Monster monster;
		get_rect.setX(x);
		get_rect.setY(y);

		if((monster = (Monster) collide("MONSTER", x+2, y)) != null) {
			monster.getHitted(damage);
			sound.stop();
			destroy();
		}
		
		distance = getDistance(sender);
		if(distance > MAX_DISTANCE) {
			sound.stop();
			destroy();
		}
	}
	
	private void calculateSpeed(float speed_const, float targetX, float targetY) {
		Vector2f distance = getDistanceTo(targetX, targetY);
		distance.normalise();
		speed = distance.scale(speed_const);
	}
	
	private Vector2f getDistanceTo(float targetX, float targetY) {
		Vector2f targetCoords = new Vector2f(targetX, targetY);
		Vector2f selfCoords = new Vector2f(x, y);
		Vector2f distance = targetCoords.sub(selfCoords);
		return distance;
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

	private void loadSound() {
		try {
			sound = new Sound("res/sounds/103687__chimerical__melt-down-1.ogg");
		} catch (SlickException e) {
			System.err.println("вынь пальцы из розетки, электрического звука шаровой молнии не слышно (NPE)");
		}
	}
}

