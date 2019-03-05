package scienes;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.marteEngine.ME;
import logic.pony.StarlightGlimmer;
import scienes.playable.LastState;
import scienes.playable.FirstLocation;
import scienes.transfer.About;
import scienes.transfer.MainMenu;

public class Launcher extends StateBasedGame{
	
	public static final int MENU = 0;
	public static final int ABOUT = 2;
	public static final int SCIENE_1 = 3;
	public static final int FINISH = 4;

		public Launcher(String name) {
		super(name);
	}

		@Override
		public void initStatesList(GameContainer container) throws SlickException {
			StarlightGlimmer pony = new StarlightGlimmer(5, 280);
			
			addState(new MainMenu());
			addState(new About());
			addState(new FirstLocation(SCIENE_1, pony));
			addState(new LastState(FINISH, pony));
			
			ME.debugEnabled = true;
			enterState(MENU);
		}

		public static void main(String[] args) {
			AppGameContainer apploader;
			try {
				apploader = new AppGameContainer(new Launcher("Frozen Heart"));
				apploader.setDisplayMode(640, 480, false);
				apploader.setAlwaysRender(true);
				apploader.setMultiSample(2);
				apploader.setSmoothDeltas(true);
				apploader.setVSync(true);
				apploader.setUpdateOnlyWhenVisible(true);
				apploader.start();
			} catch (SlickException e1) {
				System.out.println("Ошибка движка Slick2D. Попробуйте обновить драйверы видеокарты,"
						+ "\nили переустановить игру");
				e1.printStackTrace();
			}				
		}
	}

