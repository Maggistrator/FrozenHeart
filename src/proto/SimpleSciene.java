package proto;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.ui.Button;
import scienes.Launcher;

public class SimpleSciene extends BasicGameState {

	boolean gameover = false;
	Button button;
	
	public SimpleSciene() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		button = new Button("", 12, 13);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		if(gameover) game.enterState(Launcher.MENU);//переход в state с номером 0 (константа)
	}

	@Override
	public int getID() {
		return 0;
	}

}
