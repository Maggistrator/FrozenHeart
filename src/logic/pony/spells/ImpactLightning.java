package logic.pony.spells;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.monster.Monster;
import logic.pony.StarlightGlimmer;

public class ImpactLightning extends Entity{
	
	private final float SEARCHING_SPEED = 2F;
	private final float CHAISING_SPEED = 8F;
	private final float ORIENTED_MOTION_SPEED = 6f;
	public final int MAX_DISTANCE = 400;
	public float finding_range = 100;
	
	public int damage = 10;
	public float endx, endy;
	public float distance = 0;
	
	private StarlightGlimmer sender;
	private Sound impactSound;
	
	private Rectangle get_rect  = new Rectangle(x, y, 40, 40);
	ParticleSystem sparkleburst;
	private Image temp_image;
	
	boolean timeToBurst = false;
	
	public ImpactLightning(float x, float y, float newx, float newy, StarlightGlimmer sender) throws SlickException {
		super(x, y);
		this.sender = sender;
		this.endx = newx;
		this.endy = newy;
		addType(SOLID);
		calculateSpeed(ORIENTED_MOTION_SPEED, endx, endy);
		setHitBox(40, 0, 40, 40);
		loadAnim();
		loadParticles();
		loadSound();
	}
	
	Color opaqueWhite = new Color(1f, 1f, 1f, 1f); 
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		if(timeToBurst) sparkleburst.render(x, y);
		g.setColor(opaqueWhite);
		if(!timeToBurst) super.render(container, g);
		g.setColor(Color.white);
	}	

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		super.update(container, delta);
		Monster monster;
		get_rect.setX(x);
		get_rect.setY(y);
		
		if(timeToBurst) {
			melt();
			sparkleburst.update(delta);
		}

		//при попадании в монстра..
		if((monster = (Monster) collide("MONSTER", x-50, y)) != null) {
			monster.getHitted(damage);//..отнимем ему хп
			sender.stats.damageDealed+=damage;//добавим урон в копилку поняшки
			speed.x = 0;
			speed.y = 0;
			//..взорвём к херам молнию
			timeToBurst = true;
			collidable = false;
			//..и сыграем победный "трунь" по этому поводу
	        impactSound.play(0.8f, 1f);
		}
		
		distance = getDistance(sender);
		if(distance > MAX_DISTANCE) {
			speed.x = 0.01f;
			speed.y = 0.01f;
			timeToBurst = true;
			collidable = false;
			melt();
		}
	}
	
	private void calculateSpeed(float speed_const, float targetX, float targetY) {
		Vector2f distance = getDistanceTo(targetX, targetY);
		distance.normalise();
		speed = distance.scale(speed_const);
	}
	
	private Vector2f getDistanceTo(float targetX, float targetY) {
		Vector2f targetCoords = new Vector2f(targetX, targetY);
		Vector2f selfCoords = new Vector2f(x, y);
		Vector2f distance = targetCoords.sub(selfCoords);
		return distance;
	}
	
	private void loadParticles() {
		try {
			sparkleburst = new ParticleSystem("textures/particles/colorable power.png", 800);
			ParticleEmitter emitter = ParticleIO.loadEmitter("res/emitters/sparkleburst.xml");
			sparkleburst.addEmitter(emitter);
		} catch (IOException e) {
			System.out.println("ресурсы повреждены: отсутствует эмиттер частиц для шаровой молнии");
		}
	}

	private void melt() {
		if (opaqueWhite.a > 0) {
			opaqueWhite.a -= 0.025f;
			get_rect.scaleGrow(1.001f, 1.001f);
		}
		if(opaqueWhite.a < 0.1f) destroy();
	}
	
	private void loadAnim() {
		//TODO: нарисовать и подгрузить анимации
		try {		
			float scale_factor = 0.2f;
			sheet = new SpriteSheet(new Image("textures/spells/spell_impact2.png").getScaledCopy(scale_factor), (int)(480*scale_factor), (int)(240*scale_factor));
			Animation anim = new Animation(sheet, 50);
			
		float rotate = 0;
		if (speed.x > 0) {
			// если скорость <0, значит, сосулька летит влево
			rotate = calculateAngle(x, y, endx, endy) - 90;
		} else {
			
			// в противном случае - вправо
			Image[] arr_img = new Image[12];
			for (int i = 0; i < sheet.getHorizontalCount(); i++) {
				arr_img[i] = sheet.getSubImage(i, 0).getFlippedCopy(true, false);
			}
			anim = new Animation(arr_img, 50);
			rotate = calculateAngle(x, y, endx, endy) + 90;
		}

		addAnimation("moving", anim);
		setAnim("moving");
		setCentered(true);
		setCenterOfRotation(width / 2, height / 2);
		this.setAngle((int) rotate);
		} catch (SlickException e) {
			System.out.println("вместо шаровой молнии поняша призвала Сотону, и он сожрал нужную пикчу");
		}	
	}

	
	private void loadSound() {
		try {
			impactSound = new Sound("res/sounds/lightning_impact.ogg");
		} catch (SlickException e) {
			System.err.println("вынь пальцы из розетки, электрического звука шаровой молнии не слышно (NPE)");
		}
	}
}

