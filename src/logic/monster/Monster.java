package logic.monster;

import org.newdawn.slick.SlickException;

import it.marteEngine.entity.Entity;
import logic.pony.StarlightGlimmer;

public abstract class Monster extends Entity{
	protected float hp;
	protected StarlightGlimmer pony;
	
	public Monster(float x, float y, StarlightGlimmer pony) {
		super(x, y);
		this.pony = pony;
		addType("MONSTER");
	}
	
	public abstract void die();
	
	public abstract void getHitted(int damage);
	
	public abstract void hit(Entity target) throws SlickException;
	
}