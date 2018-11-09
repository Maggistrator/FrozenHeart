package scienes.playable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import scienes.Launcher;

public class Sciene1 extends BasicGameState {
	int timer = 0;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

	}

	String text = "Exai nahoi";
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString(text, 12, 120);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		timer++;
		if(timer == 1000)text = "sasi";
		if(timer == 1500) game.enterState(Launcher.MENU);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}
	

}
