package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;

public class Camera {
	// сущность, за которой следует камера
	private Entity toFollow;
	// границы камеры
	private Rectangle bounds;
	// хитбокс сущности, за которой следует камера
	private Rectangle hitBox2Follow = new Rectangle(0,0,20,20);
	
	//координаты камеры
	public float X = 0;
	public float Y = 0;
	
	
	/**
	 * Конструктор камеры
	 * @param toFollow - сущность, за которой должна следовать камера
	 * @param bounds - границы камеры (обычно, равные размерам игрового мира) 
	 * @param container - игровой контейнер, от его свойств зависит центрирование сущности
	 **/
	public Camera(Entity toFollow, Rectangle bounds, GameContainer container) {
		// инициализируем переменные 
		X = toFollow.x;
		Y = toFollow.y;
		this.toFollow = toFollow;
		this.bounds = bounds;
		hitBox2Follow = new Rectangle(toFollow.x,toFollow.y,toFollow.hitboxWidth,toFollow.hitboxHeight);
		/*
		 * если область, за которой нужно следить не влезает 
		 * на экран, то она уменьшается до [размеры области - (размеры контейнера+хитбокс)/2]
		 * вероятно, это свободная область, в которой камера следит за игроком
		 * за её пределами, камера открепляется
		 * */
		if(bounds.getWidth()>container.getWidth()){
			bounds.setWidth(bounds.getWidth()-(container.getWidth()+hitBox2Follow.getWidth())/2);
			bounds.setX(bounds.getX()+(container.getWidth()+hitBox2Follow.getWidth())/2);
		}
		// высоты это тоже касается
		if(bounds.getHeight()>container.getHeight()){
			bounds.setHeight(bounds.getHeight()-(container.getHeight()+hitBox2Follow.getHeight())/2);
			bounds.setY(bounds.getY()+(container.getHeight()+hitBox2Follow.getHeight())/2);
		}
		
	}
	
	/**
	 * Сама камера не отрисовывается, но трансляция графического контекста, составляющая
	 * основу принципа действия камеры происходит именно здесь
	 * */
	public void draw(GameContainer container, Graphics g) throws SlickException{
		float hitboxless_boundsX = container.getWidth() - hitBox2Follow.getWidth();
		float hitboxless_boundsY = container.getHeight()- hitBox2Follow.getHeight();
		
		/*
		 * сия ибучая конструкция, перемещает весь графический контекст
		 * на координату, противоположную координатам сущности,
		 * до тех пор, пока до границы отслеживаемой области не останется
		 * расстояние, равное половине контейнера
		 * (учитывая допуск в (контейнер+хитбокс)/2, для центрирования)
		 * */
		g.translate(-(X - hitboxless_boundsX / 2), -(Y - hitboxless_boundsY / 2));
	}

	public void update(int delta) {
		/*
		 * свобода перемещения сущности определяется как половина высоты контейнера
		 * в каждую сторону. Пока сущность не вышла за эти границы, камера следит
		 * за ней
		 * */
		sasha_approximation_algorythm(toFollow, this, delta);
	}
	
	//буферные переменные, хранящие координаты сущности, 
	//пока она не выйдет за пределы допустимой области
	private float bufferedX = 0;
	private float bufferedY = 0;
	
	//коэффициент скорости камеры
	private Vector2f speed_coefficient = new Vector2f(0.005f, 0.002f);
	
	private void sasha_approximation_algorythm(Entity target, Camera camera, int delta) { 
		//синхронизация буферных переменных с сущностью
		if(target.x > bounds.getX() && target.x < bounds.getWidth()) bufferedX = target.x;
		if(target.y > bounds.getY() && target.y < bounds.getHeight()) bufferedY = target.y;
		
		//вычисление аппроксимации
		float deltaX = bufferedX - camera.X;
		float deltaY = bufferedY - camera.Y;
		
		camera.X += deltaX * speed_coefficient.x * delta;
		camera.Y += deltaY * speed_coefficient.y * delta;		
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Camera:");
		result.append("\n"+"x:"+bounds.getX());
		result.append("\n"+"y:"+bounds.getY());
		result.append("\n"+"width: "+bounds.getWidth());
		result.append("\n"+"height: "+bounds.getHeight());
		return result.toString();
	}
	
}
