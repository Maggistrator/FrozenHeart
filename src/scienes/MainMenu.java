package scienes;

import java.awt.Font;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.TrueTypeFont;
import core.ui.ActiveText;

public class MainMenu extends BasicGameState {

	ActiveText new_game;
	ActiveText about;
	ActiveText exit;

	int selected = 1;

	Sound switchSound;
	Sound approveSound;
	Sound btnSound;

	Music bgmusic;
	StateBasedGame game;
	
	public static Font font = new Font("Courier New", Font.PLAIN, 16);
    public static TrueTypeFont slicFont = new TrueTypeFont(font, true,("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
    
	// этот шрифт нужен дл¤ анимации выделени¤ пункта меню
	private static Font newfont = new Font("Courier New", Font.BOLD, 18);
	private static TrueTypeFont activeSlicFont = new TrueTypeFont(newfont, true,
			("йцукенгшщзхъфывапролджэ¤чсмитьбюЄ".toUpperCase() + "йцукенгшщзхъфывапролджэ¤чсмитьбюЄ").toCharArray());

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		int button_x = 20, button_y = 20;
		new_game = new ActiveText("Новая игра", button_x, button_y, 200, 200);
		about = new ActiveText("Об Авторе", button_x, button_y + 40, 200, 200);
		exit = new ActiveText("Выход", button_x, button_y + 80, 120, 120);
		
//		approveSound = new Sound("data/sound/confirm.ogg");
//		btnSound = new Sound("data/sound/button.ogg");
//		bgmusic = new Music("data/music/Ruins.ogg");
		container.setMusicVolume(0.5f);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		setCharset_Russian(g);

		if (selected == 1) g.setFont(activeSlicFont);
		else g.setFont(slicFont);
		new_game.draw(g);
		
		if (selected == 2) g.setFont(activeSlicFont);
		else g.setFont(slicFont);
		about.draw(g);
		
		if (selected == 3) g.setFont(activeSlicFont);
		else g.setFont(slicFont);
		exit.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		int previousSelectedItem = selected;
		
		if(new_game.hitbox.contains(newx, newy)) selected = 1;
		if(about.hitbox.contains(newx, newy)) selected = 3;
		if(exit.hitbox.contains(newx, newy)) selected = 4;
		
		if(selected != previousSelectedItem) btnSound.play();
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		if(new_game.hitbox.contains(x, y)) {
			//новая игра
			approveSound.play();
		}
		if(about.hitbox.contains(x, y)) {	
			//об авторе
			approveSound.play();
		}
		if(exit.hitbox.contains(x, y)) 
			//выход
			System.exit(0);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP && selected > 1) {
			selected--;
			btnSound.play();
		}
		if (key == Input.KEY_DOWN && selected < 4) {
			selected++;
			btnSound.play();
		}
		if (key == Input.KEY_ENTER) {
			switch (selected) {
			case 1:
				//"начать игру"
				break;
			case 2:
				//это для "about"
				break;
			case 4:
				//выход
				System.exit(0);
				break;
			}
			approveSound.play();
		}
	}

	
	private void setCharset_Russian(Graphics g) {
		if(!g.getFont().equals(slicFont)) {
			g.setFont(slicFont);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
