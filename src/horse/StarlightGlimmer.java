package horse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;

public class StarlightGlimmer extends Entity{

	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";

	public StarlightGlimmer(float x, float y) {
		super(x, y);
		//клавиши управления
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);

		setHitBox(0, 60, 80, 20);
		speed = new Vector2f(0, 0);
		addType(PLAYER);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		if(check(RIGHT)) move(delta, RIGHT);
		if(check(LEFT)) move(delta, LEFT);
		if(check(UP)) move(delta, UP);
		if(check(RIGHT)) move(delta, RIGHT);
	}
	
	public void move(int delta, String direction) {
		switch(direction) {
		case RIGHT:
			x += delta * speed.getX();
		break;
		case LEFT:			
			x -= delta * speed.getX();
		break;
		case UP:
			y -= delta * speed.getY();
		break;
		case DOWN:
			y += delta * speed.getY();
		break;
		}
	}
	
}
