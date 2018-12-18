package logic.entity;

import it.marteEngine.entity.Entity;

public abstract class Monster extends Entity{
	float hp;
	StarlightGlimmer pony;
	
	public Monster(float x, float y, StarlightGlimmer pony) {
		super(x, y);
		this.pony = pony;
		addType("MONSTER");
	}
	
	public abstract void die();
	
	public abstract void getHitted(int damage);
	
	public abstract void hit(Entity target);
	
}
