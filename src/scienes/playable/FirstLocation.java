package scienes.playable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import core.MovingAreaCamera;
import core.ui.GameUI;
import it.marteEngine.World;
import logic.entity.StarlightGlimmer;
import scienes.Launcher;

public class FirstLocation extends World {

	StarlightGlimmer player;
	MovingAreaCamera  camera;
	GameUI ui;
	
	Image background;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.container = container;
		container.setTargetFrameRate(90);
		container.setShowFPS(true);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		super.init(container, game);
		player = new StarlightGlimmer(0, 300);
		background = new Image("textures/locations/test.png");
		camera = new MovingAreaCamera(player, new Rectangle(0, 0, 1024, 480), container.getWidth()/3 * 2, container.getHeight());
		ui = new GameUI(container);
		add(player, GAME);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		camera.draw(g);
		background.draw();
		super.render(container, game, g);
		g.drawString("Press ESC to exit to main menu", 0, 460);
		ui.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)) game.enterState(Launcher.MENU);
		player.update(container, delta);
		camera.update(container);
	}

	@Override
	public int getID() {
		return 3;
	}
	
	public FirstLocation(int id) {
		super(id);
	}
}
