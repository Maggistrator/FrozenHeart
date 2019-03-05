package logic.monster.snowman;

import it.marteEngine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import logic.monster.Monster;
import logic.pony.StarlightGlimmer;

public class Icicle extends Entity {

	private final float SPEED = 4f;
	public final int MAX_DISTANCE = 400;
	public float finding_range = 100;

	public int damage = 10;
	public float endx, endy;
	public float distance = 0;
	
	private Monster sender;

	private Shape get_rect = new Rectangle(x, y, width = 50, height = 30);

	private Image image;

	public Icicle(float x, float y, float newx, float newy, Monster sender) throws SlickException {
		super(x, y);
		this.sender = sender;
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
		calculateSpeed(SPEED, endx, endy);
		setHitBox(0, 0, width, height);
		float rotate = 0;
		if (speed.x < 0) {
			// если скорость <0, значит, сосулька летит влево
			image = new Image("textures/spells/icicle.png");
			rotate = calculateAngle(x, y, newx, newy) + 90;
		} else {
			// в противном случае - вправо
			image = new Image("textures/spells/icicle.png").getFlippedCopy(true, false);
			rotate = calculateAngle(x, y, newx, newy) - 90;
		}

		scale = 0.4f;
		setGraphic(image);
		setCentered(true);
		setCenterOfRotation(width / 2, height / 2);
		this.setAngle((int) rotate);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		//image.draw(get_rect.getX(), get_rect.getY(), 50, 30);
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
}
