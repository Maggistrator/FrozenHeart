package core;

import java.util.Observable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import core.event.CameraEvent;
import it.marteEngine.entity.Entity;

public class MovingAreaCamera extends Observable{
	
	//----зона свободного хода----
	public float x = 0, y = 0;
	public int width = 0, height = 0;
	//----------------------------
	/**коэффициент аппроксимации камеры (условная скорость)*/
	public float speed = 0.004f;
	/**конечные координаты - нужны, чтобы камера не "телепортировалась" к игроку, а догоняла его*/
	float end_x = 0, end_y = 0;
	
	Rectangle bounds;
	Entity toFollow;
	
	public MovingAreaCamera(Entity toFollow, Rectangle bounds, int width, int height) {
		this.toFollow = toFollow;
		this.bounds = bounds;
		this.width = width;
		this.height = height;
	}

	//сдвиг по оси new_x
	//из-за специфики игры, по другим осям камера не двигается
	public void draw(Graphics g) {
			g.translate(-x, 0);
	}
	
	public void update(GameContainer container) {
	    //пересечение границы зоны свободного хода устанавливает новую путевую точку
		if(toFollow.x > (x + container.getWidth()/2) && toFollow.x < bounds.getWidth()) {
			end_x = toFollow.x;
		}
		//если задана путевая точка, камера догоняет её
		if(end_x > x) {
			float deltaX = end_x - x;
			float step = deltaX * speed;
			//слишком маленький шаг приравнивается к 0 
			if(step < 2f) end_x = x;
			x += step;
			//уведомляем слушателей
			hasChanged();
			notifyObservers(new CameraEvent(x, y));
		}
		//если персонаж отступил за границы экрана слева, камера останавливается
		//(но обратно уже не возвращается!)
		if(toFollow.x < x) end_x = x;
		
		//TODO: разбить update на отдельные методы, выполняющие по 1-й задаче
	}
	
}
