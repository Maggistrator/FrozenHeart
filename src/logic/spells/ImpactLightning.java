package logic.spells;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import it.marteEngine.entity.Entity;
import logic.entity.Monster;

public class ImpactLightning extends Entity{
	
	private final float CONST_SPEED_FACTOR = 6f;
	public final int MAX_DISTANCE = 640;
	
	public int damage = 10;
	public float endx, endy;
	public int distance = 0;
	private Rectangle get_rect  = new Rectangle(x, y, 40, 40);
	
	private Image temp_image;
	
	public ImpactLightning(float x, float y, float newx, float newy) {
		super(x, y);
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
//		calculateSpeed();
		speed.x = CONST_SPEED_FACTOR; 
		speed.y = 0;
		setHitBox(0, 0, 40, 40);
		loadAnim();
	}
	
	private void calculateSpeed() {
		float deltaX = (endx - x);
		float deltaY = (endy - y);
		
		System.out.println("deltaX = "+deltaX);
		System.out.println("deltaY = "+deltaY);

		// тут скорость по обеим осям уравнивается, на основе отношения расстояний по этим осям
		float cocksuckingFactor = Math.abs(deltaX / deltaY);

		this.speed.x = cocksuckingFactor > 1 ? cocksuckingFactor : Math.abs(deltaY / deltaX);
		this.speed.y = this.speed.x > 1 ? Math.abs(deltaY / deltaX) : cocksuckingFactor;
		
		System.out.println("speedx = "+speed.x);
		System.out.println("speedy = "+speed.y);
		
		this.speed.x *= CONST_SPEED_FACTOR; 
		this.speed.y *= CONST_SPEED_FACTOR;
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		g.texture(get_rect, temp_image, true);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		get_rect.setX(x);
		get_rect.setY(y);
		distance += speed.x;
		if(distance > MAX_DISTANCE) world.remove(this);
	}
	
	private void loadAnim() {
		//TODO: нарисовать и подгрузить анимации
		try {
			temp_image = new Image("textures/spells/zap.png");
		} catch (SlickException e) {
			System.out.println("вместо шаровой молнии поняша призвала Сотону, и он сожрал нужную пикчу");
		}	
	}
	
	@Override
	public void collisionResponse(Entity other) {	
		super.collisionResponse(other);
		if(other instanceof Monster) {
			Monster enemy = (Monster) other;
			enemy.getHitted(damage);
		}
	}
	
}

