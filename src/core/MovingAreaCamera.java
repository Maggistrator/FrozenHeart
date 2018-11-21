package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import it.marteEngine.entity.Entity;

public class MovingAreaCamera {
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
	
	public void draw(Graphics g) {
			g.translate(-x, 0);
	}
	
	public void update(GameContainer container) {
		if(toFollow.x > (x + container.getWidth()/2) && toFollow.x < bounds.getWidth()) {
			end_x = toFollow.x;
		}
		if(end_x > x) {
			float deltaX = end_x - x;
			x += deltaX * speed;
		}
		if(toFollow.x < x) end_x = x;
	}
}
