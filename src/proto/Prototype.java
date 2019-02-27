package proto;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.marteEngine.World;
import logic.entity.EvilSnowman;
import logic.entity.StarlightGlimmer;

public class Prototype extends World {

	StarlightGlimmer pony;
	EvilSnowman snowman;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		super.init(container, game);
		pony = new StarlightGlimmer(10, 10);
		snowman = new EvilSnowman(120, 60, pony);
		add(pony, GAME);
		add(snowman, GAME);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		pony.update(container, delta);
		snowman.update(container, delta);
	}

	@Override
	public int getID() {
		return 0;
	}	
	
	public Prototype(int id) {
		super(id);
	}
	
}
