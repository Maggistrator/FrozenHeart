package logic.entity;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.spells.Icicle;

public class EvilSnowman extends Monster {
	
	private int ATACK_COOLDOWN = 100;
	
	//numeric constants
	private int effectiveRange = 550;
	private int meleeAtackRange = 25;
	
	private float SPEED_CONST = 0.5f;
	
	Random rand = new Random();
	Image image;
	Rectangle textured = new Rectangle(x, y, 120, 140);
	
	private boolean isAllowedToMove = true;

	public EvilSnowman(float x, float y, StarlightGlimmer pony) throws SlickException {
		super(x, y, pony);
		//добавление циклического таймера на регулярные атаки
		image = new Image("textures/snowman/snowman.png");
		setHitBox(0, 0, 60, 120);
		addType("MONSTER");
		hp = 10;
	}

	int timer = 0;
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		
		if (isAllowedToMove && getDistance(pony) > pony.width) {
			recalculateSpeed(delta);
		} else {
			speed.x = 0;
			speed.y = 0;
		}
		
		timer += rand.nextInt(1)+1;
		if(timer % ATACK_COOLDOWN == 0) {
			timer = 0;
			scheduledTask();
		}
		
		textured.setX(x);
		textured.setY(y);
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		g.texture(textured, image, true);
		g.draw(textured);
	}
	
	/**
	 * Принятие решения о типе атаки - сами атаки выделены в отдельные методы	
	 * */
	@Override
	public void hit(Entity target) {
		System.err.println("hit!");
		if(getDistance(pony) > pony.width+meleeAtackRange) {
			throwIcicle();
		} else {
			meleeAtack();
		}
	}
	
	private void recalculateSpeed(int delta) {
		Vector2f ponyCoords = new Vector2f(pony.x, pony.y);
		Vector2f coords = new Vector2f(x, y);

		Vector2f distance = ponyCoords.sub(coords);
		distance.normalise();
		speed = distance.scale(SPEED_CONST);
	}

	private void throwIcicle() {
		if (rand.nextBoolean()) {
			System.err.println("icicle!");
			world.add(new Icicle(x, y, pony.x, pony.y, this), World.GAME);
			// анимация в разработке
		}
		isAllowedToMove = true;
	}
	
	public void meleeAtack() {
		// TODO Автоматически созданная заглушка метода
		System.err.println("melee!");
		isAllowedToMove = true;
	}
	
	@Override
	public void getHitted(int damage) {
		// TODO: впилить анимацию получения повреждений
		hp -= damage;
		if (hp <= 0) die();
	}
	
	public void die() {
		destroy();
	}
	
	public void scheduledTask() {
		// если пришло время атаковать, ударим лошадку
		if (Math.abs(x - pony.x) < effectiveRange) {
			isAllowedToMove = false;
			hit(pony);
		}
	}
}
