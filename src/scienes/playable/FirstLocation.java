package scienes.playable;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import core.MovingAreaCamera;
import core.Solid;
import core.ui.GameUI;
import core.ui.PathBar;
import it.marteEngine.Camera;
import it.marteEngine.CameraFollowStyle;
import it.marteEngine.ME;
import it.marteEngine.World;
import it.marteEngine.entity.Entity;
import logic.monster.MonsterFactory;
import logic.monster.snowman.EvilSnowman;
import logic.pony.StarlightGlimmer;
import scienes.Launcher;

public class FirstLocation extends World {

	StarlightGlimmer player;
	GameUI ui;
	EvilSnowman snowman;
	MonsterFactory factory;
	
	PathBar path;
	
	Music bgmusic;
	Rectangle location = new Rectangle(0, 0, 1280, 480);
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.container = container;
		//фиксируем время начала матча
		startTime = System.currentTimeMillis()/1000;
		
		//сбрасываем поняшкины статы
		player.hope = 90;
		player.power = 90;
		player.stresspoints = 0;
		player.x = 5;
		player.y = 280;
		player.ultcharge = 0;
		
		snowman = new EvilSnowman(640, 250, player);

		Entity background1 = new Solid(0, 0, new Image("textures/locations/test.png"));
		Entity background2 = new Solid(640, 0, new Image("textures/locations/test.png"));
		Entity background3 = new Solid(1280, 0, new Image("textures/locations/test.png"));
		//camera = new Camera(container.getWidth()/5, container.getWidth()/3, 1280, 480);
		
		ui = new GameUI(container, camera, player);
		factory = new MonsterFactory(player, container);
		bgmusic = new Music("res/music/Radiarc - Aleph Null [Hybrid Orchestral].ogg");
		path = new PathBar(container.getWidth() * 3 - 200, container.getWidth(), container.getHeight());

		add(background1, GAME);
		add(background2, GAME);
		add(background3, GAME);

		add(player, GAME);
		add(snowman, GAME);
		
		add(ui, ABOVE);
		add(path, ABOVE);
		
		container.setTargetFrameRate(60);
		if (!ME.debugEnabled) bgmusic.loop();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		super.init(container, game);
//		player = new StarlightGlimmer(x, y)

		camera.follow(player, CameraFollowStyle.PLATFORMER);
		camera.setDeadZone(0, 0, 0, 0);
		
		camera.setSceneBounds(0, 0, 1920, 480);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		g.drawString("Press ESC to exit to main menu", 20, 460);
	}

	int timer = 0;
	int quant = 1;
	float friquency = 50;
	Random rand = new Random();

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)) game.enterState(Launcher.MENU);
				
		if(timer % quant == 0) {
			timer = 0;
			if(friquency > 1)friquency--;
			quant = rand.nextInt((int)friquency)+100;
			//add(factory.createMonster(), GAME);
		}
		
		if(player.hope < 0) game.enterState(Launcher.FINISH);
		path.update(container, camera.getX(), player.x, delta);
		if (player.x > container.getWidth()*3 - 200) {
			game.enterState(Launcher.FINISH);
		}
		
		updateTimeUsed();
		timer++;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if(button == Input.MOUSE_LEFT_BUTTON) {
			player.castSpell(StarlightGlimmer.SPELL_A, camera.getX()+x, y);
		}
		if(button == Input.MOUSE_RIGHT_BUTTON) {
			player.castSpell(StarlightGlimmer.SPELL_B, camera.getX()+x, y);
		}
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);
		clear();
		bgmusic.stop();
	}
	
	long startTime;
	long timePassed = 0;//в секундах
	/**пересчёт времени прохождения*/
	private void updateTimeUsed() {
		long currentTimeMillis = System.currentTimeMillis();
		timePassed = currentTimeMillis/1000 - startTime;
		player.stats.timeUsed = timePassed;
	}
	
	public FirstLocation(int id, StarlightGlimmer pony) {
		super(id);
		this.player = pony;
	}
}
