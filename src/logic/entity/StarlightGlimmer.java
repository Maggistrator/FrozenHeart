package logic.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;

public class StarlightGlimmer extends Entity{

	//константы движения (но не направления!)
	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";
	
	//константы анимации
	private static final String ANIM_MOVING = "move";
	private static final String ANIM_CALM = "calm";

	public StarlightGlimmer(float x, float y) throws SlickException {
		super(x, y);
		//клавиши управления
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);

		setHitBox(0, 60, 80, 20);
		speed = new Vector2f(0, 0);
		addType(PLAYER);
		
		initalizeAnimations();
		setAnim(ANIM_CALM);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		speed.x = 0;
		speed.y = 0;
		if(check(RIGHT)) speed.x = 1;
		if(check(LEFT)) speed.x = -1;
		if(check(UP)) speed.y = -1;
		if(check(DOWN)) speed.y = 1;
		if(check(LEFT)||check(RIGHT)||check(UP)||check(DOWN)) setAnim(ANIM_MOVING);
		else setAnim(ANIM_CALM);
	}
	
	private void initalizeAnimations() throws SlickException{
		SpriteSheet moving = new SpriteSheet(new Image("textures/sprites/starlight/starlight.png").getScaledCopy(0.4f), 120, 100);
		Animation anim_moving = new Animation(moving, 350);
		addAnimation(ANIM_MOVING, anim_moving);
		
		Animation anim_calm = new Animation(new Image[] {new Image("textures/sprites/starlight/starlight_calm.png").getScaledCopy(0.4f)}, 20000);
		addAnimation(ANIM_CALM, anim_calm);
		
	    this.setAlpha(0.5f);  
	}
	
}
