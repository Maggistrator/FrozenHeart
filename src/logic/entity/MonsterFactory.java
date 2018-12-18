package logic.entity;

import java.util.Random;

import org.newdawn.slick.SlickException;

public class MonsterFactory {
	
	StarlightGlimmer pony;
	
	public MonsterFactory(StarlightGlimmer pony) {
		this.pony = pony;
		
	}

	public Monster createMonster() throws SlickException {
		Random rand = new Random();
		Monster monster;
		
		if(rand.nextBoolean()) {
			monster = new EvilSnowman(0, 0, pony);		
		}
		else {
			//TODO: создать класс оленя
			monster = new EvilSnowman(0, 0, pony); 
		}
		return monster;
	}
	
}
