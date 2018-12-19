package logic.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import core.MovingAreaCamera;
import it.marteEngine.Camera;
import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.spells.ImpactLightning;

public class StarlightGlimmer extends Entity{
	
	public static final String SPELL_FIREBALL = "fireball";
	public static final String SPELL_IMPULS = "impuls";
	public static final String SPELL_SHIELD = "shield";
	public static final String SPELL_TELEPORT = "teleport";

	//константы движения (но не направления!)
	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";
	
	//константы анимации
	private static final String ANIM_MOVING = "move";
	private static final String ANIM_CALM = "calm";

	private static final String ATTACKING = "attack";
	private static final String PROTECTING = "protect";

	private static final String SPELL_A = "A";
	private static final String SPELL_B = "B";
	
	public float hope = 90;
	public float power = 90;
	public float ultcharge = 0;
	public String spellgroup = ATTACKING;
			
	public StarlightGlimmer(float x, float y) throws SlickException {
		super(x, y);
		//клавиши управления
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);

		define(ATTACKING, Input.KEY_E);
		define(PROTECTING, Input.KEY_Q);
		
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
		if(check(RIGHT)) speed.x = 2;
		if(check(LEFT)) speed.x = -2;
		if(check(UP)) speed.y = -2;
		if(check(DOWN)) speed.y = 2;
		if(check(LEFT)||check(RIGHT)||check(UP)||check(DOWN)) setAnim(ANIM_MOVING);
		else setAnim(ANIM_CALM);
		
		// ---пример заклинания---//
		// заклинанния следует вынести в отдельные методы, а снаряды сделать
		// классами-сущностями
		if(power<90) {
			power += 0.1f;
		}
		
		if(ultcharge<100) {
			ultcharge +=0.005f;
		}
	}
	
	public void castSpell(String spell, int mouseX, int mouseY) {
		if (spell.equals(SPELL_A) && spellgroup.equals(ATTACKING)) {
			if (power > 10) {
				power -= 10;
				world.add(new ImpactLightning(x + width-20, y, mouseX, mouseY), World.GAME);
			}
		}
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
