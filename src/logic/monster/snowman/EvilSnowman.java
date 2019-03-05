package logic.monster.snowman;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.pony.StarlightGlimmer;
import logic.monster.Monster;

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
		width = 60; height = 120;
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
	}
	
	/**
	 * Принятие решения о типе атаки - сами атаки выделены в отдельные методы	
	 * */
	@Override
	public void hit(Entity target) {
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
			float icicle_x = x < pony.x ? x+width*0.58f : x+(width-width*0.58f);
			float icicle_y = x < pony.x ? y+height*0.67f : y+(height-height*0.67f);
			
			world.add(new Icicle(icicle_x, icicle_y, pony.x + pony.width/2, pony.y+pony.height/2, this), World.GAME);
			// анимация в разработке
		}
		isAllowedToMove = true;
	}
	
	public void meleeAtack() {
		// TODO Автоматически созданная заглушка метода
		isAllowedToMove = true;
	}
	
	@Override
	public void getHitted(int damage) {
		// TODO: впилить анимацию получения повреждений
		hp -= damage;
		if (hp <= 0) die();
	}
	
	public void die() {
		pony.stats.enemiesKilled++;//засчитываем поняшке фраг
		destroy();//умираем
	}
	
	public void scheduledTask() {
		// если пришло время атаковать, ударим лошадку
		if (Math.abs(x - pony.x) < effectiveRange) {
			isAllowedToMove = false;
			hit(pony);
		}
	}
}
