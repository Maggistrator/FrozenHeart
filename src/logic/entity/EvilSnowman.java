package logic.entity;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import it.marteEngine.entity.Entity;

public class EvilSnowman extends Monster {
	
	//Alarms names
	private static final String ATTACK_COOLDOWN_PASSED = "nope!";
	
	//numeric constants
	private int effectiveRange = 550;
	private int meleeAtackRange = 25;
	private float SPEED_CONST = 2f;
	
	StarlightGlimmer pony;
	Random rand = new Random();
	
	private boolean isAllowedToMove = true;

	public EvilSnowman(float x, float y, StarlightGlimmer pony) throws SlickException {
		super(x, y);
		this.pony = pony;
		//добавление циклического таймера на регулярные атаки
		//addAlarm(ATTACK_COOLDOWN_PASSED, 4, false, true);
		setGraphic(new Image("textures/snowman/test.png"));
		hp = 10;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		if (isAllowedToMove) {
			recalculateSpeed(container.getFPS());
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

	/**
	 * 
	 * */
	private void recalculateSpeed(int FPS) {
		if(Math.abs(x - pony.x) < Math.abs(y - pony.y)) {
			float axesFactor = pony.y / pony.x;

			speed.x = x < pony.x ? SPEED_CONST : -SPEED_CONST;
			speed.y = pony.x * axesFactor; //не использован SPEED_CONST, ибо в pony.x учтён знак
		}else {
			float axesFactor = pony.x / pony.y;

			speed.y = y < pony.y ? SPEED_CONST : -SPEED_CONST;
			speed.x = pony.y * axesFactor;
		}
	}

	private void throwIcicle() {
		// TODO Автоматически созданная заглушка метода
		System.err.println("icicle!");
	}
	
	private void meleeAtack() {
		// TODO Автоматически созданная заглушка метода
		System.err.println("melee!");
	}
	
	@Override
	public void getHitted(int damage) {
		// TODO: впилить анимацию получения повреждений
		hp -= damage;
	}
	
	private void die() {
		// TODO Автоматически созданная заглушка метода
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
