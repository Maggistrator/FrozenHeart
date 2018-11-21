package scienes;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import scienes.playable.FirstLocation;
import scienes.transfer.About;

public class Launcher extends StateBasedGame{
	
	public static final int MENU = 0;
	public static final int ABOUT = 2;
	public static final int SCIENE_1 = 3;

		public Launcher(String name) {
		super(name);
	}

		@Override
		public void initStatesList(GameContainer container) throws SlickException {
			addState(new MainMenu());
			addState(new About());
			addState(new FirstLocation(3));
			enterState(MENU);
		}

		public static void main(String[] args) {
			AppGameContainer apploader;
			try {
				apploader = new AppGameContainer(new Launcher("Frozen Heart"));
				apploader.setDisplayMode(640, 480, false);
				apploader.start();
			} catch (SlickException e1) {
				System.out.println("Ошибка движка Slick2D. Попробуйте обновить драйверы видеокарты,"
						+ "\nили переустановить игру");
				e1.printStackTrace();
			}				
		}
	}

