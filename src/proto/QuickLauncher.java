package proto;
import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class QuickLauncher extends StateBasedGame {

	public QuickLauncher(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new SimpleSciene());
		enterState(0);
	}

	public static void main(String[] args) {
		AppGameContainer apploader;
		try {
			apploader = new AppGameContainer(new QuickLauncher("Test"));
			apploader.setDisplayMode(800, 600, false);
			apploader.setTargetFrameRate(60);
			apploader.start();
		} catch (SlickException e1) {
			JOptionPane.showMessageDialog(null, "Go fuck yourself :3");
			e1.printStackTrace();
		}				
	}
}
