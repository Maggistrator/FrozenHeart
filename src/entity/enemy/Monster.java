package entity.enemy;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;

public abstract class Monster extends Entity{
	float end_x = 0, end_y = 0;
	
	public Monster(float x, float y) {
		super(x, y);
		Random rand = new Random();
		end_y = rand.nextInt(480);
		speed = new Vector2f(0, 0);
	}

	float speedFactorX = 0.12f;
	float speedFactorY = 0.12f;
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		//отрицательны ли координаты
		boolean isXNegative = x < 0 ? true : false;
		boolean isYNegative = y < 0 ? true : false;
		
		//отрицательны ли целевые координаты
		boolean isEndXNegative = end_x < 0 ? true : false;		
		boolean isEndYNegative = end_y < 0 ? true : false;
		
		//если x > конечная x, и они оба отрицательны, или оба неотрицательны, то движемся слева направо
		if(Math.abs(x) < Math.abs(end_x) && ((isEndXNegative && isXNegative) || !(isXNegative && isEndXNegative)))  
		{
			speed.x  =0;
		}
	}
	
	public void lolkek() {
int delta =0;//deprec!!!!
		



		//Расстояние по обоим осям, которое предстоит пройти (в пикселях)
		//p.s. Отрицательное расстояние значит движение назад
		float deltaX = (end_x - x);
		float deltaY = (end_y - y);

		//-------------------упрощенный алгоритм-----------------------------//
		//количество шагов до цели по каждой из осей
		float stepsOnXAxis = Math.abs(deltaX) / (delta * speedFactorX);
		float stepsOnYAxis = Math.abs(deltaY) / (delta * speedFactorY);
		
		int LEFT = -1;
		int RIGHT = 1;
		int UP = -1;
		int DOWN = 1;
		
		//вычисление направление на основе знака расстояния
		int directionX = deltaX < 0 ? LEFT : RIGHT;
		int directionY = deltaY < 0 ? UP : DOWN;

		//тут скорость по обеим осям уравнивается, на основе отношения расстояний по этим осям
		float cocksuckingFactor = Math.abs(deltaX/deltaY);
		
		this.speed.x = cocksuckingFactor > 0 ? cocksuckingFactor : Math.abs(deltaY/deltaX);
		this.speed.y = this.speed.x > 0 ? Math.abs(deltaY/deltaX) : cocksuckingFactor; 
		}
	
}
