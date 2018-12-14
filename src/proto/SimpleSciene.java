package proto;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Button;
import core.ui.GameUI;
import scienes.Launcher;

public class SimpleSciene extends BasicGameState {

	boolean gameover = false;
	Button button;
	GameUI ui;
	
	public SimpleSciene() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		button = new Button("", 12, 13);
		//ui = new GameUI(container, );
	
		//не поедет - NPE из-за UI
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		ui.draw(g);
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
