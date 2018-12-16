package logic.entity;

import it.marteEngine.entity.Entity;

public class EvilSnowman extends Monster {

	public EvilSnowman(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hit(Entity target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getHitted(int damage) {
		{
			// анимация получения повреждений
			hp -= damage;
		}

	}

}
