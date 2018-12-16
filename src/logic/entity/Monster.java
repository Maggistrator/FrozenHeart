package logic.entity;

import it.marteEngine.entity.Entity;

public abstract class Monster extends Entity{
	float hp;
	
	public Monster(float x, float y) {
		super(x, y);
	}
	
	public abstract void getHitted(int damage);
	
	public abstract void hit(Entity target);
	
}
