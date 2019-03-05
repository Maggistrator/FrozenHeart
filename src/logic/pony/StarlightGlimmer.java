package logic.pony;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.pony.spells.ImpactLightning;

public class StarlightGlimmer extends Entity{

	//константы движения (но не направления!)
	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";
	
	//константы анимации
	private static final String ANIM_MOVING = "move";
	private static final String ANIM_CALM = "calm";

	//группы заклинаний - атакующие и защитные
	public static final String ATTACKING = "attack";
	public static final String PROTECTING = "protect";

	//заклинание на ЛКМ, на ПКМ, и ультимативная способность
	public static final String SPELL_A = "A";
	public static final String SPELL_B = "B";
	public static final String ULTIMATE = "disintegrate 'em flesh 'n souls";	
	
	public float hope = 90;//жизнь, она же - надежда
	public float power = 90;//энергия, она же - мощь
	public float stresspoints = 0;//цветы стресса - восстанавливаемые очки поглощения урона
	public float ultcharge = 0;//процент заряда ультимативной способности
	
	public float delta_power = 0.007f;//коэффициент, регулирующий прирост мощи 
	
	//текущая избранная группа заклинаний
	public String spellgroup = ATTACKING;
	
	//служебный таймер отката следующего очка стресса
	private int stresspointsTimer = 0;
			
	public Statistics stats = new Statistics();
	
	public StarlightGlimmer(float x, float y) throws SlickException {
		super(x, y);
		//клавиши управления
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);

		define(ULTIMATE, Input.KEY_F);

		//клавиши смены группы заклинаний
		define(ATTACKING, Input.KEY_E);
		define(PROTECTING, Input.KEY_Q);
		
		//хитбокс и скорость
		setHitBox(0, 0, 80, 80);
		width = 80;
		height = 80;
		speed = new Vector2f(0, 0);
		
		//тип сущности
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
		if(check(ATTACKING)) {
			spellgroup = ATTACKING;
			System.out.println("now spellgroup is ATTACKING");
		}
		if(check(PROTECTING)) {
			spellgroup = PROTECTING;
			System.out.println("now spellgroup is PROTECTING");
		}
		if(check(LEFT)||check(RIGHT)||check(UP)||check(DOWN)) setAnim(ANIM_MOVING);
		else setAnim(ANIM_CALM);
		
		//восстановление энергии
		if(power<90) power += delta*delta_power;
		
		//подзаряка ультимейта
		if(ultcharge<100) {
			ultcharge +=0.005f;
		}
		
		//восстановление стресспоинтов
		if (stresspoints < 4) {
			if (stresspointsTimer % 500 == 0) {
				stresspoints++;
				stresspointsTimer = 0;
				stats.stresspointsRestored++;
			}
			stresspointsTimer++;
		}
	}
	
	public void castSpell(String spell, float mouseX, float mouseY) {
		try {
			if (spell.equals(SPELL_A) && spellgroup.equals(ATTACKING)) {
				if (power > 10) {
					power -= 10;
					world.add(new ImpactLightning(x + width - 20, y, mouseX, mouseY, this), World.GAME);
					Sound sound = new Sound("res/sounds/cast_lightning.ogg");
					sound.play();
				}
			}
		} catch (SlickException e) {
			e.printStackTrace();
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

	public void getHitted(int damage) {
		if(stresspoints > 0) {
			if(damage <= 5) {
				stresspoints--;
			} else {
				if(stresspoints >= 2) stresspoints -= 2; 
				else {
					stresspoints = 0;
					hope -= damage/2;
				}
			}
		} else {
			hope -= damage;
		}
		//вставить анимацию по готовности
	}
	
}
