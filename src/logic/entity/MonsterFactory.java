package logic.entity;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class MonsterFactory {
	
	StarlightGlimmer pony;
	GameContainer container;
	
	public MonsterFactory(StarlightGlimmer pony, GameContainer container) {
		this.pony = pony;
		this.container = container;
	}

	public Monster createMonster() throws SlickException {
		Random rand = new Random();
		Monster monster;
		
		if(rand.nextBoolean()) {
			monster = new EvilSnowman(pony.x+container.getWidth()+rand.nextInt(200), rand.nextInt(container.getHeight()), pony);		
		}
		else {
			//TODO: создать класс оленя
			monster = new EvilSnowman(pony.x+container.getWidth()+rand.nextInt(200), rand.nextInt(container.getHeight()), pony); 
		}
		return monster;
	}
	
}
