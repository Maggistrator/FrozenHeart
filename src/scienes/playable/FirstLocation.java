package scienes.playable;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import core.MovingAreaCamera;
import core.ui.GameUI;
import it.marteEngine.World;
import logic.entity.EvilSnowman;
import logic.entity.MonsterFactory;
import logic.entity.StarlightGlimmer;
import scienes.Launcher;

public class FirstLocation extends World {

	StarlightGlimmer player;
	MovingAreaCamera  camera;
	GameUI ui;
	EvilSnowman snowman;
	MonsterFactory factory;
	
	Image background;
	Image background2;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.container = container;
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		super.init(container, game);
		player = new StarlightGlimmer(5, 300);
		snowman = new EvilSnowman(640, 250, player);
		background = new Image("textures/locations/test.png");
		background2 = new Image("textures/locations/test.png");
		camera = new MovingAreaCamera(player, new Vector2f(1280, 480), container.getWidth()/5, container.getWidth()/3);
		ui = new GameUI(container, camera, player);
		factory = new MonsterFactory(player, container);
		add(player, GAME);
		add(snowman, GAME);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		camera.draw(g);
		background.draw();
		background2.draw(640,0);
		g.drawString("Press ESC to exit to main menu", camera.x, 460);
		super.render(container, game, g);
		ui.draw(g);
	}

	int timer = 0;
	int quant = 1;
	Random rand = new Random();

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)) game.enterState(Launcher.MENU);
				
		if(timer % quant == 0) {
			timer = 0;
			quant = rand.nextInt(150)+100;
			add(factory.createMonster(), GAME);
		}
		
		camera.update(container);
		ui.update(delta);
	
		timer++;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if(button == Input.MOUSE_LEFT_BUTTON) {
			player.castSpell(StarlightGlimmer.SPELL_A, camera.x+x, y);
		}
		if(button == Input.MOUSE_RIGHT_BUTTON) {
			player.castSpell(StarlightGlimmer.SPELL_B, camera.x+x, y);
		}
	}
	
	@Override
	public int getID() {
		return this.id;
	}
	
	public FirstLocation(int id) {
		super(id);
	}
}
