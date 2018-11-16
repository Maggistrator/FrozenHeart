package scienes.playable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entity.horse.StarlightGlimmer;
import it.marteEngine.World;
import scienes.Launcher;

public class Sciene1 extends World {
	public Sciene1(int id) {
		super(id);
	}

	StarlightGlimmer player;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.container = container;
		container.setTargetFrameRate(90);
		container.setShowFPS(true);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		player = new StarlightGlimmer(0, 300);
		add(player, GAME);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		player.render(container, g);
		g.drawString("Press ESC to exit to main menu", 0, 460);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)) game.enterState(Launcher.MENU);
		player.update(container, delta);
	}

	@Override
	public int getID() {
		return 3;
	}
	

}
