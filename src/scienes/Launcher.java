package scienes;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Launcher extends StateBasedGame{
	
	public static final int MENU = 0;

		public Launcher(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

		@Override
		public void initStatesList(GameContainer container) throws SlickException {
			addState(new MainMenu());
			enterState(MENU);
		}

		public static void main(String[] args) {
			AppGameContainer apploader;
			try {
				apploader = new AppGameContainer(new Launcher("Frozen Heart"));
				apploader.setDisplayMode(640, 480, false);
				apploader.setTargetFrameRate(30);
				apploader.start();
			} catch (SlickException e1) {
				System.out.println("Ошибка движка Slick2D. Попробуйте обновить драйверы видеокарты,"
						+ "\nили переустановить игру");
				e1.printStackTrace();
			}				
		}
	}

