package logic.spells;

import it.marteEngine.entity.Entity;

import java.io.File;
import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import logic.entity.Monster;
import logic.entity.StarlightGlimmer;

public class Icicle extends Entity {

	private final float SPEED = 4f;
	public final int MAX_DISTANCE = 400;
	public float finding_range = 100;

	public int damage = 5;
	public float endx, endy;
	public float distance = 0;
	
	private Monster sender;

	private Rectangle get_rect = new Rectangle(x, y, 50, 30);
	ParticleSystem trail;
	private Image image;
	private Image flippedCopy;

	public Icicle(float x, float y, float newx, float newy, Monster sender) {
		super(x, y);
		this.sender = sender;
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
		calculateSpeed(SPEED, endx, endy);
		setHitBox(0, 0, 50, 30);
		
		try {
			image = new Image("textures/spells/icicle.png");
			flippedCopy = image.getFlippedCopy(true, false);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		//targetAngle = speed.x > 0 ? getTargetAngle() : 360 - getTargetAngle();
		//вращение пока не работает
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
//		----начало вращения----
//		g.rotate(x + width/2, y+height/2, targetAngle);
//		----------------------
		
		if(speed.x < 0) g.texture(get_rect, image, true);
		else  g.texture(get_rect, flippedCopy, true);
		
		g.draw(get_rect);
		
//		----конец вращения----
//		g.rotate(x + width/2, y+height/2, -targetAngle);
//		----------------------
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		StarlightGlimmer player;
		get_rect.setX(x);
		get_rect.setY(y);

		if ((player = (StarlightGlimmer) collide(PLAYER, x + 2, y)) != null) {
			player.getHitted(damage);
			destroy();
		}

		distance = getDistance(sender);
		if (distance > MAX_DISTANCE)
			destroy();
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

	public float getTargetAngle() {
		float angle = 0f;
		Vector2f targetPos = new Vector2f();
		
		angle = getAngleToPosition(targetPos);
		return angle;
	}
	
	public double getTargetAngle(float startX, float startY, float targetX, float targetY) {
		double dist = getDistance(new Vector2f(targetX, targetY));
		double sinNewAng = (startY - targetY) / dist;
		double cosNewAng = (targetX - startX) / dist;
		double angle = 0;

		if (sinNewAng > 0) {
			if (cosNewAng > 0) {
				angle = 90 - Math.toDegrees(Math.asin(sinNewAng));
			} else {
				angle = Math.toDegrees(Math.asin(sinNewAng)) + 270;
			}
		} else {
			angle = Math.toDegrees(Math.acos(cosNewAng)) + 90;
		}
		return angle;
	}
}
