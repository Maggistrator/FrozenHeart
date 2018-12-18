package logic.entity;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;

public class EvilSnowman extends Monster {
	
	//Alarms names
	private static final String ATTACK_COOLDOWN_PASSED = "nope!";
	
	//numeric constants
	private int effectiveRange = 550;
	private int meleeAtackRange = 25;
	
	private float SPEED_CONST = 2f;
	
	Random rand = new Random();
	
	private boolean isAllowedToMove = true;

	public EvilSnowman(float x, float y, StarlightGlimmer pony) throws SlickException {
		super(x, y, pony);
		//добавление циклического таймера на регулярные атаки
		//addAlarm(ATTACK_COOLDOWN_PASSED, 4, false, true);
		setGraphic(new Image("textures/snowman/test.png"));
		setHitBox(0, 0, 60, 120);
		hp = 10;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		
		if (isAllowedToMove) {
			recalculateSpeed(delta);
		} else {
			speed.x = 0;
			speed.y = 0;
		}
		
	}
	
	/**
	 * Принятие решения о типе атаки - сами атаки выделены в отдельные методы	
	 * */
	@Override
	public void hit(Entity target) {
		System.err.println("hit!");
		if(Math.abs(x - pony.x) > meleeAtackRange) {
			throwIcicle();
		} 
		else {
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
		// TODO Автоматически созданная заглушка метода
		System.err.println("icicle!");
	}
	
	public void meleeAtack() {
		// TODO Автоматически созданная заглушка метода
		System.err.println("melee!");
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
	
	@Override
	public void alarmTriggered(String name) {
		//если пришло время атаковать..
		if (name.equalsIgnoreCase(ATTACK_COOLDOWN_PASSED)) {
			//..с вероятностью 1 к 2-м мы..
			if (rand.nextBoolean()) {
				//..ударим лошадку
				if (Math.abs(x - pony.x) < effectiveRange) {
					hit(pony);
					isAllowedToMove = false;
				}
			}
		}
	}
}
