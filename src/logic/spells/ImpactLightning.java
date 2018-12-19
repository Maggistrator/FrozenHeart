package logic.spells;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import it.marteEngine.entity.Entity;
import logic.entity.Monster;

public class ImpactLightning extends Entity{
	
	private final float SEARCHING_SPEED = 2F;
	private final float CHAISING_SPEED = 8F;
	private final float ORIENTED_MOTION_SPEED = 6f;
	public final int MAX_DISTANCE = 400;
	public float finding_range = 100;
	
	public int damage = 10;
	public float endx, endy;
	public int distance = 0;
	public boolean isLinear = false;
	private Monster enemy;
	
	private Rectangle get_rect  = new Rectangle(x, y, 40, 40);
	ParticleSystem trail;
	private Image temp_image;
	
	public ImpactLightning(float x, float y) {
		super(x, y);
		addType(SOLID);
		setHitBox(0, 0, 40, 40);
		loadAnim();
		loadParticles();
		isLinear = true;
	}
	
	public ImpactLightning(float x, float y, float newx, float newy) {
		super(x, y);
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
		calculateSpeed(ORIENTED_MOTION_SPEED, endx, endy);
		setHitBox(0, 0, 40, 40);
		loadAnim();
		loadParticles();
		isLinear = false;
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
		if (isLinear) {
			if (enemy == null) {
				distance += SEARCHING_SPEED;
				x += SEARCHING_SPEED;
				// y += Math.sin(x/80);
			} else {
				if (!hasAlarm("target compute"))
					addAlarm("target compute", 4, false, true);
			}

			List<Entity> entities = world.getEntities();
			for (Entity potentialEnemy : entities) {
				if (potentialEnemy instanceof Monster) {
					if (potentialEnemy.getDistance(this) < finding_range) {
						enemy = (Monster) potentialEnemy;
					}
				}
			}
		}
		trail.update(delta);

		if((monster = (Monster) collide("MONSTER", x+2, y)) != null) {
			monster.getHitted(damage);
			destroy();
		}
		if(distance > MAX_DISTANCE) destroy();
	}
	
	private void calculateSpeed(float speed_const, float targetX, float targetY) {
		Vector2f targetCoords = new Vector2f(targetX, targetY);
		Vector2f selfCoords = new Vector2f(x, y);

		Vector2f distance = targetCoords.sub(selfCoords);
		distance.normalise();
		speed = distance.scale(speed_const);
	}

	private void calculateSpeed(Monster target, float speed_const) {
		Vector2f enemyCoords = new Vector2f(target.x, target.y);
		Vector2f selfCoords = new Vector2f(x, y);

		Vector2f distance = enemyCoords.sub(selfCoords);
		distance.normalise();
		speed = distance.scale(speed_const);
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
	
	@Override
	public void alarmTriggered(String alarmName) {
		super.alarmTriggered(alarmName);
		if(alarmName.equalsIgnoreCase("target compute")) {
			calculateSpeed(enemy, CHAISING_SPEED);
		}
	}
}

