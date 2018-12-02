package core;

import java.util.Observable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;
import logic.event.CameraEvent;

public class MovingAreaCamera extends Observable{
	
	//----зона свободного хода----
	public float x = 0, y = 0;
	public int rightBorder = 0, leftBorder = 0;
	//----------------------------
	/**коэффициент аппроксимации камеры (условная скорость)*/
	public float speed = 0.004f;
	/**конечные координаты - нужны, чтобы камера не "телепортировалась" к игроку, а догоняла его*/
	float end_x = 0, end_y = 0;
	
	Entity toFollow;
	private Vector2f worldBoundaries;
	
	public MovingAreaCamera(Entity toFollow, Vector2f worldBoundaries, int leftBorder, int rightBorder) {
		this.toFollow = toFollow;
		this.rightBorder = rightBorder;
		this.leftBorder = leftBorder;
		this.worldBoundaries = worldBoundaries;
	}

	//сдвиг по оси new_x
	//из-за специфики игры, по другим осям камера не двигается
	public void draw(Graphics g) {
			g.translate(-x, 0);
	}
	
	public void update(GameContainer container) {
	    //------пересечение границы зоны свободного хода устанавливает новую путевую точку-----//
		if(toFollow.x > x + rightBorder ) {
			end_x += toFollow.x - (x + rightBorder);
			recalculateCoords();
		}
		
		if(toFollow.x < x + leftBorder) {
			end_x += toFollow.x - (x+leftBorder);
			recalculateCoords();
		}
	}
		private void recalculateCoords() {
		//--------вычисление новых координат--------//
		float deltaX = end_x - x;
		float step = deltaX * speed;
		// слишком маленький шаг приравнивается к 0
		if (step < 2f)
			end_x = x;
		x += step;
		
		if(x < 0) x = 0;
		if(x > worldBoundaries.x) x = worldBoundaries.x;
		
		// уведомляем слушателей
		//this.setChanged();
		notifyObservers(new CameraEvent(x, y));
		//TODO: разбить update на отдельные методы, выполняющие по 1-й задаче
	}
	
}
