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
		System.out.println(x);
		System.out.println(y);
		System.out.println(width);
		System.out.println(height);
	}
	
	public void draw(Graphics g) {
			g.translate(-x, 0);
			System.out.println("translate!");
	}
	
	public void update(GameContainer container) {
		if(toFollow.x > (x + container.getWidth()/2) && toFollow.x < bounds.getWidth()) {
			end_x = toFollow.x;
			System.out.println("x changed, endx = " + end_x);
		}
		if(end_x > x) {
			float deltaX = end_x - x;
			x += deltaX * speed;
			System.out.println("newx generated: " + x);
		}
	}
}
